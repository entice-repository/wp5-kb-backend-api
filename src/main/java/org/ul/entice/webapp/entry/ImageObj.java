package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Deprecated
// Replace it with DiskImage and other confirmed entries
public class ImageObj {

    private String filename;
    private boolean isScript;
    //todo: in the future ID of owner
    private String owner;
    private boolean optimize;

    public ImageObj(String filename, boolean isScript, String owner, boolean optimize) {
        this.filename = filename;
        this.isScript = isScript;
        this.owner = owner;
        this.optimize = optimize;
    }

    public ImageObj() {
    }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isScript() {
        return isScript;
    }

    public void setIsScript(boolean isScript) {
        this.isScript = isScript;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }
}
