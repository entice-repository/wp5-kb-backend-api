package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiskImageSLA {
    private String id;
    private String hasAgreedAvailabilityCountry;   //countryId
    private String hasAgreedAvailabilityRepository;   //repositoryId
    private String hasAgreedRestriction;   //countryId
    private int hasAgreedPriorityLevel;
    private int hasAgreedQoSOrder;
    private int securedDelivery;

    public DiskImageSLA(String id, String hasAgreedAvailabilityCountry, String hasAgreedAvailabilityRepository,
                        String hasAgreedRestriction, int hasAgreedPriorityLevel, int hasAgreedQoSOrder, int
                                securedDelivery) {
        this.id = id;
        this.hasAgreedAvailabilityCountry = hasAgreedAvailabilityCountry;
        this.hasAgreedAvailabilityRepository = hasAgreedAvailabilityRepository;
        this.hasAgreedRestriction = hasAgreedRestriction;
        this.hasAgreedPriorityLevel = hasAgreedPriorityLevel;
        this.hasAgreedQoSOrder = hasAgreedQoSOrder;
        this.securedDelivery = securedDelivery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHasAgreedAvailabilityCountry() {
        return hasAgreedAvailabilityCountry;
    }

    public void setHasAgreedAvailabilityCountry(String hasAgreedAvailabilityCountry) {
        this.hasAgreedAvailabilityCountry = hasAgreedAvailabilityCountry;
    }

    public String getHasAgreedAvailabilityRepository() {
        return hasAgreedAvailabilityRepository;
    }

    public void setHasAgreedAvailabilityRepository(String hasAgreedAvailabilityRepository) {
        this.hasAgreedAvailabilityRepository = hasAgreedAvailabilityRepository;
    }

    public String getHasAgreedRestriction() {
        return hasAgreedRestriction;
    }

    public void setHasAgreedRestriction(String hasAgreedRestriction) {
        this.hasAgreedRestriction = hasAgreedRestriction;
    }

    public int getHasAgreedPriorityLevel() {
        return hasAgreedPriorityLevel;
    }

    public void setHasAgreedPriorityLevel(int hasAgreedPriorityLevel) {
        this.hasAgreedPriorityLevel = hasAgreedPriorityLevel;
    }

    public int getHasAgreedQoSOrder() {
        return hasAgreedQoSOrder;
    }

    public void setHasAgreedQoSOrder(int hasAgreedQoSOrder) {
        this.hasAgreedQoSOrder = hasAgreedQoSOrder;
    }

    public int getSecuredDelivery() {
        return securedDelivery;
    }

    public void setSecuredDelivery(int securedDelivery) {
        this.securedDelivery = securedDelivery;
    }
}
