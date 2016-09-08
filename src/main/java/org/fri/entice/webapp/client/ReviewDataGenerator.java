package org.fri.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.DiskImage;
import org.fri.entice.webapp.entry.Geolocation;
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
        final String PATH = "http://193.2.72.90:3030/entice-review1/update";
        try {
            UpdateProcessor upp;
            String insertStatement;

            // insert USER data:
            User user = new User(UUID.randomUUID().toString(), "guest@gmail.com", "Guest user", "pass", "12345632424", "guest",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "vlado@stankovski.net", "Vlado Stankovski", "vlado", "123456", "vlados",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "admin@admin.com", "John Admin", "admin", "123456", "admin",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "polona.stefanic@fgg.uni-lj.si", "Polona Štefanič", "polona", "123456", "polonas",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            // insert GEOLOCATION data:
            Geolocation geolocationSlovenia = new Geolocation(UUID.randomUUID().toString(),"Slovenia","si","Europe",46.0660481,14.3920137,11,"UTC+01:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationSlovenia);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            Geolocation geolocationAustria = new Geolocation(UUID.randomUUID().toString(),"Austria","at","Europe",47.2853696,11.2387048,11,"UTC+01:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationAustria);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            Geolocation geolocationHungary = new Geolocation(UUID.randomUUID().toString(),"Hungary","hu","Budapest",47.4876761,19.0329196,13.71,"UTC+01:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationHungary);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            Geolocation geolocationUK = new Geolocation(UUID.randomUUID().toString(),"UK","gb","Edinburgh ",55.9409861,-3.3454651,11,"UTC+00:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationUK);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            Geolocation geolocationSpain = new Geolocation(UUID.randomUUID().toString(),"Spain","es","Madrid ",40.4378698,-3.8196232,11,"UTC+01:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationSpain);
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
            Repository repository = new Repository(UUID.randomUUID().toString(), geolocationSlovenia.getId(),
                    "http://www.example.org/interfaceEndpointSlovenia", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationAustria.getId(),
                    "http://www.example.org/interfaceEndpointAustria", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationHungary.getId(),
                    "http://www.example.org/interfaceEndpointHungary", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationUK.getId(),
                    "http://www.example.org/interfaceEndpointUK", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationSpain.getId(),
                    "http://www.example.org/interfaceEndpointSpain", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
