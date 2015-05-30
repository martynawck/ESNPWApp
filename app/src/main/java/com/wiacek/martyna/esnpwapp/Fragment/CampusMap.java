package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.wiacek.martyna.esnpwapp.Domain.FacultyMarker;
import com.wiacek.martyna.esnpwapp.Domain.FacultyNames;
import com.wiacek.martyna.esnpwapp.Domain.PWFacultyMarkerList;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CampusMap extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @InjectView(R.id.mapView)
    MapView mMapView;
    private GoogleMap googleMap;
    private HashMap<Marker, FacultyMarker> markersHashMap;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_campus_map, container, false);
        ButterKnife.inject(this, v);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        markersHashMap = new HashMap<>();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker arg0) {
                View v = inflater.inflate(R.layout.info_window_layout, null);
                ButterKnife.inject(this, v);
                FacultyMarker myMarker = markersHashMap.get(arg0);
                ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);
                TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);
                TextView markerDeansOffice = (TextView) v.findViewById(R.id.marker_deans_office);
                TextView markerInternationalOffice = (TextView) v.findViewById(R.id.marker_international_office);

                markerIcon.setImageResource(myMarker.getmIcon());
                markerLabel.setText(myMarker.getmLabel());
                markerDeansOffice.setText(myMarker.getDeansOffice());
                markerInternationalOffice.setText(myMarker.getInternationalOffice());
                return v;
            }
        });

        PWFacultyMarkerList markerList = new PWFacultyMarkerList();
        ArrayList<FacultyMarker> markers = markerList.getFacultyMarkers();
        plotMarkers(markers);
        LatLng coordinate = markerList.getLatLngOfFaculty(sessionManager.getValueOfFaculty());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 14);
        googleMap.moveCamera(yourLocation);
        return v;
    }

    private void plotMarkers(ArrayList<FacultyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (FacultyMarker myMarker : markers)
            {
                String shortFacName = sessionManager.getValueOfFaculty();
                String longFacName = new FacultyNames().returnLongName(shortFacName);

                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                if (myMarker.getmLabel().equals("ESN Office") || myMarker.getmLabel().equals("International Student Office"))
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                if (myMarker.getmLabel().equals(longFacName)) {
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }

                Marker currentMarker = googleMap.addMarker(markerOption);
                markersHashMap.put(currentMarker, myMarker);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
