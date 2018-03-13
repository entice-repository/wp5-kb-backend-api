package org.ul.entice.webapp.client;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.joda.time.DateTime;
import org.ul.entice.webapp.entry.*;
import org.ul.entice.webapp.util.FusekiUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 1. 100 repositories (so we can change from 10-100)
// 2. 100 Destination clouds
// 3. 500 fragments/images
// 4. 10000 usage history data where the delivery time you will calculate with the method that you already have

// now I’m currently working on how to measure the quality of the solutions
// please prepare a method throu which we can select 10 or 20 repositories out of 100 so we can test them
// and for example the history usage data to take into account those changes

// get_all_repositories,
// get_fragment_history_data,
// get_fragment_data,
// get_fragment_history_delivery_data,
// get_pareto

// this methods i will need for sure
public class MODataGenerator {
    private static int fragments = 0;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();
    private static final int cloudSize = 10;

    public static void main(String[] args) {
        try {
            final String PATH = "http://193.2.72.90:3030/entice-mo2/update";

            // 2.
            // no fragments 8, no clouds 8 clouds, number of historical data: 80

            final int repositorySize = 8;
            final int diskImageSize = 10;
            final int fragmentMaxSize = 8;
            int historyDataMaxSize = 80;  // usage history data where the delivery time you will calculate with the method that you already have


            // get all centroids coordinates of all countries:
           List<Geolocation> geolocationList =  readXMLFromFile("\\internal_work\\countryInfo.xml");

            System.out.println("Started insertion..");
            long startTime = System.currentTimeMillis();

            // --- USER
            User user = new User(UUID.randomUUID().toString(), "sandi.gec@gmail.com", "Sandi Gec", "444", "112", "sandi",1);
            String insertStatement = FusekiUtils.generateInsertObjectStatement(user);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
            upp.execute();

            // --- REPOSITORY
            for (int i = 0; i < repositorySize; i++) {
                List<String> supportedFormats = new ArrayList<String>();
                supportedFormats.add("ISO");

                if (Math.random() < 0.5) supportedFormats.add("DAA");
                if (Math.random() < 0.5) supportedFormats.add("ADF");
                if (Math.random() < 0.5) supportedFormats.add("MD1");
                if (Math.random() < 0.5) supportedFormats.add("MD2");
                if (Math.random() < 0.5) supportedFormats.add("CSO");
                if (Math.random() < 0.5) supportedFormats.add("CUE");

                insertStatement = FusekiUtils.generateInsertObjectStatement(geolocationList.get(i));
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                Repository repository = new Repository(UUID.randomUUID().toString(), geolocationList.get(i).getId(), "http://www" +
                        ".example" + "" +
                        ".org/interfaceEndpoint", 0.0275 + Math.random() * 0.0133, 0.0374 + Math.random() * 0.0034,
                        0.0374 + Math.random() * 0.0034, 50 + Math.random() * 100, supportedFormats,Math.random() * 0.7 + 99.3);
                repositories.add(repository);
                insertStatement = FusekiUtils.generateInsertObjectStatement(repository);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();
            }

            String[] titles = {"public","private","Cent OS","Java SDK","NodeJS","Python","Ubuntu","Ubuntu 2"};
            // --- DISK_IMAGE
            for (int i = 0; i < diskImageSize; i++) {
                DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.CI, "some " +
                        "description", i < titles.length ? titles[i] : "Fedora " + i , "some predecessor..", FileFormat.IMG, "http://www.example" + "" +
                        ".org/PictureURL", Math.random() < 0.5, "http://www.example.org/iri", "123", 50 + Math.random
                        () * 100, "http://www.example.org/ownerID", "789", "100", "7", Math.random() < 0.5, (int)
                        (Math.random() * 30), Math.random() < 0.5, "1.0", (int) (100 + Math.random() * 100000),-1,-1,"paretoID",null,repositories.get((int)
                        (Math.random() * repositorySize)).getId());
                diskImages.add(diskImage);
                insertStatement = FusekiUtils.generateInsertObjectStatement(diskImage);
                upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                upp.execute();

                // --- HISTORY_DATA aka. PROVENIENCE_DATA
                List<HistoryData> historyDataList = new ArrayList<HistoryData>();
                int fragmentHistoryLength = randomWithRange(10, 30);
                historyDataMaxSize-= fragmentHistoryLength;

                // to assure that it is always the same size of provenience data
                if (historyDataMaxSize < 0)
                    fragmentHistoryLength -= historyDataMaxSize;

                if (historyDataMaxSize > 0) {
                    for (int j = 0; j < fragmentHistoryLength; j++) {
                        DateTime dateFrom = new DateTime(DateTime.now());
                        dateFrom = dateFrom.minusDays(fragmentHistoryLength - j - 2);

                        DateTime dateTo = new DateTime(dateFrom);
                        dateTo = dateTo.minusDays(fragmentHistoryLength - j + 10);


                        HistoryData historyData = new HistoryData(UUID.randomUUID().toString(), dateFrom.getMillis(),
                                dateTo.getMillis(), String.valueOf(1000 + Math.random() * 100000), repositories.get(
                                (int) (Math.random() * repositorySize)).getId());
                        historyDataList.add(historyData);

                        //insert history data entity:
                        insertStatement = FusekiUtils.generateInsertObjectStatement(historyData);
                        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                        upp.execute();
                    }
                }

                // --- FRAGMENT
                for (int j = 0; j < fragmentMaxSize; j++) {
                    List<String> hashValue = new ArrayList<String>();
                    hashValue.add("a");
                    if (Math.random() < 0.5) hashValue.add("b");
                    if (Math.random() < 0.5) hashValue.add("c");
                    if (Math.random() < 0.5) hashValue.add("d");

                    //generate replication data for every fragment:
                    List<String> refReplicas = new ArrayList<>();
                    for (Repository repository : repositories)
                        if (Math.random() < 0.3)
                            refReplicas.add(repository.getId());

                    if(refReplicas.size() == 0)
                        refReplicas = null;

                    Fragment fragment = new Fragment(UUID.randomUUID().toString(), diskImage.getId(), repositories
                            .get((int) (Math.random() * repositorySize)).getId(), "http://www" + ".example.org/do",
                            diskImage.getDiskImageSize(), hashValue, historyDataList, refReplicas, randomWithRange(0,10));
                    insertStatement = FusekiUtils.generateInsertObjectStatement(fragment);
                    upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                    upp.execute();
                    fragments++;

                    // --- DELIVERY
                    for (int k = 0; k < fragmentMaxSize; k++) {
                        long requestTime = DateTime.now().getMillis(); // aka query time
                        Delivery delivery = new Delivery(UUID.randomUUID().toString(), "http://www.example" + "" +
                                ".org/ownerID", "http://www.example.org/functionalityID", requestTime, repositories
                                .get((int) (Math.random() * repositorySize)).getId(), diskImage.getId(),
                                generateCloudValue(), fragment.getId(), requestTime + (int) (fragment.getFragmentSize
                                () * 1.0 / ReviewDataGenerator.getTransferTime(k) * 1000), (int) (Math.random() *
                                20000));
                        insertStatement = FusekiUtils.generateInsertObjectStatement(delivery);
                        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatement), PATH);
                        upp.execute();
                    }

                }
            }


            System.out.println("..repositories inserted: " + repositorySize);
            System.out.println("..diskImages inserted: " + diskImageSize);
            System.out.println("..fragments inserted: " + fragments);
            System.out.println("..provenience data inserted: " + (10000 - historyDataMaxSize));
            System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    private static List<Geolocation> readXMLFromFile(String filename) {
        List<Geolocation> geolocationList = new ArrayList<>();
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("country");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                System.out.println("\n\n----------------");

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    geolocationList.add(new Geolocation(UUID.randomUUID().toString(), eElement.getElementsByTagName
                            ("countryName").item(0).getTextContent(), eElement.getElementsByTagName("countryCode")
                            .item(0).getTextContent(), eElement.getElementsByTagName("continentName").item(0)
                            .getTextContent(), (Double.valueOf(eElement.getElementsByTagName("east").item(0)
                            .getTextContent()) + Double.valueOf(eElement.getElementsByTagName("west").item(0)
                            .getTextContent())) / 2.0, (Double.valueOf(eElement.getElementsByTagName("south").item(0)
                            .getTextContent()) + Double.valueOf(eElement.getElementsByTagName("north").item(0)
                            .getTextContent())) / 2.0, 1, ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geolocationList;
    }

    private static String generateCloudValue() {
        return "cloud" + (int) (1 + (Math.random() * ((cloudSize - 1) + 1)));
    }

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
