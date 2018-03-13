package org.ul.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;

//@JsonIgnoreProperties(ignoreUnknown = true)

@XmlRootElement
public class SZTAKIExecuteObj {

    private String jobID;
    private String uploadCommand;

    // -- Source image
    // it will be generated on SZTAKI cloud deploy
    private String imageURL;
    private String imageFormat;
    private String ovfURL;
    // ValidatorScript (String)
    private String validatorScriptURL;
    private String validatorServerURL;
    private String validatorScript;
    private String fsPartition;
    private String fsType;

    // -- Optimisation resource
    private String cloudInterface;
    private String cloudEndpointURL;
    private String cloudAccessKey;
    private String cloudSecretKey;
    private String imageId; // Image ID is the ID within the cloud repository
    private String kbImageId;
    private String imageKeyPair;
    private String imageUserName;
    private String imagePrivateKey;
    private String imageContextualizationURL;
    private String imageContextualization;
    private String cloudOptimizerVMInstanceType;
    private String cloudWorkerVMInstanceType;

    // -- Optimisation goals and limits
    private int maxIterationsNum;
    private int numberOfParallelWorkerVMs;
    private int maxRunningTime;
    private int maxNumberOfVMs;
    private int aimedSize;
    private double aimedReductionRatio;
    private int freeDiskSpace;

    // additional data for SZTAKI
    // knowledgeBaseURL
    // id

    public SZTAKIExecuteObj() {
    }

