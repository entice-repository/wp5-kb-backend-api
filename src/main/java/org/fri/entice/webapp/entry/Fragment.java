package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Fragment {
    private String id;
    private String referenceImageId;
    private String repositoryId;
    private String anyURI;
    private int fragmentSize;
    private List<String> hashValue = new ArrayList<String>(10);

    public Fragment(String id, String referenceImageId, String repositoryId, String anyURI, int fragmentSize,
                    List<String> hashValue) {
        this.id = id;
        this.referenceImageId = referenceImageId;
        this.repositoryId = repositoryId;
        this.anyURI = anyURI;
        this.fragmentSize = fragmentSize;
        this.hashValue = hashValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceImageId() {
        return referenceImageId;
    }

    public void setReferenceImageId(String referenceImageId) {
        this.referenceImageId = referenceImageId;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
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
}
