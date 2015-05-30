package com.wiacek.martyna.esnpwapp.AsyncTask;

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
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-24.
 */

public class DownloadMarkersByUser {

    FragmentFunMap fragment;
    ArrayList<MarkerOptions> markerOptions;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    Context mContext;

    public DownloadMarkersByUser (Context context, FragmentFunMap fragment, TreeMap <FunMapCategory, ArrayList<FunMapPlace>> places){
        this.fragment = fragment;
        this.places = places;
        this.mContext = context;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String uri = String.format("funmapPlacesByUser.php?id=%1$s",
                new SessionManager(mContext).getValueOfUserId());
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + uri , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    for (FunMapCategory category : places.keySet()) {
                        places.get(category).clear();
                    }

                    markerOptions = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String post = jsonObject.getString("post");
                        JSONObject postObject = new JSONObject(post);
                        FunMapPlace place = JSONFunctions.JSONToFunMapPlace(postObject);
                        Log.d("PLACE",place.getId());
                        for (FunMapCategory category : places.keySet()) {
                            if (postObject.getString("category_id").equals(category.getId())) {
                                places.get(category).add(place);
                            }
                        }

                        MarkerOptions marker = new MarkerOptions();
                        marker.position(place.getCoordinate());
                        marker.title(place.getName());
                        marker.snippet(place.getDescription());
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        markerOptions.add(marker);

                    }
                    fragment.setUserMarkerList(markerOptions, places);

                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                    Toast.makeText(mContext, "Error. Cannot download your places!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot download your places!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",new SessionManager(mContext).getValueOfUserId());

                return params;
            }
        };
        queue.add(sr);
    }
}


