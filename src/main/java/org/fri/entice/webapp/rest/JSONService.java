package org.fri.entice.webapp.rest;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.fri.entice.webapp.cassandra.CassandraService;
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.jena.Fuseki;
import org.fri.entice.webapp.utils.DBUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
public class JSONService implements IMonitoringRequests {

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

    @GET
    @Path("get_user")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.TEXT_PLAIN)
    public String getUser(@QueryParam("user") String username) {
        // subject.checkPermission("protected:read");
        /*
        //Example using most common scenario:
        //String username and password.  Acquire in
        //system-specific manner (HTTP request, GUI, etc)
        System.out.println("user:: " + username);
        UsernamePasswordToken token = new UsernamePasswordToken(username, "neki");

        //"Remember Me" built-in, just do this:
        token.setRememberMe(true);

        //With most of Shiro, you'll always want to make sure you're working with the currently executing user,
        // referred to as the subject
        Subject currentUser = SecurityUtils.getSubject();
        // Session session = currentUser.getSession();


        try {
            //Authenticate the subject by passing
            //the user name and password token
            //into the login method
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println(uae.getMessage());
        } catch (IncorrectCredentialsException ice) {
            System.out.println(ice.getMessage());
        } catch (LockedAccountException lae) {
            System.out.println(lae.getMessage());
        } catch (ExcessiveAttemptsException eae) {
            System.out.println(eae.getMessage());
        } catch (AuthenticationException ae) {
            //unexpected error?
        }
        */
        //No problems, show authenticated view...

        return "lalala";
    }

    @GET
    @Path("get_metric_value_table")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getMetricValueTable(@QueryParam("date_from") String dateFrom) {

        com.datastax.driver.core.ResultSet resultSet = CassandraService.session.execute("select name from " +
                "metric_value_table where mgroup = 'Network' and name = 'netPacketsOut' allow filtering;");

        List<Row> resultList = resultSet.all();
        new Date(UUIDs.unixTimestamp(resultList.get(1).getUUID("event_timestamp")));
        return resultList.size() + "";
    }

    @GET
    @Path("get_all_metrics")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getAllMetrics() {
        com.datastax.driver.core.ResultSet resultSet = CassandraService.session.execute("select * from metric_table;");

        StringBuffer sb = new StringBuffer();
        String formatedRule = "%-35s | %-50s | %10s | %15s | %15s | %10s \n";
        sb.append(String.format(formatedRule, "agentid", "metricid", "mgroup", "name", "type", "units"));
        sb.append(StringUtils.leftPad("", 150, '-') + "\n");


        List<Row> resultList = resultSet.all();
        for (int i = 0; i < resultList.size(); i++) {
            sb.append(String.format(formatedRule, resultList.get(i).getString("agentid"), resultList.get(i).getString
                    ("metricid"), resultList.get(i).getString("mgroup"), resultList.get(i).getString("name"),
                    resultList.get(i).getString("type"), resultList.get(i).getString("units")));
        }

        return sb.toString();
    }

    @GET
    @Path("get_used_metrics")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ListResponse getUsedMetrics() {
        Select select = QueryBuilder.select().column("metricid").column("event_date").distinct().from
                ("metric_value_table");
//                .where(eq("type", "Frienwd"))
//                .and(eq("city", "San Francisco"));
        ResultSet results = CassandraService.session.execute(select);

        List<Row> resulList = results.all();
        Set<String> usedMonitoringData = new HashSet<String>();

        for (int i = 0; i < resulList.size(); i++) {
            usedMonitoringData.add(resulList.get(i).getString("metricid"));
        }
        return null;
//        return new ListResponse(new ArrayList<String>(usedMonitoringData));
    }

    @GET
    @Path("get_existing_metrics")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<MetricTable> getAllMonitoringData() {
        return CassandraService.allMonitoringData;
    }

    @Override
    public String getPeriodMonitoringData(String dateFrom) {
        return null;
    }

    @GET
    @Path("get_cpu_usage_times")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCPUUsageTimes(@QueryParam("min_cpu") String minCPU, @QueryParam("max_cpu") String maxCPU,
                                   @QueryParam("date_from") String dateFrom) {
        if (maxCPU == null) maxCPU = "100.0";

        DateTime dateTime = null;
        if (dateFrom != null) {
            String[] dateSplit = dateFrom.split("\\.");
            // year, month, day, hour, minute, second
            dateTime = new DateTime(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt
                    (dateSplit[0]), 0, 0, 0);
        }

        com.datastax.driver.core.ResultSet resultSet = CassandraService.session.execute("SELECT * FROM " +
                "monitoring_data WHERE " +
                "monitoring_metric = 'CPU_USAGE' and value >= " + minCPU + " and value <= " + maxCPU + " " +
                (dateTime != null ? "and time >= " + dateTime.getMillis() : "") + " limit 1000 " +
                "allow filtering;");

        DecimalFormat df = new DecimalFormat("#.00");
        StringBuffer sb = new StringBuffer();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        List<Row> resulsList = resultSet.all();
        for (int i = 0; i < 20; i++) {
            sb.append(String.format("%s | %s | %s \n", resulsList.get(i).getUUID("id"), df.format(resulsList.get(i)
                    .getDouble("value")), dateFormat.format(resulsList.get(i).getDate("time"))));
        }

        sb.insert(0, "               ID                  |   CPU   | DATE \n");
        sb.insert(0, "\n Result size: " + resulsList.size() + " ; Displayed results: 20\n");
        return sb.toString();
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
        return Fuseki.getAllImages();
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
            List<DiskImage> ldi = Fuseki.getImagesWithGivenId(curId);
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
        Fuseki.insertDiskImage(uniqueID, im);
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
        return Fuseki.getDiskImagesWithGivenType(it);
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
            Fuseki.deleteDiskImage(curId);
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

            String insertStatementStr = Fuseki.generateInsertVMStatement(contentDispositionHeader.getFileName(),
                    true, "guest", doOptimize);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr), "http://localhost:3030/ds/update");
            upp.execute();

            // save the file to the server
            saveFile(fileInputStream, filePath);

            //todo: connect with UIBK service

            output = "Script file saved to server location : " + filePath + "<br>Perform optimization: " + doOptimize;
        }
        return Response.status(200).entity(output).build();
    }



    @GET
    @Path("get_image_list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImageObj> getImageList(@QueryParam("optimized") Boolean optimized) {
        //Query the collection, dump output
        String query = Fuseki.getAllUploadedImages(optimized);
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
        com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

        List<String> idsList =  DBUtils.parseSubjectResult(results);
        List<ImageObj> imageObjs = new ArrayList<ImageObj>();
        for(String id : idsList){
            String query2 = Fuseki.getAllAttributesMatchingSubjectID(id);
            qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query2);
            results = qe.execSelect();
            List<ResultObj>  resultObjs =  DBUtils.parsePredicateObjectResult(id,results);

            ImageObj imageObj = new ImageObj(id,false,"",false);
            for(ResultObj ro : resultObjs){
                if(ro.getP().equals(DBUtils.OBJECT_PREFIX + "filename"))
                    imageObj.setFilename(ro.getO());
                else if(ro.getP().equals(DBUtils.OBJECT_PREFIX + "is_script"))
                    imageObj.setIsScript(ro.getO().contains("true"));
                else if(ro.getP().equals(DBUtils.OBJECT_PREFIX + "owner"))
                    imageObj.setOwner(ro.getO());
                else if(ro.getP().equals(DBUtils.OBJECT_PREFIX + "optimize"))
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
            ;
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

}