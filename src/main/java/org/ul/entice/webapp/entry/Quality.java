package org.ul.entice.webapp.entry;

import java.util.List;

public class Quality extends MyEntry {

    //deprecated
    private long aimedSize;
    //deprecated
    private long optimizedSize;

    private int percentStorageOptimised;    //(0-100)
    private boolean functionalityTested;
    private int userRating;     //(0-5)
    private boolean isUpdateNecessary;
    private boolean isOptimizationNecessary;
    private int numberOfDownloads;
    private int maxIterationsNum;
    private int actualIterationsNum;
    private double aimedReductionRatio;
    private long maxRunningTime;
    private long actualRunningTime;
    private short maxNumberOfVMs;
    private String jobID;

    private long started;
    private String status;
    private String optimizerPhase;
    private long originalImageSize;
    private long optimizedImageSize;
    private String failure;

    private List<String> resultList;

    public Quality(String id) {
        super(id);
    }
    public Quality() {
    }

    // version 1
    public Quality(String id, long aimedSize, long optimizedSize, int percentStorageOptimised, boolean
            functionalityTested, int userRating, boolean isUpdateNecessary, boolean isOptimizationNecessary, int
            numberOfDownloads, int maxIterationsNum, int actualIterationsNum, double aimedReductionRatio, long
            maxRunningTime, long actualRunningTime, short maxNumberOfVMs, List<String> resultList) {
        super(id);
        this.aimedSize = aimedSize;
        this.optimizedSize = optimizedSize;
        this.percentStorageOptimised = percentStorageOptimised;
        this.functionalityTested = functionalityTested;
        this.userRating = userRating;
        this.isUpdateNecessary = isUpdateNecessary;
        this.isOptimizationNecessary = isOptimizationNecessary;
        this.numberOfDownloads = numberOfDownloads;
        this.maxIterationsNum = maxIterationsNum;
        this.actualIterationsNum = actualIterationsNum;
        this.aimedReductionRatio = aimedReductionRatio;
        this.maxRunningTime = maxRunningTime;
        this.actualRunningTime = actualRunningTime;
        this.maxNumberOfVMs = maxNumberOfVMs;
        this.resultList = resultList;
        jobID = "";
    }

    // version 2
    public Quality(String id, long started, String status, String optimizerPhase,
                                       long originalImageSize, long optimizedImageSize, String failure, String jobID) {
        super(id);
        this.started = started;
        this.status = status;
        this.optimizerPhase = optimizerPhase;
        this.originalImageSize = originalImageSize;
        this.optimizedImageSize = optimizedImageSize;
        this.failure = failure;
        this.jobID = jobID;
    }


    public long getOptimizedSize() {
        return optimizedSize;
    }

    public void setOptimizedSize(long optimizedSize) {
        this.optimizedSize = optimizedSize;
    }

    public int getPercentStorageOptimised() {
        return percentStorageOptimised;
    }

    public void setPercentStorageOptimised(int percentStorageOptimised) {
        this.percentStorageOptimised = percentStorageOptimised;
    }

    public boolean isFunctionalityTested() {
        return functionalityTested;
    }

    public void setFunctionalityTested(boolean functionalityTested) {
        this.functionalityTested = functionalityTested;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public boolean isUpdateNecessary() {
        return isUpdateNecessary;
    }

    public void setIsUpdateNecessary(boolean isUpdateNecessary) {
        this.isUpdateNecessary = isUpdateNecessary;
    }

    public boolean isOptimizationNecessary() {
        return isOptimizationNecessary;
    }

    public void setIsOptimizationNecessary(boolean isOptimizationNecessary) {
        this.isOptimizationNecessary = isOptimizationNecessary;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public int getMaxIterationsNum() {
        return maxIterationsNum;
    }

    public void setMaxIterationsNum(int maxIterationsNum) {
        this.maxIterationsNum = maxIterationsNum;
    }

    public int getActualIterationsNum() {
        return actualIterationsNum;
    }

    public void setActualIterationsNum(int actualIterationsNum) {
        this.actualIterationsNum = actualIterationsNum;
    }

    public double getAimedReductionRatio() {
        return aimedReductionRatio;
    }

    public void setAimedReductionRatio(float aimedReductionRatio) {
        this.aimedReductionRatio = aimedReductionRatio;
    }

    public long getMaxRunningTime() {
        return maxRunningTime;
    }

    public void setMaxRunningTime(long maxRunningTime) {
        this.maxRunningTime = maxRunningTime;
    }

    public long getActualRunningTime() {
        return actualRunningTime;
    }

    public void setActualRunningTime(long actualRunningTime) {
        this.actualRunningTime = actualRunningTime;
    }

    public short getMaxNumberOfVMs() {
        return maxNumberOfVMs;
    }

    public void setMaxNumberOfVMs(short maxNumberOfVMs) {
        this.maxNumberOfVMs = maxNumberOfVMs;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public void setAimedSize(long aimedSize) {
        this.aimedSize = aimedSize;
    }

    public void setAimedReductionRatio(double aimedReductionRatio) {
        this.aimedReductionRatio = aimedReductionRatio;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public long getAimedSize() {
        return aimedSize;
    }

    public void setUpdateNecessary(boolean updateNecessary) {
        isUpdateNecessary = updateNecessary;
    }

    public void setOptimizationNecessary(boolean optimizationNecessary) {
        isOptimizationNecessary = optimizationNecessary;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOptimizerPhase() {
        return optimizerPhase;
    }

    public void setOptimizerPhase(String optimizerPhase) {
        this.optimizerPhase = optimizerPhase;
    }

    public long getOriginalImageSize() {
        return originalImageSize;
    }

    public void setOriginalImageSize(long originalImageSize) {
        this.originalImageSize = originalImageSize;
    }

    public long getOptimizedImageSize() {
        return optimizedImageSize;
    }

    public void setOptimizedImageSize(long optimizedImageSize) {
        this.optimizedImageSize = optimizedImageSize;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }
}
