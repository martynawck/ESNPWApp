package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Fragment.FragmentFunMap;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-19.
 */


public class DownloadPointsMarkers extends AsyncTask<String, Void, String> {

    FragmentFunMap fragment;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    ArrayList<MarkerOptions> markerOptions;

    public DownloadPointsMarkers (FragmentFunMap fragment, TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places){
        this.fragment = fragment;
        this.places = places;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
            float color = 0;
        markerOptions = new ArrayList<>();

        FunMapCategory category = null;

        for (FunMapCategory funMapCategory : places.keySet()) {
            Log.d("CAT", funMapCategory.getName());

            if (funMapCategory.getName().equals(urls[0])) {
                category = funMapCategory;
                break;
            }
        }

        switch (category.getId()) {
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

        for (FunMapPlace place : places.get(category)) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(place.getCoordinate());
            Log.d("name",place.getName());
            marker.title(place.getName());
            marker.snippet(place.getDescription());
            marker.icon(BitmapDescriptorFactory.defaultMarker(color));
            markerOptions.add(marker);
        }


        return "0";

    }

    @Override
    protected void onPostExecute(String result) {


        fragment.setList(markerOptions,places);
    }
}