package org.fri.entice.webapp.util;

import org.fri.entice.webapp.entry.*;

import java.io.IOException;
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


    public static void mapResultObjectToEntry(List<?> list, ResultObj resultObj) {
        try {
            if(list.size() == 0)
                return;

            if (list.get(list.size() - 1) instanceof Repository) {
                List<Repository> repositoryList = (List<Repository>) list;
                if (resultObj.getP().endsWith("Repository_Country"))
                    repositoryList.get(repositoryList.size() - 1).setCountryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_GeoLocation"))
                    repositoryList.get(repositoryList.size() - 1).setGeolocationId(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_OperationalCost"))
                    repositoryList.get(repositoryList.size() - 1).setInterfaceEndpoint(resultObj.getO());
                else if (resultObj.getP().endsWith("Repository_OperationalCost"))
                    repositoryList.get(repositoryList.size() - 1).setOperationalCost(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_PriorityLevel1Cost"))
                    repositoryList.get(repositoryList.size() - 1).setPriorityLevel1Cost(Double.valueOf(resultObj.getO
                            ()));
                else if (resultObj.getP().endsWith("Repository_PriorityLevel2Cost"))
                    repositoryList.get(repositoryList.size() - 1).setPriorityLevel2Cost(Double.valueOf(resultObj.getO
                            ()));
                else if (resultObj.getP().endsWith("Repository_PriorityLevel3Cost"))
                    repositoryList.get(repositoryList.size() - 1).setPriorityLevel3Cost(Double.valueOf(resultObj.getO
                            ()));
                else if (resultObj.getP().endsWith("Repository_Space"))
                    repositoryList.get(repositoryList.size() - 1).setSpace(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Repository_SupportedFormat"))
                    repositoryList.get(repositoryList.size() - 1).setSupportedFormat(resultObj.getO());
            }
            else if (list.get(list.size() - 1) instanceof DiskImage) {
                List<DiskImage> repositoryList = (List<DiskImage>) list;
                if (resultObj.getO().endsWith("CI"))
                    repositoryList.get(repositoryList.size() - 1).setImageType(ImageType.CI);
                else if (resultObj.getO().endsWith("VMI"))
                    repositoryList.get(repositoryList.size() - 1).setImageType(ImageType.VMI);
                else if (resultObj.getP().endsWith("DiskImage_Description"))
                    repositoryList.get(repositoryList.size() - 1).setDescription(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Encryption"))
                    repositoryList.get(repositoryList.size() - 1).setEncryption(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_FileFormat"))
                    repositoryList.get(repositoryList.size() - 1).setFileFormat(FileFormat.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Owner"))
                    repositoryList.get(repositoryList.size() - 1).setRefOwnerId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_DataId"))
                    repositoryList.get(repositoryList.size() - 1).setDataId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_refFunctionalityId"))
                    repositoryList.get(repositoryList.size() - 1).setRefFunctionalityId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_generationTime"))
                    repositoryList.get(repositoryList.size() - 1).setGenerationTime(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_IRI"))
                    repositoryList.get(repositoryList.size() - 1).setIri(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_NeedsDataFile"))
                    repositoryList.get(repositoryList.size() - 1).setNeedsData(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Obfuscation"))
                    repositoryList.get(repositoryList.size() - 1).setObfuscation(Boolean.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_OperatingSystem"))
                    repositoryList.get(repositoryList.size() - 1).setRefOperatingSystemId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Picture"))
                    repositoryList.get(repositoryList.size() - 1).setPictureUrl(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Predecessor"))
                    repositoryList.get(repositoryList.size() - 1).setPredecessor(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Price"))
                    repositoryList.get(repositoryList.size() - 1).setPrice(Double.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Quality"))
                    repositoryList.get(repositoryList.size() - 1).setRefQualityId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_SLA"))
                    repositoryList.get(repositoryList.size() - 1).setRefSlaId(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Title"))
                    repositoryList.get(repositoryList.size() - 1).setTitle(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Version"))
                    repositoryList.get(repositoryList.size() - 1).setVersion(resultObj.getO());
                else if (resultObj.getP().endsWith("DiskImage_Size"))
                    repositoryList.get(repositoryList.size() - 1).setDiskImageSize(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("DiskImage_Size"))
                    repositoryList.get(repositoryList.size() - 1).setDiskImageSize(Integer.valueOf(resultObj.getO()));
            }
            else if (list.get(list.size() - 1) instanceof Fragment) {
                List<Fragment> fragmentList = (List<Fragment>) list;
                if (resultObj.getP().endsWith("Fragment_hasReferenceImage"))
                    fragmentList.get(fragmentList.size() - 1).setRefDiskImageId(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_hasRepository"))
                    fragmentList.get(fragmentList.size() - 1).setRefRepositoryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_Size"))
                    fragmentList.get(fragmentList.size() - 1).setFragmentSize(Integer.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Fragment_IRI"))
                    fragmentList.get(fragmentList.size() - 1).setAnyURI(resultObj.getO());
                else if (resultObj.getP().endsWith("Fragment_HashValues"))
                    fragmentList.get(fragmentList.size() - 1).setHashValue(resultObj.getO());
            }
            else if (list.get(list.size() - 1) instanceof Delivery) {
                List<Delivery> fragmentList = (List<Delivery>) list;
                if (resultObj.getP().endsWith("Delivery_hasDeliveredDiskImage"))
                    fragmentList.get(fragmentList.size() - 1).setRefDiskImageId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasFunctionality"))
                    fragmentList.get(fragmentList.size() - 1).setRefFunctionalityId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasTargetRepository"))
                    fragmentList.get(fragmentList.size() - 1).setRefTargetRepositoryId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_hasUser"))
                    fragmentList.get(fragmentList.size() - 1).setRefUserId(resultObj.getO());
                else if (resultObj.getP().endsWith("Delivery_DeliveryTime"))
                    fragmentList.get(fragmentList.size() - 1).setDeliveryTime(Long.valueOf(resultObj.getO()));
                else if (resultObj.getP().endsWith("Delivery_RequestTime"))
                    fragmentList.get(fragmentList.size() - 1).setRequestTime(Long.valueOf(resultObj.getO()));
            }
            else {
                throw new UnsupportedOperationException("The mapping is not implemented for this class! ! " + list.get(list.size() - 1).getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
