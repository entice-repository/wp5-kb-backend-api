package org.ul.entice.webapp.entry;

public enum FileFormat {
    //assigning the values of enum constants - http://examples.javacodegeeks.com/java-basics/java-enumeration-example/
    ISO("ISO"), DMG("DMG"), FVD("FVD"), IMG("IMG"), NDIF("NDIF"), QCOW("QCOW"), UDIF("UDIF"), VDI("VDI"), VHD("VHD"),
    VMDK("VMDK"), WIM("WIM");

    private String value;

    private FileFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
