package com.wiacek.martyna.esnpwapp.Domain;

import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */
public class EmergencyContactList {

    ArrayList<EmergencyContact> emergencyContacts;

    public ArrayList<EmergencyContact> getEmergencyContacts() {
        emergencyContacts = new ArrayList<>();
        emergencyContacts.add(new EmergencyContact(R.drawable.rys112, "General Emergency", "+48112"));
        emergencyContacts.add(new EmergencyContact(R.drawable.ryspog,"Ambulance", "+4822999"));
        emergencyContacts.add(new EmergencyContact(R.drawable.ryspoz,"Fire Brigade", "+4822998"));
        emergencyContacts.add(new EmergencyContact(R.drawable.ryspol,"Police", "+4822997"));
        emergencyContacts.add(new EmergencyContact(R.drawable.rysdrog,"Road Assistance", "+4822981"));
        emergencyContacts.add(new EmergencyContact(R.drawable.rysmiej,"Municipal Police", "+4822985"));

        return emergencyContacts;
    }
}
