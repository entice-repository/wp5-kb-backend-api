package org.fri.entice.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
