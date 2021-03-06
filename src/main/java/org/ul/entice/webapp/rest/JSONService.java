package org.ul.entice.webapp.rest;

import org.apache.commons.lang3.SystemUtils;
import org.apache.jena.query.*;
import org.apache.jena.sparql.core.ResultBinding;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;
import org.ul.common.DBUtils;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.entry.*;
import org.ul.entice.webapp.entry.client.ResponseObj;
import org.ul.entice.webapp.uibk.client.IUibkService;
import org.ul.entice.webapp.uibk.client.UibkService;
import org.ul.entice.webapp.util.FusekiUtils;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Path("/service/")
public class JSONService implements IUserService, IService {

    // Allows to insert contextual objects into the class (e.g. ServletContext, Request, Response, UriInfo)
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    private String KB_PREFIX = "http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#";

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
        ResultSet results = qe.execSelect();

        List<ResultObj> resultObjs = new ArrayList<ResultObj>();

        // For each solution in the result set                        c
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
    public ResponseObj uploadImage(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file")
            FormDataContentDisposition contentDispositionHeader, @FormDataParam("optimization") String optimize) {
        boolean doOptimize = new Boolean(optimize);
        System.out.println("Uploading image... !");
        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();

        // save the file to the server
        saveFile(fileInputStream, filePath);

        String output = "File saved to server location : " + filePath + "<br>Perform optimization: " + doOptimize;

        // Response.status(200).entity(output).build()
        return new ResponseObj(200, "repositoryID");
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
        } else {
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
            uibkService.addInterface(vmImage, AppContextListener.prop.getProperty("uibk.distribution.url"));

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
        ResultSet results = qe.execSelect();

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
    @Path("get_all_repositories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Repository> getAllRepositories() {
        List<Repository> repositories = FusekiUtils.getAllEntityAttributes(Repository.class,this);

        // MO specific redistribution parameters
        repositories.get(0).setTheoreticalCommunicationalPerformance(2030);
        repositories.get(1).setTheoreticalCommunicationalPerformance(145);
        repositories.get(2).setTheoreticalCommunicationalPerformance(198);
        repositories.get(3).setTheoreticalCommunicationalPerformance(250);
        repositories.get(4).setTheoreticalCommunicationalPerformance(900);
        repositories.get(5).setTheoreticalCommunicationalPerformance(500);
        repositories.get(6).setTheoreticalCommunicationalPerformance(1000);
        repositories.get(7).setTheoreticalCommunicationalPerformance(1200);
        repositories.get(8).setTheoreticalCommunicationalPerformance(1200);

        return repositories;
   }

    @GET
    @Path("get_all_repositories2")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Repository> getAllRepositories2() {


        List<Repository> repositories = FusekiUtils.getAllEntityAttributes(Repository.class,this);
        List<Repository> res = new ArrayList<>();
        //        0 - WT, 1 - SZTAKI, 2 - AMAZON, 3 - UIBK
        res.add(repositories.get(8));
        res.add(repositories.get(0));
        res.add(repositories.get(2));
        res.add(repositories.get(1));

        // added specific MO parameters for redistribution
        res.get(0).setTheoreticalCommunicationalPerformance(2030);
        res.get(0).setPriorityLevel1Cost(0.021);

        res.get(1).setTheoreticalCommunicationalPerformance(145);
        res.get(1).setPriorityLevel1Cost(0.021);

        res.get(2).setTheoreticalCommunicationalPerformance(198);
        res.get(2).setPriorityLevel1Cost(0.028);

        res.get(3).setTheoreticalCommunicationalPerformance(250);
        res.get(3).setPriorityLevel1Cost(0.03);
        return res;
   }

    @GET
    @Path("get_all_geolocations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Geolocation> getAllGeolocations() {
        return FusekiUtils.getAllEntityAttributes(Geolocation.class, this);
    }

    @GET
    @Path("get_all_disk_images")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DiskImage> getAllDiskImages(@QueryParam("deep") Boolean deep) {
        List<DiskImage> diskImages = FusekiUtils.getAllEntityAttributes(DiskImage.class, this);

        if (deep != null && deep == true) {
            for (DiskImage diskImage : diskImages) {
                List<Fragment> fragmentList = FusekiUtils.getIdEntityAttributes(Fragment.class, this, diskImage.getId());
                diskImage.setFragmentList(fragmentList);
            }
        }

        return diskImages;
    }

    @GET
    @Path("get_fragment_data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fragment> getFragmentData(@QueryParam("disk_image_id") String diskImageId, @QueryParam
            ("show_history_data") Boolean showHistoryData) {
        return FusekiUtils.getFragmentDataOfDiskImage(diskImageId, showHistoryData, this);
    }


    @GET
    @Path("get_fragment_history_data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fragment> get_fragment_history_data(@QueryParam("disk_image_id") String diskImageId, @QueryParam
            ("show_history_data") Boolean showHistoryData) {
        String queryCondition = null;
        if (diskImageId != null)
            queryCondition = ".?s knowledgebase:Fragment_hasReferenceImage knowledgebase:" + diskImageId + " ";

        List<Fragment> fragmentList = FusekiUtils.getAllEntityAttributes(Fragment.class, this, queryCondition);

        if (showHistoryData != null && showHistoryData == true) {
            for (Fragment fragment : fragmentList) {
                String selectQuery = FusekiUtils.getHistoryDataIDs(fragment.getId());

                QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki" +
                        ".url" + "" + ".query"), selectQuery);
                ResultSet results = qe.execSelect();

                List<ResultObj> resultIds = FusekiUtils.getResultObjectListFromResultSet(results);
                for (ResultObj obj : resultIds) {
                    selectQuery = FusekiUtils.getAllEntitiesQuery(HistoryData.class.getSimpleName(), obj.getO());
                    qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url.query"),
                            selectQuery);
                    results = qe.execSelect();

                    List<ResultObj> historyDataResultObj = FusekiUtils.getResultObjectListFromResultSet(results);

                    // fill the subject to result list obj.   ; REMOVE when query is optimized
                    for (ResultObj ro : historyDataResultObj) {
                        ro.setS(obj.getO());
                    }

                    List<HistoryData> historyDataList = FusekiUtils.mapResultObjectListToEntry(HistoryData.class,
                            historyDataResultObj, this);

                    fragment.setHistoryDataList(historyDataList);
                }
            }
        }

        return fragmentList;
    }

    @GET
    @Path("get_fragment_history_delivery_data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FragmentHistoryDeliveryData> get_fragment_history_data(@QueryParam("disk_image_id") final String
                                                                               diskImageId, @QueryParam
                                                                               ("valid_date_from") final String validDateFrom, @QueryParam("valid_date_to") final String validDateTo) {

        final List<FragmentHistoryDeliveryData> fragmentHistoryDeliveryDatas = new
                ArrayList<FragmentHistoryDeliveryData>();
        List<String> diskImageIds = new ArrayList<String>();


        try {
            if (diskImageId != null) {
                diskImageIds.add(diskImageId);
            } else {
                String allDiskImagesQuery = FusekiUtils.getDiskImageIDs();
                QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki" +
                        ".url" + "" + ".query"), allDiskImagesQuery);
                ResultSet results = qe.execSelect();
                List<ResultObj> resultIds = FusekiUtils.getResultObjectListFromResultSet(results);
                for (ResultObj resultObj : resultIds) {
                    diskImageIds.add(resultObj.getS().replace(FusekiUtils.KB_PREFIX_SHORT, ""));
                }
            }

            final CountDownLatch countDownLatch = new CountDownLatch(diskImageIds.size());

            // optimize for list queries
            for (final String diskImageID : diskImageIds) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String queryCondition = ".?s knowledgebase:Fragment_hasReferenceImage knowledgebase:" +
                                    diskImageID + " ";

                            List<Fragment> fragmentIdList = FusekiUtils.getAllEntityAttributes(Fragment.class, JSONService.this,
                                    queryCondition);

                            List<Delivery> deliveryList = FusekiUtils.getAllEntityAttributes(Delivery.class, JSONService.this, ".?s " +
                                    "knowledgebase:Delivery_hasDeliveredDiskImage \"" + diskImageID + "\" ");
                            int deliveryIndex = 0;
                            for (Fragment fragment : fragmentIdList) {
                                String selectQuery = FusekiUtils.getHistoryDataIDs(fragment.getId());

                                QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop
                                        .getProperty("fuseki" +
                                                ".url" + "" + ".query"), selectQuery);
                                ResultSet results = qe.execSelect();

                                List<ResultObj> resultIds = FusekiUtils.getResultObjectListFromResultSet(results);
                                for (ResultObj obj : resultIds) {
                                    selectQuery = FusekiUtils.getAllEntitiesQuery(HistoryData.class.getSimpleName(), obj
                                            .getO());
                                    qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki" +
                                            ".url" + ".query"), selectQuery);
                                    results = qe.execSelect();

                                    List<ResultObj> historyDataResultObj = FusekiUtils.getResultObjectListFromResultSet
                                            (results);

                                    // fill the subject to result list obj.   ; REMOVE when query is optimized
                                    for (ResultObj ro : historyDataResultObj) {
                                        ro.setS(obj.getO());
                                    }

                                    List<HistoryData> historyDataList = FusekiUtils.mapResultObjectListToEntry
                                            (HistoryData.class, historyDataResultObj, JSONService.this);

                                    for (HistoryData historyDataObj : historyDataList) {
                                        if (deliveryList.size() > 0) {
                                            // filter results that are not in the selected interval
                                            if (validDateFrom != null && validDateTo != null)
                                                selectQuery = FusekiUtils.getAllEntitiesQuery(HistoryData.class
                                                        .getSimpleName(), obj.getO(), validDateFrom, validDateTo);
                                            else
                                                selectQuery = FusekiUtils.getAllEntitiesQuery(HistoryData.class
                                                        .getSimpleName(), obj.getO());

                                            qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty
                                                    ("fuseki.url" + ".query"), selectQuery);
                                            results = qe.execSelect();
                                            List<ResultObj> tmpObj = FusekiUtils.getResultObjectListFromResultSet(results);
                                            if (tmpObj.size() > 0) {
                                                FragmentHistoryDeliveryData fragmentHistoryDeliveryData = new
                                                        FragmentHistoryDeliveryData(historyDataObj.getId(), fragment
                                                        .getId(), fragment.getRefDiskImageId(), (deliveryIndex != -1 ?
                                                        deliveryList.get(deliveryIndex).getRequestTime() : null),
                                                        deliveryList.get(deliveryIndex).getDeliveryTime(),
                                                        historyDataObj.getLocation(), deliveryList.get(deliveryIndex)
                                                        .getTargetCloud(),fragment.getCopyIdentification());
                                                if (deliveryIndex < deliveryList.size())
                                                    deliveryIndex += 1;
                                                System.out.println("Delivery size " + deliveryList.size() + " | " + deliveryIndex);
                                                fragmentHistoryDeliveryDatas.add(fragmentHistoryDeliveryData);
                                            }
                                        }
                                    }
                                    fragment.setHistoryDataList(historyDataList);
                                }
                            }
                            countDownLatch.countDown();
                        } catch (Exception e) {
                            System.out.println("Error in thread: " + e.getMessage());
                            countDownLatch.countDown();
                        }
                    }
                }).run();
                System.out.println("processing id " + diskImageID);
            }

            countDownLatch.await();
            System.out.println("finito");
            return fragmentHistoryDeliveryDatas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int checkIfDeliveryMatchesTheFragmentInterval(List<Delivery> deliveryList, DateTime validFrom, DateTime
            validTo) {
        int i = 0;
        for (Delivery delivery : deliveryList) {
            final DateTime deliveryTime = new DateTime(delivery.getDeliveryTime());
            if (deliveryTime.isAfter(validFrom) && deliveryTime.isBefore(validTo)) return i;
            i++;
        }

        return 0;   //hack: just use first value instead of -1
    }


    @GET
    @Path("get_delivery_data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Delivery> getDeliveryData(@QueryParam("disk_image_id") String diskImageId) {
        String queryCondition = null;
        if (diskImageId != null)
            queryCondition = ".?s knowledgebase:Delivery_hasDeliveredDiskImage knowledgebase:" + diskImageId + " ";
//            queryCondition = ".?s knowledgebase:Delivery_hasDeliveredDiskImage \"" + diskImageId + "\"";

        return FusekiUtils.getAllEntityAttributes(Delivery.class, this, queryCondition);
    }

    /**
     * returns the specified VMimages (input JSON, output JSON) - which are specified by a list of ID-s of images.
     * Use http://localhost:8080/CcgMiniV01/minimini/images/returnSpecifiedImages to get a list of "VMimages" you
     * specified (by providing their IDs).
     */

    // example for stage 3:
    // curl -H "Content-Type: application/json" -X POST -d '{"variables":[[1]],"stage":3,"objectives":[[0.039708, 93.52273420297443]]}' http://localhost:8080/JerseyREST/rest/service/storePareto

    @POST
    @Path("storePareto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean storePareto(Pareto pareto) {
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

    @POST
    @Path("storeParetoReplica")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean storeParetoReplica(ReplicationObj replicationObj) {
        try {
            String insertStatement = FusekiUtils.generateInsertObjectStatement(replicationObj);
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
    @Path("get_pareto")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pareto> getPareto() {
        return FusekiUtils.getAllEntityAttributes(Pareto.class, this);
    }

    @GET
    @Path("get_cloud_resource")
    @Produces(MediaType.APPLICATION_XML)
    public CloudResource getCloudResource(@QueryParam("cloud_provider_id") String cloudProviderID) {
        try {
            // TODO: retrieve the cloud resource attributes from DRIP KB
            List<String> strings = new ArrayList<String>();
            strings.add("first element");
            strings.add("second element");
            strings.add("third element");
            return new CloudResource("some cloud provider", 3.5, strings);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFusekiQuery() {
        return AppContextListener.prop.getProperty("fuseki.url.query");
    }
}