package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnFunMapCategory;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */
public class DownloadMarkerCategories extends AsyncTask<String, Void, String> {

    private OnFunMapCategory listener;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    Context context;

    public DownloadMarkerCategories ( Context context, OnFunMapCategory listener){
        this.listener = listener;
        this.context = context;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {

     try{
            places = new TreeMap<>();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "funmapCategories.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            FunMapCategory categoryAll = new FunMapCategory();
            categoryAll.setName("All");
            categoryAll.setId("0");
            places.put (categoryAll, new ArrayList<FunMapPlace>());

            if(!response.equalsIgnoreCase("null")){
                Log.d("RESP", "NOT NULL");
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    FunMapCategory category = new FunMapCategory();
                    category.setId(postObject.getString("id"));
                    category.setName(postObject.getString("name"));

                    places.put(category, new ArrayList<FunMapPlace>());
                }
            }

            for (FunMapCategory cat : places.keySet()) {
                places.put(cat, new ArrayList<FunMapPlace>());
            }

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }

        return "0";

    }

    @Override
    protected void onPostExecute(String result) {

        listener.onTaskCompleted(places);
    }
}
