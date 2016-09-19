package org.fri.entice.webapp.entry;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;

@XmlRootElement
public class HistoryData extends MyEntry implements Comparable<HistoryData> {

    private long validFrom;
    private long validTo;
    private String value;
    private String location;

    public HistoryData(String id) {
        super(id);
    }

    public HistoryData(String id, long validFrom, long validTo, String value, String location) {
        super(id);
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.value = value;
        this.location = location;
    }

    public long getValidTo() {
        return validTo;
    }

    public void setValidTo(long validTo) {
        this.validTo = validTo;
    }

    public long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(long validFrom) {
        this.validFrom = validFrom;
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

    @Override
    public int compareTo(HistoryData historyDataObj) {
        DateTime compareDate = new DateTime(historyDataObj.getValidFrom());

        System.out.println("sort: " + new DateTime(this.getValidFrom()).compareTo(compareDate));

        //ascending order
        return new DateTime(this.getValidFrom()).compareTo(compareDate);
    }

    public static Comparator<HistoryData> HistoryDataComparator = new Comparator<HistoryData>() {

        public int compare(HistoryData h1, HistoryData h2) {
            //ascending order
            return h1.compareTo(h2);
        }

    };
}
