package org.fri.entice.webapp.entry.client;

import org.fri.entice.webapp.entry.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class EnticeDetailedImage extends EnticeImage {

    private List<Repository> repositoriesList;
    private List<String> functionalTests;
    private List<String> optimizationHistory; //optimization history list: URLS||SIZE||IMAGENAME

//    public EnticeDetailedImage(String id, int avatarID, String imageName, String ownerID, int imageSize, int pulls, List<String>
//            categories, int numberOfChilds) {
//        super(id, avatarID, imageName, ownerID, imageSize, pulls, categories, numberOfChilds);
//    }

    public EnticeDetailedImage(String id, int avatarID, String imageName, String ownerID, int imageSize, int pulls, List<String>
            categories, int numberOfChilds, List<Repository> repositoriesList, List<String> functionalTests,
                               List<String> optimizationHistory) {
        super(id, avatarID, imageName, ownerID, imageSize, pulls, categories, numberOfChilds);
        this.repositoriesList = repositoriesList;
        this.functionalTests = functionalTests;
        this.optimizationHistory = optimizationHistory;
    }

    public List<Repository> getRepositoriesList() {
        return repositoriesList;
    }

    public void setRepositoriesList(List<Repository> repositoriesList) {
        this.repositoriesList = repositoriesList;
    }

    public List<String> getFunctionalTests() {
        return functionalTests;
    }

    public void setFunctionalTests(List<String> functionalTests) {
        this.functionalTests = functionalTests;
    }

    public List<String> getOptimizationHistory() {
        return optimizationHistory;
    }

    public void setOptimizationHistory(List<String> optimizationHistory) {
        this.optimizationHistory = optimizationHistory;
    }
}
