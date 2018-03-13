package org.ul.entice.webapp.util;

import org.joda.time.DateTime;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.client.PackageEvalObj;
import org.ul.entice.webapp.entry.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class CommonUtils implements IService {
    public static void initProperties(Properties properties, String propertyName) {
        try {
            // load a properties file
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mapResultObjectToEntry(List<?> list, ResultObj resultObj, Class clazz, IService service) {
        try {
//            if (list.size() == 0) return;

            int index = getIndex((List<MyEntry>) list, resultObj.getS(), clazz);

            if (list.get(index) instanceof Repository) {
                List<Repository> repositoryList = (List<Repository>) list;
                if (resultObj.getP().endsWith("Repository_GeoLocation")) {
                    final String geolocationID = resultObj.getO();
                    repositoryList.get(index).setGeolocationId(resultObj.getO());
                    if (geolocationID != null && geolocationID.length() > 0) {
                        repositoryList.get(index).setGeolocation(FusekiUtils.getAllEntityAttributes(Geolocation.class, service, geolocationID).get(0));
                    }
                } else if (resultObj.getP().endsWith("Repository_InterfaceEndPoint"))
                    repositoryList.get(index).setInterfaceEndpoint(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_OperationalCost"))
                    repositoryList.get(index).setOperationalCost(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_StorageLevelCost") || resultObj.getP().endsWith("Repository_PriorityLevel1Cost")) {
                    repositoryList.get(index).setStorageLevelCost(Double.valueOf(resultObj.getO()));
                    repositoryList.get(index).setPriorityLevel1Cost(Double.valueOf(resultObj.getO
                            ()));
                } else if (resultObj.getP().endsWith("Repository_Space"))
                    repositoryList.get(index).setSpace(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_Availability"))
                    repositoryList.get(index).setAvailability(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_SupportedFormat"))
                    repositoryList.get(index).setSupportedFormat(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_TheoreticalCommunicationalPerformance"))
                    repositoryList.get(index).setTheoreticalCommunicationalPerformance(Double
                            .valueOf(resultObj.getO()));
            } else if (list.get(index) instanceof DiskImage) {
                List<DiskImage> diskImageList = (List<DiskImage>) list;

                if (resultObj.getO().endsWith("CI"))
                    diskImageList.get(index).setImageType(ImageType.CI);
                else if (resultObj.getO().endsWith("VMI"))
                    diskImageList.get(index).setImageType(ImageType.VMI);
                else if (resultObj.getP().endsWith("DiskImage_Description"))
                    diskImageList.get(index).setDescription(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Encryption"))
                    diskImageList.get(index).setEncryption(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_FileFormat"))
                    diskImageList.get(index).setFileFormat(FileFormat.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_hasOwner"))
                    diskImageList.get(index).setRefOwnerId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_DataId"))
                    diskImageList.get(index).setDataId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_refFunctionalityId"))
                    diskImageList.get(index).setRefFunctionalityId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_generationTime"))
                    diskImageList.get(index).setGenerationTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_IRI"))
                    diskImageList.get(index).setIri(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_NeedsDataFile"))
                    diskImageList.get(index).setNeedsData(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Obfuscation"))
                    diskImageList.get(index).setObfuscation(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_OperatingSystem"))
                    diskImageList.get(index).setRefOperatingSystemId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Picture"))
                    diskImageList.get(index).setPictureUrl(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Predecessor"))
                    diskImageList.get(index).setPredecessor(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Price"))
                    diskImageList.get(index).setPrice(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Quality"))
                    diskImageList.get(index).setRefQualityId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_SLA"))
                    diskImageList.get(index).setRefSlaId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Title"))
                    diskImageList.get(index).setTitle(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Version"))
                    diskImageList.get(index).setVersion(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Size"))
                    diskImageList.get(index).setDiskImageSize(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_ParetoPointX"))
                    diskImageList.get(index).setParetoPointX(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_ParetoPointY"))
                    diskImageList.get(index).setParetoPointY(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_OvfURL"))
                    diskImageList.get(index).setOvfUrl(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Pareto"))
                    diskImageList.get(index).setParetoId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Repository")) {
                    String repositoryID = resultObj.getO();
                    diskImageList.get(index).setRepositoryID(repositoryID);
                    if (repositoryID != null && !repositoryID.equals("null") && repositoryID.length() > 2) {
                        diskImageList.get(index).setRepository(FusekiUtils
                                .getAllEntityAttributes(Repository.class, service, repositoryID).get(0));
                    }
                } else if (resultObj.getP().endsWith("DiskImage_Categories"))
                    diskImageList.get(index).setCategoriesFromString(resultObj.getO());
            } else if (list.get(index) instanceof Fragment) {
                List<Fragment> fragmentList = (List<Fragment>) list;
                if (resultObj.getP().endsWith("Fragment_hasReferenceImage"))
                    fragmentList.get(index).setRefDiskImageId(resultObj.getO().replaceFirst
                            (FusekiUtils.KB_PREFIX_SHORT, ""));
                else if (resultObj.getP().endsWith("Fragment_hasRepository"))
                    fragmentList.get(index).setRefRepositoryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_Size"))
                    fragmentList.get(index).setFragmentSize(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Fragment_CopyIdentification"))
                    fragmentList.get(index).setCopyIdentification(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Fragment_IRI"))
                    fragmentList.get(index).setAnyURI(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_HashValues"))
                    fragmentList.get(index).setHashValue(resultObj.getO());
//                else if (resultObj.getP().endsWith("hasHistoryData"))
//                    fragmentList.get(index).setHistoryDataList(resultObj.getO());   //TODO
                else if (resultObj.getP().endsWith("Fragment_refReporeplicas"))
                    fragmentList.get(index).getRefReporeplicas().add(resultObj.getO());
            } else if (list.get(index) instanceof RecipeBuild) {
                List<RecipeBuild> recipeBuilds = (List<RecipeBuild>) list;
                if (resultObj.getP().endsWith("RecipeBuild_Message"))
                    recipeBuilds.get(index).setMessage(resultObj.getO());
                else if (resultObj.getP().endsWith("RecipeBuild_RecipeID"))
                    recipeBuilds.get(index).setJobId(resultObj.getO());
                else if (resultObj.getP().endsWith("RecipeBuild_Status"))
                    recipeBuilds.get(index).setRequest_status(resultObj.getO());
                else if (resultObj.getP().endsWith("RecipeBuild_Outcome"))
                    recipeBuilds.get(index).setOutcome(resultObj.getO());
                else if (resultObj.getP().endsWith("RecipeBuild_Size"))
                    recipeBuilds.get(index).setSize(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("RecipeBuild_URL"))
                    recipeBuilds.get(index).setUrl(resultObj.getO());
            } else if (list.get(index) instanceof Redistribution) {
                List<Redistribution> redistributionList = (List<Redistribution>) list;
                if (resultObj.getP().endsWith("Redistribution_RepositoryID"))
                    redistributionList.get(index).setRepositoryID(resultObj.getO());
                else if (resultObj.getP().endsWith("Redistribution_RepositoryName"))
                    redistributionList.get(index).setRepositoryName(resultObj.getO());
                else if (resultObj.getP().endsWith("Redistribution_VmiName"))
                    redistributionList.get(index).setVmiName(resultObj.getO());
                else if (resultObj.getP().endsWith("Redistribution_RedistributionURI"))
                    redistributionList.get(index).setRedistributionURI(resultObj.getO());
                else if (resultObj.getP().endsWith("Redistribution_SaveTime")) {
                    try {
                        redistributionList.get(index).setSaveTime(new DateTime(resultObj.getO()).getMillis());
                    } catch (Exception e) {
                        redistributionList.get(index).setSaveTime(Long.valueOf(resultObj.getO()));
                    }
                } else if (resultObj.getP().endsWith("Redistribution_RedistributionTimeInSeconds"))
                    redistributionList.get(index).setRedistributionTimeInSeconds(Integer.valueOf(resultObj.getO()));
            } else if (list.get(index) instanceof Quality) {
                List<Quality> qualityList = (List<Quality>) list;
                if (resultObj.getP().endsWith("Quality_AimedReductionRatio"))
                    qualityList.get(index).setAimedReductionRatio(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_IsOptimizationNecessary"))
                    qualityList.get(index).setIsOptimizationNecessary(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_IsUpdateNecessary"))
                    qualityList.get(index).setIsUpdateNecessary(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_MaxNumberOfVMs"))
                    qualityList.get(index).setMaxNumberOfVMs(Short.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_IsUpdateNecessary"))
                    qualityList.get(index).setIsUpdateNecessary(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_OptimizedSize")) {
                    try {
                        qualityList.get(index).setOptimizedSize(Long.valueOf(resultObj.getO()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultObj.getP().endsWith("Quality_PercentStorageOptimised"))
                    qualityList.get(index).setPercentStorageOptimised(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_UserRating"))
                    qualityList.get(index).setUserRating(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_actualIterationsNum"))
                    qualityList.get(index).setActualIterationsNum(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_actualRunningTime"))
                    qualityList.get(index).setActualRunningTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_maxIterationsNum"))
                    qualityList.get(index).setMaxIterationsNum(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_maxRunningTime"))
                    qualityList.get(index).setMaxRunningTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_JobID"))
                    qualityList.get(index).setJobID(resultObj.getO());
                else if (resultObj.getP().endsWith("Quality_Started"))
                    qualityList.get(index).setStarted(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_Status"))
                    qualityList.get(index).setStatus(resultObj.getO());
                else if (resultObj.getP().endsWith("Quality_OptimizerPhase"))
                    qualityList.get(index).setOptimizerPhase(resultObj.getO());
                else if (resultObj.getP().endsWith("Quality_OriginalImageSize"))
                    qualityList.get(index).setOriginalImageSize(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_OptimizedImageSize"))
                    qualityList.get(index).setOptimizedImageSize(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Quality_Failure"))
                    qualityList.get(index).setFailure(resultObj.getO());
            } else if (list.get(index) instanceof Functionality) {
                List<Functionality> functionalityList = (List<Functionality>) list;
                if (resultObj.getP().endsWith("Functionality_hasImplementation"))
                    functionalityList.get(index).setRefImplementationId(resultObj.getO().replaceFirst(FusekiUtils
                            .KB_PREFIX_SHORT, ""));
                else if (resultObj.getP().endsWith("Functionality_Classification"))
                    functionalityList.get(index).setClassification(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Functionality_Description"))
                    functionalityList.get(index).setDescription(resultObj.getO());
                else if (resultObj.getP().endsWith("Functionality_Domain"))
                    functionalityList.get(index).setDomain(resultObj.getO());
                else if (resultObj.getP().endsWith("Functionality_InputDescription"))
                    functionalityList.get(index).setInputDescription(resultObj.getO());
                else if (resultObj.getP().endsWith("Functionality_Name"))
                    functionalityList.get(index).setName(resultObj.getO());
                else if (resultObj.getP().endsWith("Functionality_OutputDescription"))
                    functionalityList.get(index).setOutputDescription(resultObj.getO());
                else if (resultObj.getP().endsWith("Functionality_Tag"))
                    functionalityList.get(index).setTag(resultObj.getO());
            } else if (list.get(index) instanceof Delivery) {
                List<Delivery> deliveryList = (List<Delivery>) list;
                if (resultObj.getP().endsWith("Delivery_hasDeliveredDiskImage"))
                    deliveryList.get(index).setRefDiskImageId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasFunctionality"))
                    deliveryList.get(index).setRefFunctionalityId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasTargetRepository"))
                    deliveryList.get(index).setRefTargetRepositoryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasUser"))
                    deliveryList.get(index).setRefUserId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_DeliveryTime"))
                    deliveryList.get(index).setDeliveryTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Delivery_RequestTime"))
                    deliveryList.get(index).setRequestTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Delivery_TargetCloud"))
                    deliveryList.get(index).setTargetCloud(resultObj.getO());
            } else if (list.get(index) instanceof Geolocation) {
                List<Geolocation> geolocationList = (List<Geolocation>) list;
                if (resultObj.getP().endsWith("GeoLocation_CountryName"))
                    geolocationList.get(index).setCountryName(resultObj.getO());
                if (resultObj.getP().endsWith("GeoLocation_CountryNameShort"))
                    geolocationList.get(index).setCountryNameShort(resultObj.getO());
                else if (resultObj.getP().endsWith("Geolocation_Continent"))
                    geolocationList.get(index).setContinent(resultObj.getO());
                else if (resultObj.getP().endsWith("Geolocation_Latitude"))
                    geolocationList.get(index).setLatitude(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Geolocation_Longitude")) {
                    String val = resultObj.getO();    //ugly hack
                    if (val.contains("^^http://www.w3.org/2001/XMLSchema#double"))
                        val = val.replace("^^http://www.w3.org/2001/XMLSchema#double", "").replaceAll("\"", "");
                    geolocationList.get(index).setLongitude(Double.valueOf(val));
                } else if (resultObj.getP().endsWith("GeoLocation_Altitude"))
                    geolocationList.get(geolocationList.size() - 1).setAltitude(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("GeoLocation_Timezone"))
                    geolocationList.get(index).setTimezone(resultObj.getO());
            } else if (list.get(index) instanceof User) {
                List<User> userList = (List<User>) list;
                if (resultObj.getP().endsWith("User_Email")) userList.get(index).setEmail(resultObj.getO());
                else if (resultObj.getP().endsWith("User_FullName")) userList.get(index).setFullName(resultObj.getO());
                else if (resultObj.getP().endsWith("User_PhoneNumber"))
                    userList.get(index).setPhoneNumber(resultObj.getO());
                else if (resultObj.getP().endsWith("User_UserName")) userList.get(index).setUsername(resultObj.getO());
                else if (resultObj.getP().endsWith("User_Password")) userList.get(index).setPassword(resultObj.getO());
                else if (resultObj.getP().endsWith("User_Privilege"))
                    userList.get(index).setGroupID(Integer.valueOf(resultObj.getO()));
            } else if (list.get(index) instanceof HistoryData) {
                List<HistoryData> historyDataList = (List<HistoryData>) list;
                if (resultObj.getP().endsWith("HistoryData_Location"))
                    historyDataList.get(index).setLocation(resultObj.getO());
                else if (resultObj.getP().endsWith("HistoryData_ValidFrom"))
                    historyDataList.get(index).setValidFrom(new DateTime(resultObj.getO()).getMillis());
                else if (resultObj.getP().endsWith("HistoryData_ValidTo"))
                    historyDataList.get(index).setValidTo(new DateTime(resultObj.getO()).getMillis());
                else if (resultObj.getP().endsWith("HistoryData_Value"))
                    historyDataList.get(index).setValue(resultObj.getO());
            } else if (list.get(index) instanceof Pareto) {
                List<Pareto> paretoList = (List<Pareto>) list;

                if (resultObj.getP().endsWith("Pareto_Objectives"))
                    paretoList.get(index).setTableValuesFromString(resultObj.getO(), Double.class);
                else if (resultObj.getP().endsWith("Pareto_Variables"))
                    paretoList.get(index).setTableValuesFromString(resultObj.getO(), Integer.class);
                else if (resultObj.getP().endsWith("Pareto_Replications"))
                    paretoList.get(index).setOneDimensionTableValuesFromString(resultObj.getO());
                else if (resultObj.getP().endsWith("Pareto_Stage"))
                    paretoList.get(index).setStage(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Pareto_Create_Date")) {
                    try {
                        paretoList.get(index).setSaveTime(new DateTime(resultObj.getO()).getMillis());
                    } catch (Exception e) {
                        e.printStackTrace();    //remove after new reimport
                    }
                }
            } else if (list.get(index) instanceof PackageEvalObj) {
                List<PackageEvalObj> packageEvalObjs = (List<PackageEvalObj>) list;

                if(resultObj.getS() == null)
                    return;

                if (resultObj.getP().endsWith("Package_Name"))
                    packageEvalObjs.get(index).setName(resultObj.getO());
            } else {
                throw new UnsupportedOperationException("The mapping is not implemented for this class! ! " + list
                        .get(index).getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getIndex(List<MyEntry> entryList, String id, Class clazz) {
        int i = 0;
//        Set<String> ids = new HashSet<>();
        for (MyEntry entry : entryList) {
//            ids.add(entry.getId());
            if (entry.getId().contains("85265b06-00fd-4e9f-8c7a-fdb542862649"))
                System.out.println();
            if (entry.getId().contains(id))
                return i;
            i++;
        }

//        System.out.println("ID ne obstaja!!! "+ id);
//
        entryList.add(EntryFactory.getInstance(clazz, id));
        return entryList.size() - 1;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static int getFileSize(String url) {
        try {
            final URL uri = new URL(url);
            URLConnection ucon;
            ucon = uri.openConnection();
            ucon.connect();
            final String contentLengthStr = ucon.getHeaderField("content-length");
            return (int) (Long.valueOf(contentLengthStr) / 1024);
        } catch (final IOException e1) {
            e1.getMessage();
            return -1;
        }
    }

    // save uploaded file to a defined location on the server
    public static void saveFile(InputStream uploadedInputStream, String serverLocation) {

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


    @Override
    public String getFusekiQuery() {
        return null;
    }
}
