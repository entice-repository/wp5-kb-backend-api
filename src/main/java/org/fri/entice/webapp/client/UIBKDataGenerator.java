package org.fri.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.util.FusekiUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
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

            final int repositorySize = 3;
            final int diskImageSize = 10;
            final int fragmentMaxSize = 3;
            final int historyDataMaxSize = 5;

            System.out.println("Started insertion..");
            long startTime = System.currentTimeMillis();

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


                Repository repository = new Repository(UUID.randomUUID().toString(), "http://www.example" + "" +
                        ".org/country", "http://www.example.org/geolocationID", "http://www.example" + "" +
                        ".org/interfaceEndpoint", Math.random() * 100, Math.random() * 20, 20 + Math.random() * 20,
                        40 + Math.random() * 20, 50 + Math.random() * 100, supportedFormats);
                repositories.add(repository);
                String insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();
            }

            // insert dummy DiskImage data
            for (int i = 0; i < diskImageSize; i++) {

                DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.CI, "some " +
                        "description", "some" +
                        " " + "title", "some predecessor..", FileFormat.IMG, "http://www.example" + "" +
                        ".org/PictureURL", Math.random() < 0.5, "http://www.example.org/iri", "123", 50 + Math.random
                        () * 100, "http://www.example.org/ownerID", "789", "100", "7", Math.random() < 0.5, (int)
                        (Math.random() * 30), Math.random() < 0.5, "1.0", (int) (1000 + Math.random() * 100000));
                diskImages.add(diskImage);
                String insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                //insert history data
                List<HistoryData> historyDataList = new ArrayList<HistoryData>();
                int fragmentHistoryLength = randomWithRange(1, historyDataMaxSize);
                for (int j = 0; j < fragmentHistoryLength; j++) {
                    DateTime dateFrom = new DateTime(DateTime.now());
                    dateFrom.minusDays(fragmentHistoryLength - j - 2);

                    DateTime dateTo = new DateTime(DateTime.now());
                    dateTo.minusDays(fragmentHistoryLength - j);

                    HistoryData historyData = new HistoryData(UUID.randomUUID().toString(), dateFrom, dateTo, String
                            .valueOf(1000 + Math.random() * 100000), repositories.get((int) (Math.random() *
                            repositorySize)).getId());
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
                            .get((int) (Math.random() * repositorySize)).getId(), "http://www" + ".example.org/do", 1
                            + (int) (Math.random() * 10), hashValue, historyDataList);
                    insertStatement = FusekiUtils.generateInsertObjectStatement(fragment);
                    upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                    upp.execute();
                    fragments++;
                }
            }

            int deliverySize = randomWithRange(1, diskImageSize);
            for (int i = 0; i < deliverySize; i++) {
                Delivery delivery = new Delivery(UUID.randomUUID().toString(), "http://www.example.org/ownerID",
                        "http://www.example.org/functionalityID", DateTime.now().getMillis(), repositories.get((int)
                        (Math.random() * repositorySize)).getId(), diskImages.get((int) (Math.random() *
                        diskImageSize)).getId(), DateTime.now().getMillis() + 1000);
                String insertStatement = FusekiUtils.generateInsertObjectStatement(delivery);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();
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

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
