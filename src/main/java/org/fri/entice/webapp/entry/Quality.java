package org.fri.entice.webapp.entry;

import java.util.List;

public class Quality extends MyEntry {

    private long aimedSize;
    private int optimizedSize;
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

    private List<String> resultList;

    public Quality(String id) {
        super(id);
    }

    public Quality(String id, long aimedSize, int optimizedSize, int percentStorageOptimised, boolean
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

    public int getOptimizedSize() {
        return optimizedSize;
    }

    public void setOptimizedSize(int optimizedSize) {
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
}
