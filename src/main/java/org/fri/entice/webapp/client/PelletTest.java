package org.fri.entice.webapp.client;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.google.common.collect.Multimap;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.SimpleRenderer;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.io.File;
import java.util.*;

//import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
//import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

public class PelletTest {
    private static String fname = "C:\\Users\\sandig\\Desktop\\data-06.ttl";
    private static File file = new File("C:\\Users\\sandig\\Desktop\\testooo_entice.owl");
    //    private static File file = new File("D:\\projects\\lpt\\apache-jena-fuseki-2.3
    // .0\\run\\configuration\\entice.ttl");
    private static String NS = "http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#";

    //    private static final String BASE_URL_ENTICE = "http://193.2.72.90:3030/test2";
    private static final String BASE_URL_ENTICE = "http://193.2.72.90:3030/entice";
    private static final String BASE_URL = "http://acrab.ics.muni.cz/ontologies/tutorial.owl";

    // this renderer is not OK for UUID based IDs because it trims first ID character! !
//    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    private static OWLObjectRenderer renderer = new SimpleRenderer();
//    private static OWLObjectRenderer renderer3 = new ManchesterOWLSyntaxOWLObjectRendererImpl();
//    private static OWLObjectRenderer renderer4 = new LatexOWLObjectRenderer();

    private static String KB_PREFIX_SHORT = "http://www.semanticweb" + "" +
            ".org/project-entice/ontologies/2015/7/knowledgebase";

