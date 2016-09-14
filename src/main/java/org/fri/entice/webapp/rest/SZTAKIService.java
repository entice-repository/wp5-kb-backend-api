package org.fri.entice.webapp.rest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.fri.entice.webapp.entry.Quality;
import org.fri.entice.webapp.entry.client.MyJsonObject;
import org.fri.entice.webapp.entry.client.SZTAKIExecuteObj;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/sztaki/")
public class SZTAKIService implements ISZTAKIService {
    private JSONService parent;
    private Map<String,List<String>> optimizerMap = new HashMap<>();

    public SZTAKIService(JSONService parent) {
        this.parent = parent;
    }

    /*
    // POST: start new optimization procedure
    @Deprecated
    @POST
    @Path("execute_optimizer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Map<String, String> executeOptimizer(SZTAKIExecuteObj sztakiExecuteObj ) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, sztakiExecuteObj.getImageId());
//            String jsonContent = CommonUtils.readFile("D:\\projects\\lpt\\entice-ul-api\\internal_work\\input_test.json", StandardCharsets.UTF_8);
//            sztakiExecuteObj = new SZTAKIExecuteObj("ami-00001483",
//                    "http://s3.lpds.sztaki.hu/atisu/entice/wp3/wordpress-centos7.0-20160627a.qcow",
//                    "https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.sh",
//                    "root",
//                    "vg0 root",
//                    "ahajnal_keypair",
//                    "LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQ0KTUlJRXBBSUJBQUtDQVFFQXpzY0xpcmpGNUlibEgwNWljUVFZa2VWRzdCeWFWcVhuVXZQSDcwOHV2OHV0Rk1nOA0KcTNmZ09xa2YvUkgvMUIwSHZjZkdEa2x6NkhRVksrWGgwN1J1Ykh6M0x2by9SMm5MVmI3YlJnY3oxYjEwR1I3Nw0KdWJGTEZ6Y3hsUFpKR3Z4OUxsYVd5UTUwL2VlU3R5VHQ3bGV3UWJacWtRZ0R4b2pYMm1iS3VrOGRscXRYeDZhZw0KU01hK2pUOGtXZWp4dnlGNDJqWVRNSk9DZ0FjYWxrdnpqYnNpRUxrWE5Xb0pvd3JQM1V3YUR1OHRFaW91UTY0dw0Kc2pJQnBSNmp5eHlqZW9uRVJGeU9mL2M3WkMvaW9nOU50cWRmcUo4YXVKLzRMYm8xYUpiemw1dkJCeTBnZ3BzVA0KejM3eGQzTWp5UEtNZm1SQXEvOEQ1TkJ4SUtodGZYU0FMZEIrTFFJREFRQUJBb0lCQUdEcWdDbGowelQ2V05law0KaUpWS0F5NFdsWGhESzcrakFOb3JjckZpbnBtOG9BSVduQUVPTGFXdzhWSlBKbVpIdVFJbGFWbjI2WUd4THQ4bA0KWHdRNEZHMTY0T3crMUh2blJTdUtTZ0gzakQ4SkRpcGNFRVlIcUJkWWdqKzhjNlZYWkdEY3FzM1BuZHdIdHdkcQ0KSXE3TW9Nc2I4YlRLV2VLcTd1anB0dWs5L2JOSkFYTThhN3ptaFNPMWNRTkZFeWxqRStLWjFyNTRkS0lVcUlLZg0KOTh2cFFvbmhDMHA4OExOVFBNMkthdk5uK1Y3Y3ArcnlNNHVRUnhkUjc5YVFaaGFuWXQxWllTQWYrRjN2bHppTg0KVUI2OXpSNGhhbzZEUUtWeE1yVWZxU1N5cHRPcW8vN1hXTlVYM1F5R3VsZ24wTHhORjdvdnI3aEtZQlpuZ0pQdg0KeERIWEt5MENnWUVBK2NhaG83MHErYlJBTzl3RHZPeHFuSHlFV2ZGQ2ZITk5lcmJaQ3MveVRHNmFNZGo5dVd0SA0KNnNmR2ltMGcwUWtoT1M1V0ZOV1ZYWVBkeDVaMk4yMTlhQ3hLdUlUd1pvdWx6WlREZW1hdkIzUXU5OE9aZi9TTA0KRU9HMS81d2xlY0JHK2dBRnVvMGNZRVlrZWFvby9EZXU2ejFuYmRXWHMzNGhjU21Kb1JQdlBtTUNnWUVBMCs0ZQ0KWTZMVlpqUUNFOVR0bUdFRWt3Ky9UQXorUmVTa3pRWmZxWDZZTTNVOGg5OVp3bzFBNWRQaFVPQ2M0ODdwbE1UYQ0KRys0dWhtRmVJd0FFbWRBMm9FK3NUaW5CWlVMQUJ5SDZJd2xRV0pKMW5WeUE3aVZSSElYK0F4S0tmaUV4QXJQYw0KUC82dVVzaUQvTXZGQTVoR3JEUnUyWUlHRXdkRCtyRE9sN2JNN2k4Q2dZRUF0bTJHL1VwYXF3b2xxQktuZ0VMRQ0KRXdzMnQySm9odkRIOUFxOE54TnVDcmoxVWRjRWFYcWJpalRqSTVOVTFwZnVkZzhMdkNmSzhnUXY1V2hWYTJKQw0KcCtWQnBjY2l0aUxrdEdRazZhODV3eDN1ZC9PYWwwUUtsZ2ZrbjQ1eUtKeHd1b050cTdVSXRxQkVYOEFTTXpTUQ0KUXl3VDhMcUNGQXpaYkFkRWlDdEJIN1VDZ1lFQXdYcDNaZkVIcjRtMWg5TnhvaGFZYllZSDErOVl5QWhJYUNEMA0KZnJIalU3OHBKc1pDbFBvT0VJUVNCSnM2d0VOclBmVkZSaEI1aXhjak1RTFlNSEJGSHEvK0YxSEpqSitXM2l3bg0KeDRxK1BrNWZiKzAraTZ1bjFFbURyOXhpY1dudDY1QzJkL29UdmVIdmxYK1dlb1N2cUpFcHpnc0ZicVBJYlBxeg0KY1JCaklPTUNnWUJBTnZoQWppb25Ya0l1NjYzUFZsekFKQ0REcTZ1empDKzlSTkpFdEhxbkZYd1B6RHRXV3NZSQ0KeDVNSW9hWjZOZ1dSSVZJek9mc3UzMU9QdVdqZ2lHTFJQY3ZBNi80UjJmWm5sc0tvbitHRXFmajlTQ0tLOUJGZg0KYVEra0xoOHVpbUVtYlBkcVFtVWtjYkl6QmdjMnVlY1pjNWlyVGJuS1RVVjdWTkhqQzViaEhRPT0NCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0t",
//                    "http://cfe2.lpds.sztaki.hu:4567",
//                    "uros.pascinski@partners.sztaki.hu",
//                    "e8cc564a5e9320d6c22647c5e6dab55005bf1e68",
//                    "m1.medium",
//                    "m1.small",
//                    200,
//                    8,
//                    1,
//                    8,
//                    0.8,
//                    1073741824,
//                    36000,
//                    "https://s3.lpds.sztaki.hu",
//                    "WAU8PTCX8NSIL0RSG8K9",
//                    "R16UWaOBfz44nvoGmCGXykHNjlKCVzpWc65KjiF6",
//                    "images/optimized-image.qcow2");

            List<String> urosImageList = getSZTAKIOpenNebulaImageList("/usr/local/bin/econe-describe-images -U http://cfe2.lpds.sztaki.hu:4567 -K uros.pascinski@partners.sztaki.hu -S e8cc564a5e9320d6c22647c5e6dab55005bf1e68","uros.pascins");


//            TODO: uncomment this!!
//            sztakiExecuteObj.setImageURL(diskImages.get(0).getIri());
//            URL website = new URL(sztakiExecuteObj.getImageURL());
//            String[] fileNameTab = sztakiExecuteObj.getImageURL().split("/");

            //TODO: REMOVE THIS!!
            sztakiExecuteObj.setImageURL("https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2");
            URL website = new URL("https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2");
            String[] fileNameTab = "https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2".split("/");
            //////////////

            String fileName = fileNameTab[fileNameTab.length-1];
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            // UPLOAD TO SZTAKI
            String filePath = "/tmp/"+fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            StringBuffer sb = new StringBuffer();
            try {
                Process p = Runtime.getRuntime().exec("/usr/local/bin/econe-upload -U http://cfe2.lpds.sztaki.hu:4567 -K uros.pascinski@partners.sztaki.hu -S e8cc564a5e9320d6c22647c5e6dab55005bf1e68 "+filePath);


                p.waitFor();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                        sb.append(line);
                }

                new File(filePath).delete();
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("catched error",e.getMessage());
                return resultMap;
            }

            String result = sb.toString();
            //             set image ID with new generated one on SZTAKI cloud
            if(result.startsWith("Success: ImageId ")){
                result = result.replace("Success: ImageId ","");
                sztakiExecuteObj.setImageId(result.trim());
            }
            else{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<String> urosImageList2 = getSZTAKIOpenNebulaImageList("/usr/local/bin/econe-describe-images -U http://cfe2.lpds.sztaki.hu:4567 -K uros.pascinski@partners.sztaki.hu -S e8cc564a5e9320d6c22647c5e6dab55005bf1e68","uros.pascins");
                urosImageList2.removeAll(urosImageList);

                if(urosImageList2.size() < 1)   {
                    resultMap.put("ImageId cannot be established!",urosImageList2.size() + " is the size of the econe-describe-images");
                    return resultMap;
                }

                if(urosImageList2.get(0).length() < 27){
                    resultMap.put("Result list is too short: ",urosImageList2.get(0));
                    return resultMap;
                }


                sztakiExecuteObj.setImageId(urosImageList2.get(0).substring(14,27).trim());
            }

            sztakiExecuteObj.setCloudOptimizerVMInstanceType("m1.medium");

//             URL of SZTAKI cloud
//            http://cfe2.lpds.sztaki.hu:4567

            sztakiExecuteObj.setNumberOfParallelWorkerVMs(8);

            sztakiExecuteObj.setS3EndpointURL(AppContextListener.prop.getProperty("s3.endpoint.url"));
            sztakiExecuteObj.setS3AccessKey(AppContextListener.prop.getProperty("s3.access.key"));
            sztakiExecuteObj.setS3SecretKey(AppContextListener.prop.getProperty("s3.secret.key"));
            sztakiExecuteObj.setS3Path(AppContextListener.prop.getProperty("s3.path"));
            sztakiExecuteObj.setCloudEndpointURL(AppContextListener.prop.getProperty("sztaki.cloud.endpoint.url"));

            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String jsonInString = mapper.writeValueAsString(sztakiExecuteObj);
            System.out.println(jsonInString);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.optimizer.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonInString));
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                final String optimizerID = EntityUtils.toString(resEntity, "UTF-8");
                optimizerMap.put(optimizerID, new ArrayList<String>());
                try{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String currentOptimizationID = optimizerID;
                        System.out.println("current optimization ID: "+ currentOptimizationID);
                        JsonObject optimizationStatusResult = new JsonParser().parse(getOptimizationStatusLocal(currentOptimizationID)).getAsJsonObject();
                        String status = optimizationStatusResult.get("optimizerVMStatus").getAsString();
                        System.out.println(status);
                        while (status.equals("running") || status.equals("pending")) {
                            System.out.println("running...");
                            optimizerMap.get(currentOptimizationID).add(optimizationStatusResult.toString());
                            if (optimizerMap.get(currentOptimizationID).size() > 30)
                                optimizerMap.get(currentOptimizationID).remove(0);

                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            status = optimizationStatusResult.get("optimizerVMStatus").getAsString();
                        }
                        System.out.println("terminated");
                    }
                }).start();
                }catch (Exception e){
                    e.printStackTrace();
                }
                resultMap.put(optimizerID,sztakiExecuteObj.getImageId());
                return resultMap;
            }
        } catch (IOException|IndexOutOfBoundsException|NullPointerException|ParseException e) {
            e.printStackTrace();
            resultMap.put("IOException",e.getMessage());
            return resultMap;
        }
        return null;
    }
    */

