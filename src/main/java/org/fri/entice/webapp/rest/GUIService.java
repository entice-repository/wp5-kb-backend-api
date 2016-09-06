package org.fri.entice.webapp.rest;

import com.vmrepository.RepoGuardClientFunctionalities.MOEstart;
import com.vmrepository.RepoGuardClientFunctionalities.UploadImageAsInstanceFromS3;
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
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.entry.client.EnticeDetailedImage;
import org.fri.entice.webapp.entry.client.EnticeImage;
import org.fri.entice.webapp.entry.client.ResponseObj;
import org.fri.entice.webapp.entry.client.ResponseSynthesisObj;
import org.fri.entice.webapp.util.FusekiUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

@Path("/gui/")
public class GUIService implements IGUIService {

    JSONService parent;

    private static final String SERVER_UPLOAD_LOCATION_FOLDER = SystemUtils.IS_OS_LINUX ? "/home/sandi/uploads/" :
            "C://Users/sandig/Desktop/";

    public GUIService(JSONService parent) {
        this.parent = parent;
    }

    // priority: low
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

//                    if (var.getVarName().equals("pass"))
//                        x = ((ResultBinding) qs).getBinding().get(var).getLiteralValue().toString();
                }
            }
            qe.close();

//            return matched + " match. Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms";
            final boolean loginSuccess = id != null;

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("success", String.valueOf(loginSuccess));
            if (loginSuccess) resultMap.put("id", id.replaceFirst(FusekiUtils.KB_PREFIX_SHORT, ""));
            if (groupId != null) resultMap.put("group", groupId.replaceAll("\"", ""));

            return resultMap;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return "false";
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // priority: low
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
    @Path("get_newest_pareto") // rename it to: get_pareto_stage1 ..2
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Pareto getNewestPareto() {
        boolean generatePareto = true;
        try {
            generatePareto = MOEstart.generateParetoStage1();

            if (generatePareto) {
                String id = FusekiUtils.getEntityID(Pareto.class, "ORDER BY DESC(xsd:dateTime(?o)) LIMIT 1");
                return FusekiUtils.getAllEntityAttributes(Pareto.class, id).get(0);
            }
            else {
                return null;
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

//    @GET
//    @Path("pre_upload_of_image")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Override
//    public Pareto preUploadOfImage(@QueryParam("image_size") Long imageSize, @QueryParam("image_name") String
//            imageName) {
//        System.out.println("Image size: " + imageSize + " | Image name: " + imageName);
//        // Call UIBK request!!d
//        List<Pareto> paretoList = parent.getPareto();
//        return paretoList.get((int)(Math.random()*paretoList.size()));
//    }


//    1. image_name: Sting -> ime slike
//    2. file_upload: Multipart ... -> VMI, ki jo je potrebno uploadat
//    3. category_list: List<String>, več opcij -> Izbira tagov za posamezno sliko: možno je več opcij
//    4. image_description: String -> Opis slike: Apache strežnik omogoča blabla ...
//    5. pareto_point: Array<Double>  -> Točka na grafu, ki jo izbere uporabnik. ali pošljem 2D array doublov?
//    6. functional_tests: Multipart upload -> funkcionalni testi, ki se jih potrebuje za optimizacijo. -> Upload
// datoteke
//    7. avatar: Int -> Avatar VMI. Če imaš ti na strežniku shranjene avatarje, potem potrebujem od tebe seznam teh
// avatarjev (get request: id: Int, name: String, avatar: img/avatar.jpg), ki jih bom prikazala uporabniku, napo bo
// izbral enega in jaz ti vrnem Int tega em samo int


    /*
        http://localhost:8080/JerseyREST/upload_image.html
     */
    @POST
    @Path("upload_image")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public ResponseObj uploadImage(@FormDataParam("file_upload") InputStream fileInputStream, @FormDataParam
            ("file_upload") FormDataContentDisposition contentDispositionHeader, @FormDataParam("functional_tests")
    InputStream functionalTestInputStream, @FormDataParam("functional_tests") FormDataContentDisposition
            functionalTestDispositionHeader, @Nullable @FormDataParam("category_list") List<String> categoryList,
                                   @FormDataParam("image_description") String imageDescription, @FormDataParam
                                               ("image_name") String imageName, @FormDataParam("pareto_point_x") int
                                               paretoPointIndexX, @FormDataParam("pareto_point_y") int
                                               paretoPointIndexY, @FormDataParam("pareto_point_id") String
                                               paretoPointId, @Nullable @FormDataParam("avatar_id") int avatarID,
                                   @FormDataParam("user_id") String userID) {
        try {
            if (contentDispositionHeader == null && contentDispositionHeader.getFileName().length() <= 0)
                return new ResponseObj(204, "file name is null! File name: " + contentDispositionHeader.getFileName());
            else if (imageDescription == null || imageDescription.length() == 0)
                return new ResponseObj(204, "image description is not set! image_description");
            else if (imageName == null || imageName.length() == 0)
                return new ResponseObj(204, "image name is not set! image_name");
            else if (paretoPointIndexX < 0) return new ResponseObj(204, "pareto point x is not set! pareto_point_x");
            else if (paretoPointIndexY < 0) return new ResponseObj(204, "pareto point y is not set! pareto_point_y");
            else if (paretoPointId == null || paretoPointId.length() == 0)
                return new ResponseObj(204, "pareto ID is not set!\npareto_point_id");
            else if (userID == null || userID.length() == 0)
                return new ResponseObj(204, "user ID is not set!\nuser_id");

            String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();

            // save the file to the server
            saveFile(fileInputStream, filePath);

            //upload VMI in the repository
            String successMessage = UploadVMI.performUpload(filePath);

            String functionalityID = "";
            // if functionality test is selected
            if (functionalTestDispositionHeader != null && functionalTestDispositionHeader.getFileName().length() > 0) {
                String functionalTestFilePath = SERVER_UPLOAD_LOCATION_FOLDER + functionalTestDispositionHeader
                        .getFileName();
                if (functionalTestDispositionHeader != null) saveFile(functionalTestInputStream, functionalTestFilePath);

                File file = new File(functionalTestFilePath);

                //upload functional test in the repository
                String successFunctionalTestMessage = UploadVMI.performUpload(functionalTestFilePath); //todo: get ID
                // of the functional test when Nishant fixes JSON
                functionalityID = UUID.randomUUID().toString();

                // TODO: fix UIBK message parsing when string will be fixed and parsable to JSON
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
                File file = new File(filePath);

                // create DiskImage entity
                DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.VMI, imageDescription,
                        imageName, "", FileFormat.IMG, "http://193.2.72.90/src/images/avatars/" + (avatarID != -1 ?
                        avatarID + "" : ""), false, "https://s3.tnode" +
                        ".com:9869/flexiant-entice/" + contentDispositionHeader.getFileName(), "", 0, userID,
                        functionalityID, "", "", false, System.currentTimeMillis(), false, "1.0", (int) (file.length
                        () / 1024), paretoPointIndexX, paretoPointIndexY, paretoPointId, categoryList);

                String insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement),
                        AppContextListener.prop.getProperty("fuseki.url.update"));
                upp.execute();
                file.delete();
                return new ResponseObj(200, "success upload");
            }
            else return new ResponseObj(404, "failed upload");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj(404, "FAILED uploaded on LPT VMI | stacktrace"+e.getMessage() );
        }
    }

    // save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream, String serverLocation) {

        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));

            int read;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

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

