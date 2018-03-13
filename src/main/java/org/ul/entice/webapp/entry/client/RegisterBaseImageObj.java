package org.ul.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class RegisterBaseImageObj {
    // '{owner:admin,url:"http://source-images.s3.lpds.sztaki.hu/ami-000001654.qcow2",name:"Ubuntu 16.04",tags:[ubuntu,ubuntu16,"16.04"],description:"[BASE OFFICIAL] Ubuntu 16.04.02",partition:"1",cloudImageIds:{sztaki:"ami-000001654"}}'
    private String owner;
    private String url;
    private String name;
    private List<String> tags;
    private String description;
    private int partition;
    private long imageSize;
    private Map<String, String> cloudImageIds;

    public RegisterBaseImageObj() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public Map<String, String> getCloudImageIds() {
        return cloudImageIds;
    }

    public void setCloudImageIds(Map<String, String> cloudImageIds) {
        this.cloudImageIds = cloudImageIds;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }
}
