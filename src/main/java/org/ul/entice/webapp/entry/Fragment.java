package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Fragment extends MyEntry {
    private String refDiskImageId;
    private String refRepositoryId;
    private String anyURI;      // TODO: list of string showing the name/ipadress/geolocation of the given cloud  + date
    // TODO: Then by using simple search I could sort all entries by name and find how many times the fragment was
    // deployed to any given cloud.
    // So we only need deployment time and the URI field to be populated with some data.
    private int fragmentSize;   // probably in bytes

    // Multi-value (Note: Structured values limit the ability for applications to query for specific data items, e.g.
    // documents that have a specific keyword):
    // http://patterns.dataincubator.org/book/repeated-property.html
    private List<String> hashValue = new ArrayList<String>(10);

    private List<HistoryData> historyDataList;
    private List<String> refReporeplicas = new ArrayList<>();    // reference to the repository
    private int copyIdentification;

    public Fragment(String id, String refDiskImageId, String refRepositoryId, String anyURI, int fragmentSize,
                    List<String> hashValue, List<HistoryData> historyDataList, List<String> refReporeplicas, int copyIdentification) {
        super(id);
        this.refDiskImageId = refDiskImageId;
        this.refRepositoryId = refRepositoryId;
        this.anyURI = anyURI;
        this.fragmentSize = fragmentSize;
        this.hashValue = hashValue;
        this.historyDataList = historyDataList;
        this.refReporeplicas = refReporeplicas;
        this.copyIdentification = copyIdentification;
    }

    public Fragment(String id) {
        super(id);
    }

    public Fragment() {
    }

    public String getRefDiskImageId() {
        return refDiskImageId;
    }

    public void setRefDiskImageId(String refDiskImageId) {
        this.refDiskImageId = refDiskImageId;
    }

    public String getRefRepositoryId() {
        return refRepositoryId;
    }

    public void setRefRepositoryId(String refRepositoryId) {
        this.refRepositoryId = refRepositoryId;
    }

    public String getAnyURI() {
        return anyURI;
    }

    public void setAnyURI(String anyURI) {
        this.anyURI = anyURI;
    }

    public int getFragmentSize() {
        return fragmentSize;
    }

    public void setFragmentSize(int fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    public List<String> getHashValue() {
        return hashValue;
    }

    public void setHashValue(List<String> hashValue) {
        this.hashValue = hashValue;
    }

    public void setHashValue(String val) {
        if (hashValue == null) hashValue = new ArrayList<String>();

        hashValue.add(val);
    }

    public List<HistoryData> getHistoryDataList() {
        return historyDataList;
    }

    public void setHistoryDataList(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    public List<String> getRefReporeplicas() {
        return refReporeplicas;
    }

    public void setRefReporeplicas(List<String> refReporeplicas) {
        this.refReporeplicas = refReporeplicas;
    }

    public int getCopyIdentification() {
        return copyIdentification;
    }

    public void setCopyIdentification(int copyIdentification) {
        this.copyIdentification = copyIdentification;
    }
}
