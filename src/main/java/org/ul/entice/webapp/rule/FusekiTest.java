package org.ul.entice.webapp.rule;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;
import org.apache.log4j.BasicConfigurator;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.ul.entice.webapp.util.FusekiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

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
         validateOntology();

        BasicConfigurator.configure();

        reasonerTest();

        pelletTest();

        String query = FusekiUtils.getAllUploadedImages(false);

        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", query);
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }

    private static void validateOntology() {
        Model data = FileManager.get().loadModel("http://193.2.72.90:3030/entice");
        InfModel infmodel = ModelFactory.createRDFSModel(data);
        ValidityReport validity = infmodel.validate();
        if (validity.isValid()) {
            System.out.println("OK");
        } else {
            System.out.println("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext(); ) {
                System.out.println(" - " + i.next());
            }
        }
    }

    private static void pelletTest() {
        // ontology that will be used
        String ont = "http://www.mindswap.org/2004/owl/mindswappers";
        // create an empty ontology model using Pellet spec
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.getInstance().getReasonerName());

        // read the file
        model.read(ont);

        // get the instances of a class
        OntClass Person = model.getOntClass("http://xmlns.com/foaf/0.1/Person");
        Iterator instances = Person.listInstances();
    }

    private static String ns = "http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#";

    public static void printIterator(Iterator<?> i, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();

        if(i.hasNext()) {
            while (i.hasNext())
                System.out.println( i.next() );
        }
        else
            System.out.println("<EMPTY>");

        System.out.println();
    }

    private static void reasonerTest() {
        try {
//            String SOURCE = FusekiUtils.getFusekiDBSource("http://193.2.72.90:3030/switch/data");
            String SOURCE = FusekiUtils.getFusekiDBSource("http://193.2.72.90:3030/entice");

            //create a model using TRANSITIVE reasoner (can identify transactional relationships)
            OntModel model1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF);

            //create a model which doesn't use a reasoner (default)
            OntModel model2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

            final String format = "TTL";

            // read the TURTLE file
            model1.read(new FileInputStream(SOURCE), null, format);
            model2.read(new FileInputStream(SOURCE), null, format);

            ////////////////
            Resource res = model1.getResource(ns+"DiskImageSLA");
            printIterator(model1.listObjectsOfProperty(res, RDFS.subClassOf), "All super classes of " + res
                    .getLocalName());

            String queryString = "PREFIX tutorial: <http://acrab.ics.muni.cz/ontologies/tutorial.owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "select  ?uri\n" +
                    "where { ?uri rdfs:subClassOf tutorial:Person \n" +
                    "}";

            OntClass c = model1.getOntClass( ns + "DiskImageSLA" );
            printIterator(c.listSuperClasses(true), "Direct superclasses of " + c.getLocalName());
            /////////////
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            // Loads the ontology
            File file = new File("C:\\Users\\sandig\\Desktop\\entice-ontologies-owl-REVISION-9223372036854775807\\root-ontology.owl");
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

            PelletReasoner reasoner = PelletReasonerFactory.getInstance().createNonBufferingReasoner(ontology);    reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            reasoner.getKB().realize();
            reasoner.getKB().printClassTree();

           // reasoner.getKB().getRules()
            /////////////

            Query query = QueryFactory.create(queryString);

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
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}