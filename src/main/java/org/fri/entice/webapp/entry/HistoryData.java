package org.fri.entice.webapp.entry;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HistoryData extends MyEntry {

    private DateTime validFrom;
    private DateTime validTo;
    private String value;
    private String location;

    public HistoryData(String id) {
        super(id);
    }

    public HistoryData(String id, DateTime validFrom, DateTime validTo, String value, String location) {
        super(id);
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.value = value;
        this.location = location;
    }

    public DateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(DateTime validFrom) {
        this.validFrom = validFrom;
    }

    public DateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(DateTime validTo) {
        this.validTo = validTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
