package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnFunMapCategory;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */
public class DownloadMarkerCategories {

    private final OnFunMapCategory listener;
    private TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    private final Context mContext;

    public DownloadMarkerCategories ( Context context, OnFunMapCategory listener){
        this.listener = listener;
        this.mContext = context;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + "funmapCategories.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    places = new TreeMap<>();
                    FunMapCategory categoryAll = new FunMapCategory();
                    categoryAll.setName("All");
                    categoryAll.setId("0");
                    places.put (categoryAll, new ArrayList<FunMapPlace>());

                    if(!response.equalsIgnoreCase("null")){
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String post = jsonObject.getString("post");
                            JSONObject postObject = new JSONObject(post);
                            FunMapCategory category = JSONFunctions.JSONToFunMapCategory(postObject);

                            places.put(category, new ArrayList<FunMapPlace>());
                        }
                    }

                    for (FunMapCategory cat : places.keySet()) {
                        places.put(cat, new ArrayList<FunMapPlace>());
                    }

                    listener.onTaskCompleted(places);
                } catch (Exception e) {
                    Toast.makeText(mContext, "Error. Cannot download places categories!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot download places categories!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(sr);
    }
}
