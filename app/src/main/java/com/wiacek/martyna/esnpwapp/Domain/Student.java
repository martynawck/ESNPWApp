package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-04.
 */
public class Student extends Buddy {

    private String faculty;
    private String imgUrl;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Student() {
        super();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return  faculty;
    }
}
