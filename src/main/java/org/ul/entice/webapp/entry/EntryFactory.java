package org.ul.entice.webapp.entry;

public class EntryFactory {

    public static <T extends MyEntry> T getInstance(Class<T> clazz, String id) {
        try {
            T cls = (T) Class.forName(clazz.getName()).newInstance();
            cls.setId(id);
            return cls;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // if something gets wrong!
        return null;
    }
}
