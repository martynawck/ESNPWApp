package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnOffersDownload;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */

public class DownloadOffersTask {

    private Context mContext;
    OnOffersDownload listener;
    TreeMap<Integer, List<ESNPartner>> expandableListDetail;
    List<String> expandableListTitle;

    public DownloadOffersTask(Context context, OnOffersDownload listener){
        mContext = context;
        this.listener = listener;
     }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET,
                ServerUrl.BASE_URL + "include/partners_data.json", null, new Response.Listener<JSONObject>()  {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    expandableListDetail = new TreeMap<>();
                    expandableListTitle = new ArrayList<>();
                    ArrayList<ESNPartner> partners = new ArrayList<>();

                    JSONObject jsonObject;
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        ESNPartner esnPartner = JSONFunctions.JSONToPartner(jsonObject);
                        partners.add(esnPartner);
                    }

                    Collections.sort(partners, new Comparator<ESNPartner>() {
                        public int compare(ESNPartner o1, ESNPartner o2) {
                            if (o1.getName() == null || o2.getName() == null)
                                return 0;
                            return o1.getName().compareTo(o2.getName());
                        }
                    });

                    int i = 0;
                    for (ESNPartner p : partners) {
                        expandableListTitle.add(p.getName());
                        ArrayList<ESNPartner> pa = new ArrayList<>();
                        pa.add(p);
                        expandableListDetail.put(i, pa);
                        i++;
                    }

                    listener.onTaskCompleted(expandableListTitle, expandableListDetail);

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error. Cannot download offers!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot download offers!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(sr);
    }
}