    public static void main(String args[]) {
        try {
           // executeWorkingReasoningTest(BASE_URL);

            //prepare ontology and reasoner
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            // String source = FusekiUtils.getFusekiDBSource("http://193.2.72.90:3030/entice/data");
            OWLOntology ontology = manager.loadOntology(IRI.create(BASE_URL_ENTICE));
            OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
            // OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
            OWLDataFactory factory = manager.getOWLDataFactory();
            PrefixManager pm = (PrefixManager) manager.getOntologyFormat(ontology);
            //define knowledgebase prefix
            pm.setPrefix("knowledgebase", "http://www.semanticweb.org/project-entice/ontologies/2015/7/knowledgebase#");

            //get class and its individuals
            OWLClass repositoryClass = factory.getOWLClass("knowledgebase:Fragment", pm);
            System.out.println("-- Found all fragments:");

            OWLNamedIndividual someFragment = null;
            for (OWLNamedIndividual repo : reasoner.getInstances(repositoryClass, true).getFlattened()) {
                System.out.println("fragment element: " + renderer.render(repo));
                someFragment = repo; // set repository object of last element in list
            }

            //get values of selected properties on the individual
            OWLDataProperty fragmentSize = factory.getOWLDataProperty(":Fragment_Size", pm);

            for (OWLLiteral size : reasoner.getDataPropertyValues(someFragment, fragmentSize)) {
                System.out.println("Fragment size: " + size.getLiteral());
            }


            //todo: implement other Pellet functionalities
//        for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(someRepo, isEmployedAtProperty).getFlattened
// ()) {
//            System.out.println("Martin is employed at: " + renderer.render(ind));
//        }
//
//        //get labels
//        LocalizedAnnotationSelector as = new LocalizedAnnotationSelector(ontology, factory, "en", "cs");
//        for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(someRepo, isEmployedAtProperty).getFlattened
// ()) {
//            System.out.println("Martin is employed at: '" + as.getLabel(ind) + "'");
//        }
//
//        //get inverse of a property, i.e. which individuals are in relation with a given individual
//        OWLNamedIndividual university = factory.getOWLNamedIndividual(":MU", pm);
//        OWLObjectPropertyExpression inverse = factory.getOWLObjectInverseOf(isEmployedAtProperty);
//        for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(university, inverse).getFlattened()) {
//            System.out.println("MU inverseOf(isEmployedAt) -> " + renderer.render(ind));
//        }

            //find to which classes the individual belongs
            Collection<OWLClassExpression> assertedClasses = EntitySearcher.getTypes(someFragment, ontology);
            for (OWLClass c : reasoner.getTypes(someFragment, false).getFlattened()) {
                // old version:
//            boolean asserted = assertedClasses.contains(c);
                boolean asserted = assertedClasses.equals(c);

                System.out.println((asserted ? "asserted" : "inferred") + " class: " + renderer.render(c));
            }

            OWLNamedIndividual individualRepository = null;
            OWLNamedIndividual individualImage = null;
            //list all object property values for the individual
            Multimap<OWLObjectPropertyExpression, OWLIndividual> assertedValues = EntitySearcher
                    .getObjectPropertyValues(someFragment, ontology);
            for (OWLObjectPropertyExpression objProp : ontology.getObjectPropertiesInSignature(true)) {
                for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(someFragment, objProp).getFlattened()) {
                    boolean asserted = assertedValues.get(objProp).contains(ind);

                    if (renderer.render(objProp).contains("hasReferenceImage")) individualImage = ind;
                    else if (renderer.render(objProp).contains("hasRepository")) individualRepository = ind;

                    System.out.println((asserted ? "asserted" : "inferred") + " object property: " +
                            renderer.render(objProp) + " -> " + renderer.render(ind));
                }
            }

            OWLObjectProperty hasReferenceImage = factory.getOWLObjectProperty(":Fragment_hasReferenceImage", pm);
            OWLNamedIndividual repo1 = factory.getOWLNamedIndividual(":78cd9996-ea71-4718-99f8-76874c157f2c",pm);
            boolean result = reasoner.isEntailed(factory.getOWLObjectPropertyAssertionAxiom(hasReferenceImage, someFragment, repo1));
            System.out.println("Some fragment has reference image "+repo1.getIRI()+"? " + result);

            OWLNamedIndividual repo2 = factory.getOWLNamedIndividual(":d0eb1c49-e049-4341-830d-751f9d44ffe9",pm);
            result = reasoner.isEntailed(factory.getOWLObjectPropertyAssertionAxiom(hasReferenceImage, someFragment, repo2));
            System.out.println("Some fragment has reference image "+repo2.getIRI()+"? " + result);


            for (OWLLiteral size : reasoner.getDataPropertyValues(individualRepository, factory.getOWLDataProperty
                    (":Repository_OperationalCost", pm))) {
                System.out.println("Operational cost: " + size.getLiteral());
            }

            //list all same individuals
            for (OWLNamedIndividual ind : reasoner.getSameIndividuals(someFragment)) {
                System.out.println("same as selected repository: " + renderer.render(ind));
            }
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeWorkingReasoningTest(String baseUrl) {
        try {
            //prepare ontology and reasoner
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntology(IRI.create(baseUrl));
            OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
            OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
            OWLDataFactory factory = manager.getOWLDataFactory();
            PrefixManager pm = (PrefixManager) manager.getOntologyFormat(ontology);
            pm.setDefaultPrefix(baseUrl + "#");
            Map<String, String> prefixMap = pm.getPrefixName2PrefixMap();

            //get class and its individuals
            OWLClass personClass = factory.getOWLClass(":Person", pm);
            System.out.println("Found persons:");
            for (OWLNamedIndividual person : reasoner.getInstances(personClass, false).getFlattened()) {
                System.out.println("person : " + renderer.render(person));
            }

            //get a given individual
            OWLNamedIndividual martin = factory.getOWLNamedIndividual(":Martin", pm);

            //get values of selected properties on the individual
            OWLDataProperty hasEmailProperty = factory.getOWLDataProperty(":hasEmail", pm);

            OWLObjectProperty isEmployedAtProperty = factory.getOWLObjectProperty(":isEmployedAt", pm);

            for (OWLLiteral email : reasoner.getDataPropertyValues(martin, hasEmailProperty)) {
                System.out.println("Martin has email: " + email.getLiteral());
            }

            for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(martin, isEmployedAtProperty).getFlattened
                    ()) {
                System.out.println("Martin is employed at: " + renderer.render(ind));
            }

            //get labels
            LocalizedAnnotationSelector as = new LocalizedAnnotationSelector(ontology, factory, "en", "cs");
            for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(martin, isEmployedAtProperty).getFlattened
                    ()) {
                System.out.println("Martin is employed at: '" + as.getLabel(ind) + "'");
            }

            //get inverse of a property, i.e. which individuals are in relation with a given individual
            OWLNamedIndividual university = factory.getOWLNamedIndividual(":MU", pm);
            OWLObjectPropertyExpression inverse = factory.getOWLObjectInverseOf(isEmployedAtProperty);
            for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(university, inverse).getFlattened()) {
                System.out.println("MU inverseOf(isEmployedAt) -> " + renderer.render(ind));
            }

