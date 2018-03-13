package org.ul.entice.webapp.entry.client;

import org.ul.entice.webapp.entry.MyEntry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseObj extends MyEntry {

    private int code;
    private String message;

    public ResponseObj(int code, String message) {
        super(null);
        this.code = code;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
