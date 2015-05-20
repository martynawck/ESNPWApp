package com.wiacek.martyna.esnpwapp.Domain;

import java.util.Date;

/**
 * Created by Martyna on 2015-05-01.
 */
public class Event implements  Comparable<Event> {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String name;
    private String where;
    private String place;
    private Date startTime;
    private String owner;
    private String ownerId;
    private String imageUrl;



    @Override
    public int compareTo(Event o) {
        if (this.getStartTime() == null || o.getStartTime() == null)
            return 0;
        return getStartTime().compareTo(o.getStartTime());
    }
}
