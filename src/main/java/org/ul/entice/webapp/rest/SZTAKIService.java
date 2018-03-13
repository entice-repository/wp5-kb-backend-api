package org.ul.entice.webapp.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmrepository.RepoGuardClientFunctionalities.MOEstart;
import com.vmrepository.RepoGuardClientFunctionalities.UploadVMI;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.SystemUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jclouds.javax.annotation.Nullable;
import org.json.JSONObject;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.entry.Quality;
import org.ul.entice.webapp.entry.RecipeBuild;
import org.ul.entice.webapp.entry.client.*;
import org.ul.entice.webapp.util.CommonUtils;
import org.ul.entice.webapp.util.FusekiUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

@Path("/sztaki/")
public class SZTAKIService implements ISZTAKIService, IService {
    private JSONService parent;
    private Map<String, List<String>> optimizerMap = new HashMap<>();


    public SZTAKIService(JSONService parent) {
        this.parent = parent;
    }

    // POST: start new optimization procedure
    @POST
    @Path("execute_optimizer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Map<String, String> executeOptimizer(final SZTAKIExecuteObj sztakiExecuteObj) {
        Map<String, String> resultMap = new HashMap<>();
        Quality quality = new Quality(UUID.randomUUID().toString(), 0, -1, 0,
                true, 0, false, true, 0, sztakiExecuteObj.getMaxIterationsNum(), 0, 0.0, 0, 0, (short) 1, new
                ArrayList<String>());
        try {
            Map<String, String> sztakiOptimizerResultMap = initOptimizationLifecycle(sztakiExecuteObj);
            if (sztakiOptimizerResultMap.get("id") != null) {
                quality.setJobID(sztakiOptimizerResultMap.get("id"));
                String insertStatement = FusekiUtils.generateInsertObjectStatement(quality);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();
                resultMap.put("optimization_job_id", quality.getId());
                resultMap.put("optimizer_id", sztakiOptimizerResultMap.get("id"));
            } else resultMap.put("error", sztakiOptimizerResultMap.get("error"));
            return resultMap;

        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
        }

        return resultMap;
    }

    private Map<String, String> initOptimizationLifecycle(SZTAKIExecuteObj sztakiExecuteObj) {
        Map<String, String> resultMap = new HashedMap();
        try {
            sztakiExecuteObj.setUploadCommand(
                    "curl -F \"file_upload=@%s\" \"parent_vmi_id=@" + sztakiExecuteObj.getKbImageId() + "\" " + AppContextListener.prop.getProperty("deploy.url") +
                            "/JerseyREST/rest/gui/optimized_vmi_upload");

            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(sztakiExecuteObj);

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.optimizer.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonInString));

            HttpResponse response = httpClient.execute(httppost);

            HttpEntity resEntity = response.getEntity();

            final String optimizerID = EntityUtils.toString(resEntity, "UTF-8");
            String resStr = optimizerID.toLowerCase();

