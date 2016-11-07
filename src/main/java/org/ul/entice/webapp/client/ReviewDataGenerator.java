package org.ul.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.joda.time.DateTime;
import org.ul.common.rest.IService;
import org.ul.entice.webapp.entry.*;
import org.ul.entice.webapp.util.FusekiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ReviewDataGenerator {
    private static int fragments = 0;
    private static int fragmentMaxSize = 1;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();

    public static void generateData(){
        final String PATH = "http://193.2.72.90:3030/entice-review1/update";
        try {
            UpdateProcessor upp;
            String insertStatement;

//            User user = new User(UUID.randomUUID().toString(), "test@gmail.com2", "Test user2", "test2", "12345632424", "test2",1);
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();

            // insert USER data:
            User user = new User(UUID.randomUUID().toString(), "guest1@gmail.com", "Mr. Guest no 1", "guest1", "12345632424", "guest1",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "guest2@gmail.com", "Mr. Guest no 2", "guest2", "12345632424", "guest2",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "user1@gmail.com", "Mr. User no 1", "user1", "12345632424", "user1",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "user2@gmail.com", "Mr. User no 2", "user2", "12345632424", "user2",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "jose@gmail.com", "Jose J. Ramos", "jose", "12345632424", "jose",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "vlado@stankovski.net", "Vlado Stankovski", "vlado", "123456", "vlado",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "dragi@admin.com", "Dragi Kimovski", "dragi", "123456", "dragi",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();
            //Polona, Dragi, Radu, Thomas, Attila
            user = new User(UUID.randomUUID().toString(), "polona.stefanic@fgg.uni-lj.si", "Polona Štefanič", "polona", "123456", "polona",1);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "radu.prodan@gmail.com", "Radu Prodan", "radu", "123456", "radu",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "thomasf@gmail.com", "Thomas Fahringer", "thomas", "123456", "thomas",2);
            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            user = new User(UUID.randomUUID().toString(), "atila@gmail.com", "Attila Casba Marosi", "attila", "123456", "attila",2);
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

//            Geolocation geolocationUK = new Geolocation(UUID.randomUUID().toString(),"UK","gb","Edinburgh ",55.9409861,-3.3454651,11,"UTC+00:00");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationUK);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();

//            Geolocation geolocationSpain = new Geolocation(UUID.randomUUID().toString(),"Spain","es","Madrid ",40.4378698,-3.8196232,11,"UTC+01:00");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationSpain);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();

            Geolocation geolocationUS = new Geolocation(UUID.randomUUID().toString(),"USA","wvu","Virginia West ",38.8986891,-82.4256365,7,"UTC-05:00");
            insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationUS);
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

                /*
                node ID | Matching repository
                ------------------------------
                1       | SZTAKI
                2       | UIBK
                3       | Amazon - Virginia East
                4       | LJ
                */

            // insert REPOSITORY data:
            Repository repository = new Repository(UUID.randomUUID().toString(), geolocationHungary.getId(),
                    "http://www.example.org/interfaceEndpointHungary", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            repositories.add(repository);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationAustria.getId(),
                    "http://www.example.org/interfaceEndpointAustria", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            repositories.add(repository);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationUS.getId(),
                    "http://www.example.org/interfaceEndpointVirginiaEast", 0.0275 + Math.random() * 0.0133 + 1.0, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            repositories.add(repository);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            repository = new Repository(UUID.randomUUID().toString(), geolocationSlovenia.getId(),
                    "http://www.example.org/interfaceEndpointSlovenia", 0.0275 + Math.random() * 0.0133, 0.0374
                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
            repositories.add(repository);
            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

//            repository = new Repository(UUID.randomUUID().toString(), geolocationSpain.getId(),
//                    "http://www.example.org/interfaceEndpointSpain", 0.0275 + Math.random() * 0.0133, 0.0374
//                    + Math.random() * 0.0034, 50 + Math.random() * 100, 50 + Math.random() * 1000, supportedFormats);
//            insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
            List<DiskImage> diskImageList = FusekiUtils.getAllEntityAttributes(DiskImage.class, new IService() {
                @Override
                public String getFusekiQuery() {
                    return "http://193.2.72.90:3030/entice-review1/query";
                }
            });
            repositories = FusekiUtils.getAllEntityAttributes(Repository.class,new IService() {
                @Override
                public String getFusekiQuery() {
                    return "http://193.2.72.90:3030/entice-review1/query";
                }
            });
            for (DiskImage diskImage : diskImageList) {
                insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                //insert history data
                List<HistoryData> historyDataList = new ArrayList<HistoryData>();
                DateTime dateFrom = new DateTime(DateTime.now());
                dateFrom = dateFrom.minusDays(10);

                DateTime dateTo = new DateTime(dateFrom);
                dateTo = dateTo.minusDays(2);

                HistoryData historyData = new HistoryData(UUID.randomUUID().toString(), dateFrom.getMillis(), dateTo
                        .getMillis(), String.valueOf(1000 + Math.random() * 100000), repositories.get((int) (Math
                        .random() * repositories.size())).getId());
                historyDataList.add(historyData);

                //insert history data entity:
                insertStatement = FusekiUtils.generateInsertObjectStatement(historyData);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                // insert dummy Fragment data for the Disk image
                final int selectedFragmentSize = 1;
                for (int j = 0; j < selectedFragmentSize; j++) {
                    List<String> hashValue = new ArrayList<String>();
                    hashValue.add("a");
                    if (Math.random() < 0.5) hashValue.add("b");
                    if (Math.random() < 0.5) hashValue.add("c");
                    if (Math.random() < 0.5) hashValue.add("d");
                    Fragment fragment = new Fragment(UUID.randomUUID().toString(), diskImage.getId(), repositories
                            .get((int) (Math.random() * repositories.size())).getId(), "http://www" + ".example" +
                            ".org/do", diskImage.getDiskImageSize(), hashValue, historyDataList);
                    insertStatement = FusekiUtils.generateInsertObjectStatement(fragment);
                    upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                    upp.execute();
                    fragments++;

                    // 20 % of the cases the fragments to be transferred with a theoretical speed of 10 Gbit/s. In
                    // reality
                    // that is like between 812 and 937 Mbyte/s. So if the fragment size is 500 Mbyte, for 937
                    // Mbytes/s the
                    // delivery time will be 0.5336second.

                    // 40 % of the cases the fragments to be transferred through an overloaded 10gibit network with a
                    // speed of
                    // 437 to 562 Mbytes. So for 500 Mbyte fragment the minimal transfer time would be 0.8896 seconds.

                    // 40% of the cases the fragments to be transferred through 1gibit network with a speed of 87 to
                    // 100 Mbyts
                    // per second. So for 500 Mbyte fragment the minimal transfer time would 5 seconds.

                    // each fragment should be deployed 10 times
                    for (int k = 0; k < fragmentMaxSize; k++) {
                        long requestTime = DateTime.now().getMillis(); // aka query time
                        Delivery delivery = new Delivery(UUID.randomUUID().toString(), "http://www.example" + "" +
                                ".org/ownerID", "http://www.example.org/functionalityID", requestTime, repositories
                                .get((int) (Math.random() * repositories.size())).getId(), diskImage.getId(), "cloud"
                                + (int) (1 + Math.random() * 10), fragment.getId(), requestTime + (int) (fragment
                                .getFragmentSize() * 1.0 / getTransferTime(k) * 1000), (int) (Math.random() * 20000));
                        insertStatement = FusekiUtils.generateInsertObjectStatement(delivery);
                        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                        upp.execute();
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void main(String[] args) {
         generateData();
    }

    public static float getTransferTime(int k) {
        Random rn = new Random();
        // rn.nextInt(max - min + 1) + min
        switch(k){
            case 0:
            case 1:
                // 812 to 937
                return rn.nextInt(937 - 812 + 1) + 812;
            case 2:
            case 3:
            case 4:
            case 5:
                // 437 to 562
                return rn.nextInt(562 - 437 + 1) + 437;
            default:
                // 87 to 100
                return rn.nextInt(100 - 87 + 1) + 87;
        }
    }
}
