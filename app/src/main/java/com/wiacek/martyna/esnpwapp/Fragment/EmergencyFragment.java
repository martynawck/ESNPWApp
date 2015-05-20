package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.Adapter.EmergencyAdapter;
import com.wiacek.martyna.esnpwapp.Domain.EmergencyContact;
import com.wiacek.martyna.esnpwapp.Domain.EmergencyContactList;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;


public class EmergencyFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private ListView listView1;

    public EmergencyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        listView1 = (ListView) view.findViewById(R.id.listView1);

        ArrayList<EmergencyContact> emergency_data = new EmergencyContactList().getEmergencyContacts();

        EmergencyAdapter adapter = new EmergencyAdapter(getActivity().getApplicationContext(), R.layout.listview_item_row, emergency_data);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView v = (TextView) view.findViewById(R.id.faculty);
                String telephone_number = v.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+" + telephone_number.trim()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
        return view;
    }
}
