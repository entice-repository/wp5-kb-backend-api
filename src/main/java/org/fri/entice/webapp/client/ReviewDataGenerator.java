package org.fri.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.DiskImage;
import org.fri.entice.webapp.entry.Repository;
import org.fri.entice.webapp.entry.User;
import org.fri.entice.webapp.util.FusekiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewDataGenerator {
    private static int fragments = 0;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();

    public static void main(String[] args) {
        final String PATH = "http://193.2.72.90:3030/entice/update";
        try {

            // insert some dummy User data
            User user = new User(UUID.randomUUID().toString(), "guest@gmail.com", "Some User", "pass", "12345632424", "user1",1);
            String insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "vlado@stankovski.net", "Vlado Stankovski", "vlado", "123456", "vlados",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
