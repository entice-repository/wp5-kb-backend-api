package org.fri.entice.webapp.util;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.core.ResultBinding;
import org.apache.jena.sparql.core.Var;
import org.fri.entice.webapp.entry.ResultObj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBUtils {

    public static final String OBJECT_PREFIX = "http://purl.org/dc/elements/1.1/";

    public static List<String> parseSubjectResult(ResultSet results) {
        List<String> resultObjs = new ArrayList<String>();

        // For each solution in the result set
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();
            String res = null;
            while (varIter.hasNext()) {
                Var var = varIter.next();
                if (var.getVarName().equals("s")) res = ((ResultBinding) qs).getBinding().get(var).toString();
            }
            resultObjs.add(res);
        }
        return resultObjs;
    }

    public static List<ResultObj> parsePredicateObjectResult(String subject, ResultSet results) {
        List<ResultObj> resultObjs = new ArrayList<ResultObj>();

        // For each solution in the result set
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Iterator<Var> varIter = ((ResultBinding) qs).getBinding().vars();
            String p = null;
            String o = null;
            while (varIter.hasNext()) {
                Var var = varIter.next();
                if (var.getVarName().equals("p")) p = ((ResultBinding) qs).getBinding().get(var).toString();
//                else if (var.getVarName().equals("o")) o = ((ResultBinding) qs).getBinding().get(var).toString();
                else if (var.getVarName().equals("o")) o = (String) ((ResultBinding) qs).getBinding().get(var).getLiteralValue();
            }

            resultObjs.add(new ResultObj(subject, p, o));
        }
        return resultObjs;
    }
}
