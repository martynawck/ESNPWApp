package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-04.
 */
public class Student extends Buddy {

    String faculty;
    String imgUrl;

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
