package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martyna on 2015-05-20.
 */

public class GetDirectFbIdTask {

    private Context mContext;
    private String ID;
    private Activity activity;
    String url;

    public GetDirectFbIdTask (Context context, Activity activity, String url) {
        mContext = context;
        this.activity = activity;
        this.url = url;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.GET, "http://graph.facebook.com/" + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ID = jsonObject.getString("id");
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + ID));
                            activity.startActivity(intent);
                        } catch(Exception e) {
                            Toast.makeText(mContext, "Facebook is not installed. Please install Facebook!",
                                    Toast.LENGTH_SHORT).show(); }
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Facebook is not installed. Please install Facebook!",
                                Toast.LENGTH_SHORT).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Facebook is not installed. Please install Facebook!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
    }
}