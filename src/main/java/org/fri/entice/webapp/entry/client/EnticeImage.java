package org.fri.entice.webapp.entry.client;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class EnticeImage {
    private String id;
    private String avatarURI;
    private String imageName;
    private String ownerID;
    private String ownerFullName;
    private int imageSize;
    private int pulls;
    private List<String> categories;
    private int numberOfChilds;
    private String description;

//    - image_name (String)
//    - image_avatar (Int)
//    - image_owner (String) (Tukaj je potrebno kreirati 2 tipa uporabnikov (admine in navadne uporabnike in jih prirediti slike)
//    - image_size (Double) (velikost slike)
//            - image_pulls (Int) (Število prenosov posamezne slike)
//            - number_of_optiminzed_variants (Int) - število optimizacij ene instance (slike)
//    - image_tags (List<String>) (seznam tagov oz. kategorij, ki jih izbere uporabnik pri uploadu slike)

    public EnticeImage(String id, String avatarURI, String imageName, String ownerID, int imageSize, int pulls, List<String>
            categories, int numberOfChilds, String description, String ownerFullName) {
        this.id = id;
        this.avatarURI = avatarURI;
        this.imageName = imageName;
        this.ownerID = ownerID;
        this.imageSize = imageSize;
        this.pulls = pulls;
        this.categories = categories;
        this.numberOfChilds = numberOfChilds;
        this.description = description;
        this.ownerFullName = ownerFullName;
    }

    public EnticeImage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarURI() {
        return avatarURI;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getPulls() {
        return pulls;
    }

    public void setPulls(int pulls) {
        this.pulls = pulls;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getNumberOfChilds() {
        return numberOfChilds;
    }

    public void setNumberOfChilds(int numberOfChilds) {
        this.numberOfChilds = numberOfChilds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
}
