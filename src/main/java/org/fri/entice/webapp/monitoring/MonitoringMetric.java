package org.fri.entice.webapp.monitoring;

public class MonitoringMetric {

    // This property allows a unique number to be generated when a new individual as a Monitoring Metric instance is
    // inserted into the knowledge base.
    private int MonitoringMetric_id;

    // This property implies the name of Monitoring Metric.
    private String MonitoringMetric_Name;

    // This is a longer, freestyle textual description of the Monitoring Metric as provided by the Switch’s user.
    private String MonitoringMetric_Description;

    // For example metrics like memTotal, memFree, memUsed and memUsedPerecent are belonging to group "MemoryProbe".
    private String MonitoringMetric_Group;

    // MonitoringProbe_id
    // Each raw Monitoring Metric is measured and gathered by the associated Monitoring Probe.
    private String MonitoringMetric_ProbeId;

    // Important Monitoring Metrics can be monitored in IaaS and in SaaS scenarios which are respectively
    // infrastructure-level and application-level.
    private int MonitoringMetric_Level;

    // Monitoring Metric’s unit could be percentage (%), KBps, MBps, Bps and so on.
    private String MonitoringMetric_Unit;

    // Monitoring Metric’s data type could be integer, double, etc.
    private String MonitoringMetric_DataType;

    // (seconds)	The collecting period is an important parameter for the time-critical environment.
    private int MonitoringMetric_CollectingPeriod;

    // This property implies the maximum value of the metric that could be observed.
    private double MonitoringMetric_UperLimit;

    // This property implies the minimum value of the metric that could be observed.
    private double MonitoringMetric_LowerLimit;

    // (“true” or “false”)	In JCatascopia, a SimpleFilter is available, where by providing a range for a specific
    // Probe Property, a window [LastValue-range, LastValue+range] is determined. Values residing in the window are
    // discarded.
    private boolean MonitoringMetric_Filtering;

    // For example, it is possible to set a SimpleFilter with the range “0.5” on the memUsedPercent property to
    // filter out values between ±0.5% of the previously reported value.
    private double MonitoringMetric_FilteringRange;

    // The metric’s average value for the past 1 minute.
    private double MonitoringMetric_1Minute;

    // The metric’s average value for the past 5 minutes.
    private double MonitoringMetric_5Minutes;

    // The metric’s average value for the past 15 minutes.
    private double MonitoringMetric_15Minutes;

    // The metric’s average value for the next 1 minute. This prediction could be done by a simple linear regression
    // and so on.
    private double MonitoringMetric_Next1Minute;

    // The metric’s average value for the next 5 minutes. This prediction could be done by a simple linear regression
    // and so on.
    private double MonitoringMetric_Next5Minutes;

    // The metric’s average value for the next 15 minutes. This prediction could be done by a simple linear
    // regression and so on.
    private double MonitoringMetric_Next15Minutes;


    public MonitoringMetric(int monitoringMetric_id, String monitoringMetric_Name, String
            monitoringMetric_Description, String monitoringMetric_Group, String monitoringMetric_ProbeId, int
            monitoringMetric_Level, String monitoringMetric_Unit, String monitoringMetric_DataType, int
            monitoringMetric_CollectingPeriod, double monitoringMetric_UperLimit, double monitoringMetric_LowerLimit,
                            boolean monitoringMetric_Filtering, double monitoringMetric_FilteringRange, double
                                    monitoringMetric_1Minute, double monitoringMetric_5Minutes, double
                                    monitoringMetric_15Minutes, double monitoringMetric_Next1Minute, double
                                    monitoringMetric_Next5Minutes, double monitoringMetric_Next15Minutes) {
        MonitoringMetric_id = monitoringMetric_id;
        MonitoringMetric_Name = monitoringMetric_Name;
        MonitoringMetric_Description = monitoringMetric_Description;
        MonitoringMetric_Group = monitoringMetric_Group;
        MonitoringMetric_ProbeId = monitoringMetric_ProbeId;
        MonitoringMetric_Level = monitoringMetric_Level;
        MonitoringMetric_Unit = monitoringMetric_Unit;
        MonitoringMetric_DataType = monitoringMetric_DataType;
        MonitoringMetric_CollectingPeriod = monitoringMetric_CollectingPeriod;
        MonitoringMetric_UperLimit = monitoringMetric_UperLimit;
        MonitoringMetric_LowerLimit = monitoringMetric_LowerLimit;
        MonitoringMetric_Filtering = monitoringMetric_Filtering;
        MonitoringMetric_FilteringRange = monitoringMetric_FilteringRange;
        MonitoringMetric_1Minute = monitoringMetric_1Minute;
        MonitoringMetric_5Minutes = monitoringMetric_5Minutes;
        MonitoringMetric_15Minutes = monitoringMetric_15Minutes;
        MonitoringMetric_Next1Minute = monitoringMetric_Next1Minute;
        MonitoringMetric_Next5Minutes = monitoringMetric_Next5Minutes;
        MonitoringMetric_Next15Minutes = monitoringMetric_Next15Minutes;
    }
}
