package org.ul.entice.webapp.rest;

import com.vmrepository.RepoGuardClientFunctionalities.MOEstart;
import com.vmrepository.RepoGuardClientFunctionalities.UploadOptimizedVMI;
import com.vmrepository.RepoGuardClientFunctionalities.UploadVMI;
import com.vmrepository.repoguardwebserver.FileNotFoundException_Exception;
import com.vmrepository.repoguardwebserver.IOException_Exception;
import com.vmrepository.repoguardwebserver.URISyntaxException_Exception;
import org.apache.commons.lang3.SystemUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.core.ResultBinding;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jclouds.javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.entry.*;
import org.ul.entice.webapp.entry.client.EnticeDetailedImage;
import org.ul.entice.webapp.entry.client.EnticeImage;
import org.ul.entice.webapp.entry.client.ResponseObj;
import org.ul.entice.webapp.entry.client.ResponseSynthesisObj;
import org.ul.entice.webapp.util.CommonUtils;
import org.ul.entice.webapp.util.FusekiUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;

@Path("/gui/")
public class GUIService implements IGUIService, IService {

    JSONService parent;

    public GUIService(JSONService parent) {
        this.parent = parent;
    }

    @GET
    @Path("perform_user_login")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Map<String, String> performUserLogin(@QueryParam("username") String username, @QueryParam("password")
            String password) {
        boolean matched = false;
        try {
            long startTime = System.currentTimeMillis();
            String query = FusekiUtils.performUserLogin(username, password);

            //Query the collection, dump output
            QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url"
                    + ".query"), query);
            ResultSet results = qe.execSelect();
            String id = null;
            String groupId = null;
            while (results.hasNext()) {
                QuerySolution qs = results.next();
                Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();

                while (varIter.hasNext()) {
                    Var var = varIter.next();

                    if (var.getVarName().equals("s")) id = ((ResultBinding) qs).getBinding().get(var).toString();
                    if (var.getVarName().equals("privilege"))
                        groupId = ((ResultBinding) qs).getBinding().get(var).toString();
                }
            }
            qe.close();

            final boolean loginSuccess = id != null;

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("success", String.valueOf(loginSuccess));
            if (loginSuccess) resultMap.put("id", id.replaceFirst(FusekiUtils.KB_PREFIX_SHORT, ""));
            if (groupId != null) resultMap.put("group", groupId.replaceAll("\"", ""));

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("perform_user_logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String performUserLogout(@QueryParam("session_id") String sessionID) {
        return null;
    }

    // priority: low
    @GET
    @Path("register_user")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response registerUser(@QueryParam("user_object") User user) {
        return null;
    }


    @GET
    @Path("get_pareto_stage1")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Pareto> getParetoStage1() {
        return null;
    }

    @GET
    @Path("get_pareto_stage2")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Pareto getParetoStage2() {
        return null;
    }

    @GET
    @Path("get_pareto_stage3")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Pareto getParetoStage3() {
        return null;
    }