    public SZTAKIExecuteObj(String jobID, String imageURL, String imageFormat, String ovfURL, String validatorScriptURL, String validatorServerURL, String validatorScript, String fsPartition, String fsType, String cloudInterface, String cloudEndpointURL, String cloudAccessKey, String cloudSecretKey, String imageId, String imageKeyPair, String imageUserName, String imagePrivateKey, String imageContextualizationURL, String cloudOptimizerVMInstanceType, String cloudWorkerVMInstanceType, int maxIterationsNum, int numberOfParallelWorkerVMs, int maxRunningTime, int maxNumberOfVMs, int aimedSize, double aimedReductionRatio, int freeDiskSpace) {
        this.jobID = jobID;
        this.imageURL = imageURL;
        this.imageFormat = imageFormat;
        this.ovfURL = ovfURL;
        this.validatorScriptURL = validatorScriptURL;
        this.validatorServerURL = validatorServerURL;
        this.validatorScript = validatorScript;
        this.fsPartition = fsPartition;
        this.fsType = fsType;
        this.cloudInterface = cloudInterface;
        this.cloudEndpointURL = cloudEndpointURL;
        this.cloudAccessKey = cloudAccessKey;
        this.cloudSecretKey = cloudSecretKey;
        this.imageId = imageId;
        this.imageKeyPair = imageKeyPair;
        this.imageUserName = imageUserName;
        this.imagePrivateKey = imagePrivateKey;
        this.imageContextualizationURL = imageContextualizationURL;
        this.cloudOptimizerVMInstanceType = cloudOptimizerVMInstanceType;
        this.cloudWorkerVMInstanceType = cloudWorkerVMInstanceType;
        this.maxIterationsNum = maxIterationsNum;
        this.numberOfParallelWorkerVMs = numberOfParallelWorkerVMs;
        this.maxRunningTime = maxRunningTime;
        this.maxNumberOfVMs = maxNumberOfVMs;
        this.aimedSize = aimedSize;
        this.aimedReductionRatio = aimedReductionRatio;
        this.freeDiskSpace = freeDiskSpace;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getOvfURL() {
        return ovfURL;
    }

    public void setOvfURL(String ovfURL) {
        this.ovfURL = ovfURL;
    }

    public String getValidatorScriptURL() {
        return validatorScriptURL;
    }

    public void setValidatorScriptURL(String validatorScriptURL) {
        this.validatorScriptURL = validatorScriptURL;
    }

    public String getValidatorServerURL() {
        return validatorServerURL;
    }

    public void setValidatorServerURL(String validatorServerURL) {
        this.validatorServerURL = validatorServerURL;
    }

    public String getValidatorScript() {
        return validatorScript;
    }

    public void setValidatorScript(String validatorScript) {
        this.validatorScript = validatorScript;
    }

    public String getFsPartition() {
        return fsPartition;
    }

    public void setFsPartition(String fsPartition) {
        this.fsPartition = fsPartition;
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }

    public String getCloudInterface() {
        return cloudInterface;
    }

    public void setCloudInterface(String cloudInterface) {
        this.cloudInterface = cloudInterface;
    }

    public String getCloudEndpointURL() {
        return cloudEndpointURL;
    }

    public void setCloudEndpointURL(String cloudEndpointURL) {
        this.cloudEndpointURL = cloudEndpointURL;
    }

    public String getCloudAccessKey() {
        return cloudAccessKey;
    }

    public void setCloudAccessKey(String cloudAccessKey) {
        this.cloudAccessKey = cloudAccessKey;
    }

    public String getCloudSecretKey() {
        return cloudSecretKey;
    }

    public void setCloudSecretKey(String cloudSecretKey) {
        this.cloudSecretKey = cloudSecretKey;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageKeyPair() {
        return imageKeyPair;
    }

    public void setImageKeyPair(String imageKeyPair) {
        this.imageKeyPair = imageKeyPair;
    }

    public String getImageUserName() {
        return imageUserName;
    }

    public void setImageUserName(String imageUserName) {
        this.imageUserName = imageUserName;
    }

    public String getImagePrivateKey() {
        return imagePrivateKey;
    }

    public void setImagePrivateKey(String imagePrivateKey) {
        this.imagePrivateKey = imagePrivateKey;
    }

    public String getImageContextualizationURL() {
        return imageContextualizationURL;
    }

    public void setImageContextualizationURL(String imageContextualizationURL) {
        this.imageContextualizationURL = imageContextualizationURL;
    }

    public String getCloudOptimizerVMInstanceType() {
        return cloudOptimizerVMInstanceType;
    }

    public void setCloudOptimizerVMInstanceType(String cloudOptimizerVMInstanceType) {
        this.cloudOptimizerVMInstanceType = cloudOptimizerVMInstanceType;
    }

    public String getCloudWorkerVMInstanceType() {
        return cloudWorkerVMInstanceType;
    }

    public void setCloudWorkerVMInstanceType(String cloudWorkerVMInstanceType) {
        this.cloudWorkerVMInstanceType = cloudWorkerVMInstanceType;
    }

    public int getMaxIterationsNum() {
        return maxIterationsNum;
    }

    public void setMaxIterationsNum(int maxIterationsNum) {
        this.maxIterationsNum = maxIterationsNum;
    }

    public int getNumberOfParallelWorkerVMs() {
        return numberOfParallelWorkerVMs;
    }

    public void setNumberOfParallelWorkerVMs(int numberOfParallelWorkerVMs) {
        this.numberOfParallelWorkerVMs = numberOfParallelWorkerVMs;
    }

    public int getMaxRunningTime() {
        return maxRunningTime;
    }

    public void setMaxRunningTime(int maxRunningTime) {
        this.maxRunningTime = maxRunningTime;
    }

    public int getMaxNumberOfVMs() {
        return maxNumberOfVMs;
    }

    public void setMaxNumberOfVMs(int maxNumberOfVMs) {
        this.maxNumberOfVMs = maxNumberOfVMs;
    }

    public int getAimedSize() {
        return aimedSize;
    }

    public void setAimedSize(int aimedSize) {
        this.aimedSize = aimedSize;
    }

    public double getAimedReductionRatio() {
        return aimedReductionRatio;
    }

    public void setAimedReductionRatio(double aimedReductionRatio) {
        this.aimedReductionRatio = aimedReductionRatio;
    }

    public int getFreeDiskSpace() {
        return freeDiskSpace;
    }

    public void setFreeDiskSpace(int freeDiskSpace) {
        this.freeDiskSpace = freeDiskSpace;
    }

    public String getImageContextualization() {
        return imageContextualization;
    }

    public void setImageContextualization(String imageContextualization) {
        this.imageContextualization = imageContextualization;
    }

    public String getUploadCommand() {
        return uploadCommand;
    }

    public void setUploadCommand(String uploadCommand) {
        this.uploadCommand = uploadCommand;
    }

    public String getKbImageId() {
        return kbImageId;
    }

    public void setKbImageId(String kbImageId) {
        this.kbImageId = kbImageId;
    }
}