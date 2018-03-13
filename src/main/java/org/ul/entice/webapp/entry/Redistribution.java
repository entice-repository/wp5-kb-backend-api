package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Redistribution extends MyEntry {

    private long saveTime;
    private String repositoryID;
    private String repositoryName;
    private String vmiName;
    private String redistributionURI;
    private int redistributionTimeInSeconds;

    public Redistribution(String id) {
        super(id);
    }

    public Redistribution() {
    }

    public Redistribution(String id, long saveTime, String repositoryID, String repositoryName, String vmiName,
                          String redistributionURI, int redistributionTimeInSeconds) {
        super(id);
        this.saveTime = saveTime;
        this.repositoryID = repositoryID;
        this.repositoryName = repositoryName;
        this.vmiName = vmiName;
        this.redistributionURI = redistributionURI;
        this.redistributionTimeInSeconds = redistributionTimeInSeconds;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public String getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(String repositoryID) {
        this.repositoryID = repositoryID;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getVmiName() {
        return vmiName;
    }

    public void setVmiName(String vmiName) {
        this.vmiName = vmiName;
    }

    public String getRedistributionURI() {
        return redistributionURI;
    }

    public void setRedistributionURI(String redistributionURI) {
        this.redistributionURI = redistributionURI;
    }

    public int getRedistributionTimeInSeconds() {
        return redistributionTimeInSeconds;
    }

    public void setRedistributionTimeInSeconds(int redistributionTimeInSeconds) {
        this.redistributionTimeInSeconds = redistributionTimeInSeconds;
    }
}
