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

import com.wiacek.martyna.esnpwapp.Adapter.EventsAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnEventTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
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
    HttpPost httppost;
    HttpClient httpclient;
    Fragment fragment;
    ArrayList<Event> events;
    ProgressDialog progressDialog;

    public GetEventsFromServerTask ( ProgressDialog progressDialog, OnEventTaskCompleted listener) {
        this.listener = listener;
        this.progressDialog = progressDialog;
    }

    protected String doInBackground(Void... urls) {
        try {
            events = new ArrayList<>();
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "events.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if (!response.equalsIgnoreCase("null")) {
                Log.d("RESP", "NOT NULL");
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Event e = new Event();
                    e.setId(jsonObject.getString("id"));
                    e.setName(jsonObject.getString("name"));

                    if (jsonObject.has("place")) {
                        JSONObject placeObject = jsonObject.getJSONObject("place");
                        e.setPlace(placeObject.getString("name"));
                        if (placeObject.has("city")) {
                            JSONObject locationObject = placeObject.getJSONObject("location");
                            e.setWhere(locationObject.getString("city"));
                        } else {
                            e.setWhere("");
                        }
                    } else {
                        e.setPlace("");
                        e.setWhere("");
                    }

                    if (jsonObject.has("start_time")) {
                        String stringStartTime = jsonObject.getString("start_time");
                        SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                        Date date = new Date();
                        try {
                            date = incomingFormat.parse(stringStartTime);

                        } catch (Exception ex ) {
                            SimpleDateFormat incomingFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                            date = incomingFormat2.parse(stringStartTime);
                        }
                        e.setStartTime(date);
                    }

                    if (jsonObject.has("owner")) {
                        JSONObject ownerObject = jsonObject.getJSONObject("owner");
                        e.setOwner(ownerObject.getString("name"));
                        e.setOwnerId(ownerObject.getString("id"));
                    } else {
                        e.setOwner("");
                    }

                    if (jsonObject.has("cover")) {
                        JSONObject coverObject = jsonObject.getJSONObject("cover");
                        e.setImageUrl(coverObject.getString("source"));
                    } else {
                        e.setImageUrl("");
                    }
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
        }
    }
}
