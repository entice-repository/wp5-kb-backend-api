package org.fri.entice.webapp.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.*;
import org.fri.entice.webapp.util.FusekiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class PerformanceTest {
    private static int fragments = 0;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();

    private static int BATCH_SIZE = 25;
    private static CountDownLatch countDownLatch = new CountDownLatch(50);

    public static void main(String[] args) {

        String mssg = "[{\"StorageNodeId\":\"4\",\"vmiName\":\"mini-ubuntu_12_04.iso\"," +
                "\"bucketName\":\"flexiant-entice\",\"vmiURI\":https://s3.tnode" +
                ".com:9869/flexiant-entice/mini-ubuntu_12_04.iso,\"UploadUnoptimizedTimeInSeconds\":3}]";

        JsonObject jsonObject = new JsonParser().parse(mssg).getAsJsonObject();
        jsonObject.toString();

        try {
            if(args.length < 1)
                throw new Exception("First argument must specify dataset namespace!");

//            final String PATH = "http://193.2.72.90:3030/entice/update";
            final String PATH = args[0]+"/update";
            /**
             *  Insert repositories, fragments and disk images.
             */
            final int repositorySize = 10;
            final int diskImageSize = 50;
            final int fragmentMaxSize = 10;


            final List<HistoryData> historyDataList = new ArrayList<>();
            historyDataList.add(new HistoryData(UUID.randomUUID().toString()));
            historyDataList.add(new HistoryData(UUID.randomUUID().toString()));

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


                Repository repository = new Repository(UUID.randomUUID().toString(), "geolocationID", "http://www" +
                        ".example" + "" +
                        ".org/interfaceEndpoint", 0.0275 + Math.random() * 0.0133, 0.0374 + Math.random() * 0.0034,
                        0.0374 + Math.random() * 0.0034, 50 + Math.random() * 100, supportedFormats);
                repositories.add(repository);
                String insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
                UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();
            }

            // insert dummy DiskImage data
            for (int i = 0; i < diskImageSize; i++) {
                if (i % 10000 == 0) System.out.println(i + " thousand disk images inserted");

                /*
                if (i % BATCH_SIZE == 0 && i > 0) {
                    countDownLatch.await();
                    countDownLatch = new CountDownLatch(BATCH_SIZE);
                    System.out.println("inserted images " + i);
                }*/


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.CI, "some " +
                                "description", "some" +
                                " " + "title", "some predecessor..", FileFormat.IMG, "http://www.example" +
                                ".org/PictureURL", Math.random() < 0.5, "http://www.example.org/iri", "123", 50 + Math.random() * 100
                                , "http://www.example.org/ownerID", "789", "100", "7", Math.random
                                () < 0.5, (int) (Math.random() * 30), Math.random() < 0.5, "1.0", (int) (1000 + Math
                                .random() * 100000),-1,-1,"paretoID",null);
                        diskImages.add(diskImage);
                        String insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                        UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create
                                (insertStatement), PATH);
                        upp.execute();

                        // insert dummy Fragment data for the Disk image
                        final int selectedFragmentSize = 1 + (int) (Math.random() * fragmentMaxSize);
                        for (int j = 0; j < selectedFragmentSize; j++) {
                            List<String> hashValue = new ArrayList<String>();
                            hashValue.add("a");
                            if (Math.random() < 0.5) hashValue.add("b");
                            if (Math.random() < 0.5) hashValue.add("c");
                            if (Math.random() < 0.5) hashValue.add("d");

                            Fragment fragment = new Fragment(UUID.randomUUID().toString(), diskImage.getId(),
                                    repositories.get((int) (Math.random() * repositorySize)).getId(), "http://www" +
                                    ".example.org/do", 1 + (int) (Math.random() * 10), hashValue,historyDataList);
                            insertStatement = FusekiUtils.generateInsertObjectStatement(fragment);
                            upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                            upp.execute();
                            fragments++;
                        }
                        countDownLatch.countDown();
                    }
                }).start();
            }

            countDownLatch.await();
            System.out.println("..repositories inserted: " + repositorySize);
            System.out.println("..diskImages inserted: " + diskImageSize);
            System.out.println("..fragments inserted: " + fragments);
            System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");

            /*
            triplets 381410  + 616 ontology triplets
            ..repositories inserted: 100
            ..diskImages inserted: 5000
            ..fragments inserted: 27402
            Time elapsed: 379380 ms

            triplets per second: 1006,358839050132

             */

            //TODO PERFORMANCE TASK: for each image check all the fragments and informations contained (about given
            // cloud).

            //        https://docs.google.com/spreadsheets/d/10kiCVMEyGN93dUT1pqz90jVrnr2lSLxMmcHG47uEf-k/edit#gid
            // =1483006114

            /** 25.2.2016 sestanek:
             * -    text search z Lucene/Solr?
             *      https://jena.apache.org/documentation/query/text-query.html#working-with-fuseki
             *      sandig: integriraj
             *      testiraj text search (tag) na cca 100 slikah
             * - Kontaktiraj Dragija in vprašaj v katerih 3 področjih se uporablja PARETO
             */

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}
