package org.fri.entice.webapp.cassandra;

import java.util.List;

public class CassandraParamsObj {

    private int port;
    private String keyspace;
    private List<String> clusterIPList;

    public CassandraParamsObj(int port, String keyspace, List<String> clusterIPList) {
        this.port = port;
        this.keyspace = keyspace;
        this.clusterIPList = clusterIPList;
    }

    public int getPort() {
        return port;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public List<String> getClusterIPList() {
        return clusterIPList;
    }
}
