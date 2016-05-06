package org.fri.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Pareto extends MyEntry {
    long saveTime;
    int[][] variables;
    double[][] objectives;
    List<Integer> variablesList;
    List<Double> objectivesList;

    public Pareto(String id, long saveTime, int[][] variables, double[][] objectives) {
        super(id);
        this.saveTime = saveTime;
        this.variables = variables;
        this.objectives = objectives;
    }

    public Pareto() {
        super("");
    }

    public Pareto(String id) {
        super(id);
    }

    public int[][] getVariables() {
        return variables;
    }

    public void setVariables(int[][] variables) {
        this.variables = variables;
    }

    public double[][] getObjectives() {
        return objectives;
    }

    public void setObjectives(double[][] objectives) {
        this.objectives = objectives;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public List<Integer> getVariablesList() {
        return variablesList;
    }

    public void setVariablesList(List<Integer> variablesList) {
        this.variablesList = variablesList;
    }

    public void setVariablesValue(int val) {
        if (variablesList == null) variablesList = new ArrayList<Integer>();

        variablesList.add(val);
    }

    public void setObjectivesValue(double val) {
        if (objectivesList == null) objectivesList = new ArrayList<Double>();

        objectivesList.add(val);
    }

    public List<Double> getObjectivesList() {
        return objectivesList;
    }
}
