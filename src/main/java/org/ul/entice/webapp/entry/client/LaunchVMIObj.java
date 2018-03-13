package org.ul.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LaunchVMIObj {

    // endpoint [string, required]: EC2 endpoint URL
    private String endpoint;

    // accessKey [string, required]: cloud access key credential
    private String accessKey;

    // secretKey [string, required]: cloud secret key credential
    private String secretKey;

    // virtualImageId [required]: the id of the virtual image to be launched
    private String virtualImageId;

    // instanceType [optional]: instance type (flavor) of the VM to be launched
    private String instanceType;

    // contextualization [optional]: user defined contextualization of the VM in base64 encoded form
    private String contextualization;

    // cloud [string, optional]: the name of the cloud where the new VM to be launched
    private String cloud;

    // cloudInterface [string, optional]: the interface of the cloud (ec2, fco, wt)
    private String cloudInterface;

    // cloudImageId [string, optional]: the proprietary image id to be used as base image
    // (overrides image id value stored in base images for this cloud during this call)
    private String cloudImageId;

    private String keypair;

    public LaunchVMIObj() {
    }

    public LaunchVMIObj(String endpoint, String accessKey, String secretKey, String virtualImageId,
                        String instanceType, String contextualization, String cloud, String cloudInterface,
                        String cloudImageId, String keypair) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.virtualImageId = virtualImageId;
        this.instanceType = instanceType;
        this.contextualization = contextualization;
        this.cloud = cloud;
        this.cloudInterface = cloudInterface;
        this.cloudImageId = cloudImageId;
        this.keypair = keypair;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getVirtualImageId() {
        return virtualImageId;
    }

    public void setVirtualImageId(String virtualImageId) {
        this.virtualImageId = virtualImageId;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getContextualization() {
        return contextualization;
    }

    public void setContextualization(String contextualization) {
        this.contextualization = contextualization;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCloudInterface() {
        return cloudInterface;
    }

    public void setCloudInterface(String cloudInterface) {
        this.cloudInterface = cloudInterface;
    }

    public String getCloudImageId() {
        return cloudImageId;
    }

    public void setCloudImageId(String cloudImageId) {
        this.cloudImageId = cloudImageId;
    }

    public String getKeypair() {
        return keypair;
    }

    public void setKeypair(String keypair) {
        this.keypair = keypair;
    }
}