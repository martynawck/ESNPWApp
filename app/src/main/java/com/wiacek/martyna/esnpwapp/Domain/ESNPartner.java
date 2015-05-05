package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-03.
 */
public class ESNPartner implements   Comparable<ESNPartner> {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public String getHowToUse() {
        return howToUse;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId (String id ) {
        this.id = id;
    }



    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String id;
    private String name;
    private String discount;
    private String howToUse;
    private String description;
    private String website;
    private String image;

    public int compareTo(ESNPartner o) {
        if (this.getName() == null || o.getName() == null)
            return 0;
        return getName().compareTo(o.getName());
    }

}
