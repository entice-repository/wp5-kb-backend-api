package org.ul.entice.webapp.entry;

public class RecipeBuild extends MyEntry {
//    private String recipeId;
//    private String status;
//    private String message;

    private String jobId;
    private String message;
    private String request_status; //" -> "finished"
    private String outcome; //" -> "cancelled"
    private long size;
    private String url;

    public RecipeBuild(String id) {
        super(id);
    }

    public RecipeBuild(String id, String jobId, String message, String request_status, String outcome,
                       long size, String url) {
        super(id);
        this.jobId = jobId;
        this.message = message;
        this.request_status = request_status;
        this.outcome = outcome;
        this.size = size;
        this.url = url;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