//    @Override
//    public ResponseSynthesisObj performImageSynthesis(String cloudID, String imageID, String instanceType, @Nullable
//    String securityGroupID, @Nullable String subnetIDs, @Nullable String ssh, InputStream contextualisationStream,
//                                                      InputStream functionalTestStream, int constraints, String
//                                                                  notifyEmail, long startTime, @Nullable int
// avatarID) {
//        return null;
//    }

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

        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, null, (title != null ? ". ?s" +
                " " +
                "knowledgebase:DiskImage_Title ?title . FILTER regex(?title, \"" + title + "\", \"i\") ." : "") +
                (categoryId != null ? "?s knowledgebase:DiskImage_Categories ?cat . FILTER regex(?cat, " +
                        "\""+categoryId+"\", \"i\")" : ""));

        ArrayList<EnticeImage> enticeImages = new ArrayList<>();
        int maxSize = diskImages.size() > (paginationPage + 1) * hitsPerPage ? (paginationPage + 1) * hitsPerPage :
                diskImages.size();
        //int count = 0;

        if (maxSize == 0) maxSize = diskImages.size();

        List<Repository> repositoriesList = FusekiUtils.getAllEntityAttributes(Repository.class);

        for (int i = paginationPage * hitsPerPage; i < maxSize; i++) {

            List<Fragment> fragmentList = FusekiUtils.getFragmentDataOfDiskImage(diskImages.get(i).getId(), false);
            List<Functionality> functionalityList = FusekiUtils.getFunctionalityOfDiskImage(diskImages.get(0)
                    .getRefFunctionalityId());
            diskImages.get(0).setFunctionalityList(functionalityList);

            Set<String> setOfRepositoryIds = new HashSet<>();
            for (Fragment fragment : fragmentList) {
                setOfRepositoryIds.add(fragment.getRefRepositoryId());
            }
            List<Repository> matchingRepositories = new ArrayList<>();
            for (Repository repository : repositoriesList) {
                if (listContainsId(setOfRepositoryIds, repository.getId())) matchingRepositories.add(repository);
            }

            String userFullName = "dummy full name";
            try {
                userFullName = FusekiUtils.getAllEntityAttributes(User.class, diskImages.get(i).getRefOwnerId()).get
                        (0).getFullName();
            } catch (Exception e) {
                //e.printStackTrace();
            }
//            enticeImages.add(new EnticeImage(diskImages.get(i).getId(), diskImages.get(i).getPictureUrl(), diskImages
//                    .get(i).getTitle(), diskImages.get(i).getRefOwnerId() == null ? "unknown user" : diskImages.get
//                    (i).getRefOwnerId(), diskImages.get(i).getDiskImageSize(), 0, diskImages.get(i).getCategoryList()
//                    , 0, diskImages.get(i).getDescription(), userFullName));
            enticeImages.add(new EnticeDetailedImage(diskImages.get(i).getId(), diskImages.get(i).getPictureUrl(), diskImages.get
                    (i).getTitle(), diskImages.get(i).getRefOwnerId() == null ? "unknown user" : diskImages.get(i)
                    .getRefOwnerId(), diskImages.get(i).getDiskImageSize(), 0, diskImages.get(i).getCategoryList(), 0,
                    matchingRepositories, diskImages.get(i).getFunctionalityList(), null, diskImages.get(i)
                    .getDescription(), userFullName));

            if(order != null && order.length() > 0){
                if(order.equals("name"))
                Collections.sort(enticeImages, new Comparator<EnticeImage>() {
                    @Override
                    public int compare(EnticeImage o1, EnticeImage o2) {
                        String o1lower = o1.getImageName().toLowerCase();
                        String o2lower = o2.getImageName().toLowerCase();
                        return isDescendingSort ? o2lower.compareTo(o1lower) : o1lower.compareTo(o2lower);
                    }
                });
                else if(order.equals("size"))
                    Collections.sort(enticeImages, new Comparator<EnticeImage>() {
                        @Override
                        public int compare(EnticeImage o1, EnticeImage o2) {
                            return isDescendingSort ? o2.getImageSize() - o1.getImageSize() :
                                    o1.getImageSize() - o2.getImageSize();
                        }
                    });

            }
            //   count++;
        }

        return new Object[]{enticeImages, diskImages.size()};
    }

    @GET
    @Path("get_entice_detailed_images")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public EnticeDetailedImage getEnticeDetailedImage(@QueryParam("id") String imageID) {
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, imageID);


        if (diskImages.size() == 0) return null;

        //todo: temporary mock data
