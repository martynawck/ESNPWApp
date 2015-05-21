package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
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
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */
public class DownloadMarkerCategories extends AsyncTask<String, Void, String> {

    private OnFunMapCategory listener;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;

    public DownloadMarkerCategories ( OnFunMapCategory listener){
        this.listener = listener;
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

            Log.d("CATEGORY_SUM", Integer.toString(places.keySet().size() ));


            for (FunMapCategory cat : places.keySet()) {
                //funMapCategoryArrayListNames.add(cat.getName());
                places.put(cat, new ArrayList<FunMapPlace>());
            }


        }catch(Exception e){
            // progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }

        return "0";

    }

    @Override
    protected void onPostExecute(String result) {

        listener.onTaskCompleted(places);
    }
}
