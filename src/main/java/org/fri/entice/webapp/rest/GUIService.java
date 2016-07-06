package org.fri.entice.webapp.rest;

import com.vmrepository.RepoGuardClientFunctionalities.MOEstart;
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
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.entry.client.EnticeDetailedImage;
import org.fri.entice.webapp.entry.client.EnticeImage;
import org.fri.entice.webapp.entry.client.ResponseObj;
import org.fri.entice.webapp.entry.client.ResponseSynthesisObj;
import org.fri.entice.webapp.util.FusekiUtils;
import org.fri.entice.webapp.util.PasswordUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    public String performUserLogin(@QueryParam("username") String username, @QueryParam("password") String password) {
        boolean matched = false;
        try {
            long startTime = System.currentTimeMillis();
            String query = FusekiUtils.getPassword(username);

            //Query the collection, dump output
            QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url"
                    + ".query"), query);
            ResultSet results = qe.execSelect();
            String x = null;
            while (results.hasNext()) {
                QuerySolution qs = results.next();
                Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();

                while (varIter.hasNext()) {
                    Var var = varIter.next();
                    if (var.getVarName().equals("pass"))
                        x = ((ResultBinding) qs).getBinding().get(var).getLiteralValue().toString();
                }
            }
            qe.close();

            matched = PasswordUtils.validatePassword(password, x);
            return matched + " match. Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "false";
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return "false";
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


    @POST
    @Path("upload_image")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseObj uploadImage(@FormDataParam("file_upload") InputStream fileInputStream, @FormDataParam
            ("file_upload") FormDataContentDisposition contentDispositionHeader, @FormDataParam("functional_tests")
    InputStream functionalTestInputStream, @FormDataParam("functional_tests") FormDataContentDisposition
            functionalTestDispositionHeader, @Nullable @QueryParam("category_list") List<String> categoryList,
                                   @QueryParam("image_description") String imageDescription, @QueryParam
                                               ("image_name") String imageName, @QueryParam("pareto_point_x") int
                                               paretoPointIndexX, @QueryParam("pareto_point_y") int
                                               paretoPointIndexY, @QueryParam("pareto_point_id") String
                                               paretoPointId, @Nullable @QueryParam("avatar_id") int avatarID) {
        boolean areValuesSet = true;
        try {
            System.out.println("Uploading image... !");
            String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();


            if (imageDescription == null || imageName == null || paretoPointIndexX == 0 || paretoPointIndexY == 0 ||
                    paretoPointIndexX == 0 || paretoPointId == null) areValuesSet = false;

            // save the file to the server
            saveFile(fileInputStream, filePath);

            // Response.status(200).entity(output).build()
            return new ResponseObj(200, "SUCCESS uploaded on LPT VMI | all attributes set: " + areValuesSet);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj(200, "FAILED uploaded on LPT VMI | all attributes set: " + areValuesSet);
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

    @POST
    @Path("perform_image_synthesis")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ResponseSynthesisObj performImageSynthesis(@QueryParam("cloud") int cloud, @QueryParam("image_id") int
            imageId, @QueryParam("instance_type") String instanceType, @Nullable @QueryParam("security_group_id")
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
            hitsPerPage, @QueryParam("order") String order, @QueryParam("sort") boolean isDescendingSort) {

        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, null, title != null ? ". ?s " +
                "knowledgebase:DiskImage_Title ?title . FILTER regex(?title, \"" + title + "\")" : null);

        ArrayList<EnticeImage> enticeImages = new ArrayList<>();
        int maxSize = diskImages.size() > (paginationPage + 1) * hitsPerPage ? (paginationPage + 1) * hitsPerPage :
                diskImages.size();
        //int count = 0;

        if (maxSize == 0) maxSize = diskImages.size();

        for (int i = paginationPage * hitsPerPage; i < maxSize; i++) {

            List<String> categoryList = new ArrayList<>();

            if (Math.random() < 0.3) categoryList.add("Database");
            if (Math.random() < 0.3) categoryList.add("Monitoring");
            if (Math.random() < 0.3) categoryList.add("Application");
            if (Math.random() < 0.3) categoryList.add("App-Server");
            if (Math.random() < 0.3) categoryList.add("File-Server");
            if (Math.random() < 0.3) categoryList.add("Openstack");
            if (Math.random() < 0.3) categoryList.add("Docker");
            if (Math.random() < 0.3) categoryList.add("Container");
            if (Math.random() < 0.3) categoryList.add("Operating system");
            if (Math.random() < 0.3) categoryList.add("Misc");

            enticeImages.add(new EnticeImage(diskImages.get(i).getId(), (int) (Math.random() * 15), diskImages.get(i)
                    .getTitle(), diskImages.get(i).getRefOwnerId() == null ? "User_" + (int) (1 + Math.random() * 10)
                    : diskImages.get(i).getRefOwnerId(), diskImages.get(i).getDiskImageSize(), (int) (Math.random() *
                    50), categoryList, (int) (Math.random() * 5)));
            //   count++;
        }
        // System.out.println(count);
//        Map<ArrayList<EnticeImage>,Integer> resultMap = new HashMap<>();
//        resultMap.put(enticeImages,diskImages.size());

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
        List<String> categoryList = new ArrayList<>();
        if (Math.random() < 0.3) categoryList.add("Database");
        if (Math.random() < 0.3) categoryList.add("Monitoring");
        if (Math.random() < 0.3) categoryList.add("Application");
        if (Math.random() < 0.3) categoryList.add("App-Server");
        if (Math.random() < 0.3) categoryList.add("File-Server");
        if (Math.random() < 0.3) categoryList.add("Openstack");
        if (Math.random() < 0.3) categoryList.add("Docker");
        if (Math.random() < 0.3) categoryList.add("Container");
        if (Math.random() < 0.3) categoryList.add("Operating system");
        if (Math.random() < 0.3) categoryList.add("Misc");

        List<Fragment> fragmentList = FusekiUtils.getFragmentDataOfDiskImage(imageID, false);
        List<Repository> repositoriesList = FusekiUtils.getAllEntityAttributes(Repository.class);
        Set<String> setOfRepositoryIds = new HashSet<>();
        for (Fragment fragment : fragmentList) {
            setOfRepositoryIds.add(fragment.getRefRepositoryId());
        }

        List<Repository> matchingRepositories = new ArrayList<>();
        for(Repository repository: repositoriesList){
            if(listContainsId(setOfRepositoryIds, repository.getId()))
                 matchingRepositories.add(repository);
        }

        List<String> functionalTests = new ArrayList<>();
        functionalTests.add("dummy functional test 1");
        functionalTests.add("dummy functional test 2");
        functionalTests.add("dummy functional test 3");
        List<String> optimizationHistory = new ArrayList<>();
        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME1");
        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME2");
        optimizationHistory.add("dummy URLS | SIZE | IMAGENAME3");

        return new EnticeDetailedImage(diskImages.get(0).getId(), (int) (Math.random() * 15), diskImages.get(0)
                .getTitle(), diskImages.get(0).getRefOwnerId() == null ? "User_" + (int) (1 + Math.random() * 10) :
                diskImages.get(0).getRefOwnerId(), diskImages.get(0).getDiskImageSize(), (int) (Math.random() * 50),
                categoryList, (int) (Math.random() * 5), matchingRepositories, functionalTests, optimizationHistory);
    }

    private boolean listContainsId(Set<String> setOfRepositoryIds, String id) {
        for(String str: setOfRepositoryIds) {
            if(str.trim().contains(id))
                return true;
        }
        return false;
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

}
