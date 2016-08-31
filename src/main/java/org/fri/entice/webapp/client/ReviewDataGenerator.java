package org.fri.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.DiskImage;
import org.fri.entice.webapp.entry.Geolocation;
import org.fri.entice.webapp.entry.Repository;
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
            UpdateProcessor upp;
            String insertStatement;

            // insert USER data:
//            User user = new User(UUID.randomUUID().toString(), "guest@gmail.com", "Some User", "pass", "12345632424", "user1",1);
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "vlado@stankovski.net", "Vlado Stankovski", "vlado", "123456", "vlados",2);
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();

            // insert GEOLOCATION data:
            Geolocation geolocation = new Geolocation(UUID.randomUUID().toString(),"country name","continent",1.1,1.1,1.1,"+0100");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocation);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            List<String> supportedFormats = new ArrayList<String>();
            supportedFormats.add("ISO");

            if (Math.random() < 0.5) supportedFormats.add("DAA");
            if (Math.random() < 0.5) supportedFormats.add("ADF");
            if (Math.random() < 0.5) supportedFormats.add("MD1");
            if (Math.random() < 0.5) supportedFormats.add("MD2");
            if (Math.random() < 0.5) supportedFormats.add("CSO");
            if (Math.random() < 0.5) supportedFormats.add("CUE");


            // insert REPOSITORY data:
            Repository repository = new Repository(UUID.randomUUID().toString(), geolocation.getId(), "http://www" +
                    ".example" + "" +
                    ".org/interfaceEndpointTest", 0.0275 + Math.random() * 0.0133, 0.0374 + Math.random() * 0.0034,
                    50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
