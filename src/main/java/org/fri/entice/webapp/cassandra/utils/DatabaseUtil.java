package org.fri.entice.webapp.cassandra.utils;

import com.datastax.driver.core.Row;
import org.fri.entice.webapp.rest.AppContextListener;

public class DatabaseUtil {

    public static void initProperties() {
        // get the property value and print it out
        System.out.println(AppContextListener.prop.getProperty("apache.cassandra.port"));
        System.out.println(AppContextListener.prop.getProperty("apache.cassandra.keyspace"));
        System.out.println(AppContextListener.prop.getProperty("dbpassword"));
    }

    public static String getCommaSepataredIDStrings(Iterable resultSetImageTags) {
        StringBuilder sb = new StringBuilder();

        for (Object row : resultSetImageTags) {
            if (row instanceof Row) {
                sb.append(((Row) row).getUUID(0) + ",");
            }
            else if (row instanceof String) {
                sb.append(row + ",");
            }

        }
        return sb.toString().isEmpty() ? null : sb.substring(0, sb.length() - 1);
    }

}