//        List<String> categoryList = new ArrayList<>();
//        if (Math.random() < 0.3) categoryList.add("Database");
//        if (Math.random() < 0.3) categoryList.add("Monitoring");
//        if (Math.random() < 0.3) categoryList.add("Application");
//        if (Math.random() < 0.3) categoryList.add("App-Server");
//        if (Math.random() < 0.3) categoryList.add("File-Server");
//        if (Math.random() < 0.3) categoryList.add("Openstack");
//        if (Math.random() < 0.3) categoryList.add("Docker");
//        if (Math.random() < 0.3) categoryList.add("Container");
//        if (Math.random() < 0.3) categoryList.add("Operating system");
//        if (Math.random() < 0.3) categoryList.add("Misc");

        List<Fragment> fragmentList = FusekiUtils.getFragmentDataOfDiskImage(imageID, false);
        List<Functionality> functionalityList = FusekiUtils.getFunctionalityOfDiskImage(diskImages.get(0)
                .getRefFunctionalityId());
        diskImages.get(0).setFunctionalityList(functionalityList);

        List<Repository> repositoriesList = FusekiUtils.getAllEntityAttributes(Repository.class);
        Set<String> setOfRepositoryIds = new HashSet<>();
        for (Fragment fragment : fragmentList) {
            setOfRepositoryIds.add(fragment.getRefRepositoryId());
        }

        List<Repository> matchingRepositories = new ArrayList<>();
        for (Repository repository : repositoriesList) {
            if (listContainsId(setOfRepositoryIds, repository.getId())) matchingRepositories.add(repository);
        }

