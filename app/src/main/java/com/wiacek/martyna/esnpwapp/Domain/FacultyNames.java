package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-20.
 */
public class FacultyNames {

    public String returnLongName(String shortName) {
        switch(shortName) {
            case "ANS":
                return "Faculty of Administration and Social Sciences";
            case "ARCH":
                return "Faculty of Architecture";
            case "CH":
                return "Faculty of Chemistry";
            case "ELKA":
                return "Faculty of Electronics and Information Technology";
            case "EE":
                return "Faculty of Electrical Engineering";
            case "FIZYKA":
                return "Faculty of Physics";
            case "GIK":
                return "Faculty of Geodesy and Cartography";
            case "ICHIP":
                return "Faculty of Chemical and Process Engineering";
            case "IL":
                return "Faculty of Civil Engineering";
            case "INMAT":
                return "Faculty of Materials Science and Engineering";
            case "WIP":
                return "Faculty of Production Engineering";
            case "IS":
                return "Faculty of Environmental Engineering";
            case "MINI":
                return "Faculty of Mathematics and Information Science";
            case "MEIL":
                return "Faculty of Power and Aeronautical Engineering";
            case "MCHTR":
                return "Faculty of Mechatronics";
            case "SIMR":
                return "Faculty of Automotive and Construction Machinery Engineering";
            case "WT":
                return "Faculty of Transport";
            case "WZ":
                return "Faculty of Management";
            case "PLOCK":
                return "Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)";
            default:
                return "";
        }
    }
}
