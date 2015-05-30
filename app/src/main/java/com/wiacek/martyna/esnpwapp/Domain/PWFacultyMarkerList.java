package com.wiacek.martyna.esnpwapp.Domain;

import com.google.android.gms.maps.model.LatLng;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */
public class PWFacultyMarkerList {

    private final ArrayList<FacultyMarker> markers = new ArrayList<>();
    public ArrayList<FacultyMarker> getFacultyMarkers() {
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
        return markers;
    }

    public LatLng getLatLngOfFaculty (String name) {


        switch(name) {
            case "ANS":
                return new LatLng(markers.get(0).getLatitude(), markers.get(0).getLongitude());
            case "ARCH":
                return new LatLng(markers.get(1).getLatitude(), markers.get(1).getLongitude());
            case "CH":
                return new LatLng(markers.get(2).getLatitude(), markers.get(2).getLongitude());
            case "ELKA":
                return new LatLng(markers.get(3).getLatitude(), markers.get(3).getLongitude());
            case "EE":
                return new LatLng(markers.get(4).getLatitude(), markers.get(4).getLongitude());
            case "FIZYKA":
                return new LatLng(markers.get(5).getLatitude(), markers.get(5).getLongitude());
            case "GIK":
                return new LatLng(markers.get(6).getLatitude(), markers.get(6).getLongitude());
            case "ICHIP":
                return new LatLng(markers.get(7).getLatitude(), markers.get(7).getLongitude());
            case "IL":
                return new LatLng(markers.get(8).getLatitude(), markers.get(8).getLongitude());
            case "INMAT":
                return new LatLng(markers.get(9).getLatitude(), markers.get(9).getLongitude());
            case "WIP":
                return new LatLng(markers.get(10).getLatitude(), markers.get(10).getLongitude());
            case "IS":
                return new LatLng(markers.get(11).getLatitude(), markers.get(11).getLongitude());
            case "MINI":
                return new LatLng(markers.get(12).getLatitude(), markers.get(12).getLongitude());
            case "MEIL":
                return new LatLng(markers.get(13).getLatitude(), markers.get(13).getLongitude());
            case "MCHTR":
                return new LatLng(markers.get(14).getLatitude(), markers.get(14).getLongitude());
            case "SIMR":
                return new LatLng(markers.get(15).getLatitude(), markers.get(15).getLongitude());
            case "WT":
                return new LatLng(markers.get(16).getLatitude(), markers.get(16).getLongitude());
            case "WZ":
                return new LatLng(markers.get(17).getLatitude(), markers.get(17).getLongitude());
            case "PLOCK":
                return new LatLng(markers.get(18).getLatitude(), markers.get(18).getLongitude());
            default:
                return new LatLng(0,0);
        }
    }
}
