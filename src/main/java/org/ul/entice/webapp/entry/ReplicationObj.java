package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReplicationObj extends MyEntry {
    private long saveTime;
    private Integer[] repIndex;
    private Integer[] repPlace;

    public ReplicationObj() {
        super("");
    }

    public ReplicationObj(String id) {
        super(id);
    }

    public ReplicationObj(String id, long saveTime, Integer[] repIndex, Integer[] repPlace) {
        super(id);
        this.saveTime = saveTime;
        this.repIndex = repIndex;
        this.repPlace = repPlace;
    }

    public Integer[] getRepIndex() {
        return repIndex;
    }

    public void setRepIndex(Integer[] repIndex) {
        this.repIndex = repIndex;
    }

    public Integer[] getRepPlace() {
        return repPlace;
    }

    public void setRepPlace(Integer[] repPlace) {
        this.repPlace = repPlace;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }
}
