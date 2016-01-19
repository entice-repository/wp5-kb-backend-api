package org.fri.entice.webapp.cassandra;

public interface ICassandraService {

    public boolean createCassandraKeyspace(String keyspaceName);

    public boolean addMonitorObject(Object object);

    public boolean editMonitorObject(Object object);

    public boolean deleteMonitorObject(Object object);

    public boolean getMonitorObject(String id);

    public boolean getAllMonitorObjects();

}
