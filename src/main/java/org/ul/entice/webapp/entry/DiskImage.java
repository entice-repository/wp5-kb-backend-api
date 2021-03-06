package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class DiskImage extends MyEntry {
    private ImageType imageType; //enumerated type VMI or CI
    private String description; //string
    private String title; //string
    private String version; // VMI versioon
    private String predecessor; //todo clarify !! DiskImage_id / Fragment_id / Data_id - what data type is this???
    private FileFormat fileFormat; // enumeration ISO, DMG, FVD, IMG, NDIF, QCOW, UDIF, VDI, VHD, VMDK, WIM
    private String pictureUrl; //should contain the URL of picture of this image??
    private boolean encryption; //boolean true - false
    private String iri; //the URL address of the file
    private String refSlaId; //the ID of the SLA associated with this image
    private double price; //currency datatype?? I think that int would do it
    private String refOwnerId; //the ID of the user owning the image
    private String refFunctionalityId; //the ID of the functionallity this image supports
    private List<Functionality> functionalityList; //the ID of the functionallity this image supports
    private String refQualityId; //the ID of the quality this image is associated with
    private String refOperatingSystemId; //the ID Of the operating system this image can run on
    private boolean needsData; //boolean true-false
    private long generationTime; //int in milliseconds after epoch
    private boolean obfuscation; //boolean true-false
    private String dataId; // relation to Data entity
    private int diskImageSize;   // probably in bytes
    private int paretoPointX;
    private int paretoPointY;
    private String paretoId;
    private List<String> categoryList;
    private String repositoryID;
    private Repository repository;

    List<Fragment> fragmentList;

    private String ovfUrl;

    public DiskImage(String id) {
        super(id);
    }

    public DiskImage() {
    }

    public DiskImage(String id, ImageType imageTypeC, String descriptionC, String titleC, String predecessorC,
                     FileFormat fileFormatC, String pictureUrlC, boolean encryption, String iriC, String refSlaId,
                     double priceC, String refOwnerId, String refFunctionalityId, String refQualityId, String
                             refOperatingSystemId, boolean needsDataC, long generationTimeC, boolean obfuscationC,
                     String version, int diskImageSize, int paretoPointX, int paretoPointY, String paretoId,List<String> categoryList, String repositoryID) {
        super(id);
        this.imageType = imageTypeC;
        this.description = descriptionC;
        this.title = titleC;
        this.predecessor = predecessorC;
        this.fileFormat = fileFormatC;
        this.pictureUrl = pictureUrlC;
        this.encryption = encryption;
        this.iri = iriC;
        this.refSlaId = refSlaId;
        this.price = priceC;
        this.refOwnerId = refOwnerId;
        this.refFunctionalityId = refFunctionalityId;
        this.refQualityId = refQualityId;
        this.refOperatingSystemId = refOperatingSystemId;
        this.needsData = needsDataC;
        this.generationTime = generationTimeC;
        this.obfuscation = obfuscationC;
        this.version = version;
        this.diskImageSize = diskImageSize;
        this.paretoPointX = paretoPointX;
        this.paretoPointY = paretoPointY;
        this.paretoId = paretoId;
        this.categoryList = categoryList;
        this.repositoryID = repositoryID;
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

    public double getPrice() {
        return this.price;
    }

    public String getRefOwnerId() {
        return this.refOwnerId;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRefOwnerId(String refOwnerId) {
        this.refOwnerId = refOwnerId;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEncryption() {
        return encryption;
    }

    public boolean isNeedsData() {
        return needsData;
    }

    public boolean isObfuscation() {
        return obfuscation;
    }

    public String getRefSlaId() {
        return refSlaId == null ? " " : refSlaId;
    }

    public void setRefSlaId(String refSlaId) {
        this.refSlaId = refSlaId;
    }

    public String getRefFunctionalityId() {
        return refFunctionalityId;
    }

    public void setRefFunctionalityId(String refFunctionalityId) {
        this.refFunctionalityId = refFunctionalityId;
    }

    public String getRefQualityId() {
        return refQualityId == null ? " " : refQualityId;
    }

    public void setRefQualityId(String refQualityId) {
        this.refQualityId = refQualityId;
    }

    public String getRefOperatingSystemId() {
        return refOperatingSystemId == null ? " " : refOperatingSystemId;
    }

    public void setRefOperatingSystemId(String refOperatingSystemId) {
        this.refOperatingSystemId = refOperatingSystemId;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(long generationTime) {
        this.generationTime = generationTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getDiskImageSize() {
        return diskImageSize;
    }

    public void setDiskImageSize(int diskImageSize) {
        this.diskImageSize = diskImageSize;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public int getParetoPointX() {
        return paretoPointX;
    }

    public void setParetoPointX(int paretoPointX) {
        this.paretoPointX = paretoPointX;
    }

    public int getParetoPointY() {
        return paretoPointY;
    }

    public void setParetoPointY(int paretoPointY) {
        this.paretoPointY = paretoPointY;
    }

    public String getParetoId() {
        return paretoId;
    }

    public void setParetoId(String paretoId) {
        this.paretoId = paretoId;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCategoriesFromString(String stringValue) {
        if(stringValue.length() == 0) return;

        if(categoryList == null)
           categoryList = new ArrayList<>();

        String[] categoryTab = stringValue.split(",");
        for(String val : categoryTab)
            categoryList.add(val);
    }

    public List<Functionality> getFunctionalityList() {
        return functionalityList;
    }

    public void setFunctionalityList(List<Functionality> functionalityList) {
        this.functionalityList = functionalityList;
    }

    public String getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(String repositoryID) {
        this.repositoryID = repositoryID;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getOvfUrl() {
        return ovfUrl;
    }

    public void setOvfUrl(String ovfUrl) {
        this.ovfUrl = ovfUrl;
    }
}

