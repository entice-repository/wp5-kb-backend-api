package org.ul.entice.webapp.client;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class CassandraDB {

    private static Session session;
    private static Logger logger;

    public static void main(String[] args) {
//        new CassandraDB(logger, "jcatascopiadb", "194.249.1.46", 9042);
        new CassandraDB(Logger.getLogger("cassandra_logger"), "jcatascopiadb", "***", 9042);
    }

    public CassandraDB(Logger logger, String databaseName, String cassandraIP, int port) {
        this.logger = logger;
        try {
            final InetAddress ip = InetAddress.getByName(cassandraIP);
            initDatabase(ip, port, databaseName);
        } catch (UnknownHostException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDatabase(InetAddress ip, int port, String databaseName) {
        try {
            //connect to cassandra cluster
            Cluster cluster = Cluster.builder().addContactPoints(ip.getHostAddress()).withCredentials
                    ("catascopia_user", "catascopia_pass").withPort(port).withRetryPolicy
                    (DowngradingConsistencyRetryPolicy.INSTANCE).withReconnectionPolicy(new
                    ConstantReconnectionPolicy(1000L)).build();
            session = cluster.connect(databaseName);

            System.out.println("Logged to keyspace: "+ session.getLoggedKeyspace());
        } catch (InvalidQueryException e) {
            if (e.getMessage().equals("Keyspace jcatascopiaDB does not exist")) {
                Cluster cluster = Cluster.builder().addContactPoints("***")
                       .withCredentials("catascopia_user", "catascopia_pass")
                        .build();
                Session session = cluster.connect("jcatascopiadb");

                session.getState();
            }
            else {
                e.printStackTrace();
            }
        } catch (NoHostAvailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        return session;
    }
}