    // POST: start new optimization procedure
    @POST
    @Path("execute_optimizer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Map<String, String> executeOptimizer(final SZTAKIExecuteObj sztakiExecuteObj) {
        Map<String, String> resultMap = new HashMap<>();
//        final Quality quality = new Quality(UUID.randomUUID().toString(), sztakiExecuteObj.getXaimedSize(), -1, 0,
//                true, 0, false, true, 0, sztakiExecuteObj.getMaxIterationsNum(), 0, sztakiExecuteObj
//                .getXaimedReductionRatio(), sztakiExecuteObj.getXmaxRunningTime(), 0, (short) 1, new
//                ArrayList<String>());
        try {
//            String insertStatement = FusekiUtils.generateInsertObjectStatement(quality);
//            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
//                    AppContextListener.prop.getProperty("fuseki.url.update"));
//            upp.execute();
//            resultMap.put("optimization_job_id: ", quality.getId());

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    initOptimizationLifecycle(sztakiExecuteObj, quality);
//                }
//            }).start();
            return initOptimizationLifecycle(sztakiExecuteObj, null);

        } catch (Exception e) {
            resultMap.put("an error occured", e.getMessage());
        }

        return resultMap;
    }

    private Map<String,String> initOptimizationLifecycle(SZTAKIExecuteObj sztakiExecuteObj, Quality quality) {
        try {
            // set other SZTAKI optimization parameters
            sztakiExecuteObj.setCloudOptimizerVMInstanceType("m1.medium");
            sztakiExecuteObj.setNumberOfParallelWorkerVMs(8);
            sztakiExecuteObj.setS3EndpointURL(AppContextListener.prop.getProperty("s3.endpoint.url"));
            sztakiExecuteObj.setS3AccessKey(AppContextListener.prop.getProperty("s3.access.key"));
            sztakiExecuteObj.setS3SecretKey(AppContextListener.prop.getProperty("s3.secret.key"));
            sztakiExecuteObj.setS3Path(AppContextListener.prop.getProperty("s3.path"));
            sztakiExecuteObj.setCloudEndpointURL(AppContextListener.prop.getProperty("sztaki.cloud.endpoint.url"));

            //get current deployment image list
            List<String> urosImageList = getSZTAKIOpenNebulaImageList("/usr/local/bin/econe-describe-images -U " +
                    "http://cfe2.lpds.sztaki.hu:4567 -K uros.pascinski@partners.sztaki.hu -S " +
                    "e8cc564a5e9320d6c22647c5e6dab55005bf1e68", "uros.pascins");


//            TODO: uncomment this!!
//            sztakiExecuteObj.setImageURL(diskImages.get(0).getIri());
//            URL website = new URL(sztakiExecuteObj.getImageURL());
//            String[] fileNameTab = sztakiExecuteObj.getImageURL().split("/");

            //TODO: REMOVE THIS!!
            sztakiExecuteObj.setImageURL("https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2");
            URL website = new URL("https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2");
            String[] fileNameTab = "https://images.s3.lpds.sztaki.hu/wordpress-centos7.0-20160627a.qcow2".split("/");
            //////////////

            String fileName = fileNameTab[fileNameTab.length - 1];
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            //TODO: deployment
//            // UPLOAD TO SZTAKI
//            String filePath = "/tmp/" + fileName;
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//            StringBuffer sb = new StringBuffer();
//            try {
//                Process p = Runtime.getRuntime().exec("/usr/local/bin/econe-upload -U http://cfe2.lpds.sztaki.hu:4567" +
//                        " -K uros.pascinski@partners.sztaki.hu -S e8cc564a5e9320d6c22647c5e6dab55005bf1e68 " +
//                        filePath);
//
//
//                p.waitFor();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                new File(filePath).delete();
//            } catch (Exception e) {
//                e.printStackTrace();
////                resultMap.put("catched error", e.getMessage());
////                return resultMap;
//            }

            sztakiExecuteObj.setImageId("ami-00001563");
//            String result = null;// sb.toString();
//            //             set image ID with new generated one on SZTAKI cloud
//            if (result.startsWith("Success: ImageId ")) {
//                result = result.replace("Success: ImageId ", "");
//                sztakiExecuteObj.setImageId(result.trim());
//            }
//            else {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
////                List<String> urosImageList2 = getSZTAKIOpenNebulaImageList("/usr/local/bin/econe-describe-images -U " +
////                        "http://cfe2.lpds.sztaki.hu:4567 -K uros.pascinski@partners.sztaki.hu -S " +
////                        "e8cc564a5e9320d6c22647c5e6dab55005bf1e68", "uros.pascins");
////                urosImageList2.removeAll(urosImageList);
//
////                if (urosImageList2.size() < 1) {
//////                    resultMap.put("ImageId cannot be established!", urosImageList2.size() + " is the size of the " +
//////                            "econe-describe-images");
//////                    return resultMap;
////                }
////
////                if (urosImageList2.get(0).length() < 27) {
//////                    resultMap.put("Result list is too short: ", urosImageList2.get(0));
//////                    return resultMap;
////                }
////
////
////                sztakiExecuteObj.setImageId(urosImageList2.get(0).substring(14, 27).trim());
//            }

            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String jsonInString = mapper.writeValueAsString(sztakiExecuteObj);
            System.out.println(jsonInString);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.optimizer.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonInString));
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

//            System.out.println(response.getStatusLine());
//            if (resEntity != null) {
                final String optimizerID = EntityUtils.toString(resEntity, "UTF-8");
//                optimizerMap.put(optimizerID, new ArrayList<String>());
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final String currentOptimizationID = optimizerID;
//                            System.out.println("current optimization ID: " + currentOptimizationID);
//                            JsonObject optimizationStatusResult = new JsonParser().parse(getOptimizationStatusLocal
//                                    (currentOptimizationID)).getAsJsonObject();
//                            String status = optimizationStatusResult.get("optimizerVMStatus").getAsString();
//                            System.out.println(status);
//                            while (status.equals("running") || status.equals("pending")) {
//                                System.out.println("running...");
//                                optimizerMap.get(currentOptimizationID).add(optimizationStatusResult.toString());
//                                if (optimizerMap.get(currentOptimizationID).size() > 30)
//                                    optimizerMap.get(currentOptimizationID).remove(0);
//
//                                try {
//                                    Thread.sleep(5000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                status = optimizationStatusResult.get("optimizerVMStatus").getAsString();
//                            }
//                            System.out.println("terminated");
//                        }
//                    }).start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                resultMap.put(optimizerID, sztakiExecuteObj.getImageId());
////                return resultMap;
//            }
            Map<String,String> resultMap  = new HashMap<>();
            resultMap.put(optimizerID, sztakiExecuteObj.getImageId());
            return resultMap;
        } catch (IOException | IndexOutOfBoundsException | NullPointerException | ParseException e) {
            e.printStackTrace();
//            resultMap.put("IOException", e.getMessage());
//            return resultMap;
            return new HashMap<>();
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
            IOUtils.copy(inputStream, writer, "UTF-8");
            inputStream.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private DefaultHttpClient createClientWithAllowedHostname() {
//        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//        DefaultHttpClient client = new DefaultHttpClient();
//
//        SchemeRegistry registry = new SchemeRegistry();
//        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//        registry.register(new Scheme("https", socketFactory, 443));
//        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//
//        // Set verifier
//        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//        return httpClient;
//    }




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
            // todo: optimize query parameter
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.optimizer.url") + "/" + optimizerID);
//            builder.setParameter("id", optimizerID);

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet httpGet = new HttpGet(builder.build());
//            httpGet.addHeader("Content-Type", "application/json");


            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

//            System.out.println(result.toString());
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } return null;
    }

    // GET: get status of an optimization procedure
    @GET
    @Path("get_optimization_status_list")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getOptimizationStatusList(@QueryParam("optimizer_id")final String optimizerID) {
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
                return EntityUtils.toString(resEntity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }

    @GET
    @Path("get_builder_status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getImageBuilderStatus(@QueryParam("builder_id") String builderID, @QueryParam("show_result") boolean showResult) {
        try {
            URIBuilder builder = new URIBuilder(AppContextListener.prop.getProperty("sztaki.builder.url") + "/" + builderID+ (showResult ? "/result" : ""));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(builder.build());
            HttpResponse response = httpClient.execute(httpGet);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

//            System.out.println(result.toString());
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } return null;
    }

    @GET
    @Path("delete_builder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String stopImageBuilder(@QueryParam("builder_id") String builderID) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete(AppContextListener.prop.getProperty("sztaki.builder.url") + "/" + builderID+ "/result");
            httpDelete.addHeader("optimizer_id", builderID);
            HttpResponse response = httpClient.execute(httpDelete);

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

    @Override
    public String showLast10ValuesofOptimization(@QueryParam("optimizer_id") String optimizerID) {
        return null;
    }
}

