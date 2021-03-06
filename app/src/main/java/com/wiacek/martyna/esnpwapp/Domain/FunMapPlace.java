package com.wiacek.martyna.esnpwapp.Domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Martyna on 2015-05-18.
 */
public class FunMapPlace {


    private String name;
    private String description;
    private String id;
    private String userId;
    private LatLng coordinate;

    public FunMapPlace() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof FunMapPlace)
        {
            sameSame = this.coordinate == ((FunMapPlace) object).coordinate;
        }

        return sameSame;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 7 * hash + this.coordinate.hashCode();
        return hash;
    }

}