            //dummy validator
            if (resStr.contains("missing") || resStr.contains("parameter"))
                resultMap.put("error", optimizerID);
            else
                resultMap.put("id", optimizerID);
        } catch (IOException | IndexOutOfBoundsException | NullPointerException | ParseException e) {
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            return resultMap;
        }
    }

    private List<String> getSZTAKIOpenNebulaImageList(String command, String user) {
        List<String> urosImageList = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(command);
            StringBuffer sb = new StringBuffer();

            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(user)) {
                    sb.append(line + "\n");
                    urosImageList.add(line);
                }
            }
            return urosImageList;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return urosImageList;
        } catch (IOException e) {
            e.printStackTrace();
            return urosImageList;
        }
    }

    // read json string from file
    private String readStringFromInputStream(InputStream inputStream) {
        try {
            StringWriter writer = new StringWriter();
//            IOUtils.copy(inputStream, writer, "UTF-8");
            inputStream.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // GET: get status of an optimization procedure
    @GET
    @Path("delete_optimization_job")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String deleteOptimizationJob(@QueryParam("entity_id") String entityID) {
        return "Optimization removed: " + FusekiUtils.deleteEntityById(entityID, Quality.class.getSimpleName());
    }

    // GET: get status of an optimization procedure
    @GET
    @Path("get_optimization_status")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getStatus(@QueryParam("optimizer_id") String optimizerID) {
        return getOptimizationStatusLocal(optimizerID);
    }

    private String getOptimizationStatusLocal(String optimizerID) {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.optimizer.url") + "/" + optimizerID);

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet httpGet = new HttpGet(builder.build());

            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    // GET: get status of an optimization procedure
    @GET
    @Path("get_optimization_status_list")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getOptimizationStatusList(@QueryParam("optimizer_id") final String optimizerID) {
        return optimizerMap.get(optimizerID) != null ? optimizerMap.get(optimizerID).toString() : null;
    }

    // PUT: stop optimization and save sub-optimal image
    @GET
    @Path("stop_optimization")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String stopOptimization(@QueryParam("optimizer_id") String optimizerID) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut(AppContextListener.prop.getProperty("sztaki.optimizer.url"));
            httpPut.addHeader("optimizer_id", optimizerID);

            HttpResponse response = httpClient.execute(httpPut);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }

    // DELETE: cancel optimization and drop intermediate results
    @GET
    @Path("cancel_optimization")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String cancelOptimization(@QueryParam("optimizer_id") String optimizerID) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete(AppContextListener.prop.getProperty("sztaki.optimizer.url"));
            httpDelete.addHeader("optimizer_id", optimizerID);

            HttpResponse response = httpClient.execute(httpDelete);
            HttpEntity resEntity = response.getEntity();

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }

    @POST
    @Path("execute_image_build")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String executeImageBuild(MyJsonObject jsonObject) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(jsonObject);
            System.out.println(jsonInString);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.builder.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonInString));
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                String resultStr = EntityUtils.toString(resEntity, "UTF-8");
                JSONObject resultJson = new JSONObject(resultStr);
                String recipeID = "";
                try {
                    recipeID = ((JSONObject) resultJson.get("result")).get("request_id").toString();
                } catch (Exception e) {
                }
                RecipeBuild recipeBuild = new RecipeBuild(UUID.randomUUID().toString(), recipeID, resultJson.get
                        ("status").toString(), resultJson.get("message").toString(), "", 0, "");
                String insertStatement = FusekiUtils.generateInsertObjectStatement(recipeBuild);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();

                return resultStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }


    @POST
    @Path("launch_virtual_image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> launchVirtualImage(LaunchVMIObj jsonObject) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            HttpResponse httpResponse = executeSztakiPostRequest(AppContextListener.prop.getProperty("sztaki.launcher.url"), jsonObject);
            HttpEntity resEntity = httpResponse.getEntity();

            if (resEntity != null) {
                final String resp = EntityUtils.toString(resEntity, "UTF-8");
                if (httpResponse.getStatusLine().getStatusCode() == 200)
                    resultMap.put("message", resp);
                else
                    resultMap.put("error", resp);

            } else resultMap.put("error", "Empty response");
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", e.getMessage());
        } finally {
            return resultMap;
        }
    }

    @POST
    @Path("create_virtual_image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createVirtualImage(CreateVirtualImageObj jsonObject) {
        Map<String, String> resultMap = new HashMap<String, String>();

        if (jsonObject.getKnowledgeBaseRef() == null || jsonObject.getKnowledgeBaseRef().length() == 0)
            jsonObject.setKnowledgeBaseRef(UUID.randomUUID().toString());

        try {
            HttpEntity resEntity =
                    executeSztakiPostRequest(AppContextListener.prop.getProperty("sztaki.virtual.images.url"), jsonObject).getEntity();

            try {
                MOEstart.generateParetoForFragments(3, AppContextListener.prop.getProperty("uibk.distribution.url"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (resEntity != null) resultMap.put("message", EntityUtils.toString(resEntity, "UTF-8"));
            else resultMap.put("error", "Empty response");
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", e.getMessage());
        } finally {
            return resultMap;
        }
    }

    private HttpResponse executeSztakiPostRequest(String url, Object jsonObject) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(jsonObject);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader("Token", "entice");
            httppost.setEntity(new StringEntity(jsonInString));
            //System.out.println("executing request " + httppost.getRequestLine());
            return httpClient.execute(httppost);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("get_recipe_builds")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<RecipeBuild> getRecipeBuilds() {
        try {
            return FusekiUtils.getAllEntityAttributes(RecipeBuild.class, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<RecipeBuild> globalRecipeList = new ArrayList<>();
    private long recipeLastRefreshTime = 0;

    @GET
    @Path("get_recipe_refreshed_builds")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<RecipeBuild> getRecipeRefreshedBuilds() {
        if (recipeLastRefreshTime > 0) {
            if ((System.currentTimeMillis() - recipeLastRefreshTime) <= 10000) {
                // System.out.println("not refreshed..");
                return globalRecipeList;
            }
            recipeLastRefreshTime = System.currentTimeMillis();
        } else recipeLastRefreshTime = System.currentTimeMillis();

        try {
            List<RecipeBuild> recipeBuildList = FusekiUtils.getAllEntityAttributes(RecipeBuild.class, this);
            List<RecipeBuild> sztakiRecipeStatusObjs = new ArrayList<>();
            for (RecipeBuild recipeBuild : recipeBuildList) {
                if (recipeBuild.getRequest_status().equals("finished")) {
                    sztakiRecipeStatusObjs.add(recipeBuild);
                    continue;
                }

                RecipeBuild statusObj = new RecipeBuild(recipeBuild.getId());
                sztakiRecipeStatusObjs.add(statusObj);

                if (recipeBuild.getJobId().length() > 0) {
                    String result = getLocalBuilderStatus(recipeBuild.getJobId(), false);
                    JSONObject resultJson = new JSONObject(result);
                    try {
                        JSONObject subResult = (JSONObject) resultJson.get("result");
                        statusObj.setJobId(subResult.getString("request_id"));
                        statusObj.setRequest_status(subResult.getString("request_status"));
                        statusObj.setOutcome(subResult.getString("outcome:"));
                        statusObj.setMessage(resultJson.getString("message"));

                        if (statusObj.getRequest_status().equals("finished") && statusObj.getOutcome().equals
                                ("success")) {
                            String builderResult = getLocalBuilderStatus(statusObj.getJobId(), true);
                            JSONObject builderResultJson = new JSONObject(builderResult);
                            JSONObject imageResResObj = ((JSONObject) ((JSONObject) builderResultJson.get("result"))
                                    .get("image"));
                            statusObj.setUrl(imageResResObj.getString("url"));
                            statusObj.setSize(CommonUtils.getFileSize(statusObj.getUrl()));

                            boolean successRecipeManipulation = deleteRecipeAndCreateANewOne(recipeBuild.getId(),
                                    statusObj);
                            if (!successRecipeManipulation) System.out.println("error while recipe modification!");
                        } else if (statusObj.getRequest_status().equals("finished")) {
                            boolean successRecipeManipulation = deleteRecipeAndCreateANewOne(recipeBuild.getId(),
                                    statusObj);
                            if (!successRecipeManipulation) System.out.println("error while recipe modification!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //System.out.println(">> refreshed..");
            globalRecipeList = sztakiRecipeStatusObjs;
            return sztakiRecipeStatusObjs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean deleteRecipeAndCreateANewOne(String recipeBuildID, RecipeBuild recipeBuild) {
        try {
            boolean successDeleteFromKB = FusekiUtils.deleteEntityById(recipeBuildID, RecipeBuild.class.getSimpleName());

            if (successDeleteFromKB) {
                String insertStatement = FusekiUtils.generateInsertObjectStatement(recipeBuild);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @GET
    @Path("get_optimization_list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Quality> getOptimizationList() {
        try {
            return FusekiUtils.getAllEntityAttributes(Quality.class, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<SZTAKIOptimizationStatusObj> globalOptimizationList = new ArrayList<>();
    private long lastOptimizationTime = 0;
    private boolean optimizationExecuted = false;

    @GET
    @Path("get_optimization_refreshed_list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<SZTAKIOptimizationStatusObj> getOptimizationRefreshedList(@QueryParam("force_refresh") boolean forceRefresh) {
        if (forceRefresh || lastOptimizationTime > 0) {
            if (optimizationExecuted || (System.currentTimeMillis() - lastOptimizationTime) <= 4000) {
                return globalOptimizationList;
            }
            optimizationExecuted = true;
            lastOptimizationTime = System.currentTimeMillis();
        } else lastOptimizationTime = System.currentTimeMillis();

        try {
            // 1. check current optimization jobs in KB
            List<Quality> qualityList = FusekiUtils.getAllEntityAttributes(Quality.class, this);

            List<SZTAKIOptimizationStatusObj> sosoList = new ArrayList<>();
            for (Quality quality : qualityList) {
                if (quality.getJobID() != null && quality.getJobID().length() > 0) {

                    // 2. finished (done, error) jobs can be just passed and displayed
                    if (quality.getStatus() != null &&
                            (quality.getStatus().equals("DONE") || quality.getStatus().equals("ABORTED") || quality.getStatus().equals("FAILED"))) {
                        sosoList.add(new SZTAKIOptimizationStatusObj(quality.getJobID(), quality.getStarted(),
                                quality.getStatus(), quality.getOptimizerPhase(), quality.getOriginalImageSize(),
                                quality.getOptimizedImageSize(), quality.getFailure()));
                    }
                    // 3. not running jobs must be queries from SZTAKI optimization service
                    else {
                        SZTAKIOptimizationStatusObj so = new SZTAKIOptimizationStatusObj(quality.getJobID());
                        sosoList.add(so);


                        // invoke SZTAKI service to get latest information about optimization
                        String result = getOptimizationStatusLocal(quality.getJobID());
                        try {
                            // if the optimization does not exist any more, delete it from KB
                            if (result == null) {
                                FusekiUtils.deleteEntityById(quality.getId(), Quality.class.getSimpleName());
                                continue;
                            }
                            JSONObject jsonObject = new JSONObject(result);

                            so.setOptimizerPhase(jsonObject.getString("optimizerPhase"));
                            so.setAimedReductionRatio(jsonObject.getDouble("aimedReductionRatio"));
                            so.setMaxRunningTime(jsonObject.getInt("maxRunningTime"));
                            so.setNumberOfVMsStarted(jsonObject.getInt("numberOfVMsStarted"));
                            so.setOptimizedImageURL(jsonObject.getString("optimizedImageURL"));
                            so.setStarted(jsonObject.getLong("started"));
                            so.setRunningTime(jsonObject.getLong("runningTime"));
                            so.setOptimizedUsedSpace(jsonObject.getLong("optimizedUsedSpace"));
                            so.setOriginalImageSize(jsonObject.getLong("originalImageSize"));
                            so.setMaxNumberOfVMs(jsonObject.getInt("maxNumberOfVMs"));
                            so.setRemovables(jsonObject.getString("removables"));
                            so.setOptimizerVMStatus(jsonObject.getString("optimizerVMStatus"));
                            so.setOriginalUsedSpace(jsonObject.getLong("originalUsedSpace"));
                            so.setFailure(jsonObject.getString("failure"));
                            so.setOptimizedImageSize(jsonObject.getLong("optimizedImageSize"));
                            so.setAimedSize(jsonObject.getInt("aimedSize"));
                            so.setEnded(jsonObject.getLong("ended"));
                            so.setIteration(jsonObject.getInt("iteration"));
                            so.setShrinkerPhase(jsonObject.getString("shrinkerPhase"));
                            so.setChart(jsonObject.get("chart").toString());
                            so.setMaxIterationsNum(jsonObject.getInt("maxIterationsNum"));
                            so.setStatus(jsonObject.getString("status"));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 4. fill new data into Quality entity and store it into KB
                        if (so.getStatus() != null &&
                                (so.getStatus().equals("DONE") || so.getStatus().equals("ABORTED") || so.getStatus().equals("FAILED"))) {
                            String insertStatement = FusekiUtils.generateInsertObjectStatement(
                                    new Quality(UUID.randomUUID().toString(), so.getStarted(), so.getStatus(),
                                            so.getOptimizerPhase(), so.getOriginalImageSize(),
                                            so.getOptimizedImageSize(), so.getFailure(), so.getId()));

                            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                                    AppContextListener.prop.getProperty("fuseki.url.update"));
                            upp.execute();

                            //delete old Quality entities jobs
                            FusekiUtils.deleteEntityById(quality.getId(), Quality.class.getSimpleName());
                        }
                    }

                }
            }
            Collections.sort(sosoList, new SZTAKIOptimizationStatusObjComparator());
            globalOptimizationList = sosoList;
            return sosoList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            optimizationExecuted = false;
        }
        return null;
    }


    @GET
    @Path("get_builder_status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getImageBuilderStatus(@QueryParam("builder_id") String builderID, @QueryParam("show_result") boolean showResult) {
        return getLocalBuilderStatus(builderID, showResult);
    }

    private String getLocalBuilderStatus(String builderID, boolean showResult) {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.builder.url") + "/" +
                    builderID + (showResult ? "/result" : ""));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("get_sztaki_virtual_images")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getSZTAKIVirtualImages() {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.virtual.images.url"));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GET
    @Path("get_sztaki_statistic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getSZTAKIStatistics() {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.statistics.url"));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GET
    @Path("get_sztaki_base_image_details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getBaseImageDetails(@QueryParam("base_image_id") String baseImageId) {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.base.images.details.url") + baseImageId);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("get_sztaki_installers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getSZTAKIInstallers() {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.installers.url"));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param fileInputStream
     * @param contentDispositionHeader
     * @param diskImageId
     * @return May return any storage site, where this fragment is available.
     */
    @POST
    @Path("store_fragment")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseObj storeFragment(@Nullable @FormDataParam("file_upload") InputStream fileInputStream, @Nullable
    @FormDataParam("file_upload") FormDataContentDisposition contentDispositionHeader, @FormDataParam("disk_image_id") String diskImageId) {
       // save fragment to the server
        String filePath = (SystemUtils.IS_OS_LINUX ? AppContextListener.prop.getProperty("kb.api.upload.location.linux") :
                AppContextListener.prop.getProperty("kb.api.upload.location.windows")) + contentDispositionHeader.getFileName();
        CommonUtils.saveFile(fileInputStream, filePath);

        String successMessage = null;
        //upload fragment in the repository
        try {
            successMessage = UploadVMI.performUpload(filePath, 1);
        } catch (Exception e) {
            return new ResponseObj(401, "failed to upload from url: " + e.getMessage());
        }

        return new ResponseObj(200, "Fragment attached to the VMI.");
    }

    @POST
    @Path("register_base_image")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> registerBaseImage(RegisterBaseImageObj regObj) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(regObj);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.base.images.details.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader("Token", "entice");
            httppost.setEntity(new StringEntity(jsonInString));

            HttpResponse response = httpClient.execute(httppost);
            resultMap.put("code", "" + response.getStatusLine().getStatusCode());

            HttpEntity resEntity = response.getEntity();

            resultMap.put("message", EntityUtils.toString(resEntity, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error", e.getMessage());
        }
        return resultMap;
    }

    @GET
    @Path("delete_builder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Map<String, String> stopImageBuilder(@QueryParam("builder_id") String recipeBuilderID, @QueryParam("cancel_execution") boolean cancelExecution) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            boolean successDeleteFromKB = false;
            List<RecipeBuild> recipeBuildList = FusekiUtils.getAllEntityAttributes(RecipeBuild.class, this, recipeBuilderID);
            if (recipeBuildList.size() == 0) {
                resultMap.put("error", "RecipeBuild object does not exist!");
                return resultMap;
            }

            try {
                String kbBuilderID = recipeBuildList.get(0).getId();
                successDeleteFromKB = FusekiUtils.deleteEntityById(kbBuilderID, RecipeBuild.class.getSimpleName());
                resultMap.put("kb_deletion", String.valueOf(successDeleteFromKB));
            } catch (Exception e) {
                resultMap.put("error", "ID does not exist in Knowledge base");
                return resultMap;
            }

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete(AppContextListener.prop.getProperty("sztaki.builder.url") + "/" +
                    recipeBuildList.get(0).getJobId() + (cancelExecution ? "" : "/result"));
            httpDelete.addHeader("optimizer_id", recipeBuildList.get(0).getJobId());
            HttpResponse response = httpClient.execute(httpDelete);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            resultMap.put("sztaki_recipe_deletion", result.toString());
            recipeLastRefreshTime = 0;
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", e.getMessage());
            return resultMap;
        }
    }

    @GET
    @Path("delete_sztaki_vmi")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteSztakiVmi(@QueryParam("vim_id") String vimId) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete(
                    AppContextListener.prop.getProperty("sztaki.virtual.images.url") + "/" + vimId);
            httpDelete.addHeader("Owner", "admin");
            httpDelete.addHeader("Token", "entice-manager");
            HttpResponse response = httpClient.execute(httpDelete);

            if (response.getStatusLine().getStatusCode() != 200)
                resultMap.put("error", response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            resultMap.put("result", result.toString());
            recipeLastRefreshTime = 0;
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", e.getMessage());
            return resultMap;
        }
    }

    @Override
    public String showLast10ValuesofOptimization(@QueryParam("optimizer_id") String optimizerID) {
        return null;
    }

    @Override
    public String getFusekiQuery() {
        return AppContextListener.prop.getProperty("fuseki.url.query");
    }
}

