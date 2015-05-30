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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnQADownload;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */


public class DownloadQandATask  {

    OnQADownload listener;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Context mContext;

    public DownloadQandATask(Context context, OnQADownload listener){
        this.listener = listener;
        this.mContext = context;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET,
                ServerUrl.BASE_URL + "include/qanda_data.json", null, new Response.Listener<JSONObject>()  {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    expandableListDetail = new HashMap<>();
                    JSONObject jsonObject;
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String question = jsonObject.getString("question");
                        String answer = jsonObject.getString("answer");
                        List<String> list = new ArrayList<>();
                        list.add(answer);
                        expandableListDetail.put(question, list);
                    }
                    listener.onTaskCompleted(expandableListTitle, expandableListDetail);

                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                    Toast.makeText(mContext, "Error. Cannot download Q&As!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot download Q&As!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(sr);
    }
}
