package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiskImageResource {
    private String resourceId;
    private DiskImage di;

    //no arg constructor needed for JAXB
    public DiskImageResource() {
    }

    public DiskImageResource(String resourceIdC, DiskImage diC) {
        this.resourceId = resourceIdC;
        this.di = diC;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public DiskImage getDiskImage() {
        return this.di;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setDiskImage(DiskImage di) {
        this.di = di;
    }


}
