package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.apache.http.NameValuePair;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-19.
 */

public class DownloadAllMarkers extends AsyncTask<String, Void, String> {

    Context mContext;
    FragmentFunMap fragment;
    ArrayList<MarkerOptions> markerOptions;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;

    public DownloadAllMarkers (Context context, FragmentFunMap fragment, TreeMap <FunMapCategory, ArrayList<FunMapPlace>> places){
        this.fragment = fragment;
        this.mContext = context;
        this.places = places;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpGet httpget = new HttpGet(ServerUrl.BASE_URL + "funmapAllPlaces.php");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            markerOptions = new ArrayList<>();
            final String response = httpclient.execute(httpget, responseHandler);
            float color = 0;

            if(!response.equalsIgnoreCase("null")){
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    FunMapPlace place = JSONFunctions.JSONToFunMapPlace(postObject);

                    for (FunMapCategory category : places.keySet()) {
                        if (postObject.getString("category_id").equals(category.getId())) {
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
                            color = BitmapDescriptorFactory.HUE_CYAN;
                            break;
                        case "8":
                            color = BitmapDescriptorFactory.HUE_ORANGE;
                            break;

                    }
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(place.getCoordinate());
                    marker.title(place.getName());
                    marker.snippet(place.getDescription());
                    marker.icon(BitmapDescriptorFactory.defaultMarker(color));
                    markerOptions.add(marker);

                }

            }
            return response;

        }catch(Exception e){
            return "-1";
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (!result.equals("null") && !result.equals("-1"))
            fragment.setList(markerOptions, places);
        else
            Toast.makeText(mContext, "Error. Cannot download all the markers! Try again later!", Toast.LENGTH_LONG).show();

    }
}


