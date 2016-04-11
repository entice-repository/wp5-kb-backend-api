package org.fri.entice.webapp.rest;

import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.fri.entice.webapp.cassandra.CassandraParamsObj;
import org.fri.entice.webapp.cassandra.CassandraService;
import org.fri.entice.webapp.util.CommonUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            CommonUtils.initProperties(prop,"db.properties");
            logger.log(Level.INFO, "DB properties successfully initialized.");

            // connect to Cassandra Service
            //initCassandraConnectionToService();


            // Read JRules from file ...
            BufferedReader br = null;
            try{
                br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("test_rule.txt")));

                List rules = Rule.parseRules(Rule.rulesParserFromReader(br));
                GenericRuleReasoner genericRuleReasoner = new GenericRuleReasoner(rules);
                genericRuleReasoner.setMode(GenericRuleReasoner.FORWARD);
                genericRuleReasoner.setTraceOn(true);

                for(Object r : rules)  //... and print them
                    System.out.println(r.toString());
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCassandraConnectionToService() {
        List<String> clusters = new ArrayList<String>();
        clusters.add(prop.getProperty("apache.cassandra.ip"));
        cassandraService = new CassandraService(new CassandraParamsObj(Integer.parseInt(prop.getProperty("apache" + "" +
                ".cassandra.port")), prop.getProperty("apache.cassandra.keyspace"), clusters));
        logger.log(Level.INFO, "Cassandra connected");


        CassandraService.initCassandraPropertyTables();
        logger.log(Level.INFO, "Metric table data loaded.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
