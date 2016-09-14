package org.fri.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.util.FusekiUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Generate:
 * 3 repositories,
 * 10 Fragments (lined to one of three repositories),
 * 3 cloud URIs,
 * deliver DiskImage on different repositories,
 * Monitoring data (e.g. deploy time and fragment size).
 */
public class UIBKDataGenerator {
    private static int fragments = 0;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();

    public static void main(String[] args) {
        try {
//            final String PATH = "http://193.2.72.90:3030/entice/update";
            final String PATH = "http://193.2.72.90:3030/entice/update";

            final int repositorySize = 6;
            final int diskImageSize = 32;
            final int fragmentMaxSize = 10;
            final int historyDataMaxSize = 10;

            System.out.println("Started insertion..");
            long startTime = System.currentTimeMillis();

            // insert some dummy User data
            User user = new User(UUID.randomUUID().toString(), "sandi.gec@gmail.com", "Sandi Gec", "444", "112", "sandi",1);
            String insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "some@email.com", "Dragi Kimovski", "pass", "112", "dragi");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "some@email.com", "Polona Štefanič", "pass", "112", "polona");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "some@email.com", "Nishant Saurabh", "pass", "112", "nishant");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "some@email.com", "Uroš Paščinski", "pass", "112", "uros");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();
//
//            user = new User(UUID.randomUUID().toString(), "some@email.com", "Vlado Stankovski", "pass", "112", "vlado");
//            insertStatement = FusekiUtils.generateInsertObjectStatement(user);
//            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
//            upp.execute();


            // insert dummy Repository data
            for (int i = 0; i < repositorySize; i++) {
                List<String> supportedFormats = new ArrayList<String>();
                supportedFormats.add("ISO");

                if (Math.random() < 0.5) supportedFormats.add("DAA");
                if (Math.random() < 0.5) supportedFormats.add("ADF");
                if (Math.random() < 0.5) supportedFormats.add("MD1");
                if (Math.random() < 0.5) supportedFormats.add("MD2");
                if (Math.random() < 0.5) supportedFormats.add("CSO");
                if (Math.random() < 0.5) supportedFormats.add("CUE");

                // TODO: fill the data from amazon from cheaper to expensier (6 repos):
                // https://aws.amazon.com/s3/pricing/
                Repository repository = new Repository(UUID.randomUUID().toString(), "geolocationID", "http://www" +
                        ".example" + "" +
                        ".org/interfaceEndpoint", 0.0275 + Math.random() * 0.0133, 0.0374 + Math.random() * 0.0034,
                        0.0374 + Math.random() * 0.0034, 50 + Math.random() * 100, supportedFormats);
                repositories.add(repository);
                insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();
            }

            String[] titles = {"public","private","Cent OS","Java SDK","NodeJS","Python","Ubuntu","Ubuntu 2"};
            // insert dummy DiskImage data
            for (int i = 0; i < diskImageSize; i++) {
                DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.CI, "some " +
                        "description", i < titles.length ? titles[i] : "Fedora " + i , "some predecessor..", FileFormat.IMG, "http://www.example" + "" +
                        ".org/PictureURL", Math.random() < 0.5, "http://www.example.org/iri", "123", 50 + Math.random
                        () * 100, "http://www.example.org/ownerID", "789", "100", "7", Math.random() < 0.5, (int)
                        (Math.random() * 30), Math.random() < 0.5, "1.0", (int) (1000 + Math.random() * 100000),-1,-1,"paretoID",null,null);
                diskImages.add(diskImage);
                insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                //insert history data
                List<HistoryData> historyDataList = new ArrayList<HistoryData>();
                int fragmentHistoryLength = randomWithRange(1, historyDataMaxSize);
                for (int j = 0; j < fragmentHistoryLength; j++) {
                    DateTime dateFrom = new DateTime(DateTime.now());
                    dateFrom = dateFrom.minusDays(fragmentHistoryLength - j - 2);

                    DateTime dateTo = new DateTime(dateFrom);
                    dateTo = dateTo.minusDays(fragmentHistoryLength - j + 10);


                    HistoryData historyData = new HistoryData(UUID.randomUUID().toString(), dateFrom.getMillis(),
                            dateTo.getMillis(), String.valueOf(1000 + Math.random() * 100000), repositories.get((int)
                            (Math.random() * repositorySize)).getId());
                    historyDataList.add(historyData);

                    //insert history data entity:
                    insertStatement = FusekiUtils.generateInsertObjectStatement(historyData);
                    upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                    upp.execute();
                }

                // insert dummy Fragment data for the Disk image
                final int selectedFragmentSize = 1 + (int) (Math.random() * fragmentMaxSize);
                for (int j = 0; j < selectedFragmentSize; j++) {
                    List<String> hashValue = new ArrayList<String>();
                    hashValue.add("a");
                    if (Math.random() < 0.5) hashValue.add("b");
                    if (Math.random() < 0.5) hashValue.add("c");
                    if (Math.random() < 0.5) hashValue.add("d");
                    Fragment fragment = new Fragment(UUID.randomUUID().toString(), diskImage.getId(), repositories
                            .get((int) (Math.random() * repositorySize)).getId(), "http://www" + ".example.org/do",
                            200 + (int) (Math.random() * 300), hashValue, historyDataList);
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
                        Delivery delivery = new Delivery(UUID.randomUUID().toString(), "http://www.example" +
                                ".org/ownerID", "http://www.example.org/functionalityID", requestTime,
                                repositories.get((int) (Math.random() * repositorySize)).getId(), diskImage.getId(), "cloud" + (int) (1 + Math.random() *
                                10), fragment.getId(),requestTime + (int)(fragment.getFragmentSize() * 1.0/ getTransferTime(k) * 1000 ) ,
                                (int) (Math.random() * 20000));
                        insertStatement = FusekiUtils.generateInsertObjectStatement(delivery);
                        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                        upp.execute();
                    }

                }
            }


            System.out.println("..repositories inserted: " + repositorySize);
            System.out.println("..diskImages inserted: " + diskImageSize);
            System.out.println("..fragments inserted: " + fragments);
            System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    private static float getTransferTime(int k) {
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

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
