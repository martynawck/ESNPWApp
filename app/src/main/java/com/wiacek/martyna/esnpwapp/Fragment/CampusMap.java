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

import com.google.android.gms.maps.CameraUpdate;
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

        markers.add(new FacultyMarker("Faculty of Administration and Social Sciences", R.drawable.wains, "207","207"  ,52.221626, 21.010014));
        markers.add(new FacultyMarker("Faculty of Architecture",R.drawable.wa, "7, 21", "21", 52.222118, 21.013060 ));
        markers.add(new FacultyMarker("Faculty of Automotive and Construction Machinery Engineering", R.drawable.wsimr,"0.6", "4.7B",52.204237, 21.002284));
        markers.add(new FacultyMarker("Faculty of Chemical and Process Engineering", R.drawable.wicp,"178, 179", "516",52.213870, 21.015411));
        markers.add(new FacultyMarker("Faculty of Chemistry", R.drawable.wc,"100", "102",52.221758, 21.009125));
        markers.add(new FacultyMarker("Faculty of Civil Engineering", R.drawable.wil,"114", "114",52.217770, 21.011749));
        markers.add(new FacultyMarker("Faculty of Electrical Engineering", R.drawable.we,"132, 133", "216",52.221434, 21.006090));
        markers.add(new FacultyMarker("Faculty of Electronics and Information Technology", R.drawable.weiti,"111, 112", "158, 208",52.218978, 21.011693));
        markers.add(new FacultyMarker("Faculty of Environmental Engineering", R.drawable.wis,"110, 136", "136",52.220614, 21.007456));
        markers.add(new FacultyMarker("Faculty of Geodesy and Cartography", R.drawable.wgik,"128", "428",52.220573, 21.009991));
        markers.add(new FacultyMarker("Faculty of Mathematics and Information Science", R.drawable.wmini,"027", "515",52.222071, 21.006849));
        markers.add(new FacultyMarker("Faculty of Management", R.drawable.wz,"104, 43", "43",52.203608, 21.002839));
        markers.add(new FacultyMarker("Faculty of Materials Science and Engineering", R.drawable.wim, "204", "204", 52.201367, 20.999761));
        markers.add(new FacultyMarker("Faculty of Mechatronics", R.drawable.wm, "122", "507a", 52.202972, 21.000815));
        markers.add(new FacultyMarker("Faculty of Physics", R.drawable.wf,"130", "310",52.221461, 21.007093));
        markers.add(new FacultyMarker("Faculty of Power and Aeronautical Engineering", R.drawable.wmel,"125", "125",52.221308, 21.005266));
        markers.add(new FacultyMarker("Faculty of Production Engineering", R.drawable.wip,"116", "125 NT",52.203304, 21.002751));
        markers.add(new FacultyMarker("Faculty of Transport", R.drawable.wt,"112", "111",52.222412, 21.008008));
        markers.add(new FacultyMarker("Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)", R.drawable.wbmp,"215", "",52.561173, 19.678557));
        markers.add(new FacultyMarker("College of Economics and Social Sciences (Plock)", R.drawable.wbmp,"103", "",52.561590, 19.678994));
        markers.add(new FacultyMarker("WUT Business School", R.drawable.wwutb,"-", "-",52.222656, 21.000563));
        markers.add(new FacultyMarker("ESN Office", R.drawable.riv,"-", "A104",52.216345, 21.016234));
        markers.add(new FacultyMarker("International Student Office", R.drawable.wgik,"-", "233, 234",52.220704, 21.010667));

        plotMarkers(markers);

        /*
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
*/

        LatLng coordinate= null;

        switch(sessionManager.getValueOfFaculty()) {
            case "ANS":
                coordinate = new LatLng(markers.get(0).getLatitude(), markers.get(0).getLongitude());
                break;
            case "ARCH":
                coordinate = new LatLng(markers.get(1).getLatitude(), markers.get(1).getLongitude());
                break;
            case "CH":
                coordinate = new LatLng(markers.get(2).getLatitude(), markers.get(2).getLongitude());
                break;
            case "ELKA":
                coordinate = new LatLng(markers.get(3).getLatitude(), markers.get(3).getLongitude());
                break;
            case "EE":
                coordinate = new LatLng(markers.get(4).getLatitude(), markers.get(4).getLongitude());
                break;
            case "FIZYKA":
                coordinate = new LatLng(markers.get(5).getLatitude(), markers.get(5).getLongitude());
                break;
            case "GIK":
                coordinate = new LatLng(markers.get(6).getLatitude(), markers.get(6).getLongitude());
                break;
            case "ICHIP":
                coordinate = new LatLng(markers.get(7).getLatitude(), markers.get(7).getLongitude());
                break;
            case "IL":
                coordinate = new LatLng(markers.get(8).getLatitude(), markers.get(8).getLongitude());
                break;
            case "INMAT":
                coordinate = new LatLng(markers.get(9).getLatitude(), markers.get(9).getLongitude());
                break;
            case "WIP":
                coordinate = new LatLng(markers.get(10).getLatitude(), markers.get(10).getLongitude());
                break;
            case "IS":
                coordinate = new LatLng(markers.get(11).getLatitude(), markers.get(11).getLongitude());
                break;
            case "MINI":
                coordinate = new LatLng(markers.get(12).getLatitude(), markers.get(12).getLongitude());
                break;
            case "MEIL":
                coordinate = new LatLng(markers.get(13).getLatitude(), markers.get(13).getLongitude());
                break;
            case "MCHTR":
                coordinate = new LatLng(markers.get(14).getLatitude(), markers.get(14).getLongitude());
                break;
            case "SIMR":
                coordinate = new LatLng(markers.get(15).getLatitude(), markers.get(15).getLongitude());
                break;
            case "WT":
                coordinate = new LatLng(markers.get(16).getLatitude(), markers.get(16).getLongitude());
                break;
            case "WZ":
                coordinate = new LatLng(markers.get(17).getLatitude(), markers.get(17).getLongitude());
                break;
            case "PLOCK":
                coordinate = new LatLng(markers.get(18).getLatitude(), markers.get(18).getLongitude());
                break;
            default:
                coordinate = new LatLng(0,0);
        }


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
