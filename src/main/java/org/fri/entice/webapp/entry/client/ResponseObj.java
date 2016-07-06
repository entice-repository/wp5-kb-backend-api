package org.fri.entice.webapp.entry.client;

import org.fri.entice.webapp.entry.MyEntry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseObj extends MyEntry {

    private int code;
    private String repositoryID;

    public ResponseObj(int code, String repositoryID) {
        super(null);
        this.code = code;
        this.repositoryID = repositoryID;
    }

    public ResponseObj() {
        super(null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(String repositoryID) {
        this.repositoryID = repositoryID;
    }
}
