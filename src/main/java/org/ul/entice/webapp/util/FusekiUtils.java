package org.ul.entice.webapp.util;

//web interface to FusekiUtils - http://localhost:3030/fuseki.html

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.sparql.core.ResultBinding;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.ReasonerVocabulary;
import org.apache.jena.vocabulary.VCARD;
import org.joda.time.DateTime;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.client.ImageEvalObj;
import org.ul.entice.webapp.client.PackageEvalObj;
import org.ul.entice.webapp.entry.*;
import org.ul.entice.webapp.rest.AppContextListener;

import java.io.*;
import java.net.URL;
import java.util.*;

public class FusekiUtils {

    private static final String XSD_PREFIX = "xsd:<http://www.w3.org/2001/XMLSchema#>";
    private static final String RDF_PREFIX = "rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    public static String KB_PREFIX_SHORT = "http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#";
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
            // CREATE USER
            if (obj instanceof User) {
                User user = (User) obj;
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s  a knowledgebase:User , owl:NamedIndividual ;" +
                        "     knowledgebase:User_Email        \"%s\" ;\n" +
                        "        knowledgebase:User_FullName     \"%s\" ;\n" +
                        "        knowledgebase:User_PhoneNumber  \"%s\" ;\n" +
                        "        knowledgebase:User_UserName     \"%s\" ;" +
                        "        knowledgebase:User_Password     \"%s\" ;" +
                        "        knowledgebase:User_Privilege     \"%s\" ;" +
                        "}", user.getId(), user.getEmail(), user.getFullName(), user.getPhoneNumber(), user
                        .getUsername(), user.getPassword(), user.getGroupID());
            }
            // CREATE DISK IMAGE
            else if (obj instanceof DiskImage) {
                DiskImage diskImage = (DiskImage) obj;

                String categoriesStr = new String();
                if (diskImage.getCategoryList() != null) {
                    for (String val : diskImage.getCategoryList()) {
                        if (val.length() > 0) categoriesStr += "\"" + val + "\",";
                    }
                    if (categoriesStr.length() > 0)
                        categoriesStr = categoriesStr.substring(0, categoriesStr.length() - 1);
                } else categoriesStr = "\"\"";

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                                XSD_PREFIX + " " +
                                "INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:DiskImage, owl:NamedIndividual, knowledgebase:" +
                                (diskImage.getImageType().equals(ImageType.CI) ? "CI" : "VMI") + " ; " +
                                "knowledgebase:DiskImage_Description \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Encryption  %s ;\n" +
                                "knowledgebase:DiskImage_FileFormat  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_hasOwner \"%s\"^^xsd:anyURI ;\n" +
                                "knowledgebase:DiskImage_DataId  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_refFunctionalityId  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_generationTime  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_IRI  \"%s\"^^xsd:anyURI ;\n" +
                                "knowledgebase:DiskImage_NeedsDataFile  %s ;\n" +
                                "knowledgebase:DiskImage_Obfuscation  %s ;\n" +
                                "knowledgebase:DiskImage_OperatingSystem  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Picture  \"%s\"^^xsd:anyURI ;\n" +
                                "knowledgebase:DiskImage_Predecessor  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Price \"%f\"^^xsd:double ;\n" +
                                "knowledgebase:DiskImage_Quality  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_SLA  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Title  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Version  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Size  %d ;\n" +
                                "knowledgebase:DiskImage_ParetoPointX  %d ;\n" +
                                "knowledgebase:DiskImage_ParetoPointY  %d ;\n" +
                                "knowledgebase:DiskImage_Pareto  \"%s\" ;\n" +
                                "knowledgebase:DiskImage_Categories " + categoriesStr + " ;\n" +
                                "knowledgebase:DiskImage_Repository  \"%s\" ;\n" +
                                (diskImage.getOvfUrl() != null ? "knowledgebase:DiskImage_OvfURL  \"" + diskImage.getOvfUrl() + "\" ;\n" : "") +
                                "}", diskImage.getId(), diskImage.getDescription(), diskImage.getEncryption(),
                        diskImage.getFileFormat().getValue(), diskImage.getRefOwnerId(), diskImage.getDataId(),
                        diskImage.getRefFunctionalityId(), diskImage.getGenerationTime(), diskImage.getIri(),
                        diskImage.isNeedsData(), diskImage.isObfuscation(), diskImage.getRefOperatingSystemId(),
                        diskImage.getPictureUrl(), diskImage.getPredecessor(), diskImage.getPrice(), diskImage
                                .getRefQualityId(), diskImage.getRefSlaId(), diskImage.getTitle(), diskImage
                                .getVersion(), diskImage.getDiskImageSize(), diskImage.getParetoPointX(), diskImage
                                .getParetoPointY(), diskImage.getParetoId(), diskImage.getRepositoryID());
            }
            // CREATE DISK IMAGE SLA
            else if (obj instanceof DiskImageSLA) {
                DiskImageSLA diskImageSLA = (DiskImageSLA) obj;
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:DiskImageSLA, owl:NamedIndividual," +
                        "knowledgebase:DiskImageSLA_hasAgreedAvailabilityCountry  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedAvailabilityRepository  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedRestriction  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedPriorityLevel  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_hasAgreedQoSOrder  \"%s\" ;\n" +
                        "knowledgebase:DiskImageSLA_SecuredDelivery  \"%s\" ;\n" +
                        "}", diskImageSLA.getId());   //TODO
            }
            // CREATE RECIPE BUILD OBJECT
            else if (obj instanceof RecipeBuild) {
                RecipeBuild recipeBuild = (RecipeBuild) obj;
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:RecipeBuild, owl:NamedIndividual ;" +
                        "knowledgebase:RecipeBuild_RecipeID  \"%s\" ;\n" +
                        "knowledgebase:RecipeBuild_Message  \"%s\" ;\n" +
                        "knowledgebase:RecipeBuild_Status  \"%s\" ;\n" +
                        "knowledgebase:RecipeBuild_Outcome  \"%s\" ;\n" +
                        "knowledgebase:RecipeBuild_Size  %d ;\n" +
                        "knowledgebase:RecipeBuild_URL  \"%s\" ;\n" +
                        "}", recipeBuild.getId(), recipeBuild.getJobId(), recipeBuild.getMessage(), recipeBuild
                        .getRequest_status(), recipeBuild.getOutcome(), recipeBuild.getSize(), recipeBuild.getUrl());
            }
            // CREATE REDISTRIBUTION OBJECT
            else if (obj instanceof Redistribution) {
                Redistribution redistribution = (Redistribution) obj;
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Redistribution, owl:NamedIndividual ;" +
                                "knowledgebase:Redistribution_RepositoryID  \"%s\" ;\n" +
                                "knowledgebase:Redistribution_RepositoryName  \"%s\" ;\n" +
                                "knowledgebase:Redistribution_VmiName  \"%s\" ;\n" +
                                "knowledgebase:Redistribution_RedistributionURI  \"%s\" ;\n" +
                                "knowledgebase:Redistribution_SaveTime  %d ;\n" +
                                "knowledgebase:Redistribution_RedistributionTimeInSeconds  %d ;\n" +
                                "}", redistribution.getId(), redistribution.getRepositoryID(), redistribution.getRepositoryName(),
                        redistribution.getVmiName(), redistribution.getRedistributionURI(), redistribution.getSaveTime(),
                        redistribution.getRedistributionTimeInSeconds());
            }
            // CREATE FRAGMENT
            else if (obj instanceof Fragment) {
                Fragment fragment = (Fragment) obj;
                String hashValues = new String();
                if (fragment.getHashValue() != null) {
                    for (String val : fragment.getHashValue()) {
                        hashValues += "\"" + val + "\",";
                    }
                    if (hashValues.length() > 0) hashValues = hashValues.substring(0, hashValues.length() - 1);
                }

                String historyDataListStr = parseHistoryDataIds(fragment.getHistoryDataList());
                String refReplicasStr = parseStringIds(fragment.getRefReporeplicas());

                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " + XSD_PREFIX + " " +
                                "PREFIX " + RDF_PREFIX + " " +
                                "INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Fragment, owl:NamedIndividual ;" +
                                "knowledgebase:Fragment_hasReferenceImage knowledgebase:%s ;\n" +
                                "knowledgebase:Fragment_hasRepository knowledgebase:%s ;\n" +
                                "knowledgebase:Fragment_IRI \"%s\"^^xsd:anyURI ;\n" +
//                        "knowledgebase:Fragment_ReferenceImage \"%s\" ;\n" +
                                "knowledgebase:Fragment_Size %s ;\n" +
                                "knowledgebase:Fragment_CopyIdentification %d ;\n" +
                                "knowledgebase:Fragment_HashValues " + hashValues + " ;\n" +
                                "knowledgebase:Fragment_refReporeplicas " + refReplicasStr + " ;\n" +
                                "knowledgebase:hasHistoryData " + historyDataListStr + " ;\n" +
//                                (historyDataListStr.length() == 0 ? "" : "knowledgebase:hasHistoryData [ a       " +
//                                        "rdf:Seq ;\n" + historyDataListStr + "] .") +
                                "}", fragment.getId(), fragment.getRefDiskImageId(), fragment.getRefRepositoryId(),
                        fragment.getAnyURI(), fragment.getFragmentSize(), fragment.getCopyIdentification());

                /**
                 * OLD: (historyDataListStr.length() == 0 ? "" : "knowledgebase:hasHistoryData [ a       " +"rdf:Seq
                 * ;\n" + historyDataListStr + "] .")
                 * ABOUT rdf:Seq and rdf:List:
                 * This isn't really an answer, but I try to avoid both rdf:List and rdf:Seq and instead use some
                 * “natural” order in the modelled domain whenever possible. For example, to indicate the order of
                 * posts in a feed I wouldn't use an explicit order, but implicit ordering based on the publication
                 * date if the use case allows it. There are of course legitimate cases where that's not an option
                 * and where there simply isn't any “natural” ordering that could be used. rdf:List and rdf:Seq both
                 * suck for large lists.
                 */
            }
            // CREATE DELIVERY
            else if (obj instanceof Delivery) {
                Delivery delivery = (Delivery) obj;
                return String.format("PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Delivery, owl:NamedIndividual ;" +
                                "knowledgebase:Delivery_hasDeliveredDiskImage \"%s\" ;\n" +
                                "knowledgebase:Delivery_hasFunctionality \"%s\" ;\n" +
                                "knowledgebase:Delivery_hasTargetRepository knowledgebase:%s ;\n" +
                                "knowledgebase:Delivery_hasUser \"%s\" ;\n" +
                                "knowledgebase:Delivery_DeliveryTime \"%s\" ;\n" +
                                "knowledgebase:Delivery_RequestTime \"%s\" ;\n" +
                                "knowledgebase:Delivery_TargetCloud \"%s\" ;\n" +
                                "}", delivery.getId(), delivery.getRefDiskImageId(), delivery.getRefFunctionalityId()
                        , delivery.getRefTargetRepositoryId(), delivery.getRefUserId(), delivery.getDeliveryTime(),
                        delivery.getRequestTime(), delivery.getTargetCloud());
            }
            // CREATE FUNCTIONALITY
            else if (obj instanceof Functionality) {
                Functionality functionality = (Functionality) obj;
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
                                .getClassification(), functionality.getDescription(), functionality.getDomain(),
                        functionality.getInputDescription(), functionality.getName(), functionality
                                .getOutputDescription(), functionality.getTag());
            }

            // CREATE QUALITY - for optimization
            else if (obj instanceof Quality) {
                Quality quality = (Quality) obj;
                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Quality, owl:NamedIndividual ;" +
                                "knowledgebase:Quality_AimedSize %d ;\n" +
                                "knowledgebase:Quality_OptimizedSize %d ;\n" +
                                "knowledgebase:Quality_PercentStorageOptimised  %d ;\n" +
//                                "knowledgebase:Quality_FunctionalityTested \"%s\" ;\n" +
                                "knowledgebase:Quality_UserRating  %d ;\n" +
                                "knowledgebase:Quality_IsUpdateNecessary %s ;\n" +
                                "knowledgebase:Quality_IsOptimizationNecessary %s ;\n" +
                                "knowledgebase:Quality_NumberOfDownloads %d ;\n" +
                                "knowledgebase:Quality_maxIterationsNum %d ;\n" +
                                "knowledgebase:Quality_actualIterationsNum %d ;\n" +
                                "knowledgebase:Quality_AimedReductionRatio %f ;\n" +
                                "knowledgebase:Quality_maxRunningTime %d ;\n" +
                                "knowledgebase:Quality_actualRunningTime %d ;\n" +
                                "knowledgebase:Quality_MaxNumberOfVMs %d ;\n" +
//                                "knowledgebase:Quality_UserComments \"%s\" ;\n" +
                                "knowledgebase:Quality_JobID \"%s\" ;\n" +

                                "knowledgebase:Quality_Started  %d ;\n" +
                                "knowledgebase:Quality_Status \"%s\" ;\n" +
                                "knowledgebase:Quality_OptimizerPhase \"%s\" ;\n" +
                                "knowledgebase:Quality_OriginalImageSize %d ;\n" +
                                "knowledgebase:Quality_OptimizedImageSize %d ;\n" +
                                "knowledgebase:Quality_Failure \"%s\" ;\n" +
                                "}", quality.getId(), quality.getAimedSize(), quality.getOptimizedSize(), quality
                                .getPercentStorageOptimised(), quality.getUserRating(), quality.isUpdateNecessary(), quality
                                .isOptimizationNecessary(), quality.getNumberOfDownloads(), quality.getMaxIterationsNum(),
                        quality.getActualIterationsNum(), quality.getAimedReductionRatio(), quality.getMaxRunningTime
                                (), quality.getActualRunningTime(), quality.getMaxNumberOfVMs(), quality.getJobID(),
                        quality.getStarted(), quality.getStatus(), quality.getOptimizerPhase(), quality.getOriginalImageSize(),
                        quality.getOptimizedImageSize(), quality.getFailure());
            }
            // CREATE GEOLOCATION
            else if (obj instanceof Geolocation) {
                Geolocation geolocation = (Geolocation) obj;
                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + "PREFIX " +
                                XSD_PREFIX + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Geolocation, owl:NamedIndividual ;" +
                                "knowledgebase:GeoLocation_CountryName \"%s\" ;\n" +
                                "knowledgebase:GeoLocation_CountryNameShort \"%s\" ;\n" +
                                "knowledgebase:Geolocation_Continent \"%s\" ;\n" +
                                "knowledgebase:Geolocation_Latitude \"%f\"^^xsd:double ;\n" +
                                "knowledgebase:Geolocation_Longitude \"%f\"^^xsd:double ;\n" +
                                "knowledgebase:GeoLocation_Altitude \"%f\"^^xsd:double ;\n" +
                                "knowledgebase:GeoLocation_Timezone \"%s\" ;\n" +
                                "}", geolocation.getId(), geolocation.getCountryName(), geolocation.getCountryNameShort(),
                        geolocation.getContinent(), geolocation.getLatitude(), geolocation.getLongitude(),
                        geolocation.getAltitude(), geolocation.getTimezone());
            }
            // CREATE REPOSITORY
            else if (obj instanceof Repository) {
                Repository repository = (Repository) obj;
                String supportedFormats = new String();
                for (String val : repository.getSupportedFormat()) {
                    supportedFormats += "\"" + val + "\",";
                }
                if (supportedFormats.length() > 0)
                    supportedFormats = supportedFormats.substring(0, supportedFormats.length() - 1);

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                                XSD_PREFIX + " " + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Repository, owl:NamedIndividual ;" +
                                "knowledgebase:Repository_InterfaceEndPoint \"%s\"^^xsd:anyURI ;\n" +
                                "knowledgebase:Repository_GeoLocation \"%s\"^^xsd:anyURI ;\n" +
                                "knowledgebase:Repository_OperationalCost %f ;\n" +
                                "knowledgebase:Repository_StorageLevelCost %f ;\n" +
                                "knowledgebase:Repository_Space %f ;\n" +
                                "knowledgebase:Repository_TheoreticalCommunicationalPerformance %f ;\n" +
                                "knowledgebase:Repository_SupportedFormat " + supportedFormats + " ;\n" +
                                "knowledgebase:Repository_Availability %f ;\n" +
                                "}", repository.getId(), repository.getInterfaceEndpoint(), repository
                                .getGeolocationId(), repository.getOperationalCost(), repository.getStorageLevelCost(),
                        repository.getSpace(), repository.getTheoreticalCommunicationalPerformance(), repository.getAvailability());

