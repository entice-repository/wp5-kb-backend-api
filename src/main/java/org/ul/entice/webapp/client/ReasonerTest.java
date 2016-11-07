package org.ul.entice.webapp.client;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Derivation;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReasonerTest {

    String NS = "urn:x-hp:eg/";

    // Build a trivial example data set
    Model model = ModelFactory.createDefaultModel();
    InfModel inf;

    Resource A = model.createResource(NS + "A");
    Resource B = model.createResource(NS + "B");
    Resource C = model.createResource(NS + "C");
    Resource D = model.createResource(NS + "D");

    Property p = model.createProperty(NS, "p");
    Property q = model.createProperty(NS, "q");


    @Before
    public void init() {

        // Some small examples (subProperty)
        model.add(p, RDFS.subPropertyOf, q);
        A.addProperty(p, "foo");

        // Get an RDFS reasoner
        GenericRuleReasoner rdfsReasoner = (GenericRuleReasoner) ReasonerRegistry.getRDFSReasoner();
        // Steal its rules, and add one of our own, and create a
        // reasoner with these rules
        List<Rule> customRules = new ArrayList<Rule>(rdfsReasoner.getRules());
        String customRule = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
        customRules.add(Rule.parseRule(customRule));
        Reasoner reasoner = new GenericRuleReasoner(customRules);

        reasoner.setDerivationLogging(true);
        inf = ModelFactory.createInfModel(reasoner, model);

        // Derivations
        A.addProperty(p, B);
        B.addProperty(p, C);
        C.addProperty(p, D);
    }

    @Test
    public void subProperty() {
        StmtIterator stmts = inf.listStatements(A, q, (RDFNode) null);
        assertTrue(stmts.hasNext());
        while (stmts.hasNext()) {
            System.out.println("Statement: " + stmts.next());
        }
    }

    @Test
    public void derivations() {
        String trace = null;
        PrintWriter out = new PrintWriter(System.out);
        for (StmtIterator i = inf.listStatements(A, p, D); i.hasNext(); ) {
            Statement s = i.nextStatement();
            System.out.println("Statement is " + s);
            for (Iterator<Derivation> id = inf.getDerivation(s); id.hasNext(); ) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
                trace += deriv.toString();
            }
        }
        out.flush();
        assertNotNull(trace);
    }

    @Test
    public void listStatements() {
        StmtIterator stmtIterator = inf.listStatements();
        while (stmtIterator.hasNext()) {
            System.out.println("ST: "+ stmtIterator.nextStatement());
        }
    }
}