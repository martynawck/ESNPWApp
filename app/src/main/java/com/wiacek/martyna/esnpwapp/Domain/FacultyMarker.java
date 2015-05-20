package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-04-11.
 */
public class FacultyMarker {

    private String mLabel;
    private int mIcon;
    private String deansOffice;
    private String internationalOffice;
    private Double latitude;
    private Double longitude;

    public FacultyMarker(String label, int icon, String deansOffice, String internationalOffice, Double latitude, Double longitude)
    {
        this.mLabel = label;
        this.deansOffice = deansOffice;
        this.internationalOffice = internationalOffice;
        this.mIcon = icon;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public int getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(int icon)
    {
        this.mIcon = icon;
    }

    public String getDeansOffice() {
        return deansOffice;
    }

    public String getInternationalOffice() {
        return internationalOffice;
    }

    public void setDeansOffice(String s) {
        this.deansOffice = s;
    }

    public void setInternationalOffice(String s) {
        this.internationalOffice = s;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
