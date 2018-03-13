package org.ul.entice.webapp.entry.client;

import org.ul.entice.webapp.entry.Functionality;
import org.ul.entice.webapp.entry.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class EnticeDetailedImage extends EnticeImage {

    private List<Repository> repositoriesList;
    private List<Functionality> functionalTests;
    private List<String> optimizationHistory; //optimization history list: URLS||SIZE||IMAGENAME

//    public EnticeDetailedImage(String id, int avatarID, String imageName, String ownerID, int imageSize, int pulls, List<String>
//            categories, int numberOfChilds) {
//        super(id, avatarID, imageName, ownerID, imageSize, pulls, categories, numberOfChilds);
//    }

    public EnticeDetailedImage(String id, String avatarURI, String imageName, String ownerID, int imageSize, int pulls, List<String>
            categories, int numberOfChilds, List<Repository> repositoriesList, List<Functionality> functionalTests,
                               List<String> optimizationHistory,String description, String ownerFullName, String vmiURL, String ovfURL) {
        super(id, avatarURI, imageName, ownerID, imageSize, pulls, categories, numberOfChilds,description, ownerFullName, vmiURL, ovfURL);
        this.repositoriesList = repositoriesList;
        this.functionalTests = functionalTests;
        this.optimizationHistory = optimizationHistory;
    }

    public EnticeDetailedImage() {
    }

    public List<Repository> getRepositoriesList() {
        return repositoriesList;
    }

    public void setRepositoriesList(List<Repository> repositoriesList) {
        this.repositoriesList = repositoriesList;
    }

    public List<Functionality> getFunctionalTests() {
        return functionalTests;
    }

    public void setFunctionalTests(List<Functionality> functionalTests) {
        this.functionalTests = functionalTests;
    }

    public List<String> getOptimizationHistory() {
        return optimizationHistory;
    }

    public void setOptimizationHistory(List<String> optimizationHistory) {
        this.optimizationHistory = optimizationHistory;
    }
}
