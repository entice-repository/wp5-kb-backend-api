package org.fri.entice.webapp.cassandra;

public class CassandraService implements ICassandraService {
    @Override
    public boolean createCassandraKeyspace(String keyspaceName) {
        return false;
    }

    @Override
    public boolean addMonitorObject(Object object) {
        return false;
    }

    @Override
    public boolean editMonitorObject(Object object) {
        return false;
    }

    @Override
    public boolean deleteMonitorObject(Object object) {
        return false;
    }

    @Override
    public boolean getMonitorObject(String id) {
        return false;
    }

    @Override
    public boolean getAllMonitorObjects() {
        return false;
    }

//    private Cluster cluster;
//
//    // just temporary static
//    public static Session session;
//    public static List<MetricTable> allMonitoringData;
//
//    private Cassandra.Client client;
//    private Logger logger;
//
//    public CassandraService(CassandraParamsObj cassandraParamsObj) {
//        initCassandra(cassandraParamsObj);
//
//        logger = Logger.getLogger(this.getClass().getName());
//        // todo remove:  JUST FOR TESTING!
//        //ResultSet results = session.execute("INSERT INTO tag (id, name, time) VALUES (" + UUIDs.timeBased() + ",
//        // 'Smith', dateof(now()))");
////        for (Row row : results) {
////            System.out.format("%s %d\n", row.getString("firstname"), row.getInt("age"));
////        }
//
//    }
//
//    public static void initCassandraPropertyTables() {
//        Select select = QueryBuilder.select().from("metric_table");
//        ResultSet results = session.execute(select);
//        List<Row> resultList = results.all();
//        allMonitoringData = new ArrayList<MetricTable>();
//
//        for (int i = 0; i < resultList.size(); i++) {
//            allMonitoringData.add(new MetricTable(resultList.get(i).getString("agentid"), resultList.get(i).getString
//                    ("metricid"), resultList.get(i).getString("mgroup"), resultList.get(i).getString("name"),
//                    resultList.get(i).getString("type"), resultList.get(i).getString("units")));
//        }
//
//    }
//
//    private void initCassandra(CassandraParamsObj cassandraParamsObj) {
//
//
//        try {
//            // Connect to the cluster and keyspace
//            cluster = Cluster.builder().addContactPoint("194.249.0.72").withPort(9042).build();
//            session = cluster.connect("jcatascopiadb");
//
////            cluster = Cluster.builder().addContactPoint(cassandraParamsObj.getClusterIPList().get(0)).withPort
//// (9042).withCredentials("cassandra", "cassandra").build();
////            session = cluster.connect(cassandraParamsObj.getKeyspace());
//
//            // connectToCassandraCluster("localhost", 9160, "cassandra", "cassandra", 5000);
//            //createSimpleInsert2();
//
//            /*
//            // select KEYSPACE
//            //  client.set_keyspace("entice");
//            Statement statement = new SimpleStatement("SELECT * FROM monitoring_data limit 20");
//            statement.setFetchS ize(5);
//            ResultSet rs = session.execute(statement);
//            String formattedQuery = "SELECT count(*) FROM monitoring_data";
//
//            //"dateof" + "(now()))";
//            // About ConsistencyLevel:
//            // http://docs.datastax.com/en/cassandra/2.0/cassandra/dml/dml_config_consistency_c.html
//            CqlResult cqlQuery = client.execute_cql3_query(ByteBuffer.wrap(formattedQuery.getBytes()), Compression
//                    .NONE, ConsistencyLevel.ALL);
//            cqlQuery.toString();
//            */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("batch insert finished");
//
//    }
//
//    /**
//     * Construct an CassandaraConnection with optional authentication.
//     *
//     * @param host     the host to connect to
//     * @param port     the port to use
//     * @param username the username to authenticate with (may be nullfor no authentication)
//     * @param password the password to authenticate with (may be nullfor no authentication)
//     * @throws Exception if the connection fails
//     *                   <p/>
//     *                   Other authentication examples:
//     *                   http://www.programcreek.com/java-api-examples/index.php?api=org.apache.cassandra.thrift
//     *                   .AuthenticationRequest
//     */
//    public void connectToCassandraCluster(String host, int port, String username, String password, int timeout)
//            throws Exception {
//        TSocket socket = new TSocket(host, port);
//        if (timeout > 0) {
//            socket.setTimeout(timeout);
//        }
//        TFramedTransport m_transport = new TFramedTransport(socket);
//        TProtocol protocol = new TBinaryProtocol(m_transport);
//        client = new Cassandra.Client(protocol);
//        m_transport.open();
//        if (!username.isEmpty() && !password.isEmpty()) {
//            Map<String, String> creds = new HashMap<String, String>();
//            creds.put("username", username);
//            creds.put("password", password);
//            AuthenticationRequest auth = new AuthenticationRequest(creds);
//            client.login(auth);
//        }
//    }
//
//    /**
//     * Connect to selected keyspace and execute CQL query through RPC
//     */
////    private void createSimpleInsert() {
////        try {
////
////            // Human time (GMT): Thu, 01 Oct 2015 00:00:00 GMT
////            long currentTime = 1443657600000L;
////
////            // for each second entire month:
////            int limit = 31 * 24 * 60 * 60;
////
////            // write a query
////            for (int i = 0; i < limit; i++) {
////                String formattedQuery = "INSERT INTO monitoring_data (id, monitoring_metric, value, time) VALUES (" +
////                        UUIDs.timeBased() + ", 'CPU_USAGE', " + Math.random() * 100 + " ," + (++currentTime) + ")";
////                //"dateof" + "(now()))";
////                // About ConsistencyLevel:
////                // http://docs.datastax.com/en/cassandra/2.0/cassandra/dml/dml_config_consistency_c.html
////                client.execute_cql3_query(ByteBuffer.wrap(formattedQuery.getBytes()), Compression.NONE,
////                        ConsistencyLevel.ALL);
////            }
////        } catch (TException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//    /**
//     * Connect to selected keyspace and execute CQL #2
//     */
//    private void createSimpleInsert2() {
//        try {
//            // Human time (GMT): Thu, 01 Oct 2015 00:00:00 GMT
//            long currentTime = 1443657600000L;
//            // for each second entire month:
//            int limit = 31 * 24 * 60 * 60;
//
//            int day = 0;
//            // write a query - insertions for one week
//            for (int i = 0; i < limit / 2; i++) {
//                session.executeAsync("INSERT INTO monitoring_data (id, monitoring_metric, value, time) VALUES (" +
//                        UUIDs.timeBased() + ", 'CPU_USAGE', " + Math.random() * 100 + " ," + currentTime + ")");
//
//                // increment by 1000 for each millisecond
//                currentTime += 1000;
//
//                if (i % 86400 == 0) System.out.println("day " + ++day + " inserted (86400 entries)");
//
//            }
//            System.out.println("FINISHED!!!!!!!!!!!!!!!!!!!!!!!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public boolean createCassandraKeyspace(String keyspaceName) {
//        return false;
//    }
//
//    @Override
//    public boolean addMonitorObject(Object object) {
//        return false;
//    }
//
//    @Override
//    public boolean editMonitorObject(Object object) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteMonitorObject(Object object) {
//        return false;
//    }
//
//    @Override
//    public boolean getMonitorObject(String id) {
//        return false;
//    }
//
//    @Override
//    public boolean getAllMonitorObjects() {
//        return false;
//    }
//
//    public ResultSet executeQuery(String query) {
//        return session.execute(query);
//
//    }
}
