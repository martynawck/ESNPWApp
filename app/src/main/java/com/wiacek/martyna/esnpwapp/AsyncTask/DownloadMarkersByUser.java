package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-24.
 */

public class DownloadMarkersByUser extends AsyncTask<String, Void, String> {

    FragmentFunMap fragment;
    ArrayList<MarkerOptions> markerOptions;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    Context context;

    List<NameValuePair> nameValuePairs;

    public DownloadMarkersByUser (Context context, FragmentFunMap fragment, TreeMap <FunMapCategory, ArrayList<FunMapPlace>> places){
        this.fragment = fragment;
        this.places = places;
        this.context = context;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            for (FunMapCategory category : places.keySet()) {
                places.get(category).clear();
            }

            Log.d("id", new SessionManager(context).getValueOfUserId());
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id", new SessionManager(context).getValueOfUserId()));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "funmapPlacesByUser.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            markerOptions = new ArrayList<>();
            final String response = httpclient.execute(httppost, responseHandler);
            float color = 0;

            if(!response.equalsIgnoreCase("null")){
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    FunMapPlace place = new FunMapPlace();
                    place.setId(postObject.getString("id"));
                    place.setName(postObject.getString("name"));
                    place.setDescription(postObject.getString("description"));
                    place.setCoordinate(new LatLng(Double.parseDouble(postObject.getString("x_coordinate")),
                            Double.parseDouble(postObject.getString("y_coordinate"))));

                    Log.d("place name", place.getName());
                    for (FunMapCategory category : places.keySet()) {
                        if (postObject.getString("category_id").equals(category.getId())) {
                            Log.d("category name", category.getName());
                            places.get(category).add(place);
                        }
                    }

                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(Double.parseDouble(postObject.getString("x_coordinate")),
                            Double.parseDouble(postObject.getString("y_coordinate"))));
                    marker.title(postObject.getString("name"));
                    marker.snippet(postObject.getString("description"));
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    markerOptions.add(marker);

                }

                Log.d("markers", Integer.toString(markerOptions.size()));

            }

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }

        return "0";
    }

    @Override
    protected void onPostExecute(String result) {

        fragment.setUserMarkerList(markerOptions, places);

    }
}


