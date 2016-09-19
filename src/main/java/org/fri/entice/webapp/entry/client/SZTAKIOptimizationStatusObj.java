package org.fri.entice.webapp.entry.client;

public class SZTAKIOptimizationStatusObj {
    private String id;
    private String optimizerPhase; // -> "Uploading optimized image"
    private double aimedReductionRatio; //" -> "0"
    private int maxRunningTime; //" -> "0"
    private int numberOfVMsStarted; //" -> "14"
    private String optimizedImageURL; //" -> "https://s3.lpds.sztaki.hu/images/optimized-image.qcow2"
    private long started; // -> "1473857142721"
    private long runningTime; //" -> "6244"
    private long optimizedUsedSpace; //" -> "1743363097"
    private long originalImageSize; //" -> "2662531072"
    private int maxNumberOfVMs; //" -> "0"
    private String removables; //" -> "/opt, /var/cache"
    private String optimizerVMStatus; //" -> "terminated"
    private long originalUsedSpace; //" -> "1981316639"
    private String failure; //" -> "Cannot upload optimized image file /mnt/optimized-image.qcow2 to S3 server
    // https://s3.lpds.sztaki.hu with access key: WAU8PTCX8NSIL0RSG8K9, secret key: R16..."
    private long optimizedImageSize; //" -> "1996226560"
    private int aimedSize; //" -> "0"
    private long ended; //" -> "1473863387305"
    private int iteration; //" -> "1"
    private String shrinkerPhase; //" -> "done"
    private String chart; //" -> "[[0,1473857777212,1981316639,1981316639],[1,1473858305684,1743363097,1981316639]]"
    private int maxIterationsNum; //" -> "1"
    private String status; //" -> "FAILED"

    public SZTAKIOptimizationStatusObj(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOptimizerPhase(String optimizerPhase) {
        this.optimizerPhase = optimizerPhase;
    }

    public void setAimedReductionRatio(double aimedReductionRatio) {
        this.aimedReductionRatio = aimedReductionRatio;
    }

    public void setMaxRunningTime(int maxRunningTime) {
        this.maxRunningTime = maxRunningTime;
    }

    public void setNumberOfVMsStarted(int numberOfVMsStarted) {
        this.numberOfVMsStarted = numberOfVMsStarted;
    }

    public void setOptimizedImageURL(String optimizedImageURL) {
        this.optimizedImageURL = optimizedImageURL;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }

    public void setOptimizedUsedSpace(long optimizedUsedSpace) {
        this.optimizedUsedSpace = optimizedUsedSpace;
    }

    public void setOriginalImageSize(long originalImageSize) {
        this.originalImageSize = originalImageSize;
    }

    public void setMaxNumberOfVMs(int maxNumberOfVMs) {
        this.maxNumberOfVMs = maxNumberOfVMs;
    }

    public void setRemovables(String removables) {
        this.removables = removables;
    }

    public void setOptimizerVMStatus(String optimizerVMStatus) {
        this.optimizerVMStatus = optimizerVMStatus;
    }

    public void setOriginalUsedSpace(long originalUsedSpace) {
        this.originalUsedSpace = originalUsedSpace;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public void setOptimizedImageSize(long optimizedImageSize) {
        this.optimizedImageSize = optimizedImageSize;
    }

    public void setAimedSize(int aimedSize) {
        this.aimedSize = aimedSize;
    }

    public void setEnded(long ended) {
        this.ended = ended;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setShrinkerPhase(String shrinkerPhase) {
        this.shrinkerPhase = shrinkerPhase;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public void setMaxIterationsNum(int maxIterationsNum) {
        this.maxIterationsNum = maxIterationsNum;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOptimizerPhase() {
        return optimizerPhase;
    }

    public double getAimedReductionRatio() {
        return aimedReductionRatio;
    }

    public int getMaxRunningTime() {
        return maxRunningTime;
    }

    public int getNumberOfVMsStarted() {
        return numberOfVMsStarted;
    }

    public String getOptimizedImageURL() {
        return optimizedImageURL;
    }

    public long getStarted() {
        return started;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public long getOptimizedUsedSpace() {
        return optimizedUsedSpace;
    }

    public long getOriginalImageSize() {
        return originalImageSize;
    }

    public int getMaxNumberOfVMs() {
        return maxNumberOfVMs;
    }

    public String getRemovables() {
        return removables;
    }

    public String getOptimizerVMStatus() {
        return optimizerVMStatus;
    }

    public long getOriginalUsedSpace() {
        return originalUsedSpace;
    }

    public String getFailure() {
        return failure;
    }

    public long getOptimizedImageSize() {
        return optimizedImageSize;
    }

    public int getAimedSize() {
        return aimedSize;
    }

    public long getEnded() {
        return ended;
    }

    public int getIteration() {
        return iteration;
    }

    public String getShrinkerPhase() {
        return shrinkerPhase;
    }

    public String getChart() {
        return chart;
    }

    public int getMaxIterationsNum() {
        return maxIterationsNum;
    }

    public String getStatus() {
        return status;
    }
}
