package org.fri.entice.webapp.entry.client;

public class SZTAKIExecuteObj {

    private String imageId;
    private String imageURL;
    private String imageUserName;
    private String imageKeyPair;
    private String imagePrivateKey;
    private String validatorScriptURL;
    private String cloudEndpointURL;
    private String cloudAccessKey;
    private String cloudSecretKey;
    private String cloudOptimizerVMInstanceType;
    private String cloudWorkerVMInstanceType;
    private String Xs3EndpointURL;
    private String Xs3AccessKey;
    private String Xs3SecretKey;
    private String Xs3Path;
    private int maxIterationsNum;
    private int XmaxNumberOfVMs;
    private double XaimedReductionRatio;
    private long XaimedSize;
    private int XmaxRunningTime;

    public SZTAKIExecuteObj(String imageId, String imageURL, String imageUserName, String imageKeyPair, String
            imagePrivateKey, String validatorScriptURL, String cloudEndpointURL, String cloudAccessKey, String
            cloudSecretKey, String cloudOptimizerVMInstanceType, String cloudWorkerVMInstanceType, String
            xs3EndpointURL, String xs3AccessKey, String xs3SecretKey, String xs3Path, int maxIterationsNum, int
            xmaxNumberOfVMs, double xaimedReductionRatio, long xaimedSize, int xmaxRunningTime) {
        this.imageId = imageId;
        this.imageURL = imageURL;
        this.imageUserName = imageUserName;
        this.imageKeyPair = imageKeyPair;
        this.imagePrivateKey = imagePrivateKey;
        this.validatorScriptURL = validatorScriptURL;
        this.cloudEndpointURL = cloudEndpointURL;
        this.cloudAccessKey = cloudAccessKey;
        this.cloudSecretKey = cloudSecretKey;
        this.cloudOptimizerVMInstanceType = cloudOptimizerVMInstanceType;
        this.cloudWorkerVMInstanceType = cloudWorkerVMInstanceType;
        Xs3EndpointURL = xs3EndpointURL;
        Xs3AccessKey = xs3AccessKey;
        Xs3SecretKey = xs3SecretKey;
        Xs3Path = xs3Path;
        this.maxIterationsNum = maxIterationsNum;
        XmaxNumberOfVMs = xmaxNumberOfVMs;
        XaimedReductionRatio = xaimedReductionRatio;
        XaimedSize = xaimedSize;
        XmaxRunningTime = xmaxRunningTime;
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

    public String getXs3EndpointURL() {
        return Xs3EndpointURL;
    }

    public void setXs3EndpointURL(String xs3EndpointURL) {
        Xs3EndpointURL = xs3EndpointURL;
    }

    public String getXs3AccessKey() {
        return Xs3AccessKey;
    }

    public void setXs3AccessKey(String xs3AccessKey) {
        Xs3AccessKey = xs3AccessKey;
    }

    public String getXs3SecretKey() {
        return Xs3SecretKey;
    }

    public void setXs3SecretKey(String xs3SecretKey) {
        Xs3SecretKey = xs3SecretKey;
    }

    public String getXs3Path() {
        return Xs3Path;
    }

    public void setXs3Path(String xs3Path) {
        Xs3Path = xs3Path;
    }

    public int getMaxIterationsNum() {
        return maxIterationsNum;
    }

    public void setMaxIterationsNum(int maxIterationsNum) {
        this.maxIterationsNum = maxIterationsNum;
    }

    public int getXmaxNumberOfVMs() {
        return XmaxNumberOfVMs;
    }

    public void setXmaxNumberOfVMs(int xmaxNumberOfVMs) {
        XmaxNumberOfVMs = xmaxNumberOfVMs;
    }

    public double getXaimedReductionRatio() {
        return XaimedReductionRatio;
    }

    public void setXaimedReductionRatio(double xaimedReductionRatio) {
        XaimedReductionRatio = xaimedReductionRatio;
    }

    public long getXaimedSize() {
        return XaimedSize;
    }

    public void setXaimedSize(long xaimedSize) {
        XaimedSize = xaimedSize;
    }

    public int getXmaxRunningTime() {
        return XmaxRunningTime;
    }

    public void setXmaxRunningTime(int xmaxRunningTime) {
        XmaxRunningTime = xmaxRunningTime;
    }
}
