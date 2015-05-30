package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Adapter.EventsAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnEventTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Martyna on 2015-05-20.
 */

public class GetEventsFromServerTask extends AsyncTask<Void, Void, String> {

    private OnEventTaskCompleted listener;
    private Context mContext;
    HttpClient httpclient;
    Fragment fragment;
    ArrayList<Event> events;
    ProgressDialog progressDialog;

    public GetEventsFromServerTask ( Context context, ProgressDialog progressDialog, OnEventTaskCompleted listener) {
        this.listener = listener;
        this.mContext = context;
        this.progressDialog = progressDialog;
    }


    protected String doInBackground(Void... urls) {
        try {
            events = new ArrayList<>();
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpGet httpget = new HttpGet(ServerUrl.BASE_URL + "events.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httpget, responseHandler);

            if (!response.equalsIgnoreCase("null")) {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Event e = JSONFunctions.JSONToEvent(jsonObject);
                    events.add(e);
                }

                Collections.sort(events, new Comparator<Event>() {
                    public int compare(Event o1, Event o2) {
                        if (o1.getStartTime() == null || o2.getStartTime() == null)
                            return 0;
                        return o1.getStartTime().compareTo(o2.getStartTime());
                    }
                });
                progressDialog.dismiss();
                return "0";
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
        return "-1";
    }

    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
            listener.onTaskCompleted(events);
        } else {
            Toast.makeText(mContext, "Error. Cannot download ESN Events!", Toast.LENGTH_LONG).show();
        }
    }
}
