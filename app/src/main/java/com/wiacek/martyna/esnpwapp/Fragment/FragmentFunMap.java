package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadAllMarkers;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadMarkerCategories;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadPointsMarkers;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.FacultyMarker;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnFunMapCategory;
import com.wiacek.martyna.esnpwapp.R;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class FragmentFunMap extends Fragment {

    TextView tvItemName;
    Spinner categoryListSpinner;
    ArrayList<FunMapCategory> funMapCategoryArrayList;
    List<String> funMapCategoryArrayListNames;
    TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    MapView mMapView;
    private GoogleMap googleMap;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private ArrayList<MarkerOptions> markers;

    public void setList(ArrayList<MarkerOptions> list,  TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places){
        googleMap.clear();
        this.markers = list;
        this.places = places;
        for (MarkerOptions marker : markers)
            googleMap.addMarker(marker);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.funmap_fragment, container,
                false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(52.229784, 21.008322), 10);
        googleMap.moveCamera(yourLocation);
        places = new TreeMap<>(new MyNameComp());
        funMapCategoryArrayList = new ArrayList<>();
        funMapCategoryArrayListNames = new ArrayList<>();
        tvItemName = (TextView) view.findViewById(R.id.frag1_text);
        categoryListSpinner = (Spinner) view.findViewById(R.id.spinner);
        DownloadMarkerCategories task = new DownloadMarkerCategories(new OnFunMapCategory() {
            @Override
            public void onTaskCompleted(TreeMap<FunMapCategory, ArrayList<FunMapPlace>> placesReturned) {
                places = placesReturned;
                for (FunMapCategory s : places.keySet()) {
                    funMapCategoryArrayListNames.add(s.getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                        (getActivity().getApplicationContext(), R.layout.spinner_funmap, funMapCategoryArrayListNames);

                categoryListSpinner.setAdapter(dataAdapter);
            }
        });
        task.execute();

        categoryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                runMarkersTask(Integer.toString(pos));
            }
            public void onNothingSelected(    AdapterView<?> parent){
            }
        });
        return view;
    }

    public void runMarkersTask(String id ) {
        markers = new ArrayList<>();
        if (id.equals("0")) {
            DownloadAllMarkers taskDownload = new DownloadAllMarkers(this, places);
            taskDownload.execute();
        }
        else {
            DownloadPointsMarkers taskDownload = new DownloadPointsMarkers(this, places);
            String[] strings = new String[1];
            strings[0] = funMapCategoryArrayListNames.get(Integer.parseInt(id));
            taskDownload.execute(strings);
        }
    }

    class MyNameComp implements Comparator<FunMapCategory>{
        @Override
        public int compare(FunMapCategory o1, FunMapCategory o2) {
            if (o1.getName() == null || o2.getName() == null)
                return 0;
            return o1.getName().compareTo(o2.getName());
        }
    }
}