package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MetricTable {

    private String agentID;
    private String metricID;
    //private boolean is_sub;
    private String mGroup;
    private String name;
    private String type;
    private String units;

    public MetricTable(String agentID, String metricID, String mGroup, String name, String type, String units) {
        this.agentID = agentID;
        this.metricID = metricID;
        this.mGroup = mGroup;
        this.name = name;
        this.type = type;
        this.units = units;
    }

    public MetricTable() {
    }

    public String getAgentID() {
        return agentID;
    }

    public String getMetricID() {
        return metricID;
    }

    public String getmGroup() {
        return mGroup;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUnits() {
        return units;
    }
}