//                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
//                                XSD_PREFIX + " " + " INSERT DATA {" +
//                                "knowledgebase:%s a knowledgebase:Repository, owl:NamedIndividual ;" +
//                                "knowledgebase:Repository_hasCountry \"%s\"^^xsd:anyURI ;\n" +
//                                "knowledgebase:Repository_hasGeoLocation \"%s\"^^xsd:anyURI ;\n" +
//                                "knowledgebase:Repository_OperationalCost %f ;\n" +
//                                "knowledgebase:Repository_PriorityLevel1Cost %f ;\n" +
//                                "knowledgebase:Repository_PriorityLevel2Cost %f ;\n" +
//                                "knowledgebase:Repository_PriorityLevel3Cost %f ;\n" +
//                                "knowledgebase:Repository_Space \"%f\"^^xsd:double ;\n" +
//                                "knowledgebase:Repository_SupportedFormat " + supportedFormats + " ;\n" +
//                                "knowledgebase:Repository_TheoreticalCommunicationalPerformance \"%f\"^^xsd:double ;\n" +
//                                //todo: interfaceEndpoint anyURI
//                                "}", repository.getId(), repository.getCountryId(), repository.getGeolocationId(),
//                        repository.getOperationalCost(), repository.getPriorityLevel1Cost(), repository
//                                .getPriorityLevel2Cost(), repository.getPriorityLevel3Cost(), repository.getSpace(),repository.getTheoreticalCommunicationalPerformance());
            }
            // CREATE HISTORY DATA
            else if (obj instanceof HistoryData) {
                HistoryData historyData = (HistoryData) obj;
                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                        XSD_PREFIX + " " + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:HistoryData, owl:NamedIndividual ;" +
                        "knowledgebase:HistoryData_Location \"%s\"^^xsd:anyURI ;\n" +
                        "knowledgebase:HistoryData_ValidFrom \"%s\"^^xsd:dateTime ;\n" +
                        "knowledgebase:HistoryData_ValidTo \"%s\"^^xsd:dateTime ;\n" +
                        "knowledgebase:HistoryData_Value %s ;\n" +
                        "}", historyData.getId(), historyData.getLocation(), new DateTime(historyData.getValidFrom())
                        .toString(), new DateTime(historyData.getValidTo()).toString(), historyData.getValue());
            }
            // CREATE PARETO
            else if (obj instanceof Pareto) {
                Pareto pareto = (Pareto) obj;

                if (pareto.getId() == null || pareto.getId().length() == 0) pareto.setId(UUID.randomUUID().toString());

                String objectivesStr = createRegixBasedTableString(pareto.getObjectives());


                String historyDataListStr = parseHistoryDataIds(pareto.getHistoryDataList());

                String variablesStr = createRegixBasedTableString(pareto.getVariables());
                String replicationsStr = createRegixBasedOneDimensionalTableString(pareto.getReplications());

//                String variablesStr = new String();
//                for (int i = 0; i < pareto.getVariables().length; i++) {
//                    variablesStr += "\"" + pareto.getVariables()[i][0] + "\",";
//                    variablesStr += "\"" + pareto.getVariables()[i][1] + "\",";
//                }
//                if (variablesStr.length() > 0) variablesStr = variablesStr.substring(0, variablesStr.length() - 1);

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                                XSD_PREFIX + " " + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Pareto, owl:NamedIndividual ;" +
                                "knowledgebase:Pareto_Create_Date \"%s\"^^xsd:dateTime ;\n" +
                                "knowledgebase:Pareto_Stage %d ;\n" +
                                "knowledgebase:Pareto_Objectives \"%s\" ;\n" +
                                "knowledgebase:Pareto_Variables \"%s\" ;\n" +
                                (historyDataListStr != null && historyDataListStr.length() > 0 ? "knowledgebase:Pareto_hasHistoryData " + historyDataListStr + " ;\n" : ";") +
                                (replicationsStr != null ? "knowledgebase:Pareto_Replications \"" + replicationsStr + "\" ;\n" : "") +
                                "}", pareto.getId(), new DateTime(pareto.getSaveTime()).toString(), pareto.getStage(), objectivesStr,
                        variablesStr);
            }
            // CREATE REPLICATION OBJECT
            else if (obj instanceof ReplicationObj) {
                ReplicationObj replicationObj = (ReplicationObj) obj;

                if (replicationObj.getId() == null || replicationObj.getId().length() == 0)
                    replicationObj.setId(UUID.randomUUID().toString());

                String repIndexStr = createRegixBasedTableString(replicationObj.getRepIndex());
                String repPlaceStr = createRegixBasedTableString(replicationObj.getRepPlace());

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                                XSD_PREFIX + " " + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Replication, owl:NamedIndividual ;" +
                                "knowledgebase:Replication_Create_Date \"%s\"^^xsd:dateTime ;\n" +
                                "knowledgebase:Replication_Index \"%s\" ;\n" +
                                "knowledgebase:Replication_Place \"%s\" ;\n" +
                                "}", replicationObj.getId(), new DateTime(replicationObj.getSaveTime()).toString(), repIndexStr,
                        repPlaceStr);
            }
            // CREATE PACKAGE OBJECT - for evaluation purposes
            else if (obj instanceof PackageEvalObj) {
                PackageEvalObj packageEvalObj = (PackageEvalObj) obj;

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                        XSD_PREFIX + " " + " INSERT DATA {" +
                        "knowledgebase:%s a knowledgebase:Package, owl:NamedIndividual ;" +
                        "knowledgebase:Package_Name \"%s\" }", packageEvalObj.getId(), packageEvalObj.getName());
            }
            // CREATE IMAGE EVAL OBJECT - for evaluation purposes
            else if (obj instanceof ImageEvalObj) {
                ImageEvalObj imageEvalObj = (ImageEvalObj) obj;

                String combinesWithStr = new String();
                if (imageEvalObj.getCombinesWithId() != null) {
                    for (String val : imageEvalObj.getCombinesWithId()) {
                        if (val.length() > 0) combinesWithStr += "\"" + val + "\",";
                    }
                    if (combinesWithStr.length() > 0)
                        combinesWithStr = combinesWithStr.substring(0, combinesWithStr.length() - 1);
                } else combinesWithStr = "\"\"";

                String extendsWithStr = new String();
                if (imageEvalObj.getExtendsWithId() != null) {
                    for (String val : imageEvalObj.getExtendsWithId()) {
                        if (val.length() > 0) extendsWithStr += "\"" + val + "\",";
                    }
                    if (extendsWithStr.length() > 0)
                        extendsWithStr = extendsWithStr.substring(0, extendsWithStr.length() - 1);
                    else extendsWithStr = "\"\"";
                } else extendsWithStr = "\"\"";

                return String.format(Locale.US, "PREFIX " + KB_PREFIX + "PREFIX " + OWL_PREFIX + " PREFIX " +
                                XSD_PREFIX + " " + " INSERT DATA {" +
                                "knowledgebase:%s a knowledgebase:Image, owl:NamedIndividual ;" +
                                "knowledgebase:Image_Size  %d ;\n" +
                                "knowledgebase:Image_CombinesWith " + combinesWithStr + " ;\n" +
                                "knowledgebase:Image_ExtendsWith " + extendsWithStr + " ; }",
                        imageEvalObj.getId(), imageEvalObj.getSize());
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

    private static String parseHistoryDataIds(List<HistoryData> hdl) {
        String historyDataListStr = new String();
        if (hdl != null && hdl.size() > 0) {
            for (HistoryData historyData : hdl) {
                historyDataListStr += "\"" + historyData.getId() + "\",";
            }
            if (historyDataListStr.length() > 0)
                historyDataListStr = historyDataListStr.substring(0, historyDataListStr.length() - 1);
        }
        return historyDataListStr;
    }

    private static String parseStringIds(List<String> hdl) {
        String ids = new String();
        if (hdl != null) {
            for (String idd : hdl) {
                ids += "\"" + idd + "\",";
            }
            if (ids.length() > 0)
                ids = ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    public static <T> String createRegixBasedTableString(T[][] objectives) {
        if (objectives == null)
            return null;

        String objectivesStr = new String();
        for (int i = 0; i < objectives.length; i++) {
            for (int j = 0; j < objectives[i].length; j++) {
                objectivesStr += objectives[i][j] + ",";
            }
            objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 1);
            objectivesStr += "//";
        }
        if (objectivesStr.length() > 0) objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 2);

        return objectivesStr;
    }

    private static <T> String createRegixBasedTableString(T[] objectives) {
        String objectivesStr = new String();
        for (int i = 0; i < objectives.length; i++) {
            objectivesStr += objectives[i] + ",";

            objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 1);
            objectivesStr += "//";
        }
        if (objectivesStr.length() > 0) objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 2);

        return objectivesStr;
    }

    private static <T> String createRegixBasedOneDimensionalTableString(T[] objectives) {
        if (objectives == null)
            return null;

        String objectivesStr = new String();
        for (int i = 0; i < objectives.length; i++) {
            objectivesStr += objectives[i] + ",";

            //objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 1);
            //objectivesStr += ",";
        }
        if (objectivesStr.length() > 0) objectivesStr = objectivesStr.substring(0, objectivesStr.length() - 1);

        return objectivesStr;
    }

    @Deprecated
    public static String getPassword(String username) {
        return "prefix knowledgebase: <http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "\n" +
                "SELECT ?pass\n" +
                "WHERE { ?s a knowledgebase:User ; knowledgebase:User_UserName \"" + username + "\" ; " +
                "knowledgebase:User_Password ?pass }\n" +
                "LIMIT 25\n";
    }


    public static String performUserLogin(String username, String password) {
        return "prefix knowledgebase: <http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "\n" +
                "SELECT ?s ?privilege \n" +
                "WHERE { ?s a knowledgebase:User ; knowledgebase:User_UserName \"" + username + "\" ; " +
                "knowledgebase:User_Password \"" + password + "\" ; knowledgebase:User_Privilege ?privilege }\n" +
                "LIMIT 25\n";
    }

    public static String getAllEntitiesQuery(String entityClass, String... queryFilterCondition) {
        if (entityClass.equals("HistoryData") && queryFilterCondition.length > 1)
            return "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { \n" +
                    "knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:" + entityClass + " ; ?p ?o . \n" +
                    "  ?s knowledgebase:HistoryData_ValidFrom ?dateFrom , ?o .\n" +
                    "  ?s knowledgebase:HistoryData_ValidTo ?dateTo , ?o .\n" +
                    "FILTER(?dateTo <= \"" + new DateTime(Long.valueOf(queryFilterCondition[2])) + "\"^^xsd:dateTime " +
                    ")  \n" +
                    "FILTER(?dateFrom >= \"" + new DateTime(Long.valueOf(queryFilterCondition[1])) +
                    "\"^^xsd:dateTime )  \n" +
                    "} \n";
//                    "LIMIT 200";
        else if (entityClass.equals("Geolocation") && queryFilterCondition.length > 0)
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:" + entityClass + " ; ?p " +
                    "?o }";
        else if ((entityClass.equals("HistoryData") || entityClass.equals("Quality") || entityClass.equals("RecipeBuild")) &&
                queryFilterCondition.length > 0) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:" + entityClass + " ; ?p " +
                    "?o " +
                    "}\n";
//                    "LIMIT 200";
        } else if (entityClass.equals("User") && queryFilterCondition.length > 0) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:User ; ?p ?o }";
        } else if (entityClass.equals("Functionality")) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:Functionality ; ?p ?o" +
                    " }\n";
        } else if (entityClass.equals("Pareto") && queryFilterCondition.length > 0) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " ?p ?o }\n";
        } else if (entityClass.equals("Repository") && queryFilterCondition.length > 0) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:" + entityClass + " ; ?p ?o }";
