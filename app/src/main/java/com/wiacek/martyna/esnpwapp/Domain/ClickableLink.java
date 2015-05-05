package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-03-03.
 */
public class ClickableLink {
    private  String name;
    private String website;

    public ClickableLink(){
        super();
    }

    public ClickableLink(String name, String website) {
     //   super();
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite () {
       return website;
    }

    @Override
    public String toString() {
        return website;
    }
}
