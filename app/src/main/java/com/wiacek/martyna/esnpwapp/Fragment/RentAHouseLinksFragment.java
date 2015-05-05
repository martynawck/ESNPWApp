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
import com.wiacek.martyna.esnpwapp.Adapter.ClickableLinkAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ClickableLink;
import com.wiacek.martyna.esnpwapp.R;

public class RentAHouseLinksFragment extends Fragment {


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private ListView listView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        listView1 = (ListView) view.findViewById(R.id.listView1);

        final ClickableLink emergency_data[] = new ClickableLink[]
                {
                        new ClickableLink("Gumtree", "http://www.gumtree.pl"),
                        new ClickableLink("Ambulance", "http://www.pw.edu.pl")
                };

        ClickableLinkAdapter adapter = new ClickableLinkAdapter(getActivity().getApplicationContext(), R.layout.listview_item_row_no_img, emergency_data);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(emergency_data[position].getWebsite()));
                startActivity(intent);
            }
        });

        return view;
    }



}