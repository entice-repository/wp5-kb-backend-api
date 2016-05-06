package org.fri.entice.webapp.entry;

import org.joda.time.DateTime;

/**
 * query:  start date ?  end date  -- deployment time
 */
public class FragmentHistoryDeliveryData extends MyEntry {


    /*  query time:
        when asked to be deployed
        when ready to be deployed
    */


    // id == historyFragmentID
    private String fragmentId;
    private String refImageId;
    private DateTime queryTime; //request time to be deployed
    private DateTime deploymentTime;
    private DateTime endDeploymentTime;
    private String refRepositoryId;
    private String destinationCloudLocationURL;

    public FragmentHistoryDeliveryData(String id) {
        super(id);
    }

    public FragmentHistoryDeliveryData(String historyFragmentID, String fragmentId, String refImageId, DateTime queryTime, DateTime deploymentTime, DateTime
            endDeploymentTime, String refRepositoryId, String destinationCloudLocationURL) {
        super(historyFragmentID);
        this.fragmentId = fragmentId;
        this.refImageId = refImageId;
        this.queryTime = queryTime;
        this.deploymentTime = deploymentTime;
        this.endDeploymentTime = endDeploymentTime;
        this.refRepositoryId = refRepositoryId;
        this.destinationCloudLocationURL = destinationCloudLocationURL;
    }

    public DateTime getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(DateTime queryTime) {
        this.queryTime = queryTime;
    }

    public DateTime getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(DateTime deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getRefRepositoryId() {
        return refRepositoryId;
    }

    public void setRefRepositoryId(String refRepositoryId) {
        this.refRepositoryId = refRepositoryId;
    }

    public String getDestinationCloudLocationURL() {
        return destinationCloudLocationURL;
    }

    public void setDestinationCloudLocationURL(String destinationCloudLocationURL) {
        this.destinationCloudLocationURL = destinationCloudLocationURL;
    }

    public DateTime getEndDeploymentTime() {
        return endDeploymentTime;
    }

    public void setEndDeploymentTime(DateTime endDeploymentTime) {
        this.endDeploymentTime = endDeploymentTime;
    }

    public String getRefImageId() {
        return refImageId;
    }

    public void setRefImageId(String refImageId) {
        this.refImageId = refImageId;
    }

    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }
}
