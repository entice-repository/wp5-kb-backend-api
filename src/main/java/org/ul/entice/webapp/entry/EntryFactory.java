package org.ul.entice.webapp.entry;

public class EntryFactory {

    public static <T extends MyEntry> T getInstance(Class<T> clazz, String id) {
        // NOT WORKING:        if(clazz.isInstance(Repository.class))
        if (clazz.getSimpleName().equals("Repository")) return (T) new Repository(id);
        else if (clazz.getSimpleName().equals("DiskImage")) return (T) new DiskImage(id);
        else if (clazz.getSimpleName().equals("Fragment")) return (T) new Fragment(id);
        else if (clazz.getSimpleName().equals("Delivery")) return (T) new Delivery(id);
        else if (clazz.getSimpleName().equals("HistoryData")) return (T) new HistoryData(id);
        else if (clazz.getSimpleName().equals("FragmentHistoryDeliveryData")) return (T) new FragmentHistoryDeliveryData(id);
        else if (clazz.getSimpleName().equals("Pareto")) return (T) new Pareto(id);
        else if (clazz.getSimpleName().equals("Functionality")) return (T) new Functionality(id);
        else if (clazz.getSimpleName().equals("User")) return (T) new User(id);
        else if (clazz.getSimpleName().equals("Geolocation")) return (T) new Geolocation(id);
        else if (clazz.getSimpleName().equals("Quality")) return (T) new Quality(id);
        else if (clazz.getSimpleName().equals("RecipeBuild")) return (T) new RecipeBuild(id);
        return null;
    }
}
