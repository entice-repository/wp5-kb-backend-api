package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Fragment extends MyEntry {
    private String refDiskImageId;
    private String refRepositoryId;
    private String anyURI;
    private int fragmentSize;   // probably in bytes

    // Multi-value (Note: Structured values limit the ability for applications to query for specific data items, e.g. documents that have a specific keyword):
    // http://patterns.dataincubator.org/book/repeated-property.html
    private List<String> hashValue = new ArrayList<String>(10);

    public Fragment(String id, String refDiskImageId, String refRepositoryId, String anyURI, int fragmentSize,
                    List<String> hashValue) {
        super(id);
        this.refDiskImageId = refDiskImageId;
        this.refRepositoryId = refRepositoryId;
        this.anyURI = anyURI;
        this.fragmentSize = fragmentSize;
        this.hashValue = hashValue;
    }

    public Fragment(String id) {
        super(id);
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
}
