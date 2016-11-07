package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pareto extends MyEntry {
    private long saveTime;
    private int stage;
    private Double[][] objectives;
    private Integer[][] variables;

    public Pareto(String id, long saveTime, Double[][] objectives) {
        super(id);
        this.saveTime = saveTime;
        this.objectives = objectives;
    }

    public Pareto() {
        super("");
    }

    public Pareto(String id) {
        super(id);
    }

    public Double[][] getObjectives() {
        return objectives;
    }

    public void setObjectives(Double[][] objectives) {
        this.objectives = objectives;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Integer[][] getVariables() {
        return variables;
    }

    public void setTableValuesFromString(String stringValues, Class clazz) {
        final String[] splited = stringValues.split("//");
        Double[][] doubles = null;
        Integer[][] integers = null;

        if (clazz.equals(Integer.class)) {
            integers = new Integer[splited.length][splited[0].split(",").length];
        }
        else if (clazz.equals(Double.class)) {
            doubles = new Double[splited.length][splited[0].split(",").length];
        }

        for (int i = 0; i < splited.length; i++) {
            String[] split2 = splited[i].split(",");
            for (int j = 0; j < split2.length; j++) {
                if (doubles != null)                           //optimize
                    doubles[i][j] = Double.valueOf(split2[j]);
                else if (integers != null) integers[i][j] = Integer.valueOf(split2[j]);
            }
        }

        if (doubles != null) setObjectives(doubles);
        else if (integers != null) variables = integers;
    }
}
