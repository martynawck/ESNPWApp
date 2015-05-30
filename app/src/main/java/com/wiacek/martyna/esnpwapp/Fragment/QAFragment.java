package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadQandATask;
import com.wiacek.martyna.esnpwapp.Interface.OnQADownload;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QAFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private ExpandableListView expandableListView;
    private QandAListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, container,
                false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        DownloadQandATask task = new DownloadQandATask(getActivity().getApplicationContext(), new OnQADownload() {
            @Override
            public void onTaskCompleted(List<String> titles, HashMap<String, List<String>> expandableListDetails) {
                expandableListDetail = expandableListDetails;
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new QandAListAdapter(getActivity().getApplicationContext(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
        //task.execute(new String[] { ServerUrl.BASE_URL + "/include/file.json" });
        task.runVolley();

        return view;
    }
}
