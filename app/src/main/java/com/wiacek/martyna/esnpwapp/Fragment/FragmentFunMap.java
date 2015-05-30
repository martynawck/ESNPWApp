package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.AsyncTask.AddOrEditMarkerTask;
import com.wiacek.martyna.esnpwapp.AsyncTask.DeleteMarkerTask;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadAllMarkers;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadMarkerCategories;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadMarkersByUser;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadPointsMarkers;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnFunMapCategory;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class FragmentFunMap extends Fragment {

    private Spinner categoryListSpinner;
    private List<String> funMapCategoryArrayListNames;
    private List<String> funMapCategoryArrayListNamesForUser;
    private TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places;
    private TreeMap<FunMapCategory, ArrayList<FunMapPlace>> userPlaces;

    private GoogleMap googleMap;
    private String spinnerForUserMenu;
    private String markerIdInDatabase;
    private String previousCategory;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private ArrayList<MarkerOptions> markers;

    public void setList(ArrayList<MarkerOptions> list,  TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places){
        googleMap.clear();
        markers.clear();
        this.markers = list;
        this.places = places;
        for (MarkerOptions marker : markers)
            googleMap.addMarker(marker);
    }

    public void setUserMarkerList(ArrayList<MarkerOptions> list,  TreeMap<FunMapCategory, ArrayList<FunMapPlace>> places){
        googleMap.clear();
        markers.clear();
        this.markers = list;
        this.userPlaces = places;
        for (MarkerOptions marker : markers)
            googleMap.addMarker(marker);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.funmap_fragment, container,
                false);

        MapView mMapView = (MapView) view.findViewById(R.id.mapView);
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
        userPlaces = new TreeMap<>(new MyNameComp());
        funMapCategoryArrayListNames = new ArrayList<>();
        funMapCategoryArrayListNamesForUser = new ArrayList<>();

        categoryListSpinner = (Spinner) view.findViewById(R.id.spinner);

        DownloadMarkerCategories task = new DownloadMarkerCategories(getActivity().getApplicationContext(), new OnFunMapCategory() {
            @Override
            public void onTaskCompleted(TreeMap<FunMapCategory, ArrayList<FunMapPlace>> placesReturned) {
                places = placesReturned;
                userPlaces = placesReturned;
                for (FunMapCategory s : places.keySet()) {
                    funMapCategoryArrayListNames.add(s.getName());
                    funMapCategoryArrayListNamesForUser.add(s.getName());
                }

                funMapCategoryArrayListNamesForUser.remove(0);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                        (getActivity().getApplicationContext(), R.layout.spinner_funmap, funMapCategoryArrayListNames);

                categoryListSpinner.setAdapter(dataAdapter);
            }
        });
        task.runVolley();

        categoryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                runMarkersTask(Integer.toString(pos));
            }
            public void onNothingSelected(    AdapterView<?> parent){
            }
        });

        Button yourPlaces = (Button) view.findViewById(R.id.button);


        yourPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.setMyLocationEnabled(true);
                //  googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.clear();

                DownloadMarkersByUser task = new DownloadMarkersByUser(getActivity().getApplicationContext(), FragmentFunMap.this, userPlaces);
                task.runVolley();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Long click on the map to insert marker and then click it to add it, change its details or delete it!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                    @Override
                                    public void onMapLongClick(LatLng latLng) {
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(latLng)
                                                .draggable(true));
                                    }
                                });

                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {

                                        final Marker marker1 = marker;
                                        final Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.markerdialog);
                                        dialog.setTitle("Manage markers");

                                        final EditText placeName = (EditText) dialog.findViewById(R.id.placeName);
                                        final EditText description = (EditText) dialog.findViewById(R.id.description);
                                        final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
                                        Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                                        Button okButton = (Button) dialog.findViewById(R.id.okButton);
                                        Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);


                                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                                                (getActivity().getApplicationContext(), R.layout.spinner_funmap, funMapCategoryArrayListNamesForUser);

                                        spinner.setAdapter(dataAdapter);

                                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                                FunMapCategory category = (FunMapCategory) places.keySet().toArray()[pos+1];
                                                spinnerForUserMenu = category.getId();
                                            }
                                            public void onNothingSelected(AdapterView<?> parent) {
                                            }
                                        });

                                        FunMapPlace place = new FunMapPlace();
                                        place.setCoordinate(marker1.getPosition());
                                        Log.d("pos", marker1.getPosition().toString());
                                        boolean alreadyExists = false;
                                        //String markerIdInDatabase = "0";
                                        int iteration = 0;
                                        previousCategory="";
                                        markerIdInDatabase="";

                                        for (FunMapCategory c : userPlaces.keySet()) {
                                            iteration++;
                                            for (FunMapPlace p: userPlaces.get(c)) {
                                                if (p.getCoordinate().equals(place.getCoordinate())) {
                                                    Log.d("TAK", "TAK");
                                                    placeName.setText(p.getName());
                                                    description.setText(p.getDescription());
                                                    spinner.setSelection(iteration - 2);
                                                    alreadyExists = true;
                                                    previousCategory = c.getId();
                                                    markerIdInDatabase = p.getId();
                                                    Log.d("markerind", markerIdInDatabase);
                                                    break;
                                                }
                                            }
                                        }

                                        if (!alreadyExists) {
                                            placeName.setText("");
                                            description.setText("");
                                            spinner.setSelection(0);
                                        }

                                        okButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //dialog.dismiss();
                                                final String[] strings = new String[8];
                                                strings[0] = new SessionManager(getActivity().getApplicationContext()).getValueOfUserId();
                                                strings[1] = Double.toString(marker1.getPosition().latitude);
                                                strings[2] = Double.toString(marker1.getPosition().longitude);
                                                strings[3] = placeName.getText().toString();
                                                strings[4] = description.getText().toString();
                                                strings[5] = spinnerForUserMenu;
                                                strings[6] = previousCategory;
                                                strings[7] = markerIdInDatabase;

                                                AddOrEditMarkerTask task = new AddOrEditMarkerTask(getActivity().getApplicationContext());
                                                task.execute(strings);
                                                dialog.dismiss();
                                            }
                                        });

                                        declineButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        deleteButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                                alertDialog.setTitle("Confirm Delete");
                                                alertDialog.setMessage("Are you sure you want to delete this point?");
                                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,int which) {

                                                        final String[] strings = new String[1];
                                                        strings[0] = markerIdInDatabase;
                                                        DeleteMarkerTask task = new DeleteMarkerTask(markerIdInDatabase, getActivity().getApplicationContext());
                                                        task.runVolley();
                                                        DownloadMarkersByUser task2 = new DownloadMarkersByUser(getActivity().getApplicationContext(), FragmentFunMap.this, userPlaces);
                                                        task2.runVolley();
                                                        dialog.dismiss();
                                                    }
                                                });

                                                // Setting Negative "NO" Button
                                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Write your code here to invoke NO event
                                                        dialog.dismiss();
                                                    }
                                                });

                                                // Showing Alert Message
                                                alertDialog.show();

                                                dialog.dismiss();
                                            }
                                        });

                                        dialog.show();
                                        return true;
                                    }
                                });


                                //googleMap.setOnMarkerDragListener(this);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        return view;
    }

    void runMarkersTask(String id) {
        markers = new ArrayList<>();
        if (id.equals("0")) {
            DownloadAllMarkers taskDownload = new DownloadAllMarkers(getActivity().getApplicationContext(), this, places);
            taskDownload.execute();
        }
        else {
            DownloadPointsMarkers taskDownload = new DownloadPointsMarkers(this, places);
            String[] strings = new String[1];
            strings[0] = funMapCategoryArrayListNames.get(Integer.parseInt(id));
            taskDownload.execute(strings);
        }
    }

    private class MyNameComp implements Comparator<FunMapCategory>{
        @Override
        public int compare(FunMapCategory o1, FunMapCategory o2) {
            if (o1.getName() == null || o2.getName() == null)
                return 0;
            return o1.getName().compareTo(o2.getName());
        }
    }
}