package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * This class specifies the overall disk image delivery event. It collects important information needed for reasoning,
 * e.g. to check out if the Quality of Service and Service Level Agreement is met by the services of the distributed
 * ENTICE repository.
 */ public class Delivery extends MyEntry {

    // Specifies the user or the Cloud application that requested the delivery of a particular disk image implementing
    // a particular functionality.
    private String refUserId;

    // Specifies the functionality that was requested.
    private String refFunctionalityId;

    // Specifies the disk image which was delivered upon the request of the user or the Cloud application.
    private String refDiskImageId;

    // Specifies the target repository where according to the internal reasoning method of the ENTICE repository the
    // disk image must be delivered.
    private String refTargetRepositoryId;

    // Specifies the time when the delivery request was received by the ENTICE environment.
    private long requestTime;

    private long deliveryTime;

    public Delivery(String id, String refUserId, String refFunctionalityId, long requestTime, String
            refTargetRepositoryId, String refDiskImageId, long deliveryTime) {
        super(id);
        this.refUserId = refUserId;
        this.refFunctionalityId = refFunctionalityId;
        this.requestTime = requestTime;
        this.refTargetRepositoryId = refTargetRepositoryId;
        this.refDiskImageId = refDiskImageId;
        this.deliveryTime = deliveryTime;
    }

    public Delivery(String id){
        super(id);
    }

    public String getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(String refUserId) {
        this.refUserId = refUserId;
    }

    public String getRefFunctionalityId() {
        return refFunctionalityId;
    }

    public void setRefFunctionalityId(String refFunctionalityId) {
        this.refFunctionalityId = refFunctionalityId;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getRefTargetRepositoryId() {
        return refTargetRepositoryId;
    }

    public void setRefTargetRepositoryId(String refTargetRepositoryId) {
        this.refTargetRepositoryId = refTargetRepositoryId;
    }

    public String getRefDiskImageId() {
        return refDiskImageId;
    }

    public void setRefDiskImageId(String refDiskImageId) {
        this.refDiskImageId = refDiskImageId;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
