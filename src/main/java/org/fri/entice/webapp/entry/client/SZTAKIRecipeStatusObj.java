package org.fri.entice.webapp.entry.client;

@Deprecated
public class SZTAKIRecipeStatusObj {

    private String id;
    private String jobId;
    private String message;
    private String request_status; //" -> "finished"
    private String outcome; //" -> "cancelled"
    private long size;
    private String url;
//    private String request_id; //" -> "8befa25b-e052-4831-8f1a-7c6c07c00c46"

    public SZTAKIRecipeStatusObj(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    //    public String getRequest_id() {
//        return request_id;
//    }
//
//    public void setRequest_id(String request_id) {
//        this.request_id = request_id;
//    }
}
