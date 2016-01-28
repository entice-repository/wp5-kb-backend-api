package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Functionality {
    private String id;

    // A classification system, such as Universal Decimal Classification (UDC) or any other taxonomy
    // could be used to classify various functionalities. The users will then be able to search according
    // to various clearly defined taxonomical concepts.
    private int classification;

    // Tags may additionally be used to describe a VM image’s functionalities. By using tags, it may be possible
    // to induce various functionality domains for which the users frequently upload or search for VMIs.
    private String tag;

    private String name;
    private String description;

    // This is a freestyle description of the input data needed by the image.
    // E.g. a data disk that should be mounted to the image.
    private String inputDescription;

    // This is a freestyle description of the output data of the disk image when it is running.
    private String outputDescription;

    // This is a description of the programming language, algorithm, libraries used to implement the functionality,
    // and may be used as part of the search criteria.
    private String refImplementationId;

    // This is the application domain for the given functionality, e.g. used in civil engineering, medicine etc.
    private String domain;

    public Functionality(String id, int classification, String tag, String name, String description, String
            inputDescription, String outputDescription, String refImplementationId, String domain) {
        this.id = id;
        this.classification = classification;
        this.tag = tag;
        this.name = name;
        this.description = description;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
        this.refImplementationId = refImplementationId;
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getInputDescription() {
        return inputDescription;
    }

    public void setInputDescription(String inputDescription) {
        this.inputDescription = inputDescription;
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription;
    }

    public String getRefImplementationId() {
        return refImplementationId;
    }

    public void setRefImplementationId(String refImplementationId) {
        this.refImplementationId = refImplementationId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
