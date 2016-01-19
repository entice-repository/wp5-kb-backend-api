package org.fri.entice.webapp.rest;

import org.fri.entice.webapp.entry.ListResponse;
import org.fri.entice.webapp.entry.MetricTable;

import java.util.List;

public interface IMonitoringRequests {

    public String getMetricValueTable(String dateFrom);

    public String getAllMetrics();

    public ListResponse getUsedMetrics();

    public List<MetricTable> getAllMonitoringData();

    public String getPeriodMonitoringData(String dateFrom);
}