    @POST
    @Path("store_pareto_stage3")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean storeParetoStage3(Pareto pareto) {
        try {
            pareto.setSaveTime(DateTime.now().getMillis());

            String insertStatement = FusekiUtils.generateInsertObjectStatement(pareto);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                    AppContextListener.prop.getProperty("fuseki.url.update"));
            upp.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @GET
    @Path("get_newest_pareto")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Pareto getNewestPareto(@QueryParam("stage") int stage) {
        boolean generatePareto = true;
        try {

            if (stage == 2) {
                String id = FusekiUtils.getEntityID(Pareto.class, " . ?s knowledgebase:Pareto_Stage 2 . ?s " +
                        "knowledgebase:Pareto_Create_Date ?dateFrom ", "ORDER BY DESC(xsd:dateTime(?dateFrom)) LIMIT " +
                        "1");
                return FusekiUtils.getAllEntityAttributes(Pareto.class, this, id).get(0);
            } else {
                //currently disabled generate pareto
                if (generatePareto == false)
                    generatePareto = MOEstart.generateParetoStage1("stageI","", AppContextListener.prop.getProperty("uibk.distribution.url"));

                String id = FusekiUtils.getEntityID(Pareto.class, " . ?s knowledgebase:Pareto_Stage " + (stage <= 0 ? 1 : stage) + " . ?s " +
                        "knowledgebase:Pareto_Create_Date ?dateFrom ", "ORDER BY DESC(xsd:dateTime(?dateFrom)) LIMIT " +
                        "1");
                return FusekiUtils.getAllEntityAttributes(Pareto.class, this, id).get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException_Exception e) {
            e.printStackTrace();
            return null;
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Image upload test form:
     * http://localhost:8080/JerseyREST/upload_image.html
     */
    @POST
    @Path("upload_image")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public ResponseObj uploadImage(@Nullable @FormDataParam("file_upload") InputStream fileInputStream, @Nullable
    @FormDataParam("file_upload") FormDataContentDisposition contentDispositionHeader,
                                   @FormDataParam("functional_tests") InputStream functionalTestInputStream,
                                   @FormDataParam("functional_tests") FormDataContentDisposition functionalTestDispositionHeader,
                                   @FormDataParam("ovf_file") InputStream ovfInputStream,
                                   @FormDataParam("ovf_file") FormDataContentDisposition ovfDispositionHeader,
                                   @Nullable @FormDataParam("category_list")
                                           List<String> categoryList, @FormDataParam("image_url") String imageURL, @FormDataParam("image_description")
                                           String imageDescription, @FormDataParam("image_name") String imageName, @FormDataParam("pareto_point") int
                                           paretoPoint, @FormDataParam("pareto_point_id") String paretoPointId, @Nullable @FormDataParam
            ("avatar_id") int avatarID, @FormDataParam("user_id") String userID) {
        try {
            if (imageDescription == null || imageDescription.length() == 0)
                return new ResponseObj(204, "image description is not set! image_description");
            else if (imageName == null || imageName.length() == 0)
                return new ResponseObj(204, "image name is not set! image_name");
            else if (paretoPoint < 0) return new ResponseObj(204, "pareto point is not set! pareto_point");
            else if (paretoPointId == null || paretoPointId.length() == 0)
                return new ResponseObj(204, "pareto ID is not set!\npareto_point_id");
            else if (userID == null || userID.length() == 0)
                return new ResponseObj(204, "user ID is not set!\nuser_id");
            final boolean uploadFromURL = imageURL != null && imageURL.length() > 0 && !imageURL.equals("null");
            String filePath = (SystemUtils.IS_OS_LINUX ? AppContextListener.prop.getProperty("kb.api.upload.location.linux") :
                    AppContextListener.prop.getProperty("kb.api.upload.location.windows")) + contentDispositionHeader.getFileName();

            // save the file to the server
            if (!uploadFromURL) CommonUtils.saveFile(fileInputStream, filePath);

            String successMessage = null;
            //upload VMI in the repository
            try {
                successMessage = uploadFromURL ? UploadOptimizedVMI.executeUpload(imageURL, paretoPoint) : UploadVMI
                        .performUpload(filePath, paretoPoint);
            } catch (Exception e) {
                return new ResponseObj(111, "failed to upload from url: " + e.getMessage());
            }
            String functionalityID = "";

            String ovfFileUrl = null;
            // if OVF file is set
            if (ovfDispositionHeader != null && ovfDispositionHeader.getFileName().length() > 0) {
                String ovfFilePath = (SystemUtils.IS_OS_LINUX ? AppContextListener.prop.getProperty("kb.api.upload.location.linux") :
                        AppContextListener.prop.getProperty("kb.api.upload.location.windows")) + ovfDispositionHeader.getFileName();

                CommonUtils.saveFile(ovfInputStream, ovfFilePath);

                File file = new File(ovfFilePath);

                //upload ovfFile test in the repository
                String succcessOvfMessage = UploadVMI.performUpload(ovfFilePath, paretoPoint);

                // of the functional test when Nishant fixes JSON (url is currently not a string!)
                // TODO: fix UIBK message parsing when string will be fixed and parsable to JSON
                String[] split = succcessOvfMessage.split(",");
                Functionality functionality = new Functionality(null, 0, "tag",
                        null, "some description", split[3].substring(split[3]
                        .indexOf(":") + 1), "output description", null, "entice");
                ovfFileUrl = functionality.getInputDescription();
                file.delete();
            }

            // if functionality test is selected
            if (functionalTestDispositionHeader != null && functionalTestDispositionHeader.getFileName().length() > 0) {
                String functionalTestFilePath = (SystemUtils.IS_OS_LINUX ? AppContextListener.prop.getProperty("kb.api.upload.location.linux") :
                        AppContextListener.prop.getProperty("kb.api.upload.location.windows")) + functionalTestDispositionHeader
                        .getFileName();

                CommonUtils.saveFile(functionalTestInputStream, functionalTestFilePath);

                File file = new File(functionalTestFilePath);

                //upload functional test in the repository
                String successFunctionalTestMessage = UploadVMI.performUpload(functionalTestFilePath, paretoPoint);
                functionalityID = UUID.randomUUID().toString();

                String[] split = successFunctionalTestMessage.split(",");
                Functionality functionality = new Functionality(functionalityID, 0, "tag",
                        functionalTestDispositionHeader.getName(), "some description", split[3].substring(split[3]
                        .indexOf(":") + 1), "output description", null, "entice");
                String insertStatement = FusekiUtils.generateInsertObjectStatement(functionality);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();
                file.delete();
            }

            if (successMessage != null) {
                File file = null;

                String fileName = "";
                if (!uploadFromURL) file = new File(filePath);

                // create DiskImage entity
                DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.VMI, imageDescription,
                        imageName, "", FileFormat.IMG, "http://193.2.72.90/images/avatars/" + (avatarID != -1 ?
                        avatarID + "" : ""), false, successMessage.split(",")[3].split("\".")[2], "", 0, userID,
                        functionalityID, "", "", false, System.currentTimeMillis(), false, "1.0", uploadFromURL ?
                        CommonUtils.getFileSize(imageURL) : (int) (file.length() / 1024), paretoPoint, paretoPoint,
                        paretoPointId, categoryList, mapRepositoryIndexToRealOne(paretoPoint));

                if (ovfFileUrl != null)
                    diskImage.setOvfUrl(ovfFileUrl);

                String insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();
                if (file != null) file.delete();
                return new ResponseObj(200, "success upload" + successMessage);
            } else return new ResponseObj(404, "failed upload" + successMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj(404, "FAILED uploaded | " + e.getMessage());
        }
    }

    /**
     * VMI redistribution + Optimised VMI upload
     * <p>
     * Test form:
     * http://localhost:8080/JerseyREST/optimised_vmi_upload.html
     */
    @POST
    @Path("optimized_vmi_upload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseObj optimisedVMIUpload(@FormDataParam("file_upload") InputStream fileUploadIS,
                                          @FormDataParam("file_upload") FormDataContentDisposition fileUploadContentDispositionHeader,
                                          @QueryParam("parent_vmi_id") String parentVmiID) {
        try {
            String vmiFilePath = (SystemUtils.IS_OS_LINUX ? AppContextListener.prop.getProperty("kb.api.upload.location.linux") :
                    AppContextListener.prop.getProperty("kb.api.upload.location.windows")) + fileUploadContentDispositionHeader.getFileName();
            CommonUtils.saveFile(fileUploadIS, vmiFilePath);

            // Optimized VMI upload
            String uploadMessage = UploadVMI.performUpload(vmiFilePath, 1);
            String[] split = uploadMessage.split(",");
            File file = new File(vmiFilePath);
            int nodeId = Integer.valueOf(split[0].split(":")[1].replaceAll("\"", ""));

            String imageURL = split[3].split("vmiURI\":")[1];
            // Save optimized VMI into KB
            // create DiskImage entity
            final String optimizerServiceUserID = "8fe471ea-41d3-470a-8820-70f221f7c3cf";
            DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.VMI, "",
                    "OPTIMIZED-" + fileUploadContentDispositionHeader.getFileName(), parentVmiID, FileFormat.IMG, null, false, imageURL, "", 0, optimizerServiceUserID,
                    "", "", "", false, System.currentTimeMillis(), false, "1.0", (int) (file.length() / 1024), 0, 0,
                    0 + "", null, mapRepositoryIndexToRealOne(nodeId));

            String insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                    AppContextListener.prop.getProperty("fuseki.url.update"));
            upp.execute();

            return new ResponseObj(200, uploadMessage + " | Filename: OPTIMIZED-" + fileUploadContentDispositionHeader.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj(404, "FAILED uploaded on LPT VMI | stacktrace" + e.getMessage());
        }
    }

    private String mapRepositoryIndexToRealOne(int repositoryIndex) {
        List<Repository> repositoryList = FusekiUtils.getAllEntityAttributes(Repository.class, this);
        try {
            for (Repository repo : repositoryList) {
                if (repo.getGeolocation() != null && repositoryIndex == 1 && repo.getGeolocation().getCountryName().equals("Spain"))
                    return repo.getId();
                else if (repo.getGeolocation() != null && repositoryIndex == 2 && repo.getGeolocation().getCountryName().equals("Hungary"))
                    return repo.getId();
            }

            return repositoryList.get(repositoryIndex).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repositoryList.get(0).getId();
    }

    @POST
    @Path("create_vmi")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj createVMI(@FormDataParam("module_input_stream") InputStream moduleInputStream, @FormDataParam
            ("module_fcd") FormDataContentDisposition moduleFCD, @QueryParam("build_module") String buildModule,
                                 @QueryParam("version_module") String versionModule, @QueryParam("test_module")
                                         String testModule, @FormDataParam("test_input_stream") InputStream
                                         testInputStream, @FormDataParam("test_fcd") FormDataContentDisposition
                                         testFCD, @QueryParam("version_test") String versionTest, @QueryParam
                                         ("command_test") String commandTest) {
        return null;
    }

    @GET
    @Path("periodically_check_build_recipe")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj periodicallyCheckBuildRecipe(@QueryParam("job_id") String jobID) {
        return null;
    }


    @GET
    @Path("nishant")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNishant(@QueryParam("node_id") int nodeId) {
        try {
            return UploadOptimizedVMI.executeUpload("https://entice.lpds.sztaki" +
                    ".hu:5443/api/imagebuilder/build/99b5f56b-75ba-4a02-ad37-52ecfbeb1afa/result/image", (nodeId - 1));
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (FileNotFoundException_Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException_Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException_Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @POST
    @Path("perform_image_synthesis")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseSynthesisObj performImageSynthesis(@QueryParam("cloud") String cloud, @QueryParam("image_id")
            String imageId, @QueryParam("instance_type") String instanceType, @Nullable @QueryParam("security_group_id")
                                                              String securityGroupID, @Nullable @QueryParam("subnet_ids") String subnetIDs, @Nullable @QueryParam("ssh") String
                                                              ssh, @FormDataParam("contextualisation_stream") InputStream contextualisationStream, @FormDataParam
                                                              ("functional_test_stream") InputStream functionalTestStream, @QueryParam("constraints") int constraints,
                                                      @QueryParam("notify_email") String notifyEmail, @QueryParam
                                                              ("start_time") long startTime, @Nullable
                                                      @QueryParam("avatar_id") int avatarID) {
        return null;
    }

    @GET
    @Path("periodically_check_upload_state")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj periodicallyCheckUploadState(@QueryParam("job_id") String jobID) {
        return null;
    }

    @GET
    @Path("get_entice_images")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Object[] getEnticeImages(@QueryParam("image_title") String title, @QueryParam("category_id") String
            categoryId, @QueryParam("pagination_page") int paginationPage, @QueryParam("hits_per_page") int
                                            hitsPerPage, @QueryParam("order") String order, @QueryParam("sort") final boolean isDescendingSort) {
        //ugly arguments!
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, this, null, (title != null ? ". ?s" +
                " " +
                "knowledgebase:DiskImage_Title ?title . FILTER regex(?title, \"" + title + "\", \"i\") ." : "") +
                (categoryId != null ? ". ?s knowledgebase:DiskImage_Categories ?cat . FILTER regex(?cat, " +
                        "\"" + categoryId + "\", \"i\")" : ""));

        ArrayList<EnticeImage> enticeImages = new ArrayList<>();
        int maxSize = diskImages.size() > (paginationPage + 1) * hitsPerPage ? (paginationPage + 1) * hitsPerPage :
                diskImages.size();
        if (maxSize == 0) maxSize = diskImages.size();

        for (int i = paginationPage * hitsPerPage; i < maxSize; i++) {

            List<Functionality> functionalityList = FusekiUtils.getFunctionalityOfDiskImage(diskImages.get(i)
                    .getRefFunctionalityId(), this);
            if (functionalityList.size() > 0) diskImages.get(i).setFunctionalityList(functionalityList);

            String userFullName = "dummy full name";
            try {
                userFullName = FusekiUtils.getAllEntityAttributes(User.class, this, diskImages.get(i).getRefOwnerId()).get
                        (0).getFullName();
            } catch (Exception e) {
                //e.printStackTrace();
            }

            List<Repository> repositoryList = new ArrayList<>();
            if (diskImages.get(i).getRepository() != null) repositoryList.add(diskImages.get(i).getRepository());

            enticeImages.add(new EnticeDetailedImage(diskImages.get(i).getId(), diskImages.get(i).getPictureUrl(),
                    diskImages.get(i).getTitle(), diskImages.get(i).getRefOwnerId() == null ? "unknown user" :
                    diskImages.get(i).getRefOwnerId(), diskImages.get(i).getDiskImageSize(), 0, diskImages.get(i)
                    .getCategoryList(), 0, repositoryList, diskImages.get(i).getFunctionalityList(), null, diskImages
                    .get(i).getDescription(), userFullName, diskImages.get(i).getIri(), diskImages.get(i).getOvfUrl()));

            if (order != null && order.length() > 0) {
                if (order.equals("name")) Collections.sort(enticeImages, new Comparator<EnticeImage>() {
                    @Override
                    public int compare(EnticeImage o1, EnticeImage o2) {
                        String o1lower = o1.getImageName().toLowerCase();
                        String o2lower = o2.getImageName().toLowerCase();
                        return isDescendingSort ? o2lower.compareTo(o1lower) : o1lower.compareTo(o2lower);
                    }
                });
                else if (order.equals("size")) Collections.sort(enticeImages, new Comparator<EnticeImage>() {
                    @Override
                    public int compare(EnticeImage o1, EnticeImage o2) {
                        return isDescendingSort ? o2.getImageSize() - o1.getImageSize() : o1.getImageSize() - o2
                                .getImageSize();
                    }
                });

            }
        }

        return new Object[]{enticeImages, diskImages.size()};
    }

    @GET
    @Path("get_entice_detailed_images")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Deprecated
    public EnticeDetailedImage getEnticeDetailedImage(@QueryParam("id") String imageID) {
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, this, imageID);


        if (diskImages.size() == 0) return null;

        List<Fragment> fragmentList = FusekiUtils.getFragmentDataOfDiskImage(imageID, false, this);
        List<Functionality> functionalityList = FusekiUtils.getFunctionalityOfDiskImage(diskImages.get(0)
                .getRefFunctionalityId(), this);
        diskImages.get(0).setFunctionalityList(functionalityList);

        List<Repository> repositoriesList = FusekiUtils.getAllEntityAttributes(Repository.class, this);
        Set<String> setOfRepositoryIds = new HashSet<>();
        for (Fragment fragment : fragmentList) {
            setOfRepositoryIds.add(fragment.getRefRepositoryId());
        }

        List<Repository> matchingRepositories = new ArrayList<>();

        for (Repository repository : repositoriesList) {
            if (diskImages.get(0).getRepositoryID().equals(repository.getId())) matchingRepositories.add(repository);
        }

        String userFullName = "dummy full name";
        try {
            userFullName = FusekiUtils.getAllEntityAttributes(User.class, this, diskImages.get(0).getRefOwnerId()).get
                    (0).getFullName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EnticeDetailedImage(diskImages.get(0).getId(), diskImages.get(0).getPictureUrl(), diskImages.get
                (0).getTitle(), diskImages.get(0).getRefOwnerId() == null ? "unknown user" : diskImages.get(0)
                .getRefOwnerId(), diskImages.get(0).getDiskImageSize(), 0, diskImages.get(0).getCategoryList(), 0,
                matchingRepositories, diskImages.get(0).getFunctionalityList(), null, diskImages.get(0)
                .getDescription(), userFullName, diskImages.get(0).getIri(), diskImages.get(0).getOvfUrl());
    }

    private boolean listContainsId(Set<String> setOfRepositoryIds, String id) {
        for (String str : setOfRepositoryIds) {
            if (str.trim().contains(id)) return true;
        }
        return false;
    }

    @GET
    @Path("get_statistics_data")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getStatisticsData(@QueryParam("show_admin_data") boolean showAdminData, @QueryParam
            ("user_id") String userID) {
        Map<String, Integer> resultMap = new HashMap<>();

        if (userID != null)
            resultMap.put("number_of_user_images", FusekiUtils.getEntityCount(DiskImage.class.getSimpleName(), userID));
        else resultMap.put("number_of_all_images", FusekiUtils.getEntityCount(DiskImage.class.getSimpleName()));

        resultMap.put("number_of_all_repositories", FusekiUtils.getEntityCount(Repository.class.getSimpleName()));
        if (showAdminData) {
            resultMap.put("number_of_all_users", FusekiUtils.getEntityCount(User.class.getSimpleName()));
        }

        return resultMap;
    }

    @GET
    @Path("trigger_offline_redistribution")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> triggerOfflineRedistribution(@QueryParam("selected_point_index") int pointIndex) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            // get last insertion in the KB
            String id = FusekiUtils.getEntityID(Pareto.class, " . ?s knowledgebase:Pareto_Stage " + 2 + " . ?s " +
                    "knowledgebase:Pareto_Create_Date ?dateFrom ", "ORDER BY DESC(xsd:dateTime(?dateFrom)) LIMIT " +
                    "1");
            Pareto latestPareto = FusekiUtils.getAllEntityAttributes(Pareto.class, this, id).get(0);

            final Integer[] destinations = latestPareto.getVariables()[pointIndex];
            Fragment sourceData = FusekiUtils.getFragmentDataOfDiskImage(null, null, this).get(pointIndex);

            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (Integer destElem : destinations) {
                sb.append("\"" + destElem + "\",");
            }
            String destString = sb.substring(0, sb.length()-1);
            destString = destString + "]";

            final String[] uriSplited = sourceData.getAnyURI().split("/");
            // trigger new redistribution redistribution
            boolean newRedistributionSuccess = MOEstart.generateParetoStage1("stageII",
                    "[{\"sourceId\":\"" + sourceData.getId() + "\",\"vmiName\":\"" + uriSplited[uriSplited.length - 1] + "\"," +
                            "\"vmiURI\":\"" + sourceData.getAnyURI() + "\"," +
                            "\"destinationId\":" + destString + "}]",AppContextListener.prop.getProperty("uibk.distribution.url"));

            if (newRedistributionSuccess)
                resultMap.put("success", "The selected fragment was distributed among the following destinations: " + destString);
            else
                resultMap.put("error", "Redistribution error.");
        } catch (IOException e) {
            resultMap.put("success", Boolean.toString(false));
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException_Exception e) {
            resultMap.put("success", Boolean.toString(false));
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
        } catch (IOException_Exception e) {
            resultMap.put("success", Boolean.toString(false));
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            resultMap.put("success", Boolean.toString(false));
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException_Exception e) {
            resultMap.put("success", Boolean.toString(false));
            resultMap.put("error", e.getMessage());
            e.printStackTrace();
        } finally {
            return resultMap;
        }
    }


    @GET
    @Path("get_pareto_redistribution")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Redistribution> getParetoRedistribution() {
        try {
            return FusekiUtils.getAllEntityAttributes(Redistribution.class, this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @GET
    @Path("redistribute_user_image")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj redistributeUserImage(@QueryParam("image_id") String imageID, @QueryParam("pareto_point")
            String paretoPoint) {
        return null;
    }

    @GET
    @Path("periodically_check_redistribution")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj periodicallyCheckRedistribution(@QueryParam("job_id") String jobID) {
        return null;
    }

    @GET
    @Path("deploy_vmi_on_cloud")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String deployDiskImageOnTheCloud(@QueryParam("image_id") String imageID, @QueryParam("cloud_id") String
            cloudID, @QueryParam("cloud_access_key") String cloudAccesseKey, @QueryParam("cloud_secret_key") String
                                                    cloudSecretKey) {
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, this, imageID);
        if (diskImages.size() == 0) return "VMI with id \"" + imageID + "\" does not exist!";
        return null;
    }

    @Override
    public String getFusekiQuery() {
        return AppContextListener.prop.getProperty("fuseki.url.query");
    }
}
