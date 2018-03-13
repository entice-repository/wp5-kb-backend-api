package org.ul.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement
public class ResponseSynthesisObj {
    private int imageSize;
    private int currentSize;
    private long startTime;
    // from for examples: hours to actual dates
    private HashMap<String, String> tableOfRestriction;
    private String jobID;

    public ResponseSynthesisObj(int imageSize, int currentSize, long startTime, HashMap<String, String>
            tableOfRestriction, String jobID) {
        this.imageSize = imageSize;
        this.currentSize = currentSize;
        this.startTime = startTime;
        this.tableOfRestriction = tableOfRestriction;
        this.jobID = jobID;
    }

    public ResponseSynthesisObj() {
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public HashMap<String, String> getTableOfRestriction() {
        return tableOfRestriction;
    }

    public void setTableOfRestriction(HashMap<String, String> tableOfRestriction) {
        this.tableOfRestriction = tableOfRestriction;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }
}
