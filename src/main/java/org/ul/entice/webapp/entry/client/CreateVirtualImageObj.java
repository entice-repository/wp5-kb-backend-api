package org.ul.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CreateVirtualImageObj {
    // [string, required]: login name of the creator
    private String owner;

    // [string, required]: the id of the base- or virtual image id to extend
    private String parent;


    // ------------ OPTIONAL:

    // [string, optional]: name of the virtual image
    private String name;

    // [string, optional]: textual description of the virtual image
    private String description;

    // [string, optional]: the knowledge base reference id of the fragment that will be created for the new virtual
    // image (in order to maintain  semantics, relations of images and fragments in the knowledge base)
    private String knowledgeBaseRef;

    // [JSON array of strings, optional]: installer ids of software package installers to apply on the parent image
    private List<String> installerIds;

    // [string, optional]: custom installer script encoded in base64 form to be executed on the parent image to obtain
    // the new virtual image
    private String installerBase64;

    // [string, optional]: custom init script that must be run prior to executing installer sctipr (e.g., service stops)
    private String initBase64;

    // [string, optional]: the URL from where the target image can be downloaded
    private String snapshotUrl;

    // [JSON array of strings, optional]: virtual image tags
    private List<String> tags;

    public CreateVirtualImageObj() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKnowledgeBaseRef() {
        return knowledgeBaseRef;
    }

    public void setKnowledgeBaseRef(String knowledgeBaseRef) {
        this.knowledgeBaseRef = knowledgeBaseRef;
    }

    public List<String> getInstallerIds() {
        return installerIds;
    }

    public void setInstallerIds(List<String> installerIds) {
        this.installerIds = installerIds;
    }

    public String getInstallerBase64() {
        return installerBase64;
    }

    public void setInstallerBase64(String installerBase64) {
        this.installerBase64 = installerBase64;
    }

    public String getInitBase64() {
        return initBase64;
    }

    public void setInitBase64(String initBase64) {
        this.initBase64 = initBase64;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}