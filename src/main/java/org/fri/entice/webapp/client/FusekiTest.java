package org.fri.entice.webapp.client;

import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.fri.entice.webapp.entry.User;
import org.fri.entice.webapp.util.FusekiUtils;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Example connection to FusekiUtils. For this to work, you need to start a local
 * FusekiUtils server like this: ./fuseki-server --update --mem /ds
 */
public class FusekiTest {
    /**
     * A template for creating a nice SPARQL query
     */
    private static final String UPDATE_TEMPLATE = "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + "INSERT DATA" +
            "{ <http://example/%s>    dc:title    \"A new book\" ;" + "                         dc:creator  \"A.N" +
            ".Other\" ." + "}   ";

    public static void main(String[] args) {
        FusekiUtils.getAllReasoners();

        /*
        System.out.println(String.format("Adding %s", id));
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(String.format(UPDATE_TEMPLATE,
                id)), "http://localhost:3030/ds/update");
        upp.execute();
        */

        User user = new User(UUID.randomUUID().toString(), "sandi.gec@gmail.com", "Sandi Gec4", "444",
                "+38631873088", "sandig4");

        ClientConfig config = new ClientConfig().register(JacksonFeatures.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget service = client.target("http://localhost:8080/JerseyREST/");
        /*
        user = new User(UUID.randomUUID().toString(), "sandokan@aa.com", "Sandokan G.", "pass1234", "090 000",
                "sandigec");
        Response resp = service.path("rest").path("service").path("register_user").request().post(Entity.entity(user,
                MediaType.APPLICATION_JSON_TYPE));


        String insertStatementStr = FusekiUtils.generateInsertObjectStatement(user);
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");
         */
        //   if (Boolean.valueOf(resp.readEntity(String.class)))
        //      System.out.println("User " + user.getFullName() + " added into the KB.");

        reasonerTest();
//        pelletTest();

          /*
        upp.execute();
        System.out.println("User object added!");

        DiskImage diskImage = new DiskImage(UUID.randomUUID().toString(), ImageType.CI, "some description", "some " +
                "title", "some predecessor..", FileFormat.IMG, "picture URL", false, "iriC", "123", 49.99, "333",
                "43", "54",
                "556", false, 5, true,"1.1");
        insertStatementStr = FusekiUtils.generateInsertObjectStatement(insertStatementStr);
        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");
        upp.execute();
        System.out.println("DiskImage object added!");
        */

        /*
        List<String> hashValue = new ArrayList();
        hashValue.add("v1");
        hashValue.add("v2");
        hashValue.add("v3");
        hashValue.add("v4");
        hashValue.add("v5");
        Fragment fragment = new Fragment(UUID.randomUUID().toString(),"referenceImageId2","repositoryID2","anyURI2",
        32145,hashValue);

        insertStatementStr = FusekiUtils.generateInsertObjectStatement(fragment);
        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");
        upp.execute();
        System.out.println("Fragment object added!");
        */

        /*
        Delivery delivery = new Delivery(UUID.randomUUID().toString(), "refUserId1", "refFucntionalityId1", 31232L,
                "refRepositoryId1", "refDiskImageId1", 434234L);

        insertStatementStr = FusekiUtils.generateInsertObjectStatement(delivery);
        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");
        upp.execute();
        System.out.println("Delivery object added!");
        */
        /*
        Functionality functionality = new Functionality(UUID.randomUUID().toString(), 12, "some tag1", "some name1",
                "some description...1", "some input description1", "some otuput description1", "someRefImplId", "some" +
                " domain");

        insertStatementStr = FusekiUtils.generateInsertObjectStatement(functionality);
        upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");
        upp.execute();
        System.out.println("Functionality object added!");
        */
        String query = FusekiUtils.getAllUploadedImages(false);


        //Query the collection, dump output
        /*
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
        */
    }

    private static void pelletTest() {
        // ontology that will be used
//        String ont = "http://www.mindswap.org/2004/owl/mindswappers";
//        // create an empty ontology model using Pellet spec
//        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//
//        // read the file
//        model.read(ont);
//
//        // get the instances of a class
//        OntClass Person = model.getOntClass("http://xmlns.com/foaf/0.1/Person");
//        Iterator instances = Person.listInstances();
    }

    private static void reasonerTest() {
        try {
            // try 3. reasoner:
            // http://opentox.org/data/documents/development/RDF%20files/JavaOnly/query-reasoning-with-jena-and-sparql

//            String SOURCE = FusekiUtils.getFusekiDBSource("http://localhost:3030/entice/data");
            String SOURCE = FusekiUtils.getFusekiDBSource("http://193.2.72.90:3030/switch/data");
            //String SOURCE = "D:\\projects\\lpt\\apache-jena-fuseki-2.3.0\\run\\backups\\entice_2016-02-22_11-48-53
            // .nq";

            //create a model using reasoner (can identify transactional relationships)
            OntModel model1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF);

            //create a model which doesn't use a reasoner
            OntModel model2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

            final String format = "TTL";
            // only for backuped file data
            //final String format = "N-TRIPLE";

            // read the TURTLE file
            model1.read(new FileInputStream(SOURCE), null, format);
            model2.read(new FileInputStream(SOURCE), null, format);

//            String queryString1 = "prefix knowledgebase: <http://www.semanticweb" +
//                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
//                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
//                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
//                    "select  ?uri\n" +
//                    "where { ?uri rdfs:subClassOf knowledgebase:DiskImage\n" +
//                    "}";
//
//            String queryString2 = "prefix knowledgebase: <http://www.semanticweb" +
//                    ".org/project-entice/ontologies/2015/7/knowledgebase#>\n" +
//                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
//                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
//                    "select  ?uri\n" +
//                    "where { ?uri rdfs:subPropertyOf knowledgebase:DiskImage_DataProperty\n" +
//                    "}";
            String queryString2 = "PREFIX class: <http://www.switch-project.eu/2015/10/reference_model#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "select  ?uri\n" +
                    "where { ?uri rdfs:subClassOf class:InformationConcept \n" +
                    "}";

            Query query = QueryFactory.create(queryString2);

            System.out.println("----------------------");
            System.out.println("Query Result Sheet");
            System.out.println("----------------------");
            System.out.println("Direct&Indirect Descendants (model1)");
            System.out.println("-------------------");

            // Execute the query and obtain results
            QueryExecution qe = QueryExecutionFactory.create(query, model1);
            ResultSet results = qe.execSelect();

            // Output query results
            ResultSetFormatter.out(System.out, results, query);
            System.out.println("SIZE: " + results.getRowNumber());

            qe.close();

            System.out.println("----------------------");
            System.out.println("Only Direct Descendants (model2)");
            System.out.println("----------------------");

            // Execute the query and obtain results
            qe = QueryExecutionFactory.create(query, model2);
            results = qe.execSelect();

            // Output query results
            ResultSetFormatter.out(System.out, results, query);
            System.out.println("SIZE: " + results.getRowNumber());
            qe.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}