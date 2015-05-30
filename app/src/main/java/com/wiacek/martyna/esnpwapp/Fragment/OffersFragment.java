package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadOffersTask;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Interface.OnOffersDownload;
import com.wiacek.martyna.esnpwapp.R;

import java.util.List;
import java.util.TreeMap;

public class OffersFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private ExpandableListView expandableListView;
    private OffersListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private TreeMap<Integer, List<ESNPartner>> expandableListDetail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, container,
                false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        DownloadOffersTask task = new DownloadOffersTask(getActivity().getApplicationContext(), new OnOffersDownload() {
            @Override
            public void onTaskCompleted( List<String> titles, TreeMap<Integer, List<ESNPartner>> list) {
                expandableListDetail = list;
                expandableListTitle = titles;
                expandableListAdapter = new OffersListAdapter(getActivity().getApplicationContext(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

            }
        });
        task.runVolley();

        return view;

    }


}
