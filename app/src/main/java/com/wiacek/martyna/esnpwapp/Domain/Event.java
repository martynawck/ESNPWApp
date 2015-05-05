package com.wiacek.martyna.esnpwapp.Domain;

import java.util.Date;

/**
 * Created by Martyna on 2015-05-01.
 */
public class Event implements  Comparable<Event> {

    private String id;
    private String name;
    private String where;
    private String place;
    private Date startTime;
    private String owner;
    private String ownerId;
    private String imageUrl;



    public String getId () { return id; }
    public String getName() { return name; }
    public String getWhere() { return  where; }
    public String getPlace() { return  place; }
    public String getOwner() { return  owner; }
    public String getImageUrl() { return imageUrl; }
    public Date getStartTime () { return  startTime; }
    public String getOwnerId () { return  ownerId; }

    public void setId (String id) { this.id = id; }
    public void setOwnerId ( String ownerId) { this.ownerId = ownerId ;}
    public void setName (String name) { this.name = name; }
    public void setWhere (String where ) {this.where = where; }
    public void setPlace (String place) {this.place = place; }
    public void setStartTime (Date date) { this.startTime = date; }
    public void setOwner (String owner) { this.owner = owner; }
    public void setImageUrl (String imageUrl ) { this.imageUrl = imageUrl; }

    @Override
    public int compareTo(Event o) {
        if (this.getStartTime() == null || o.getStartTime() == null)
            return 0;
        return getStartTime().compareTo(o.getStartTime());
    }
}
