package org.fri.entice.webapp.rest;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import org.apache.commons.lang3.SystemUtils;
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.uibk.client.IUibkService;
import org.fri.entice.webapp.uibk.client.UibkService;
import org.fri.entice.webapp.util.DBUtils;
import org.fri.entice.webapp.util.FusekiUtils;
import org.fri.entice.webapp.util.PasswordUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.subject.Subject;

//import com.hp.hpl.jena.query.ResultSet;

/**
 * This class will represent the "main resource" - the list of all "VMimages" - it will be accessible at the URL:
 * http://localhost:8080/CcgMiniV01/minimini/images
 * <p/>
 * Additionally it will provide some "extra" methods/paths (resources) for uploading images etc...
 */
@Path("/service/")
//@Path("/images")
//@RequiresPermissions("protected:read")
public class JSONService implements IUserService {

    // Allows to insert contextual objects into the class (e.g. ServletContext, Request, Response, UriInfo)
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    /**
     * Test GET request with JSON response
     *
     * @return List of ResultObj objects
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ResultObj> getTrackInJSON() {

        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", "SELECT * WHERE " +
                "{?x" + " ?r ?y}");
        com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

        List<ResultObj> resultObjs = new ArrayList<ResultObj>();

        // For each solution in the result set
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();
            String x = null;
            String r = null;
            String y = null;
            while (varIter.hasNext()) {
                Var var = varIter.next();
                if (var.getVarName().equals("x")) x = ((ResultBinding) qs).getBinding().get(var).toString();
                else if (var.getVarName().equals("r")) r = ((ResultBinding) qs).getBinding().get(var).toString();
                else if (var.getVarName().equals("y")) y = ((ResultBinding) qs).getBinding().get(var).toString();
            }

            resultObjs.add(new ResultObj(x, r, y));
        }

        return resultObjs;
    }

    /**
     * Return the list of of all DiskImages for standalone clients (not browser)
     * when a GET HTTP request will arrive at URL http://localhost:8080/CcgMiniV01/mini/images/ - the function
     * getImages() will be called
     * depending on the datatype requested the function will return XML or JSON data
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<DiskImageResource> getImages() {
        return FusekiUtils.getAllImages();
    }

    /**
     * returns the specified VMimages (input JSON, output JSON) - which are specified by a list of ID-s of images.
     * Use http://localhost:8080/CcgMiniV01/minimini/images/returnSpecifiedImages to get a list of "VMimages" you
     * specified (by providing their IDs).
     */
    @POST
    @Path("returnSpecifiedImages")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<DiskImageResource> returnSpecifiedImages(ArrayList<String> list) {
        List<DiskImageResource> specimgs = new ArrayList<DiskImageResource>();
        for (int i = 0; i < list.size(); i++) {
            String curId = list.get(i);
            //System.out.println("Poslani id je: "+curId);
            //TODO: a separate SPARQL query for every ID will be executed - maybe this is not the way to go but I do
            // not know...
            //we pass an ID but maybe there are more images with the same ID in the cassandra (this should not happen
            // by the way) - so this is why a list is returned
            List<DiskImage> ldi = FusekiUtils.getImagesWithGivenId(curId);
            for (int j = 0; j < ldi.size(); j++) {
                DiskImageResource dir = new DiskImageResource(curId, ldi.get(j));
                specimgs.add(dir);
            }
        }
        return specimgs;
    }

    /**
     * When a POST HTTP request requiring MediaType.APPLICATION_JSON will arrive at URL:
     * http://localhost:8080/CcgMiniV01/minimini/images/uploadImage - the function newImageJson() will be called
     * this request is carrying the JSON data (name, owner,...).
     */
    @POST
    @Path("uploadImage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //the method takes parameters from the request, makes a new "Image" and puts it into our data model (=ArrayList
    // defined in ImageDao.java)
    //also the method returns JSON data - the id and location of the image
    public UploadResponse newImageJson(DiskImage im) throws IOException {
        //here we need to compute the Image ID
        String uniqueID = UUID.randomUUID().toString();
        //we execute the SPARQL query for image insertion
        FusekiUtils.insertDiskImage(uniqueID, im);
        //and we return the JSON data (=image id and location)
        UploadResponse ur = new UploadResponse(uniqueID, im.getIri());
        return ur;
    }

    /**
     * Returns the VMimages that we have searched for (free text search) - (input JSON, output JSON).
     * Use http://localhost:8080/CcgMiniV01/minimini/images/returnSearchedImages to get a list of "VMimages" you
     * searched for (by providing keywords).
     */
    @POST
    @Path("returnSearchedImages")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<DiskImage> returnSearchedImages(ArrayList<String> list) {
        List<DiskImage> searchedimgs = new ArrayList<DiskImage>();
        //for the moment we will use only one search keyword (but later user will be able to provide a list of keywords)
        //so for the time being I will concatenate all the words in a list together - and make it as one word
        String concatenatedKeyword = "";
        for (int i = 0; i < list.size(); i++) {
            String curKeyword = list.get(i);
            concatenatedKeyword = concatenatedKeyword + " " + curKeyword;
        }
        System.out.println(concatenatedKeyword);
        //TODO: we need to produce a SPARQL query here and construct from the results VMimage-s, put them in a list
        // and return

        return searchedimgs;
    }

    /**
     * Returns the VMimages that have the specified ImageType (VMI or CI) - (input JSON, output JSON).
     * Use http://localhost:8080/CcgMiniV01/mini/images/returnImagesWithGivenType to get a list of "VMimages" that
     * have the provided type (VMI or CI).
     */
    @POST
    @Path("returnImagesWithGivenType")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<DiskImageResource> returnImagesWithGivenType(ImageType it) {
        return FusekiUtils.getDiskImagesWithGivenType(it);
    }

    /**
     * Deletes/removes the specified DiskImages - (input JSON list of IDs of images to be deleted, output JSON).
     * Use http://localhost:8080/CcgMiniV01/minimini/images/returnSearchedImages to get a list of "VMimages" you
     * searched for (by providing keywords).
     */
    @POST
    @Path("deleteSpecifiedImages")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int deleteSpecifiedImages(ArrayList<String> list) {
        //TODO: the numbef of deleted images should contain the number of actually deleted images! because we can
        // provide ID of nonexistent image - and this nonexistent image cannot be deleted from cassandra - but
        // FUSEKI/SPARQL does not complain at all (no exception)
        int numberOfDeletedImages = 0;
        for (int i = 0; i < list.size(); i++) {
            String curId = list.get(i);
            FusekiUtils.deleteDiskImage(curId);
            numberOfDeletedImages++;
        }
        return numberOfDeletedImages;
    }

    private static final String SERVER_UPLOAD_LOCATION_FOLDER = SystemUtils.IS_OS_LINUX ? "/home/sandi/uploads/" :
            "C://Users/sandig/Desktop/";

    /**
     * Upload a File
     */

    // todo: upload (image, script) + upload and optimize, list uploaded VMS
    @POST
    @Path("/upload_image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file")
    FormDataContentDisposition contentDispositionHeader, @FormDataParam("optimization") String optimize) {
        boolean doOptimize = new Boolean(optimize);
        System.out.println("Uploading image... !");
        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();

        // save the file to the server
        saveFile(fileInputStream, filePath);

        String output = "File saved to server location : " + filePath + "<br>Perform optimization: " + doOptimize;


        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/async_test")
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
        // TODO test it and implement it in method "uploadImageScript"
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = veryExpensiveOperation();
                asyncResponse.resume(result);
            }

