package org.fri.entice.webapp.cassandra.utils;

import com.datastax.driver.core.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseUtil {

    private static Properties prop = new Properties();

    public static void initProperties() {
        try {
            InputStream input = new FileInputStream(new File("src/main/resources/db.properties"));

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("apache.cassandra.port"));
            System.out.println(prop.getProperty("apache.cassandra.keyspace"));
            System.out.println(prop.getProperty("dbpassword"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCommaSepataredIDStrings(Iterable resultSetImageTags) {
        StringBuilder sb = new StringBuilder();

        for (Object row : resultSetImageTags) {
            if (row instanceof Row) {
                sb.append(((Row) row).getUUID(0) + ",");
            } else if (row instanceof String) {
                sb.append(row + ",");
            }

        }
        return sb.toString().isEmpty() ? null : sb.substring(0, sb.length() - 1);
    }

}
