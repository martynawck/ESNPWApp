package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by Martyna on 2015-05-20.
 */

public class GetDirectFbIdTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private String ID;
    private Activity activity;

    public GetDirectFbIdTask (Context context, Activity activity) {
        mContext = context;
        this.activity = activity;

    }
    protected String doInBackground(String... urls) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://graph.facebook.com/" + urls[0]);
        String response = "";
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = client.execute(request, responseHandler);
            JSONObject jsonObject = new JSONObject(response);
            ID = jsonObject.getString("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }

    protected void onPostExecute(String result) {

        if (ID != null) {

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + ID));
                activity.startActivity(intent);
            } catch(Exception e) {
                Toast.makeText(mContext, "Facebook is not installed. Please install Facebook!",
                        Toast.LENGTH_SHORT).show(); }
        }
    }
}