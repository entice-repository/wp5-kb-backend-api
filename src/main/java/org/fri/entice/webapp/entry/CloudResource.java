package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="CloudResource")
@XmlAccessorType(XmlAccessType.FIELD)
public class CloudResource {

    @XmlElement
    private String cloudProvider;

    @XmlElement
    private double reputation;

    @XmlElement(name="list")
    public List<String> re = new ArrayList<String>();

    public CloudResource(String cloudProvider, double reputation, List<String> re) {
        this.cloudProvider = cloudProvider;
        this.reputation = reputation;
        this.re = re;
    }

    public CloudResource() {
    }

    public String getCloudProvider() {
        return cloudProvider;
    }

    public void setCloudProvider(String cloudProvider) {
        this.cloudProvider = cloudProvider;
    }

    public double getReputation() {
        return reputation;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public List<String> getRe() {
        return re;
    }

    public void setRe(List<String> re) {
        this.re = re;
    }
}
