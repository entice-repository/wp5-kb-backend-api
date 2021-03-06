package org.ul.entice.webapp.rest;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.jclouds.javax.annotation.Nullable;
import org.ul.entice.webapp.entry.Pareto;
import org.ul.entice.webapp.entry.User;
import org.ul.entice.webapp.entry.client.EnticeDetailedImage;
import org.ul.entice.webapp.entry.client.ResponseObj;
import org.ul.entice.webapp.entry.client.ResponseSynthesisObj;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IGUIService {
    @GET
    public Map<String, String> performUserLogin(String username, String password);

    @GET
    public String performUserLogout(String sessionID);

    @GET
    public Response registerUser(User user);

    @GET
    public List<Pareto> getParetoStage1();

    @GET
    public Pareto getParetoStage2();

    @GET
    public Pareto getParetoStage3();

    @GET
    public Pareto getNewestPareto(int stage);

    // contentDispositionHeader == filename
    @POST
    public ResponseObj uploadImage(InputStream fileInputStream, FormDataContentDisposition contentDispositionHeader,
                                   InputStream ovfInputStream, FormDataContentDisposition ovfDispositionHeader,
                                   InputStream functionalTestInputStream, FormDataContentDisposition
                                           functionalTestDispositionHeader, List<String> categoryList, String
                                           imageURL, String imageDescription, String imageName, int paretoPoint,
                                   String paretoPointId, int avatarID, String userID);

    //
//    // todo: verify!!!
//    // - POST ; create VMI ; buildModule (string), version (string), inputData == module FILE, testModule (string),
//    // testVersion (string), testCommand (string), inputData == test FILE ;  "status", jobID
    @POST
    public ResponseObj createVMI(InputStream moduleInputStream, FormDataContentDisposition moduleFCD, String
            buildModule, String versionModule, String testModule, InputStream testInputStream,
                                 FormDataContentDisposition testFCD, String versionTest, String commandTest);


    // returns status ID
    @GET
    public ResponseObj periodicallyCheckBuildRecipe(String jobID);

    // 1 to 4 constraints
    @POST
    public ResponseSynthesisObj performImageSynthesis(String cloudID, String imageID, String instanceType, @Nullable String
            securityGroupID, @Nullable String subnetIDs, @Nullable String ssh, InputStream contextualisationStream,
                                                      InputStream functionalTestStream, int constraints, String
                                                              notifyEmail, long startTime, @Nullable int avatarID);

    // imageSize, currentSize, startTime, table of restrictions (from for examples: hours to actual dates)
    @GET
    public ResponseObj periodicallyCheckUploadState(String jobID);

    @GET
    public Object[] getEnticeImages(String title, String categoryId, int paginationPage, int hitsPerPage,
                                    String order, boolean isDescendingSort);

    @GET
    public EnticeDetailedImage getEnticeDetailedImage(String imageID);

    /*
        ADMINS ONLY
    */
    // Users can redistribute only their images, administrator can redistribute all images
//    @GET
//    public List<Pareto> getParetoDistribution(String username, String password, String imageID);

    // Users can redistribute only their images, administrator can redistribute all images
    @GET
    public ResponseObj redistributeUserImage(String imageID, String paretoPoint);

    // later it will be used with sockets
    @GET
    public ResponseObj periodicallyCheckRedistribution(String jobID);

    @GET
    public String deployDiskImageOnTheCloud(String imageID, String cloudLocationURL, String cloudAccesseKey, String cloudSecretKey);
}
