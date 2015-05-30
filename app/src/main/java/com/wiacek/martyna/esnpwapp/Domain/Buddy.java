package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-04-13.
 */
public class Buddy {

    private String firstname;

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    private String lastname;
    private String email;
    private String skype;
    private String facebook;
    private String phone;
    private String whatsapp;

    public Buddy() {}


    public String getFirstname() {
        return  firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return  email;
    }

    public String getSkype() {
        return skype;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getPhone() {
        return phone;
    }

    public String getWhatsapp(){
        return whatsapp;
    }

}
