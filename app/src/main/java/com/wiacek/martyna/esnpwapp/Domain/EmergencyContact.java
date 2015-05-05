package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-03-03.
 */
public class EmergencyContact {
    public int icon;
    public String name;
    public String telephone_number;

    public EmergencyContact(){
        super();
    }

    public EmergencyContact(int icon, String name, String telephone_number) {
        super();
        this.icon = icon;
        this.name = name;
        this.telephone_number = telephone_number;
    }

    @Override
    public String toString() {
        return telephone_number;
    }
}
