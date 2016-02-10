package org.fri.entice.webapp.rest;

import org.fri.entice.webapp.cassandra.CassandraParamsObj;
import org.fri.entice.webapp.cassandra.CassandraService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppContextListener implements ServletContextListener {

    private Logger logger;
    public static Properties prop = new Properties();
    private CassandraService cassandraService;

    /**
     * CALLED ON SERVICE INIT
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ResourceBundle rb = ResourceBundle.getBundle("language/bundle");
//        Enumeration<String> keys = rb.getKeys();
//        while (keys.hasMoreElements()) {
//            String key = keys.nextElement();
//            String value = rb.getString(key);
//            System.out.println(key + ": " + value);
//        }

        try {
            // init logger
            logger = Logger.getLogger(this.getClass().getName());

            // init db.property list
            initProperties();
            logger.log(Level.INFO, "DB properties successfully initialized.");

            // connect to Cassandra Service
            //initCassandraConnectionToService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCassandraConnectionToService() {
        List<String> clusters = new ArrayList<String>();
        clusters.add(prop.getProperty("apache.cassandra.ip"));
        cassandraService = new CassandraService(new CassandraParamsObj(Integer.parseInt(prop.getProperty("apache"
                + ".cassandra.port")), prop.getProperty("apache.cassandra.keyspace"), clusters));
        logger.log(Level.INFO, "Cassandra connected");


        CassandraService.initCassandraPropertyTables();
        logger.log(Level.INFO, "Metric table data loaded.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    private void initProperties() {
        try {
            InputStream input = new FileInputStream(new File("src/main/resources/db.properties"));

            // load a properties file
            prop.load(input);

            // get the property value and print it out
//            System.out.println(prop.getProperty("apache.cassandra.port"));
//            System.out.println(prop.getProperty("apache.cassandra.keyspace"));
//            System.out.println(prop.getProperty("dbpassword"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
