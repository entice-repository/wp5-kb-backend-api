package org.fri.entice.webapp.util;

//web interface to FusekiUtils - http://localhost:3030/fuseki.html

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.fri.entice.webapp.entry.*;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FusekiUtils {

    private static String KB_PREFIX = "knowledgebase:<http://www.semanticweb" + "" +
            ".org/project-entice/ontologies/2015/7/knowledgebase#>";
    private static String OWL_PREFIX = "owl:<http://www.w3.org/2002/07/owl#>";

    public static String generateInsertVMStatement(String vmFilename, boolean isScript, String owner, boolean
            optimize) {
        String id = UUID.randomUUID().toString();
        return String.format("PREFIX dc: <http://purl.org/dc/elements/1.1/>" + "INSERT DATA" +
                "{ <http://example/%s>    dc:filename    \"%s\" ; dc:is_script \"%s\" ;  dc:owner  \"%s\" ; " +
                "dc:optimize \"%s\" .}   ", id, vmFilename, isScript, owner, optimize);
    }

    public static String generateInsertObjectStatement(Object obj) {
        try {
            String id = UUID.randomUUID().toString();

            // CREATE USER
            if (obj instanceof User) {
                User user = (User) obj;
                user.setId(id);
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s  a knowledgebase:User , owl:NamedIndividual ;" +
                        "     knowledgebase:User_Email        \"%s\" ;\n" +
                        "        knowledgebase:User_FullName     \"%s\" ;\n" +
                        "        knowledgebase:User_PhoneNumber  \"%s\" ;\n" +
                        "        knowledgebase:User_UserName     \"%s\" ;" +
                        "}", user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user
                        .getUsername(), user.getPassword());
                // OLD:
                //return String.format("PREFIX user:<http://www.semanticweb.org/project-entice/ontologies/User/>
                // INSERT " +
                // "DATA {" +
                // "user:%s user:User_FullName \"%s\" ;" +
                // "user:User_Email \"%s\" ; user:User_PhoneNumber \"%s\" ; user:User_UserName \"%s\" ; " +
                // "user:User_Password \"%s\" . }", user.getId(), user.getFullName(), user.getEmail(), user
                // .getPhoneNumber(), user.getUsername(), user.getPassword());
            }
            // CREATE DISK IMAGE
            else if (obj instanceof DiskImage) {
                DiskImage diskImage = (DiskImage) obj;
                diskImage.setId(id);
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:DiskImage, owl:NamedIndividual, knowledgebase:" +
                                (diskImage.getImageType().equals(ImageType.CI) ? "CI" : "VMI") + " ; " +
                                "knowledgebase:DiskImage_Description \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Encryption  %s ;\n" +
                                "knowledgebase:DiskImage_FileFormat  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Owner  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_DataId  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_refFunctionalityId  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_generationTime  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_IRI  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_NeedsDataFile  %s ;\n" +
                                "knowledgebase:DiskImage_Obfuscation  %s ;\n" +
                                "knowledgebase:DiskImage_OperatingSystem  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Picture  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Predecessor  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Price  %s ;\n" +
                                "knowledgebase:DiskImage_Quality  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_SLA  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Title  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Version  \"%s\" ;\n" +
                                "}", diskImage.getId(), diskImage.getDescription(), diskImage.getEncryption(),
                        diskImage.getFileFormat().getValue(), diskImage.getRefOwnerId(), diskImage.getDataId(),
                        diskImage.getRefFunctionalityId(), diskImage.getGenerationTime(), diskImage.getIri(),
                        diskImage.isNeedsData(), diskImage.isObfuscation(), diskImage.getRefOperatingSystemId(),
                        diskImage.getPictureUrl(), diskImage.getPredecessor(), diskImage.getPrice(), diskImage
                                .getRefQualityId(), diskImage.getRefSlaId(), diskImage.getTitle(), diskImage
                                .getVersion());
            }
            // CREATE DISK IMAGE SLA
            else if (obj instanceof DiskImageSLA) {
                DiskImageSLA diskImageSLA = (DiskImageSLA) obj;
                diskImageSLA.setId(id);
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:DiskImage, owl:NamedIndividual, knowledgebase:" +
                        "knowledgebase:DiskImageSLA_hasAgreedAvailabilityCountry  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedAvailabilityRepository  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedRestriction  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedPriorityLevel  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedQoSOrder  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_SecuredDelivery  \"%s\" ;\n" +
                        "}", diskImageSLA.getId());   //TODO
            }
            // CREATE FRAGMENT
            else if (obj instanceof Fragment) {
                Fragment fragment = (Fragment) obj;
                fragment.setId(id);
                String hashValues = new String();
                for (String val : fragment.getHashValue()) {
                    hashValues += "\"" + val + "\",";
                }

                if (hashValues.length() > 0) hashValues = hashValues.substring(0, hashValues.length() - 1);

                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:Fragment, owl:NamedIndividual ;" +
                        "knowledgebase:Fragment_hasReferenceImage \"%s\" ;\n" +
                        "knowledgebase:Fragment_hasRepository \"%s\" ;\n" +
                        "knowledgebase:Fragment_IRI \"%s\" ;\n" +
//                        "knowledgebase:Fragment_ReferenceImage \"%s\" ;\n" +
                        "knowledgebase:Fragment_Size %s ;\n" +
                        "knowledgebase:Fragment_HashValues " + hashValues + " ;\n" +
                        "}", fragment.getId(), fragment.getRefDiskImageId(), fragment.getRefRepositoryId(), fragment
                        .getAnyURI(), fragment.getFragmentSize());
            }
            // CREATE DELIVERY
            else if (obj instanceof Delivery) {
                Delivery delivery = (Delivery) obj;
                delivery.setId(id);
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Delivery, owl:NamedIndividual ;" +
                                "knowledgebase:Delivery_hasDeliveredDiskImage \"%s\" ;\n" +
                                "knowledgebase:Delivery_hasFunctionality \"%s\" ;\n" +
                                "knowledgebase:Delivery_hasTargetRepository \"%s\" ;\n" +
                                "knowledgebase:Delivery_hasUser \"%s\" ;\n" +
                                "knowledgebase:Delivery_DeliveryTime \"%s\" ;\n" +
                                "knowledgebase:Delivery_RequestTime \"%s\" ;\n" +
                                "}", delivery.getId(), delivery.getRefDiskImageId(), delivery.getRefDiskImageId(),
                        delivery.getRefFunctionalityId(), delivery.getRefTargetRepositoryId(), delivery.getRefUserId
                                (), delivery.getDeliveryTime(), delivery.getRequestTime());
            }
            // CREATE FUNCTIONALITY
            else if (obj instanceof Functionality) {
                Functionality functionality = (Functionality) obj;
                functionality.setId(id);
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Functionality, owl:NamedIndividual ;" +
                                "knowledgebase:Functionality_hasImplementation \"%s\" ;\n" +
                                "knowledgebase:Functionality_Classification \"%s\" ;\n" +
                                "knowledgebase:Functionality_Description \"%s\" ;\n" +
                                "knowledgebase:Functionality_Domain \"%s\" ;\n" +
                                "knowledgebase:Functionality_InputDescription \"%s\" ;\n" +
                                "knowledgebase:Functionality_Name \"%s\" ;\n" +
                                "knowledgebase:Functionality_OutputDescription \"%s\" ;\n" +
                                "knowledgebase:Functionality_Tag \"%s\" ;\n" +
                                "}", functionality.getId(), functionality.getRefImplementationId(), functionality
                        .getClassification(), functionality.getInputDescription(), functionality.getDomain(),
                        functionality.getInputDescription(), functionality.getName(), functionality
                                .getOutputDescription(), functionality.getTag());
            }
            //todo: add other objects
            else {
                throw new InvalidObjectException("The selected object type is not currently supported!");
            }
        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAllUploadedImages(Boolean optimizedOnly) {
//        return "SELECT DISTINCT ?s\n" +
//                "WHERE { ?s ?val  ?obj  FILTER regex(str(?s), \"http://example/\")   }\n" +
//                "LIMIT 100";
        if (optimizedOnly == null) return "SELECT DISTINCT ?s\n" +
                "WHERE { ?s ?p  ?o  FILTER regex(str(?s), \"http://example/\")   }\n" +
                "LIMIT 25";
        else return "SELECT DISTINCT ?s\n" +
                "WHERE { ?s <http://purl.org/dc/elements/1.1/optimize> \"" + (optimizedOnly ? "true" : "false") + "\"" +
                " . FILTER regex(str(?s), \"http://example/\")  }\n" +
                "LIMIT 25";
    }

    public static String getAllAttributesMatchingSubjectID(String id) {
        return "SELECT ?p ?o\n" +
                "WHERE { <" + id + "> ?p  ?o . }\n" +
                "LIMIT 25";
    }

    public static final String myNamespacePrefix = "http://entice-project/jernej/test/";

    //this method should insert an DiskImage (VMI or CI image) into cassandra - this means it should create all the
    // triples that are needed from the data model:
    //1) VMimage01 isA VMimage
    //2) VMimage01 imageType "CI"
    //3) VMimage01 description "Tole je opis VM1"
    //4) and so on......
    public static void insertDiskImage(String uniqueID, DiskImage di) {
        String endpoint = "http://localhost:3030/ds/update";

        String queryString = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + "> " +
                "INSERT { " +
                "f:" + uniqueID + " " +
                "f:imageType \"" + di.getImageType() + "\" ; " +
                "f:imageDescription \"" + di.getDescription() + "\" ; " +
                "f:imageTitle \"" + di.getTitle() + "\" ; " +
                "f:imagePredecessor \"" + di.getPredecessor() + "\" ; " +
                "f:imageFileFormat \"" + di.getFileFormat() + "\" ; " +
                "f:imagePictureUrl \"" + di.getPictureUrl() + "\" ; " +
                "f:imageEncription \"" + di.getEncryption() + "\" ; " +
                "f:imageIri \"" + di.getIri() + "\" ; " +
                "f:imageSlaId \"" + di.getRefSlaId() + "\" ; " +
                "f:imagePrice \"" + di.getPrice() + "\" ; " +
                "f:imageOwnerId \"" + di.getRefOwnerId() + "\" ; " +
                "f:imageFunctionallityId \"" + di.getRefFunctionalityId() + "\" ; " +
                "f:imageQualityId \"" + di.getRefQualityId() + "\" ; " +
                "f:imageOperatingSystemId \"" + di.getRefOperatingSystemId() + "\" ; " +
                "f:imageNeedsData \"" + di.isNeedsData() + "\" ; " +
                "f:imageGenerationTime \"" + di.getGenerationTime() + "\" ; " +
                "f:imageObfuscation \"" + di.getObfuscation() + "\"" +
                "} WHERE {?s ?p ?o}";
        UpdateRequest query = UpdateFactory.create(queryString);
        UpdateProcessor qe = UpdateExecutionFactory.createRemoteForm(query, endpoint);
        qe.execute();

        //TODO: Maybe I can pack these two SPARQL queries into one
        String queryStr = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + "> " +
                "INSERT { " +
                "f:" + uniqueID + " f:isA f:DiskImage" +
                "} WHERE {?s ?p ?o}";
        UpdateRequest que = UpdateFactory.create(queryStr);
        UpdateProcessor q = UpdateExecutionFactory.createRemoteForm(que, endpoint);
        q.execute();


    }


    //--------------------------------------------------------------
    //this method should delete a DiskImage (VMI or CI image) from cassandra - this means it should delete all the
    // triples belonging to certain DiskImage from the data model:
    //1) VMimage01 isA VMimage
    //2) VMimage01 imageType "CI"
    //3) VMimage01 description "Tole je opis VM1"
    //4) and so on......
    public static void deleteDiskImage(String imageID) {
        String endpoint = "http://localhost:3030/ds/update";
        //za razlago glej http://stackoverflow.com/questions/25531773/sparql-cascade-in-deleting-individuals
        String queryString = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + "> " +
                "DELETE WHERE {f:" + imageID + " ?p ?v} ";
        UpdateRequest query = UpdateFactory.create(queryString);
        UpdateProcessor qe = UpdateExecutionFactory.createRemoteForm(query, endpoint);
        qe.execute();
    }
    //--------------------------------------------------------------


    public static List<DiskImageResource> getDiskImagesWithGivenType(ImageType it) {
        List<DiskImageResource> allImageResourcesWithThisType = new ArrayList<DiskImageResource>();
        String endpoint = "http://localhost:3030/ds/sparql";
        //Show me properties of all DiskImages that have imageType equal to "VMI"
        String queryString = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + ">" +
                "SELECT ?DiskImage ?imageType ?imageDescription ?imageTitle ?imagePredecessor ?imageFileFormat " +
                "?imagePictureUrl ?imageEncription ?imageIri ?imageSlaId ?imagePrice ?imageOwnerId " +
                "?imageFunctionallityId ?imageQualityId ?imageOperatingSystemId ?imageNeedsData ?imageGenerationTime " +
                "?imageObfuscation " + "WHERE { ?DiskImage f:imageType \"" + it + "\" ; " + "f:imageType ?imageType ;" +
                " " +
                "" + "f:imageDescription ?imageDescription ; " + "f:imageTitle ?imageTitle ; " + "f:imagePredecessor " +
                "?imagePredecessor ; " + "f:imageFileFormat ?imageFileFormat ; " + "f:imagePictureUrl " +
                "?imagePictureUrl" +
                " ; " + "f:imageEncription ?imageEncription ; " + "f:imageIri ?imageIri ; " + "f:imageSlaId " +
                "?imageSlaId ; " + "f:imagePrice ?imagePrice ; " + "f:imageOwnerId ?imageOwnerId ; " +
                "f:imageFunctionallityId ?imageFunctionallityId ; " + "f:imageQualityId ?imageQualityId ; " +
                "f:imageOperatingSystemId ?imageOperatingSystemId ; " + "f:imageNeedsData ?imageNeedsData ; " +
                "f:imageGenerationTime ?imageGenerationTime ; " + "f:imageObfuscation ?imageObfuscation}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet rs = qe.execSelect();
        //ResultSetFormatter.out(System.out, rs);

        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            //TODO: here the type conversion should be handled in a better way, possibly also with connection with
            // SPARQL cassandra (only allowing certain types of data to be inserted etc..)
            ImageType imageTypeC = ImageType.valueOf(qs.getLiteral("imageType").toString());
            String descriptionC = qs.getLiteral("imageDescription").toString();
            String titleC = qs.getLiteral("imageTitle").toString();
            String predecessorC = qs.getLiteral("imagePredecessor").toString();
            FileFormat fileFormatC = FileFormat.valueOf(qs.getLiteral("imageFileFormat").toString());
            String pictureUrlC = qs.getLiteral("imagePictureUrl").toString();
            boolean encriptionC = Boolean.parseBoolean(qs.getLiteral("imageEncription").toString());
            String iriC = qs.getLiteral("imageIri").toString();
            String slaIdC = qs.getLiteral("imageSlaId").toString();
            double priceC = Double.parseDouble(qs.getLiteral("imagePrice").toString());
            String ownerIdC = qs.getLiteral("imageOwnerId").toString();
            String functionallityIdC = qs.getLiteral("imageFunctionallityId").toString();
            String qualityIdC = qs.getLiteral("imageQualityId").toString();
            String operatingSystemIdC = qs.getLiteral("imageOperatingSystemId").toString();
            boolean needsDataC = Boolean.parseBoolean(qs.getLiteral("imageNeedsData").toString());
            int generationTimeC = Integer.parseInt(qs.getLiteral("imageGenerationTime").toString());
            boolean obfuscationC = Boolean.parseBoolean(qs.getLiteral("imageObfuscation").toString());
            String resourceIdIri = qs.getResource("DiskImage").toString();
            String version = qs.getResource("Version").toString();
            String resourceId = resourceIdIri.replace(FusekiUtils.myNamespacePrefix, "");
            DiskImage di = new DiskImage(UUID.randomUUID().toString(), imageTypeC, descriptionC, titleC,
                    predecessorC, fileFormatC, pictureUrlC, encriptionC, iriC, slaIdC, priceC, ownerIdC,
                    functionallityIdC, qualityIdC, operatingSystemIdC, needsDataC, generationTimeC, obfuscationC,
                    version);
            DiskImageResource dir = new DiskImageResource(resourceId, di);
            allImageResourcesWithThisType.add(dir);
        }
        return allImageResourcesWithThisType;
    }


    public void createNewGraph() {
        // some definitions
        String personURI = "http://somewhere/JohnSmith";
        String fullName = "John Smith";

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource johnSmith = model.createResource(personURI);

        // add the property
        johnSmith.addProperty(VCARD.FN, fullName);

    }


    //this method should return all the DiskImages with a given ID. In reality only one DiskImage should be returned
    // for any of the IDs
    //but this is only in case we insert every DiskImage with different ID in triplestore. If we insert two
    // DiskImages and assign them the
    //same ID then the SPARQL query will return two instances.
    //TODO: we should implement some consistency checking for the cassandra (i.e. we should not allow to insert two
    // DiskImages but assigh them the same ID).
    public static List<DiskImage> getImagesWithGivenId(String imageId) {
        //I am returning a list because it could be that more than one image will have the same ID (although this
        // should not happen!)
        List<DiskImage> allImagesWithThisId = new ArrayList<DiskImage>();
        String endpoint = "http://localhost:3030/ds/sparql";
        //Show me properties of the DiskImage with specified ID
        String queryString = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + ">" +
                "SELECT ?DiskImage ?imageType ?imageDescription ?imageTitle ?imagePredecessor ?imageFileFormat " +
                "?imagePictureUrl ?imageEncription ?imageIri ?imageSlaId ?imagePrice ?imageOwnerId " +
                "?imageFunctionallityId ?imageQualityId ?imageOperatingSystemId ?imageNeedsData ?imageGenerationTime " +
                "?imageObfuscation " + "WHERE { f:" + imageId + " " + "f:imageType ?imageType ; " +
                "f:imageDescription ?imageDescription ; " + "f:imageTitle ?imageTitle ; " + "f:imagePredecessor " +
                "?imagePredecessor ; " + "f:imageFileFormat ?imageFileFormat ; " + "f:imagePictureUrl " +
                "?imagePictureUrl" +
                " ; " + "f:imageEncription ?imageEncription ; " + "f:imageIri ?imageIri ; " + "f:imageSlaId " +
                "?imageSlaId ; " + "f:imagePrice ?imagePrice ; " + "f:imageOwnerId ?imageOwnerId ; " +
                "f:imageFunctionallityId ?imageFunctionallityId ; " + "f:imageQualityId ?imageQualityId ; " +
                "f:imageOperatingSystemId ?imageOperatingSystemId ; " + "f:imageNeedsData ?imageNeedsData ; " +
                "f:imageGenerationTime ?imageGenerationTime ; " + "f:imageObfuscation ?imageObfuscation}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet rs = qe.execSelect();
        //ResultSetFormatter.out(System.out, rs);

//        while (rs.hasNext()) {
//            QuerySolution qs = rs.next();
//            //Literal met=qs.getLiteral("imageObfuscation");
//            //System.out.println(met);
//            //TODO: here the type conversion should be handled in a better way, possibly also with connection with
//            // SPARQL cassandra (only allowing certain types of data to be inserted etc..)
//            ImageType imageTypeC = ImageType.valueOf(qs.getLiteral("imageType").toString());
//            String descriptionC = qs.getLiteral("imageDescription").toString();
//            String titleC = qs.getLiteral("imageTitle").toString();
//            String predecessorC = qs.getLiteral("imagePredecessor").toString();
//            FileFormat fileFormatC = FileFormat.valueOf(qs.getLiteral("imageFileFormat").toString());
//            String pictureUrlC = qs.getLiteral("imagePictureUrl").toString();
//            boolean encriptionC = Boolean.parseBoolean(qs.getLiteral("imageEncription").toString());
//            String iriC = qs.getLiteral("imageIri").toString();
//            int slaIdC = Integer.parseInt(qs.getLiteral("imageSlaId").toString());
//            int priceC = Integer.parseInt(qs.getLiteral("imagePrice").toString());
//            int ownerIdC = Integer.parseInt(qs.getLiteral("imageOwnerId").toString());
//            int functionallityIdC = Integer.parseInt(qs.getLiteral("imageFunctionallityId").toString());
//            int qualityIdC = Integer.parseInt(qs.getLiteral("imageQualityId").toString());
//            int operatingSystemIdC = Integer.parseInt(qs.getLiteral("imageOperatingSystemId").toString());
//            boolean needsDataC = Boolean.parseBoolean(qs.getLiteral("imageNeedsData").toString());
//            int generationTimeC = Integer.parseInt(qs.getLiteral("imageGenerationTime").toString());
//            boolean obfuscationC = Boolean.parseBoolean(qs.getLiteral("imageObfuscation").toString());
//            DiskImage di = new DiskImage(UUID.randomUUID().toString(), imageTypeC, descriptionC, titleC,
//                    predecessorC, fileFormatC, pictureUrlC, encriptionC, iriC, slaIdC, priceC, ownerIdC,
//                    functionallityIdC, qualityIdC, operatingSystemIdC, needsDataC, generationTimeC, obfuscationC, 1);
//
//            allImagesWithThisId.add(di);
//        }
        return allImagesWithThisId;
    }


    //this method should return all the DiskImages in a cassandra.
    //TODO: we should implement some consistency checking for the cassandra (i.e. we should not allow to insert two
    // DiskImages but assigh them the same ID).
    public static List<DiskImageResource> getAllImages() {
        List<DiskImageResource> allImages = new ArrayList<DiskImageResource>();
        String endpoint = "http://localhost:3030/ds/sparql";
        //Show me the properties of all of the DiskImages in the cassandra
        String queryString = "PREFIX f: <" + FusekiUtils.myNamespacePrefix + ">" +
                "SELECT ?DiskImage ?imageType ?imageDescription ?imageTitle ?imagePredecessor ?imageFileFormat " +
                "?imagePictureUrl ?imageEncription ?imageIri ?imageSlaId ?imagePrice ?imageOwnerId " +
                "?imageFunctionallityId ?imageQualityId ?imageOperatingSystemId ?imageNeedsData ?imageGenerationTime " +
                "?imageObfuscation " + "WHERE { ?DiskImage " + "f:imageType ?imageType ; " + "f:imageDescription " +
                "?imageDescription ; " + "f:imageTitle ?imageTitle ; " + "f:imagePredecessor ?imagePredecessor ; " +
                "f:imageFileFormat ?imageFileFormat ; " + "f:imagePictureUrl ?imagePictureUrl ; " +
                "f:imageEncription" +
                " ?imageEncription ; " + "f:imageIri ?imageIri ; " + "f:imageSlaId ?imageSlaId ; " + "f:imagePrice " +
                "?imagePrice ; " + "f:imageOwnerId ?imageOwnerId ; " + "f:imageFunctionallityId " +
                "?imageFunctionallityId" +
                " ; " + "f:imageQualityId ?imageQualityId ; " + "f:imageOperatingSystemId ?imageOperatingSystemId ; "
                + "f:imageNeedsData ?imageNeedsData ; " + "f:imageGenerationTime ?imageGenerationTime ; " +
                "f:imageObfuscation ?imageObfuscation}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet rs = qe.execSelect();
        //ResultSetFormatter.out(System.out, rs);

        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            //TODO: here the type conversion should be handled in a better way, possibly also with connection with
            // SPARQL cassandra (only allowing certain types of data to be inserted etc..)
            ImageType imageTypeC = ImageType.valueOf(qs.getLiteral("imageType").toString());
            String descriptionC = qs.getLiteral("imageDescription").toString();
            String titleC = qs.getLiteral("imageTitle").toString();
            String predecessorC = qs.getLiteral("imagePredecessor").toString();
            FileFormat fileFormatC = FileFormat.valueOf(qs.getLiteral("imageFileFormat").toString());
            String pictureUrlC = qs.getLiteral("imagePictureUrl").toString();
            boolean encriptionC = Boolean.parseBoolean(qs.getLiteral("imageEncription").toString());
            String iriC = qs.getLiteral("imageIri").toString();
            String slaIdC = qs.getLiteral("imageSlaId").toString();
            double priceC = Double.parseDouble(qs.getLiteral("imagePrice").toString());
            String ownerIdC = qs.getLiteral("imageOwnerId").toString();
            String functionallityIdC = qs.getLiteral("imageFunctionallityId").toString();
            String qualityIdC = qs.getLiteral("imageQualityId").toString();
            String operatingSystemIdC = qs.getLiteral("imageOperatingSystemId").toString();
            boolean needsDataC = Boolean.parseBoolean(qs.getLiteral("imageNeedsData").toString());
            int generationTimeC = Integer.parseInt(qs.getLiteral("imageGenerationTime").toString());
            boolean obfuscationC = Boolean.parseBoolean(qs.getLiteral("imageObfuscation").toString());
            String resourceIdIri = qs.getResource("DiskImage").toString();
            String version = qs.getResource("Version").toString();
            String resourceId = resourceIdIri.replace(FusekiUtils.myNamespacePrefix, "");
            DiskImage di = new DiskImage(UUID.randomUUID().toString(), imageTypeC, descriptionC, titleC,
                    predecessorC, fileFormatC, pictureUrlC, encriptionC, iriC, slaIdC, priceC, ownerIdC,
                    functionallityIdC, qualityIdC, operatingSystemIdC, needsDataC, generationTimeC, obfuscationC,
                    version);
            DiskImageResource dir = new DiskImageResource(resourceId, di);
            allImages.add(dir);
        }
        return allImages;
    }


}

