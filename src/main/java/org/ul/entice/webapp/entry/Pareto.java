package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class Pareto extends MyEntry {
    private long saveTime;
    private int stage;
    private Double[][] objectives;
    private Integer[][] variables;
    private Integer[] replications;
    private List<HistoryData> historyDataList;
    // 2nd variant:
    private Map<Integer, List<HistoryData>> replicationMap;

    public Pareto(String id, long saveTime, Double[][] objectives, Integer[][] variables, Integer[] replications, List<HistoryData> historyDataList) {
        super(id);
        this.saveTime = saveTime;
        this.objectives = objectives;
        this.variables = variables;

        try {
            int j = 0;
            for (Integer elem : replications) {
                List<HistoryData> hl = new ArrayList<>();
                for (int i = 0; i < elem; i++) {
                    hl.add(historyDataList.get(j));
                    j++;
                }
                replicationMap.put(elem, hl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* EXAMPLE:
        {
            "variables":[[2, 2, 2, 2, 0, 2, 0, 0, 0], [2, 2, 2, 2, 0, 2, 0, 2, 2], [2, 2, 2, 2, 0, 2, 0, 2, 2], [
            2, 2, 2, 2, 0, 2, 0, 0, 0], [2, 2, 2, 2, 0, 2, 0, 2, 2], [2, 2, 2, 2, 0, 2, 0, 2, 2], [
            2, 2, 2, 2, 0, 2, 0, 0, 2], [2, 2, 2, 2, 0, 2, 0, 2, 2], [2, 2, 2, 2, 0, 2, 0, 2, 2], [
            2, 0, 2, 2, 0, 0, 0, 0, 0]],"replications":[0, 0, 0, 0, 0, 1, 0, 0],"stage":2, "objectives":[[
            0.018037499999999998, 511.987698507917, 1.0], [0.017922, 757.6176363178452, 1.0], [
            0.017922, 757.6176363178452, 1.0], [0.018037499999999998, 511.987698507917, 1.0], [
            0.017922, 757.6176363178452, 1.0], [0.017922, 757.6176363178452, 1.0], [0.01797975, 634.4052481458343, 1.0], [
            0.017922, 757.6176363178452, 1.0], [0.017922, 757.6176363178452, 1.0], [0.018153, 266.625041392214, 1.0]]}
        */
    }

    // 1.
    // check why is not working:
    // {"variables":[[1]],"stage":3,"objectives":[[0.039708, 93.52273420297443]]}
    // DONE!!

    // 2.
    // no fragments 8, no clouds 8 clouds, number of historical data: 80
    // http://193.2.72.90:7070/JerseyREST/rest/service/get_fragment_history_delivery_data
    //TODO: FILL MODataGenerator!!

    //TODO: Monday 29.5.2017
    // 3.
    // update ontology about replicas and have history information - Query or relationship: "Where the other replicas are stored?" Output:
    // Dragi: will you use the different table about replica    s or the same as fragments?
    // UPDATE THE FRAGMENT ENTITY - Subfield (dragi): how many replicas, and where currently is stored.
    // UPDATE the method: get_fragment_data
    /*

    DATA for the replicas: location, how many times was downloaded

    {
        "id": "4892e649-b350-43f6-81c9-c75d0e203b11",
            "saveTime": 1495529901968,
            "stage": 2,
            "objectives": [],
        "variables": [],
        "replications": [
        0,
                0,
                0,
                0,
                0,
                1,  // how many times is replicated
                0,
                0
]
    }
    */

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
        } else if (clazz.equals(Double.class)) {
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

    public void setOneDimensionTableValuesFromString(String stringValues) {
        String[] split = stringValues.split(",");
        Integer[] integers = new Integer[split.length];

        for (int i = 0; i < integers.length; i++) {
            integers[i] = Integer.valueOf(split[i]);
        }

        replications = integers;
    }

    public void setVariables(Integer[][] variables) {
        this.variables = variables;
    }

    public Integer[] getReplications() {
        return replications;
    }

    public void setReplications(Integer[] replications) {
        this.replications = replications;
    }

    public List<HistoryData> getHistoryDataList() {
        return historyDataList;
    }

    public void setHistoryDataList(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

//    @XmlElement
//    public Map<Integer, List<HistoryData>> getReplicationMap() {
//        return replicationMap;
//    }

//    public void setReplicationMap(Map<Integer, List<HistoryData>> replicationMap) {
//        this.replicationMap = replicationMap;
//    }
}