//                    "WHERE { ?s a knowledgebase:" + entityClass + " ; ?p ?o " + queryFilterCondition[0] + "} \n";
        } else if (entityClass.equals("DiskImage") && queryFilterCondition.length > 1 && queryFilterCondition[1] !=
                null) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + " ; ?p ?o " + queryFilterCondition[1] + " }\n";
        } else if (entityClass.equals("DiskImage") && queryFilterCondition.length > 0 && queryFilterCondition[0] !=
                null && queryFilterCondition[0].length() > 0) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a " +
                    "knowledgebase:" + entityClass + "; ?p ?o }\n";
//                    "LIMIT 200";
        } else if (queryFilterCondition.length > 0 && queryFilterCondition[0] != null) {
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + " " + queryFilterCondition[0] + " ; ?p ?o }\n";
//                    "LIMIT 200";
//            return "prefix knowledgebase: <http://www.semanticweb" +
//                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
//                    "\n" +
//                    "SELECT ?s ?p ?o\n" +
//                    "WHERE { knowledgebase:" + queryFilterCondition[0] + " a knowledgebase:" + entityClass + " ;
// ?p " +
//                    "?o " +
//                    "}\n" +
//                    "LIMIT 200";
        } else
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + " ; ?p ?o }\n";
//                    "LIMIT 200";
    }

    public static String getAllEntitiesCount(String entityClass, String... queryFilterCondition) {
        if (entityClass.equals("DiskImage") && queryFilterCondition.length > 0)
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "\n" +
                    "SELECT (COUNT(DISTINCT ?s) AS ?count)\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + " ; knowledgebase:DiskImage_hasOwner " +
                    "\"" + queryFilterCondition[0] + "\"^^xsd:anyURI }";
        else
            return "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "SELECT (COUNT(DISTINCT ?s) AS ?count)\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + "}";
    }

    public static String getEntityQuery(String entityClass, String... postfixCondition) {
        if (postfixCondition.length > 1)
            return "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { ?s a knowledgebase:" + entityClass + " " + postfixCondition[0] + " }\n" +
                    postfixCondition[1];

        return "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "prefix knowledgebase: <http://www.semanticweb" +
                ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "\n" +
                "SELECT ?s ?p ?o\n" +
                "WHERE { ?s a knowledgebase:" + entityClass + " ; ?p ?o }\n" + postfixCondition[0];
    }

    public static String getIdBasedEntitiesQuery(String clazz, String id) {
        if (clazz.equals("Fragment")) return "prefix knowledgebase: <http://www.semanticweb" +
                ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "SELECT ?s\n" +
                "WHERE { ?s a knowledgebase:Fragment ; knowledgebase:Fragment_hasReferenceImage ?o , " +
                "knowledgebase:" + id + " }\n";
//                "LIMIT 200";
        else throw new UnsupportedOperationException("not implemented for this class");
    }

    public static String getHistoryDataIDs(String fragmentId) {
        return "prefix knowledgebase: <http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "select ?o\n" +
                "where { knowledgebase:" + fragmentId + " knowledgebase:hasHistoryData ?o\n" +
                "}";
    }

    public static String getDiskImageIDs() {
        return "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "prefix knowledgebase: <http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                "\n" +
                "#SELECT count(*)\n" +
                "SELECT DISTINCT ?s\n" +
                "WHERE { ?s a knowledgebase:DiskImage . ?s a owl:NamedIndividual , ?o }\n" +
                "#FILTER(?o IN knowledgebase:DiskImage )";
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

    public static String getFusekiDBSource(String sourceURL) {
        InputStream is = null;
        try {
            is = new URL(sourceURL).openStream();
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            // ugly hack but works. The original content have not valid format using "{" and "}"
            result = result.replace("{", "");
            result = result.replace("}", "");

            //write content to tmp file
            File temp = File.createTempFile("tempfile", ".tmp");

            //write it
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            bw.write(result);
            bw.close();
            return temp.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getAllReasoners() {
        Model reasoners = ReasonerRegistry.theRegistry().getAllDescriptions();
        ResIterator ri = reasoners.listSubjectsWithProperty(RDF.type, ReasonerVocabulary.ReasonerClass);
        while (ri.hasNext()) {
            System.out.println(" " + ri.next());
        }
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

    public static boolean deleteEntityById(String id, String entityName) {
        try {
            String queryString = "prefix knowledgebase: <http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "DELETE WHERE { knowledgebase:" + id + " ?p ?o }\n";
            UpdateRequest query = UpdateFactory.create(queryString);
            UpdateProcessor qe = UpdateExecutionFactory.createRemoteForm(query, AppContextListener.prop.getProperty
                    ("fuseki.url.update"));
            qe.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
                    version, (int) (1000 + Math.random() * 100000), -1, -1, "paretoID", null, null);
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
                    version, (int) (1000 + Math.random() * 100000), -1, -1, "paretoID", null, null);
            DiskImageResource dir = new DiskImageResource(resourceId, di);
            allImages.add(dir);
        }
        return allImages;
    }


    public static List<ResultObj> getResultObjectListFromResultSet(ResultSet results) {
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
                if (var.getVarName().equals("s")) {
                    x = ((ResultBinding) qs).getBinding().get(var).toString();
                    if (x.contains(KB_PREFIX_SHORT)) x = x.replace(KB_PREFIX_SHORT, "");
                } else if (var.getVarName().equals("p")) r = ((ResultBinding) qs).getBinding().get(var).toString();
                else if (var.getVarName().equals("o")) {
                    try {
                        String resStr = ((ResultBinding) qs).getBinding().get(var).toString();
                        if (resStr.contains("^^http://www.w3.org/2001/XMLSchema#dateTime"))
                            y = ((ResultBinding) qs).getBinding().get(var).getLiteral().getValue().toString();
                        else if (resStr.contains("^^http://www.w3.org/2001/XMLSchema#anyURI"))
                            y = resStr.replace("^^http://www.w3.org/2001/XMLSchema#anyURI", "").replaceAll("\"", "");
                        else if (resStr.contains("^^http://www.w3.org/2001/XMLSchema#integer"))
                            y = resStr.replace("^^http://www.w3.org/2001/XMLSchema#integer", "").replaceAll("\"", "");
                        else if (resStr.contains("-")) y = resStr.replaceAll("\"", "");
                        else if (resStr.contains("^^http://www.w3.org/2001/XMLSchema#double"))
                            y = resStr.replace("^^http://www.w3.org/2001/XMLSchema#double", "").replaceAll("\"", "");
                        else y = String.valueOf(((ResultBinding) qs).getBinding().get(var).getLiteral().getValue());

                        //additional filter of prefixes cannot be done here!
                        //if(resStr.startsWith(KB_PREFIX_SHORT)) y = resStr.replaceFirst(KB_PREFIX_SHORT,"");
                    } catch (Exception e) {
                        y = ((ResultBinding) qs).getBinding().get(var).toString();
                    }
                }
            }

            resultObjs.add(new ResultObj(x, r, y));
        }

        return resultObjs;
    }

    public static int getCountResult(ResultSet results) {
        // For each solution in the result set
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();
            while (varIter.hasNext()) {
                Var var = varIter.next();
                if (var.getVarName().equals("count"))
                    return (int) ((ResultBinding) qs).getBinding().get(var).getLiteralValue();
            }
        }
        return 0;
    }

    public static <T extends MyEntry> List<T> getAllEntityAttributes(Class<T> clazz, IService service, String... conditions) {
        String selectQuery = FusekiUtils.getAllEntitiesQuery(clazz.getSimpleName(), conditions);
        QueryExecution qe = QueryExecutionFactory.sparqlService(service.getFusekiQuery(), selectQuery);
//        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/entice/query", selectQuery);
        ResultSet results = qe.execSelect();
        List<ResultObj> resultObjs = FusekiUtils.getResultObjectListFromResultSet(results);

        //exception!!
        if (conditions.length > 0 && clazz.getSimpleName().equals("Pareto") && resultObjs.size() > 0) {
//            resultObjs.add(0, new ResultObj(conditions[0], conditions[0], KB_PREFIX_SHORT + clazz.getSimpleName()));
            for (ResultObj resultObj : resultObjs) {
                resultObj.setS(conditions[0]);
            }
        } else if (conditions.length > 0 && (clazz.getSimpleName().equals("DiskImage") || clazz.getSimpleName().equals("RecipeBuild") || clazz.getSimpleName().equals("Repository") || clazz.getSimpleName().equals
                ("Functionality") || clazz.getSimpleName().equals("User") || clazz.getSimpleName().equals("Geolocation")) && resultObjs.size() > 0 &&
                resultObjs.get(0).getS() == null) {
            for (ResultObj resultObj : resultObjs) {
                resultObj.setS(conditions[0]);
            }
        }


        return FusekiUtils.mapResultObjectListToEntry(clazz, resultObjs, service);
    }

    public static <T extends MyEntry> String getRawEntityAttributes(Class<T> clazz, IService service, String... conditions) {
        String selectQuery = FusekiUtils.getAllEntitiesQuery(clazz.getSimpleName(), conditions);
        QueryExecution qe = QueryExecutionFactory.sparqlService(service.getFusekiQuery(), selectQuery);

        return ResultSetFormatter.asText(qe.execSelect());
    }

    public static <T extends MyEntry> List<T> getAllEntityAttributes(String selectQuery, Class clazz, IService service) {

        QueryExecution qe = QueryExecutionFactory.sparqlService(service.getFusekiQuery(), selectQuery);
        long startTime = System.currentTimeMillis();
        ResultSet results = qe.execSelect();
        System.out.println("SUBSET: " + (System.currentTimeMillis() - startTime) + "ms");
        List<ResultObj> resultObjs = FusekiUtils.getResultObjectListFromResultSet(results);

        return FusekiUtils.mapResultObjectListToEntry(clazz, resultObjs, service);
    }

    public static int getEntityCount(String classSimpleName, String... conditions) {
        String selectQuery = FusekiUtils.getAllEntitiesCount(classSimpleName, conditions);

        QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url" + ""
                + ".query"), selectQuery);
        ResultSet results = qe.execSelect();

        return FusekiUtils.getCountResult(results);
    }

    public static <T extends MyEntry> String getEntityID(Class<T> clazz, String... conditions) {
        String selectQuery = FusekiUtils.getEntityQuery(clazz.getSimpleName(), conditions);

        QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url" + ""
                + ".query"), selectQuery);
