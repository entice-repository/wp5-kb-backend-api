package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UploadResponse {
    private String id;
    private String location;

    //no arg constructor required for JAXB
    public UploadResponse() {
    }

    public UploadResponse(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
