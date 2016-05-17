package org.fri.entice.webapp.entry;

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
    private Long queryTime; //request time to be deployed
    private Long deploymentTime;
    private Long endDeploymentTime;
    private String refRepositoryId;
    private String destinationCloudLocationURL;

    public FragmentHistoryDeliveryData(String id) {
        super(id);
    }

    public FragmentHistoryDeliveryData(String historyFragmentID, String fragmentId, String refImageId, Long queryTime, Long deploymentTime, Long
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

    public Long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Long queryTime) {
        this.queryTime = queryTime;
    }

    public Long getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Long deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public Long getEndDeploymentTime() {
        return endDeploymentTime;
    }

    public void setEndDeploymentTime(Long endDeploymentTime) {
        this.endDeploymentTime = endDeploymentTime;
    }

    public void setEndDeploymentTime(long endDeploymentTime) {
        this.endDeploymentTime = endDeploymentTime;
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