//        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/entice/query", selectQuery);
        ResultSet results = qe.execSelect();

        List<ResultObj> resultObjs = FusekiUtils.getResultObjectListFromResultSet(results);

        return resultObjs.get(0).getS();
    }

    public static <T extends MyEntry> List<T> getIdEntityAttributes(Class<T> clazz, IService service, String id) {
        String selectQuery = FusekiUtils.getIdBasedEntitiesQuery(clazz.getSimpleName(), id);

        QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url" + ""
                + ".query"), selectQuery);
        ResultSet results = qe.execSelect();

        List<ResultObj> queryResults = FusekiUtils.getResultObjectListFromResultSet(results);

        List<T> fragmentList = new ArrayList<T>(queryResults.size());

        for (ResultObj resObj : queryResults) {
            String fragmentId = resObj.getS();

            selectQuery = "prefix knowledgebase: <http://www.semanticweb" +
                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
                    "\n" +
                    "SELECT ?s ?p ?o\n" +
                    "WHERE { knowledgebase:" + fragmentId.substring(fragmentId.indexOf("#") + 1) + " a " +
                    "knowledgebase:Fragment; ?p ?o }\n";
//                    "LIMIT 200";

            qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki.url" + "" + "" +
                    ".query"), selectQuery);
            results = qe.execSelect();

            List<ResultObj> resultObjs = FusekiUtils.getResultObjectListFromResultSet(results);

            List<T> list = new ArrayList<T>();
            for (ResultObj resultObj : resultObjs) {
                if (resultObj.getO().equals(KB_PREFIX_SHORT + clazz.getSimpleName())) {
                    //some customizations for fragment class
                    list.add(EntryFactory.getInstance(clazz, fragmentId.replace(KB_PREFIX_SHORT, "")));
                } else if (resultObj.getO().equals(KB_PREFIX_SHORT + "CI") || resultObj.getO().equals(KB_PREFIX_SHORT +
                        "VMI")) {
                    list.add(EntryFactory.getInstance(clazz, resultObj.getS().replace(KB_PREFIX_SHORT, "")));
                }


                CommonUtils.mapResultObjectToEntry(list, resultObj, clazz, service);
            }
            fragmentList.add(list.get(0));
        }
        return fragmentList;
    }

    public static <T extends MyEntry> List<T> mapResultObjectListToEntry(Class<T> clazz, List<ResultObj> resultObjs, IService service) {
        List<T> list = new ArrayList<T>();
        for (ResultObj resultObj : resultObjs) {
            // MUST INCLUDE THIS FOR ENTICE PROJECT!
            /*
            if (resultObj.getO().equals(KB_PREFIX_SHORT + clazz.getSimpleName())) {
                list.add(EntryFactory.getInstance(clazz, resultObj.getS().replace(KB_PREFIX_SHORT, "")));
            }
            else

            if (resultObj.getO().equals(KB_PREFIX_SHORT + "CI") || resultObj.getO().equals(KB_PREFIX_SHORT +
                    "VMI")) {
                list.add(EntryFactory.getInstance(clazz, resultObj.getS().replace(KB_PREFIX_SHORT, "")));
            }
*/

            CommonUtils.mapResultObjectToEntry(list, resultObj, clazz, service);
        }
        return list;
    }

    public static List<Fragment> getFragmentDataOfDiskImage(String diskImageId, Boolean showHistoryData, IService service) {
        String queryCondition = null;
        if (diskImageId != null)
            queryCondition = ".?s knowledgebase:Fragment_hasReferenceImage knowledgebase:" + diskImageId + " ";

        List<Fragment> fragmentList = FusekiUtils.getAllEntityAttributes(Fragment.class, service, queryCondition);

        if (showHistoryData != null && showHistoryData == true) {
            for (Fragment fragment : fragmentList) {
                String selectQuery = FusekiUtils.getHistoryDataIDs(fragment.getId());

                QueryExecution qe = QueryExecutionFactory.sparqlService(AppContextListener.prop.getProperty("fuseki" +
                        ".url" + "" + ".query"), selectQuery);
//        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/entice/query", selectQuery);
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
                            historyDataResultObj, service);

                    // Collections.sort(historyDataList,HistoryData.HistoryDataComparator);

                    fragment.setHistoryDataList(historyDataList);
                }
            }
        }

        return fragmentList;
    }

    public static List<Functionality> getFunctionalityOfDiskImage(String functionalityID, IService service) {
        List<Functionality> functionalityList = FusekiUtils.getAllEntityAttributes(Functionality.class, service, functionalityID);

        return functionalityList;
    }
}

