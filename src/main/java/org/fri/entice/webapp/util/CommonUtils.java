package org.fri.entice.webapp.util;

import org.fri.entice.webapp.entry.*;
import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class CommonUtils {
    public static void initProperties(Properties properties, String propertyName) {
        try {
            // load a properties file
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mapResultObjectToEntry(List<?> list, ResultObj resultObj, Class clazz) {
        try {
//            if (list.size() == 0) return;

            int index = getIndex((List<MyEntry>)list,resultObj.getS(), clazz);

            if (list.get(index) instanceof Repository) {
                List<Repository> repositoryList = (List<Repository>) list;
                if (resultObj.getP().endsWith("Repository_GeoLocation")){
                    final String geolocationID = resultObj.getO();
                    repositoryList.get(index).setGeolocationId(resultObj.getO());
                    if(geolocationID != null && geolocationID.length() > 0){
                        repositoryList.get(index).setGeolocation(FusekiUtils.getAllEntityAttributes(Geolocation.class, geolocationID).get(0));
                    }
                }
                else if (resultObj.getP().endsWith("Repository_InterfaceEndPoint"))
                    repositoryList.get(index).setInterfaceEndpoint(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_OperationalCost"))
                    repositoryList.get(index).setOperationalCost(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_StorageLevelCost") || resultObj.getP().endsWith("Repository_PriorityLevel1Cost")){
                    repositoryList.get(index).setStorageLevelCost(Double.valueOf(resultObj.getO()));
                    repositoryList.get(index).setPriorityLevel1Cost(Double.valueOf(resultObj.getO
                            ()));
                }
                else if (resultObj.getP().endsWith("Repository_Space"))
                    repositoryList.get(index).setSpace(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_SupportedFormat"))
                    repositoryList.get(index).setSupportedFormat(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_TheoreticalCommunicationalPerformance"))
                    repositoryList.get(index).setTheoreticalCommunicationalPerformance(Double
                            .valueOf(resultObj.getO()));
            }
            else if (list.get(index) instanceof DiskImage) {
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
                else if (resultObj.getP().endsWith("DiskImage_Pareto"))
                    diskImageList.get(index).setParetoId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Repository")) {
                    String repositoryID = resultObj.getO();
                    diskImageList.get(index).setRepositoryID(repositoryID);
                    if(repositoryID != null && !repositoryID.equals("null") && repositoryID.length() > 2){
                        diskImageList.get(index).setRepository(FusekiUtils
                                .getAllEntityAttributes(Repository.class, repositoryID).get(0));
                    }
                }
                else if (resultObj.getP().endsWith("DiskImage_Categories"))
                    diskImageList.get(index).setCategoriesFromString(resultObj.getO());
            }
            else if (list.get(index) instanceof Fragment) {
                List<Fragment> fragmentList = (List<Fragment>) list;
                if (resultObj.getP().endsWith("Fragment_hasReferenceImage"))
                    fragmentList.get(index).setRefDiskImageId(resultObj.getO().replaceFirst
                            (FusekiUtils.KB_PREFIX_SHORT, ""));
                else if (resultObj.getP().endsWith("Fragment_hasRepository"))
                    fragmentList.get(index).setRefRepositoryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_Size"))
                    fragmentList.get(index).setFragmentSize(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Fragment_IRI"))
                    fragmentList.get(index).setAnyURI(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_HashValues"))
                    fragmentList.get(index).setHashValue(resultObj.getO());
            }
            else if (list.get(index) instanceof RecipeBuild) {
                List<RecipeBuild> recipeBuilds = (List<RecipeBuild>) list;
                if (resultObj.getP().endsWith("RecipeBuild_Message"))
                    recipeBuilds.get(index).setMessage(resultObj.getO().replaceFirst(FusekiUtils.KB_PREFIX_SHORT, ""));
                else if (resultObj.getP().endsWith("RecipeBuild_RecipeID"))
                    recipeBuilds.get(index).setRecipeId(resultObj.getO());
                else if (resultObj.getP().endsWith("RecipeBuild_Status"))
                    recipeBuilds.get(index).setStatus(resultObj.getO());
            }
//            "knowledgebase:Functionality_hasImplementation \"%s\" ;\n" +
//                    "knowledgebase:Functionality_Classification \"%s\" ;\n" +
//                    "knowledgebase:Functionality_Description \"%s\" ;\n" +
//                    "knowledgebase:Functionality_Domain \"%s\" ;\n" +
//                    "knowledgebase:Functionality_InputDescription \"%s\" ;\n" +
//                    "knowledgebase:Functionality_Name \"%s\" ;\n" +
//                    "knowledgebase:Functionality_OutputDescription \"%s\" ;\n" +
//                    "knowledgebase:Functionality_Tag \"%s\" ;\n" +
            else if (list.get(index) instanceof Functionality) {
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
            }
            else if (list.get(index) instanceof Delivery) {
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
            }
            else if (list.get(index) instanceof Geolocation) {
                List<Geolocation> geolocationList = (List<Geolocation>) list;
                if (resultObj.getP().endsWith("GeoLocation_CountryName"))
                    geolocationList.get(index).setCountryName(resultObj.getO());
                if (resultObj.getP().endsWith("GeoLocation_CountryNameShort"))
                    geolocationList.get(index).setCountryNameShort(resultObj.getO());
                else if (resultObj.getP().endsWith("Geolocation_Continent"))
                    geolocationList.get(index).setContinent(resultObj.getO());
                else if (resultObj.getP().endsWith("Geolocation_Latitude"))
                    geolocationList.get(index).setLatitude(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Geolocation_Longitude"))      {
                    String val = resultObj.getO();    //ugly hack
                    if (val.contains("^^http://www.w3.org/2001/XMLSchema#double"))
                        val = val.replace("^^http://www.w3.org/2001/XMLSchema#double", "").replaceAll("\"", "");
                    geolocationList.get(index).setLongitude(Double.valueOf(val));
                }
                else if (resultObj.getP().endsWith("GeoLocation_Altitude")) geolocationList.get(geolocationList.size
                        () - 1).setAltitude(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("GeoLocation_Timezone")) geolocationList.get(index).setTimezone(resultObj.getO());
            }
//            "     knowledgebase:User_Email        \"%s\" ;\n" +
//                    "        knowledgebase:User_FullName     \"%s\" ;\n" +
//                    "        knowledgebase:User_PhoneNumber  \"%s\" ;\n" +
//                    "        knowledgebase:User_UserName     \"%s\" ;" +
//                    "        knowledgebase:User_Password     \"%s\" ;" +
//                    "        knowledgebase:User_Privilege     \"%s\" ;" +
            else if (list.get(index) instanceof User) {
                List<User> userList = (List<User>) list;
                if (resultObj.getP().endsWith("User_Email"))
                    userList.get(index).setEmail(resultObj.getO());
                else if (resultObj.getP().endsWith("User_FullName"))
                    userList.get(index).setFullName(resultObj.getO());
                else if (resultObj.getP().endsWith("User_PhoneNumber"))
                    userList.get(index).setPhoneNumber(resultObj.getO());
                else if (resultObj.getP().endsWith("User_UserName"))
                    userList.get(index).setUsername(resultObj.getO());
                else if (resultObj.getP().endsWith("User_Password"))
                    userList.get(index).setPassword(resultObj.getO());
                else if (resultObj.getP().endsWith("User_Privilege"))
                    userList.get(index).setGroupID(Integer.valueOf(resultObj.getO()));
            }
            else if (list.get(index) instanceof HistoryData) {
                List<HistoryData> historyDataList = (List<HistoryData>) list;
                if (resultObj.getP().endsWith("HistoryData_Location"))
                    historyDataList.get(index).setLocation(resultObj.getO());
                else if (resultObj.getP().endsWith("HistoryData_ValidFrom"))
                    historyDataList.get(index).setValidFrom(new DateTime(resultObj.getO()).getMillis());
                else if (resultObj.getP().endsWith("HistoryData_ValidTo"))
                    historyDataList.get(index).setValidTo(new DateTime(resultObj.getO()).getMillis());
                else if (resultObj.getP().endsWith("HistoryData_Value"))
                    historyDataList.get(index).setValue(resultObj.getO());
            }
            else if (list.get(index) instanceof Pareto) {
                List<Pareto> paretoList = (List<Pareto>) list;

                if (resultObj.getP().endsWith("Pareto_Objectives"))
                    paretoList.get(index).setTableValuesFromString(resultObj.getO(), Double.class);
                else if (resultObj.getP().endsWith("Pareto_Variables"))
                    paretoList.get(index).setTableValuesFromString(resultObj.getO(), Integer.class);
                else if (resultObj.getP().endsWith("Pareto_Stage"))
                    paretoList.get(index).setStage(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Pareto_Create_Date")) {
                    try {
                        paretoList.get(index).setSaveTime(new DateTime(resultObj.getO()).getMillis());
                    } catch (Exception e) {
                        e.printStackTrace();    //remove after new reimport
                    }
                }
            }
            else {
                throw new UnsupportedOperationException("The mapping is not implemented for this class! ! " + list.get(index).getClass());
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
            if (entry.getId().contains(id)) return i;
            i++;
        }

//        System.out.println("ID ne obstaja!!! "+ id);
//
        entryList.add(EntryFactory.getInstance(clazz, id));
        return entryList.size()-1;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
