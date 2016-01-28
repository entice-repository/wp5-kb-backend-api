package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiskImage {
    private String id; //unique indentifier , String better than int!!
    private ImageType imageType; //enumerated type VMI or CI
    private String description; //string
    private String title; //string
    private String version; // VMI versioon
    private String predecessor; //DiskImage_id / Fragment_id / Data_id - what data type is this???
    private FileFormat fileFormat; // enumeration ISO, DMG, FVD, IMG, NDIF, QCOW, UDIF, VDI, VHD, VMDK, WIM
    private String pictureUrl; //should contain the URL of picture of this image??
    private boolean encryption; //boolean true - false
    private String iri; //the URL address of the file
    private int slaId; //the ID of the SLA associated with this image
    private double price; //currency datatype?? I think that int would do it
    private int ownerId; //the ID of the user owning the image
    private int functionalityId; //the ID of the functionallity this image supports
    private int qualityId; //the ID of the quality this image is associated with
    private int operatingSystemId; //the ID Of the operating system this image can run on
    private boolean needsData; //boolean true-false
    private int generationTime; //int in milliseconds after epoch
    private boolean obfuscation; //boolean true-false


    //no arg constructor needed for JAXB
    public DiskImage() {
    }

    public DiskImage(String id, ImageType imageTypeC, String descriptionC, String titleC, String predecessorC, FileFormat
            fileFormatC, String pictureUrlC, boolean encriptionC, String iriC, int slaIdC, double priceC, int ownerIdC,
                     int functionallityIdC, int qualityIdC, int operatingSystemIdC, boolean needsDataC, int
                             generationTimeC, boolean obfuscationC) {
        this.imageType = imageTypeC;
        this.description = descriptionC;
        this.title = titleC;
        this.predecessor = predecessorC;
        this.fileFormat = fileFormatC;
        this.pictureUrl = pictureUrlC;
        this.encryption = encriptionC;
        this.iri = iriC;
        this.slaId = slaIdC;
        this.price = priceC;
        this.ownerId = ownerIdC;
        this.functionalityId = functionallityIdC;
        this.qualityId = qualityIdC;
        this.operatingSystemId = operatingSystemIdC;
        this.needsData = needsDataC;
        this.generationTime = generationTimeC;
        this.obfuscation = obfuscationC;
    }

    public ImageType getImageType() {
        return this.imageType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPredecessor() {
        return this.predecessor;
    }

    public FileFormat getFileFormat() {
        return this.fileFormat;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public boolean getEncryption() {
        return this.encryption;
    }

    public String getIri() {
        return this.iri;
    }

    public int getSlaId() {
        return this.slaId;
    }

    public double getPrice() {
        return this.price;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public int getFunctionalityId() {
        return this.functionalityId;
    }

    public int getQualityId() {
        return this.qualityId;
    }

    public int getOperatingSystemId() {
        return this.operatingSystemId;
    }

    public boolean getNeedsData() {
        return this.needsData;
    }

    public int getGenerationTime() {
        return this.generationTime;
    }

    public boolean getObfuscation() {
        return this.obfuscation;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
    }

    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setEncryption(boolean encryption) {
        this.encryption = encryption;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public void setSlaId(int slaId) {
        this.slaId = slaId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setFunctionalityId(int functionalityId) {
        this.functionalityId = functionalityId;
    }

    public void setQualityId(int qualityId) {
        this.qualityId = qualityId;
    }

    public void setOperatingSystemId(int operatingSystemId) {
        this.operatingSystemId = operatingSystemId;
    }

    public void setNeedsData(boolean needsData) {
        this.needsData = needsData;
    }

    public void setGenerationTime(int generationTime) {
        this.generationTime = generationTime;
    }

    public void setObfuscation(boolean obfuscation) {
        this.obfuscation = obfuscation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

