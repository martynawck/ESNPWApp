package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Domain.FacultyMarker;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CampusMap extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";


    MapView mMapView;
    private GoogleMap googleMap;
    private ArrayList<FacultyMarker> markers = new ArrayList<>();
    private HashMap<Marker, FacultyMarker> markersHashMap;
    SessionManager sessionManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_campus_map, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        markersHashMap = new HashMap<Marker, FacultyMarker>();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude =  52.220563;
        double longitude = 21.01046;

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = inflater.inflate(R.layout.info_window_layout, null);

                FacultyMarker myMarker = markersHashMap.get(arg0);

                // Getting the position from the marker
                ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

                TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);

                TextView markerDeansOffice = (TextView) v.findViewById(R.id.marker_deans_office);

                TextView markerInternationalOffice = (TextView) v.findViewById(R.id.marker_international_office);

                markerIcon.setImageResource(myMarker.getmIcon());
                markerLabel.setText(myMarker.getmLabel());
                markerDeansOffice.setText(myMarker.getDeansOffice());
                markerInternationalOffice.setText(myMarker.getInternationalOffice());
                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        markers.add(new FacultyMarker("Faculty of Administration and Social Sciences", R.drawable.wains, "bla","bla"  ,52.221626, 21.010014));
        markers.add(new FacultyMarker("Faculty of Architecture",R.drawable.wa, "aaa", "AAA", 52.222118, 21.013060 ));
        markers.add(new FacultyMarker("Faculty of Automotive and Construction Machinery Engineering", R.drawable.wsimr,"aaa", "AAA",52.204237, 21.002284));
        markers.add(new FacultyMarker("Faculty of Chemical and Process Engineering", R.drawable.wicp,"aaa", "AAA",52.213870, 21.015411));
        markers.add(new FacultyMarker("Faculty of Chemistry", R.drawable.wc,"aaa", "AAA",52.221758, 21.009125));
        markers.add(new FacultyMarker("Faculty of Civil Engineering", R.drawable.wil,"aaa", "AAA",52.217770, 21.011749));
        markers.add(new FacultyMarker("Faculty of Electrical Engineering", R.drawable.we,"aaa", "AAA",52.221434, 21.006090));
        markers.add(new FacultyMarker("Faculty of Electronics and Information Technology", R.drawable.weiti,"111, 112", "158, 208",52.218978, 21.011693));
        markers.add(new FacultyMarker("Faculty of Environmental Engineering", R.drawable.wis,"aaa", "AAA",52.220614, 21.007456));
        markers.add(new FacultyMarker("Faculty of Geodesy and Cartography", R.drawable.wgik,"aaa", "AAA",52.220573, 21.009991));
        markers.add(new FacultyMarker("Faculty of Mathematics and Information Science", R.drawable.wmini,"aaa", "AAA",52.222071, 21.006849));
        markers.add(new FacultyMarker("Faculty of Management", R.drawable.wz,"aaa", "AAA",52.203608, 21.002839));
        markers.add(new FacultyMarker("Faculty of Materials Science and Engineering", R.drawable.wim, "aaa", "aaa", 52.201367, 20.999761));
        markers.add(new FacultyMarker("Faculty of Mechatronics", R.drawable.wm, "aaa", "aaa", 52.202972, 21.000815));
        markers.add(new FacultyMarker("Faculty of Physics", R.drawable.wf,"aaa", "AAA",52.221461, 21.007093));
        markers.add(new FacultyMarker("Faculty of Power and Aeronautical Engineering", R.drawable.wmel,"aaa", "AAA",52.221308, 21.005266));
        markers.add(new FacultyMarker("Faculty of Production Engineering", R.drawable.wip,"aaa", "AAA",52.203304, 21.002751));
        markers.add(new FacultyMarker("Faculty of Transport", R.drawable.wt,"aaa", "AAA",52.222412, 21.008008));
        markers.add(new FacultyMarker("Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)", R.drawable.wbmp,"aaa", "AAA",52.561173, 19.678557));
        markers.add(new FacultyMarker("College of Economics and Social Sciences (Plock)", R.drawable.wbmp,"aaa", "AAA",52.561590, 19.678994));
        markers.add(new FacultyMarker("WUT Business School", R.drawable.wwutb,"aaa", "AAA",52.222656, 21.000563));
        markers.add(new FacultyMarker("ESN Office", R.drawable.riv,"aaa", "AAA",52.216345, 21.016234));
        markers.add(new FacultyMarker("International Student Office", R.drawable.wgik,"aaa", "AAA",52.220704, 21.010667));

        plotMarkers(markers);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        return v;
    }

    private void plotMarkers(ArrayList<FacultyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (FacultyMarker myMarker : markers)
            {

                String shortFacName = sessionManager.getValueOfFaculty();

                String longFacName = "";
                switch(shortFacName) {
                    case "ANS":
                        longFacName = "Faculty of Administration and Social Sciences";
                        break;
                    case "ARCH":
                        longFacName = "Faculty of Architecture";
                        break;
                    case "CH":
                        longFacName ="Faculty of Chemistry";
                        break;
                    case "ELKA":
                        longFacName = "Faculty of Electronics and Information Technology";
                        break;
                    case "EE":
                        longFacName = "Faculty of Electrical Engineering";
                        break;
                    case "FIZYKA":
                        longFacName = "Faculty of Physics";
                        break;
                    case "GIK":
                        longFacName = "Faculty of Geodesy and Cartography";
                        break;
                    case "ICHIP":
                        longFacName = "Faculty of Chemical and Process Engineering";
                        break;
                    case "IL":
                        longFacName = "Faculty of Civil Engineering";
                        break;
                    case "INMAT":
                        longFacName = "Faculty of Materials Science and Engineering";
                        break;
                    case "WIP":
                        longFacName = "Faculty of Production Engineering";
                        break;
                    case "IS":
                        longFacName = "Faculty of Environmental Engineering";
                        break;
                    case "MINI":
                        longFacName = "Faculty of Mathematics and Information Science";
                        break;
                    case "MEIL":
                        longFacName = "Faculty of Power and Aeronautical Engineering";
                        break;
                    case "MCHTR":
                        longFacName = "Faculty of Mechatronics";
                        break;
                    case "SIMR":
                        longFacName = "Faculty of Automotive and Construction Machinery Engineering";
                        break;
                    case "WT":
                        longFacName = "Faculty of Transport";
                        break;
                    case "WZ":
                        longFacName = "Faculty of Management";
                        break;
                    case "PLOCK":
                        longFacName = "Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)";
                        break;
                    default:
                        longFacName = "";
                }

                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                if (myMarker.getmLabel() == "ESN Office" || myMarker.getmLabel() == "International Student Office")
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
               // markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.));

                if (myMarker.getmLabel() == longFacName) {
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }

                Marker currentMarker = googleMap.addMarker(markerOption);
                markersHashMap.put(currentMarker, myMarker);

             //   googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
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
