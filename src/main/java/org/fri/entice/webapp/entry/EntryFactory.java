package org.fri.entice.webapp.entry;

public class EntryFactory {

    public static <T extends MyEntry> T getInstance(Class<T> clazz, String id) {
        // NOT WORKING:        if(clazz.isInstance(Repository.class))
        if (clazz.getSimpleName().equals("Repository")) return (T) new Repository(id);
        else if (clazz.getSimpleName().equals("DiskImage")) return (T) new DiskImage(id);
        else if (clazz.getSimpleName().equals("Fragment")) return (T) new Fragment(id);
        else if (clazz.getSimpleName().equals("Delivery")) return (T) new Delivery(id);
        return null;
    }
}
