package org.ul.entice.webapp.entry.client;

import java.util.Comparator;

public class SZTAKIOptimizationStatusObjComparator implements Comparator<SZTAKIOptimizationStatusObj> {
    @Override
    public int compare(SZTAKIOptimizationStatusObj o1, SZTAKIOptimizationStatusObj o2) {
        return (o1.getStarted() < o2.getStarted() ? 1 : -1);
    }
}
