package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadQandATask;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnQADownload;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QAFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ExpandableListView expandableListView;
    QandAListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, container,
                false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        DownloadQandATask task = new DownloadQandATask(new OnQADownload() {
            @Override
            public void onTaskCompleted(List<String> titles, HashMap<String, List<String>> expandableListDetails) {
                expandableListDetail = expandableListDetails;
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new QandAListAdapter(getActivity().getApplicationContext(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
        task.execute(new String[] { ServerUrl.BASE_URL + "/include/file.json" });

        return view;
    }
}
