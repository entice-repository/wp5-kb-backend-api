package org.fri.entice.webapp.entry.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

//@JsonIgnoreProperties(ignoreUnknown = true)

@XmlRootElement
public class SZTAKIExecuteObj {

    private String imageId;
    private String imageURL;
    private String validatorScriptURL;
    private String imageUserName;
    private String fsPartition;
    private String imageKeyPair;
    private String imagePrivateKey;

    private String cloudEndpointURL;
    private String cloudAccessKey;
    private String cloudSecretKey;
    private String cloudOptimizerVMInstanceType;
    private String cloudWorkerVMInstanceType;

    private int freeDiskSpace;
    private int numberOfParallelWorkerVMs;
    private int maxIterationsNum;
    private int XmaxNumberOfVMs;
    private double XaimedReductionRatio;
    private long XaimedSize;
    private int XmaxRunningTime;

    private String s3EndpointURL;
    private String s3AccessKey;
    private String s3SecretKey;
    private String s3Path;

    public SZTAKIExecuteObj() {
    }

    public SZTAKIExecuteObj(String imageId, String imageURL, String validatorScriptURL, String imageUserName, String
            fsPartition, String imageKeyPair, String imagePrivateKey, String cloudEndpointURL, String cloudAccessKey,
                            String cloudSecretKey, String cloudOptimizerVMInstanceType, String
                                    cloudWorkerVMInstanceType, int freeDiskSpace, int numberOfParallelWorkerVMs, int
                                    maxIterationsNum, int XmaxNumberOfVMs, double XaimedReductionRatio, long
                                    XaimedSize, int XmaxRunningTime, String s3EndpointURL, String s3AccessKey, String
                                    s3SecretKey, String s3Path) {
        this.imageId = imageId;
        this.imageURL = imageURL;
        this.validatorScriptURL = validatorScriptURL;
        this.imageUserName = imageUserName;
        this.fsPartition = fsPartition;
        this.imageKeyPair = imageKeyPair;
        this.imagePrivateKey = imagePrivateKey;
        this.cloudEndpointURL = cloudEndpointURL;
        this.cloudAccessKey = cloudAccessKey;
        this.cloudSecretKey = cloudSecretKey;
        this.cloudOptimizerVMInstanceType = cloudOptimizerVMInstanceType;
        this.cloudWorkerVMInstanceType = cloudWorkerVMInstanceType;
        this.freeDiskSpace = freeDiskSpace;
        this.numberOfParallelWorkerVMs = numberOfParallelWorkerVMs;
        this.maxIterationsNum = maxIterationsNum;
        this.XmaxNumberOfVMs = XmaxNumberOfVMs;
        this.XaimedReductionRatio = XaimedReductionRatio;
        this.XaimedSize = XaimedSize;
        this.XmaxRunningTime = XmaxRunningTime;
        this.s3EndpointURL = s3EndpointURL;
        this.s3AccessKey = s3AccessKey;
        this.s3SecretKey = s3SecretKey;
        this.s3Path = s3Path;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageUserName() {
        return imageUserName;
    }

    public void setImageUserName(String imageUserName) {
        this.imageUserName = imageUserName;
    }

    public String getImageKeyPair() {
        return imageKeyPair;
    }

    public void setImageKeyPair(String imageKeyPair) {
        this.imageKeyPair = imageKeyPair;
    }

    public String getImagePrivateKey() {
        return imagePrivateKey;
    }

    public void setImagePrivateKey(String imagePrivateKey) {
        this.imagePrivateKey = imagePrivateKey;
    }

    public String getValidatorScriptURL() {
        return validatorScriptURL;
    }

    public void setValidatorScriptURL(String validatorScriptURL) {
        this.validatorScriptURL = validatorScriptURL;
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

    @JsonProperty("XmaxNumberOfVMs")
    public int getXmaxNumberOfVMs() {
        return XmaxNumberOfVMs;
    }

    @JsonProperty("XmaxNumberOfVMs")
    public void setXmaxNumberOfVMs(int xmaxNumberOfVMs) {
        XmaxNumberOfVMs = xmaxNumberOfVMs;
    }

    @JsonProperty("XaimedReductionRatio")
    public double getXaimedReductionRatio() {
        return XaimedReductionRatio;
    }

    @JsonProperty("XaimedReductionRatio")
    public void setXaimedReductionRatio(double xaimedReductionRatio) {
        XaimedReductionRatio = xaimedReductionRatio;
    }

    @JsonProperty("XaimedSize")
    public long getXaimedSize() {
        return XaimedSize;
    }

    @JsonProperty("XaimedSize")
    public void setXaimedSize(long xaimedSize) {
        XaimedSize = xaimedSize;
    }

    @JsonProperty("XmaxRunningTime")
    public int getXmaxRunningTime() {
        return XmaxRunningTime;
    }

    @JsonProperty("XmaxRunningTime")
    public void setXmaxRunningTime(int xmaxRunningTime) {
        XmaxRunningTime = xmaxRunningTime;
    }

    public String getFsPartition() {
        return fsPartition;
    }

    public int getFreeDiskSpace() {
        return freeDiskSpace;
    }

    public int getNumberOfParallelWorkerVMs() {
        return numberOfParallelWorkerVMs;
    }

    public String getS3EndpointURL() {
        return s3EndpointURL;
    }

    public String getS3AccessKey() {
        return s3AccessKey;
    }

    public String getS3SecretKey() {
        return s3SecretKey;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setFsPartition(String fsPartition) {
        this.fsPartition = fsPartition;
    }

    public void setFreeDiskSpace(int freeDiskSpace) {
        this.freeDiskSpace = freeDiskSpace;
    }

    public void setNumberOfParallelWorkerVMs(int numberOfParallelWorkerVMs) {
        this.numberOfParallelWorkerVMs = numberOfParallelWorkerVMs;
    }

    public void setS3EndpointURL(String s3EndpointURL) {
        this.s3EndpointURL = s3EndpointURL;
    }

    public void setS3AccessKey(String s3AccessKey) {
        this.s3AccessKey = s3AccessKey;
    }

    public void setS3SecretKey(String s3SecretKey) {
        this.s3SecretKey = s3SecretKey;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }
}
