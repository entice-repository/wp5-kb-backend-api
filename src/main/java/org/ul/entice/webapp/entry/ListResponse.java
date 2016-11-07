package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ListResponse {

    private List<MetricTable> metricTableList;

    public ListResponse(List<MetricTable> metricTableList) {
        this.metricTableList = metricTableList;
    }

    public List<MetricTable> getMetricTableList() {
        return metricTableList;
    }

    public void setMetricTableList(List<MetricTable> metricTableList) {
        this.metricTableList = metricTableList;
    }
}