            //find to which classes the individual belongs
            Collection<OWLClassExpression> assertedClasses = EntitySearcher.getTypes(martin, ontology);
            for (OWLClass c : reasoner.getTypes(martin, false).getFlattened()) {
                // old version:
//            boolean asserted = assertedClasses.contains(c);
                boolean asserted = assertedClasses.equals(c);

                System.out.println((asserted ? "asserted" : "inferred") + " class for Martin: " + renderer.render(c));
            }

            //list all object property values for the individual
            Multimap<OWLObjectPropertyExpression, OWLIndividual> assertedValues = EntitySearcher
                    .getObjectPropertyValues(martin, ontology);
            for (OWLObjectPropertyExpression objProp : ontology.getObjectPropertiesInSignature(true)) {
                for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(martin, objProp).getFlattened()) {
                    boolean asserted = assertedValues.get(objProp).contains(ind);
                    System.out.println((asserted ? "asserted" : "inferred") + " object property for Martin: " +
                            renderer.render(objProp) + " -> " + renderer.render(ind));
                }
            }

            //list all same individuals
            for (OWLNamedIndividual ind : reasoner.getSameIndividuals(martin)) {
                System.out.println("same as Martin: " + renderer.render(ind));
            }
        } catch (OWLOntologyAlreadyExistsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper class for extracting labels, comments and other anotations in preffered languages.
     * Selects the first literal annotation matching the given languages in the given order.
     */
    public static class LocalizedAnnotationSelector {
        private final List<String> langs;
        private final OWLOntology ontology;
        private final OWLDataFactory factory;

        /**
         * Constructor.
         *
         * @param ontology ontology
         * @param factory  data factory
         * @param langs    list of prefered languages; if none is provided the Locale.getDefault() is used
         */
        public LocalizedAnnotationSelector(OWLOntology ontology, OWLDataFactory factory, String... langs) {
            this.langs = (langs == null) ? Arrays.asList(Locale.getDefault().toString()) : Arrays.asList(langs);
            this.ontology = ontology;
            this.factory = factory;
        }

        /**
         * Provides the first label in the first matching language.
         *
         * @param ind individual
         * @return label in one of preferred languages or null if not available
         */
        public String getLabel(OWLNamedIndividual ind) {
            return getAnnotationString(ind, OWLRDFVocabulary.RDFS_LABEL.getIRI());
        }

        @SuppressWarnings("UnusedDeclaration")
        public String getComment(OWLNamedIndividual ind) {
            return getAnnotationString(ind, OWLRDFVocabulary.RDFS_COMMENT.getIRI());
        }

        public String getAnnotationString(OWLNamedIndividual ind, IRI annotationIRI) {
            return EntitySearcher.getAnnotations(factory.getOWLAnnotationProperty(annotationIRI), ontology).toString();
//            return getLocalizedString(ind.getAnnotations(ontology, factory.getOWLAnnotationProperty(annotationIRI)));
        }

        private String getLocalizedString(Set<OWLAnnotation> annotations) {
            List<OWLLiteral> literalLabels = new ArrayList<OWLLiteral>(annotations.size());
            for (OWLAnnotation label : annotations) {
                if (label.getValue() instanceof OWLLiteral) {
                    literalLabels.add((OWLLiteral) label.getValue());
                }
            }
            for (String lang : langs) {
                for (OWLLiteral literal : literalLabels) {
                    if (literal.hasLang(lang)) return literal.getLiteral();
                }
            }
            for (OWLLiteral literal : literalLabels) {
                if (!literal.hasLang()) return literal.getLiteral();
            }
            return null;
        }
    }
}
