package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Repository extends MyEntry {
    // This property means a repository’s infrastructure is in which country. Each county has a unique Country_id.
    private String countryId;

    // There could be more than one repository in a particular country and geolocation of repository may influence the
    // download time. Each GeoLocation has a unique GeoLocation_id.
    private String geolocationId;
    private List<Geolocation> geolocationList;

    // Each repository can be easily accessed through its unique url.
    private String interfaceEndpoint;

    // ENTICE will research heuristics for multi-objective distribution and placement of VM images across a
    // decentralised ENTICE repository that optimises multiple conflicting objectives including performance-related
    // goals, operational costs, and storage space.
    private double operationalCost;         //USD per GB

    // As fast storage costs more and slow storage costs less, it is necessary to provide mechanisms for storing VM
    // images at three speed levels; namely: fast, medium and low-speed. Level1 means fast storage.
    private double priorityLevel1Cost;  //OLD
//    private double storageLevelCost;       // TODO: to be updated and aranged with UIBK

    private double priorityLevel2Cost;

    // Level1 means low-speed storage.
    private double priorityLevel3Cost;

    private double theoreticalCommunicationalPerformance;   // MB

    // In the ENTICE environment, dynamic information about the underlying federated Cloud infrastructure resources
    // such as storage space is needed to be stored.
    private double space;

    // Each repository could be able to support multiple VM image's formats or even only one format. It means each
    // repository should be able to support the conversion into various kinds of VM/C images from one format for
    // portability into various environments. This property is a likely scenario with heterogeneous infrastructure
    // behind the Cloud-based Entice environment.
    private List<String> supportedFormat; // TODO: Should we add the speed value of each format

    public Repository(String id, String countryId, String geolocationId, String interfaceEndpoint, double
            operationalCost, double priorityLevel1Cost, double priorityLevel2Cost, double priorityLevel3Cost, double
            space, List<String> supportedFormat, double theoreticalCommunicationalPerformance) {
        super(id);
        this.countryId = countryId;
        this.geolocationId = geolocationId;
        this.interfaceEndpoint = interfaceEndpoint;
        this.operationalCost = operationalCost;
        this.priorityLevel1Cost = priorityLevel1Cost;
        this.priorityLevel2Cost = priorityLevel2Cost;
        this.priorityLevel3Cost = priorityLevel3Cost;
        this.space = space;
        this.supportedFormat = supportedFormat;
        this.theoreticalCommunicationalPerformance = theoreticalCommunicationalPerformance;
    }

    public Repository(String id) {
       super(id);
    }

//    @Override
//    public void createInstanceWithId(String id) {
//        this.i
//        super(id);
//    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getGeolocationId() {
        return geolocationId;
    }

    public void setGeolocationId(String geolocationId) {
        this.geolocationId = geolocationId;
    }

    public String getInterfaceEndpoint() {
        return interfaceEndpoint;
    }

    public void setInterfaceEndpoint(String interfaceEndpoint) {
        this.interfaceEndpoint = interfaceEndpoint;
    }

    public double getOperationalCost() {
        return operationalCost;
    }

    public void setOperationalCost(double operationalCost) {
        this.operationalCost = operationalCost;
    }

    public double getPriorityLevel1Cost() {
        return priorityLevel1Cost;
    }

    public void setPriorityLevel1Cost(double priorityLevel1Cost) {
        this.priorityLevel1Cost = priorityLevel1Cost;
    }

    public double getPriorityLevel2Cost() {
        return priorityLevel2Cost;
    }

    public void setPriorityLevel2Cost(double priorityLevel2Cost) {
        this.priorityLevel2Cost = priorityLevel2Cost;
    }

    public double getPriorityLevel3Cost() {
        return priorityLevel3Cost;
    }

    public void setPriorityLevel3Cost(double priorityLevel3Cost) {
        this.priorityLevel3Cost = priorityLevel3Cost;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public List<String> getSupportedFormat() {
        return supportedFormat;
    }

    public void setSupportedFormat(List<String> supportedFormat) {
        this.supportedFormat = supportedFormat;
    }

    public void setSupportedFormat(String supportedFormatVal) {
        if (supportedFormat == null) supportedFormat = new ArrayList<String>();

        supportedFormat.add(supportedFormatVal);
    }

    public double getTheoreticalCommunicationalPerformance() {
        return theoreticalCommunicationalPerformance;
    }

    public void setTheoreticalCommunicationalPerformance(double theoreticalCommunicationalPerformance) {
        this.theoreticalCommunicationalPerformance = theoreticalCommunicationalPerformance;
    }

    public List<Geolocation> getGeolocationList() {
        return geolocationList;
    }

    public void setGeolocationList(List<Geolocation> geolocationList) {
        this.geolocationList = geolocationList;
    }
}
