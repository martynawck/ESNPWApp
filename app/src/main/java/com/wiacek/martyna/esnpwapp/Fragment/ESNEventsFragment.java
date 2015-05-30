package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wiacek.martyna.esnpwapp.Adapter.EventsAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetEventsFromServerTask;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Interface.OnEventTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ESNEventsFragment extends Fragment {

    @InjectView(R.id.listView1)
    ListView listView1;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private ArrayList<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_esn_events, container,
                false);
        ButterKnife.inject(this, view);
        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Updating Events...", true);
        new GetEventsFromServerTask(getActivity().getApplicationContext(), progressDialog, new OnEventTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Event> strings) {
                events = strings;
                EventsAdapter adapter = new EventsAdapter(getActivity().getApplicationContext(), R.layout.listview_item_row_event, events);

                listView1.setAdapter(adapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String eventId = events.get(position).getId();
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://event/" + eventId));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://www.facebook.com/"+ eventId));
                            startActivity(intent);
                        }
                    }
                });
            }
        }).execute();
        return view;
    }

}