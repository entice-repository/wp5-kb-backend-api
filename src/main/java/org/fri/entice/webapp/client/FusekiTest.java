package org.fri.entice.webapp.client;

import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import org.fri.entice.webapp.entry.User;
import org.fri.entice.webapp.util.FusekiUtils;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

        user = new User(UUID.randomUUID().toString(), "sandokan@aa.com", "Sandokan G.", "pass1234", "090 000",
                "sandigec");
        Response resp = service.path("rest").path("service").path("register_user").request().post(Entity.entity(user,
                MediaType.APPLICATION_JSON_TYPE));


        String insertStatementStr = FusekiUtils.generateInsertObjectStatement(user);
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(insertStatementStr),
                "http://localhost:3030/entice/update");

        if (Boolean.valueOf(resp.readEntity(String.class)))
            System.out.println("User " + user.getFullName() + " added into the KB.");

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
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }

}