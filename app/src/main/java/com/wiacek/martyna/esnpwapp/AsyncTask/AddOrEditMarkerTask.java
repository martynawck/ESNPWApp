package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-23.
 */
public class AddOrEditMarkerTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    HttpClient httpclient;
    ESNPWSQLHelper esnpwsqlHelper;


    public AddOrEditMarkerTask(Context context) {
        mContext = context;
    }
    protected String doInBackground(String... urls) {

        Log.d("alalal",urls[5]);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ServerUrl.BASE_URL+"addMarker.php");
        JSONObject json = new JSONObject();

        try {
         /*   JSONObject json = new JSONObject();
          */json.put("user_id", urls[0]);
            json.put("latitude", urls[1]);
            json.put("longitude", urls[2]);
            json.put("name", urls[3]);
            json.put("description", urls[4]);
            json.put("category_id", urls[5]);
            json.put("previous_category", urls[6]);
            json.put("id", urls[7]);

            Log.d("json",json.toString());

            httppost.setHeader("json",json.toString());
            httppost.getParams().setParameter("jsonpost",json);

            HttpResponse response = httpclient.execute(httppost);

       if(response != null)
            {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("resp",sb.toString());
                //TODO if sb true ok else blad
            }

        }catch (ClientProtocolException e) {
            e.getStackTrace();
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.getStackTrace();
            // TODO Auto-generated catch block
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "0";
    }

    protected void onPostExecute(String result) {


        Toast.makeText(mContext, "Place added!", Toast.LENGTH_LONG).show();
    }
}
