package org.fri.entice.webapp.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.fri.entice.webapp.util.CommonUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Path("/sztaki/")
public class SZTAKIService implements ISZTAKIService {
    JSONService parent;

    public SZTAKIService(JSONService parent) {
        this.parent = parent;
    }
    /*
     public static final String IMAGE_URL = "imageURL";
 public static final String IMAGE_ID = "imageId";
 public static final String IMAGE_KEY_PAIR = "imageKeyPair";
 public static final String IMAGE_PRIVATE_KEY = "imagePrivateKey";
 public static final String IMAGE_USER_NAME = "imageUserName";
 public static final String IMAGE_CONTEXTUALIZATION = "imageContextualization"; // TODO
 public static final String IMAGE_CONTEXTUALIZATION_URL = "imageContextualizationURL"; // TODO
 public static final String VALIDATOR_SCRIPT = "validatorScript";
 public static final String VALIDATOR_SCRIPT_URL = "validatorScriptURL";
 public static final String VALIDATOR_SERVER_URL = "validatorServerURL"; // TODO
 public static final String CLOUD_ENDPOINT_URL = "cloudEndpointURL";
 public static final String CLOUD_ACCESS_KEY = "cloudAccessKey";
 public static final String CLOUD_SECRET_KEY = "cloudSecretKey";
 public static final String CLOUD_OPTIMIZER_VM_INSTANCE_TYPE = "cloudOptimizerVMInstanceType";
 public static final String CLOUD_WORKER_VM_INSTANCE_TYPE = "cloudWorkerVMInstanceType";
 public static final String NUMBER_OF_PARALLEL_WORKER_VMS = "numberOfParallelWorkerVMs";

 public static final String S3_ENDPOINT_URL = "s3EndpointURL";
 public static final String S3_ACCESS_KEY = "s3AccessKey";
 public static final String S3_SECRET_KEY = "s3SecretKey";
 public static final String S3_PATH = "s3Path";
 public static final String S3_REGION = "s3Region";

 public static final String MAX_ITERATIONS_NUM = "maxIterationsNum";
 public static final String MAX_NUMBER_OF_VMS = "maxNumberOfVMs";
 public static final String AIMED_REDUCTION_RATIO = "aimedReductionRatio";
 public static final String AIMED_SIZE = "aimedSize";
 public static final String MAX_RUNNING_TIME = "maxRunningTime";
     */

    // POST: start new optimization procedure
    @GET
    @Path("execute_optimizer")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String executeOptimizer(@QueryParam("image_url") String imageURL) {
        try {
            String jsonContent = CommonUtils.readFile("D:\\projects\\lpt\\entice-ul-api\\internal_work\\input_test" +
                    ".json", StandardCharsets.UTF_8);

            //TODO: use imageURL to update the url of the image
            //TODO: add additional attributes to this rest method

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(AppContextListener.prop.getProperty("sztaki.service.url"));
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonContent));
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


    // GET: get status of an optimization procedure
    @GET
    @Path("get_optimization_status")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getStatus(@QueryParam("optimizer_id") String optimizerID) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(AppContextListener.prop.getProperty("sztaki.service.url"));
//            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("optimizer_id", optimizerID);

            HttpResponse response = httpClient.execute(httpGet);
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

    // PUT: stop optimization and save sub-optimal image
    @GET
    @Path("stop_optimization")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String stopOptimization(@QueryParam("optimizer_id") String optimizerID) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut(AppContextListener.prop.getProperty("sztaki.service.url"));
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
            HttpDelete httpDelete = new HttpDelete(AppContextListener.prop.getProperty("sztaki.service.url"));
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
}