            private String veryExpensiveOperation() {
                long l = 0;
                for (int i = 0; i < 1000000000; i++) {
                    //for (int j = 0; j < 1000000000; j++) {
                    for (int j = 0; j < 10; j++) {
                        l++;
                    }
                    //}
                }
                return "yes " + l;
            }
        }).start();
    }

    @POST
    @Path("/upload_vm_script")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImageScript(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file")
    FormDataContentDisposition contentDispositionHeader, @FormDataParam("optimization") String optimize) {
        boolean doOptimize = new Boolean(optimize);
        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();
        String output = null;
        if (contentDispositionHeader.getFileName().isEmpty()) {
            output = "Error: upload file failed! No file script specified!";
        }
        else {
            System.out.println("Uploading image script... !");

            String insertStatementStr = FusekiUtils.generateInsertVMStatement(contentDispositionHeader.getFileName(),
                    true, "guest", doOptimize);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                    "http://localhost:3030/ds/update");
            upp.execute();

            // save the file to the server
            saveFile(fileInputStream, filePath);

            IUibkService uibkService = new UibkService();
            File vmImage = new File(filePath);

            // call UIBK add_interface service
            uibkService.addInterface(vmImage);

            output = "Script file saved to server location : " + filePath + "<br>Perform optimization: " + doOptimize;
        }
        return Response.status(200).entity(output).build();
    }


    @GET
    @Path("get_image_list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImageObj> getImageList(@QueryParam("optimized") Boolean optimized) {
        //Query the collection, dump output
        String query = FusekiUtils.getAllUploadedImages(optimized);
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
        com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

        List<String> idsList = DBUtils.parseSubjectResult(results);
        List<ImageObj> imageObjs = new ArrayList<ImageObj>();
        for (String id : idsList) {
            String query2 = FusekiUtils.getAllAttributesMatchingSubjectID(id);
            qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query2);
            results = qe.execSelect();
            List<ResultObj> resultObjs = DBUtils.parsePredicateObjectResult(id, results);

            ImageObj imageObj = new ImageObj(id, false, "", false);
            for (ResultObj ro : resultObjs) {
                if (ro.getP().equals(DBUtils.OBJECT_PREFIX + "filename")) imageObj.setFilename(ro.getO());
                else if (ro.getP().equals(DBUtils.OBJECT_PREFIX + "is_script"))
                    imageObj.setIsScript(ro.getO().contains("true"));
                else if (ro.getP().equals(DBUtils.OBJECT_PREFIX + "owner")) imageObj.setOwner(ro.getO());
                else if (ro.getP().equals(DBUtils.OBJECT_PREFIX + "optimize"))
                    imageObj.setOptimize(ro.getO().contains("true"));
            }

            imageObjs.add(imageObj);
        }
        ResultSetFormatter.out(System.out, results);
        qe.close();

        return imageObjs;
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

    @GET
    @Path("perform_user_login")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String performUserLogin(@QueryParam("username") String username, @QueryParam("password") String password) {
        boolean matched = false;
        try {
            String query = FusekiUtils.getPassword(username);

            //Query the collection, dump output
            QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url.query")
                    , query);
            com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
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
            return matched + " match";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "false";
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public String performUserLogout(String sessionID) {
        return null;
    }

    @POST
    @Path("register_user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response registerUser(User user) {
        try {
            String generatedSecuredPasswordHash = PasswordUtils.generateStorngPasswordHash(user.getPassword());
            user.setPassword(generatedSecuredPasswordHash);

            String insertStatementStr = FusekiUtils.generateInsertObjectStatement(user);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                    AppContextListener.prop.getProperty("fuseki.url.update"));
            upp.execute();
            // ON SUCCESS USER CREATION
            return Response.status(200).entity(true).type("text/plain").build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // ON ERROR WHILE CREATION ! !
            return Response.status(200).entity(false).type("text/plain").build();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            // ON ERROR WHILE CREATION ! !
            return Response.status(200).entity(false).type("text/plain").build();

        }

    }
}