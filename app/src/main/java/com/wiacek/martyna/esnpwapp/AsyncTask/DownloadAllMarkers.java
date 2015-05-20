package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-19.
 */

public class DownloadAllMarkers extends AsyncTask<String, Void, String> {

    FragmentFunMap fragment;
    ArrayList<MarkerOptions> markerOptions;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;

    public DownloadAllMarkers (FragmentFunMap fragment, TreeMap <FunMapCategory, ArrayList<FunMapPlace>> places){
        this.fragment = fragment;
        this.places = places;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "funmapAllPlaces.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

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
                    place.setId("");
                    place.setName(postObject.getString("name"));
                    place.setDescription(postObject.getString("description"));
                    place.setCoordinate(new LatLng(Double.parseDouble(postObject.getString("x_coordinate")),
                            Double.parseDouble(postObject.getString("y_coordinate"))));

                    Log.d("place name",place.getName());
                    for (FunMapCategory category : places.keySet()) {
                        if (postObject.getString("category_id").equals(category.getId())) {
                            Log.d("category name", category.getName());
                            places.get(category).add(place);
                        }
                    }

                    switch (postObject.getString("category_id")) {
                        case "1":
                            color = BitmapDescriptorFactory.HUE_AZURE;
                            break;
                        case "2":
                            color = BitmapDescriptorFactory.HUE_MAGENTA;
                            break;
                        case "3":
                            color = BitmapDescriptorFactory.HUE_VIOLET;
                            break;
                        case "4":
                            color = BitmapDescriptorFactory.HUE_GREEN;
                            break;
                        case "5":
                            color = BitmapDescriptorFactory.HUE_RED;
                            break;
                        case "10":
                            color = BitmapDescriptorFactory.HUE_ROSE;
                            break;
                        case "7":
                            color = BitmapDescriptorFactory.HUE_YELLOW;
                            break;
                        case "8":
                            color = BitmapDescriptorFactory.HUE_ORANGE;
                            break;

                    }
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(Double.parseDouble(postObject.getString("x_coordinate")),
                            Double.parseDouble(postObject.getString("y_coordinate"))));
                    marker.title(postObject.getString("name"));
                    marker.snippet(postObject.getString("description"));
                    marker.icon(BitmapDescriptorFactory.defaultMarker(color));
                    markerOptions.add(marker);

                }

            }


        }catch(Exception e){
            // progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }

        return "0";

    }

    @Override
    protected void onPostExecute(String result) {

        fragment.setList(markerOptions, places);

    }
}


