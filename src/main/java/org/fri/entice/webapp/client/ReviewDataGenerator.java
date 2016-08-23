package org.fri.entice.webapp.client;

import org.fri.entice.webapp.entry.DiskImage;
import org.fri.entice.webapp.entry.Repository;

import java.util.ArrayList;
import java.util.List;

public class ReviewDataGenerator {
    private static int fragments = 0;
    private static List<DiskImage> diskImages = new ArrayList<DiskImage>();
    private static List<Repository> repositories = new ArrayList<Repository>();

    public static void main(String[] args) {
        final String PATH = "http://193.2.72.90:3030/entice-review/update";
        //todo: prepare a review data for the 1st review (September 2016).
    }
}