//        List<String> functionalTests = new ArrayList<>();
//        functionalTests.add("dummy functional test 1");
//        functionalTests.add("dummy functional test 2");
//        functionalTests.add("dummy functional test 3");
//        List<String> optimizationHistory = new ArrayList<>();
//        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME1");
//        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME2");
//        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME3");

        return new EnticeDetailedImage(diskImages.get(0).getId(), diskImages.get(0).getPictureUrl(), diskImages.get
                (0).getTitle(), diskImages.get(0).getRefOwnerId() == null ? "unknown user" : diskImages.get(0)
                .getRefOwnerId(), diskImages.get(0).getDiskImageSize(), 0, diskImages.get(0).getCategoryList(), 0,
                matchingRepositories, diskImages.get(0).getFunctionalityList(), null, diskImages.get(0)
                .getDescription(), "aaa");
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
    @Path("get_pareto_distribution")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Pareto> getParetoDistribution(@QueryParam("username") String username, @QueryParam("password") String
            password, @QueryParam("image_id") String imageID) {
        return null;
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

//    UploadImageAsInstanceFromS3


    //  a8169e97-59a0-4478-a0fc-db41a6d99dd9
    @GET
    @Path("deyloy_vmi_on_cloud")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String deployDiskImageOnTheCloud(@QueryParam("image_id") String imageID, @QueryParam("cloud_id")
    String cloudID, @QueryParam("cloud_access_key") String cloudAccesseKey, @QueryParam("cloud_secret_key")
    String cloudSecretKey) {
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, imageID);
        try {
            return UploadImageAsInstanceFromS3.uploadImageAsInstanceFromS3(cloudAccesseKey, cloudSecretKey,
                    diskImages.get(0).getTitle(), cloudID, diskImages.get(0).getIri());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException_Exception e) {
            e.printStackTrace();
        } catch (IOException_Exception e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
