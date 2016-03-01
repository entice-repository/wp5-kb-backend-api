package org.fri.entice.webapp.util;

import org.fri.entice.webapp.entry.Repository;
import org.fri.entice.webapp.entry.ResultObj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class CommonUtils {
    public static void initProperties(Properties properties, String propertyName) {
        try {
            InputStream input = new FileInputStream(new File("src/main/resources/" + propertyName));
            // load a properties file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mapResultObjectToEntry(List<?> list, ResultObj resultObj) {
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
                repositoryList.get(repositoryList.size() - 1).setPriorityLevel1Cost(Double.valueOf(resultObj.getO()));
            else if (resultObj.getP().endsWith("Repository_PriorityLevel2Cost"))
                repositoryList.get(repositoryList.size() - 1).setPriorityLevel2Cost(Double.valueOf(resultObj.getO()));
            else if (resultObj.getP().endsWith("Repository_PriorityLevel3Cost"))
                repositoryList.get(repositoryList.size() - 1).setPriorityLevel3Cost(Double.valueOf(resultObj.getO()));
            else if (resultObj.getP().endsWith("Repository_Space"))
                repositoryList.get(repositoryList.size() - 1).setSpace(Double.valueOf(resultObj.getO()));
            else if (resultObj.getP().endsWith("Repository_SupportedFormat"))
                repositoryList.get(repositoryList.size() - 1).setSupportedFormat(resultObj.getO());
        }
        else {
            throw new UnsupportedOperationException("The mapping is not implemented for this class! ! " + list.get
                    (list.size() - 1).getClass());
        }
    }
}
