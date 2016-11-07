package org.ul.entice.webapp.entry;


public abstract class MyEntry {
    private String id;

    public MyEntry(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
