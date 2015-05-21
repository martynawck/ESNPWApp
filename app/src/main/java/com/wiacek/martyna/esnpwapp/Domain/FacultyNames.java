package com.wiacek.martyna.esnpwapp.Domain;

import java.util.ArrayList;

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

    public ArrayList<String> getDetailedData(String name) {

        ArrayList<String> list = new ArrayList<>();
        String polishFacultyName, englishFacultyName, faculty_website, student_grades_website, faculty_mail;
        switch(name) {
            case "ANS":
                polishFacultyName = "Wydział Administracji i Nauk Społecznych";
                englishFacultyName = "Faculty of Administration and Social Sciences";
                faculty_website = "http://www.ans.pw.edu.pl";
                student_grades_website = "http://ans.pw.edu.pl/e-dziekanat/login.php";
                faculty_mail = "https://ans.pw.edu.pl:20000/cgi-bin/openwebmail/openwebmail.pl";
                break;
            case "ARCH":
                polishFacultyName = "Wydział Architektury";
                englishFacultyName = "Faculty of Architecture";
                faculty_website = "http://www.arch.pw.edu.pl";
                student_grades_website = "http://arch.pw.edu.pl/wsod";
                faculty_mail = "http://arch.pw.edu.pl/webmail";
                break;
            case "CH":
                polishFacultyName = "Wydział Chemiczny";
                englishFacultyName ="Faculty of Chemistry";
                faculty_website = "http://www.ch.pw.edu.pl";
                student_grades_website = "https://dziekanat.ch.pw.edu.pl/mel-stud-app/ledge/view/stud.StartPage";
                faculty_mail = "http://webmail.ch.pw.edu.pl/";
                break;
            case "ELKA":
                polishFacultyName = "Wydział Elektroniki i Technik Informacyjnych";
                englishFacultyName = "Faculty of Electronics and Information Technology";
                faculty_website = "http://www.elka.pw.edu.pl";
                student_grades_website = "https://studia.elka.pw.edu.pl/";
                faculty_mail = "http://medusa.elka.pw.edu.pl";
                break;
            case "EE":
                polishFacultyName = "Wydział Elektryczny";
                englishFacultyName = "Faculty of Electrical Engineering";
                faculty_website = "http://www.ee.pw.edu.pl";
                student_grades_website = "https://isod.ee.pw.edu.pl/";
                faculty_mail = "https://fe.ee.pw.edu.pl/iwc_static/layout/login.html";
                break;
            case "FIZYKA":
                polishFacultyName = "Wydział Fizyki";
                englishFacultyName = "Faculty of Physics";
                faculty_website = "http://www.fizyka.pw.edu.pl";
                student_grades_website = "https://sos.fizyka.pw.edu.pl/WebSOD/Logowanie.aspx?ReturnUrl=/WebSOD/default.aspx";
                faculty_mail = "https://student.if.pw.edu.pl/poczta/";
                break;
            case "GIK":
                polishFacultyName = "Wydział Geodezji i Kartografii";
                englishFacultyName = "Faculty of Geodesy and Cartography";
                faculty_website = "http://www.gik.pw.edu.pl";
                student_grades_website = "https://dziekanat.gik.pw.edu.pl/mel-stud-app/ledge/view/stud.StartPage";
                faculty_mail = "https://webmail.stud.pw.edu.pl/webmail/";
                break;
            case "ICHIP":
                polishFacultyName = "Wydział Inżynierii Chemicznej i Procesowej";
                englishFacultyName = "Faculty of Chemical and Process Engineering";
                faculty_website = "http://www.ichip.pw.edu.pl";
                student_grades_website = "https://dziekanat.ichip.pw.edu.pl/";
                faculty_mail = "https://webmail.stud.pw.edu.pl/webmail/";
                break;
            case "IL":
                polishFacultyName = "Wydział Inżynierii Lądowej";
                englishFacultyName = "Faculty of Civil Engineering";
                faculty_website = "http://www.il.pw.edu.pl";
                student_grades_website = "https://dziekanat.il.pw.edu.pl/Default.aspx";
                faculty_mail = "https://poczta.il.pw.edu.pl/";
                break;
            case "INMAT":
                polishFacultyName = "Wydział Inżynierii Materiałowej";
                englishFacultyName = "Faculty of Materials Science and Engineering";
                faculty_website = "http://www.inmat.pw.edu.pl";
                student_grades_website = "https://dziekanat.inmat.pw.edu.pl/mel-stud-app/ledge/view/stud.StartPage";
                faculty_mail = "https://webmail.coi.pw.edu.pl/iwc_static/layout/login.html";
                break;
            case "WIP":
                polishFacultyName = "Wydział Inżynierii Produkcji";
                englishFacultyName = "Faculty of Production Engineering";
                faculty_website = "http://www.wip.pw.edu.p";
                student_grades_website = "https://isod.wip.pw.edu.pl/";
                faculty_mail = "https://webmail.wip.pw.edu.pl/?_task=mail";
                break;
            case "IS":
                polishFacultyName = "Wydział Inżynierii Środowiska";
                englishFacultyName = "Faculty of Environmental Engineering";
                faculty_website = "http://www.is.pw.edu.pl";
                student_grades_website = "https://edziekanat.is.pw.edu.pl/";
                faculty_mail = "https://www.is.pw.edu.pl/horde/imp/";
                break;
            case "MINI":
                polishFacultyName = "Wydział Matematyki i Nauk Informacyjnych";
                englishFacultyName = "Faculty of Mathematics and Information Science";
                faculty_website = "http://www.mini.pw.edu.pl";
                student_grades_website = "https://usosweb.usos.pw.edu.pl/kontroler.php";
                faculty_mail = "https://beta.mini.pw.edu.pl/horde/login.php";
                break;
            case "MEIL":
                polishFacultyName = "Wydział Mechaniczny Energetyki i Lotnictwa";
                englishFacultyName = "Faculty of Power and Aeronautical Engineering";
                faculty_website = "http://www.meil.pw.edu.pl";
                student_grades_website = "https://dziekanat.meil.pw.edu.pl/mel-stud-app/ledge/view/ImapLogin";
                faculty_mail = "https://poczta.meil.pw.edu.pl/";
                break;
            case "MCHTR":
                polishFacultyName = "Wydział Mechatroniki";
                englishFacultyName = "Faculty of Mechatronics";
                faculty_website = "http://www.mchtr.pw.edu.pl";
                student_grades_website = "http://dziekanat.mchtr.pw.edu.pl/default.aspx";
                faculty_mail = "https://mail.mchtr.pw.edu.pl/";
                break;
            case "SIMR":
                polishFacultyName = "Wydział Samochodów i Maszyn Roboczych";
                englishFacultyName = "Faculty of Automotive and Construction Machinery Engineering";
                faculty_website = "http://www.simr.pw.edu.pl";
                student_grades_website = "https://www.simr.pw.edu.pl";
                faculty_mail = "https://www.simr.pw.edu.pl";
                break;
            case "WT":
                polishFacultyName = "Wydział Transportu";
                englishFacultyName = "Faculty of Transport";
                faculty_website = "http://www.wt.pw.edu.pl";
                student_grades_website = "https://dziekanat.wt.pw.edu.pl/mel-stud-app/ledge/view/stud.StartPage";
                faculty_mail = "http://www2.wt.pw.edu.pl/webmail/";
                break;
            case "WZ":
                polishFacultyName = "Wydział Zarządzania";
                englishFacultyName = "Faculty of Management";
                faculty_website = "http://www.wz.pw.edu.pl";
                student_grades_website = "http://dziekanat.wz.pw.edu.pl/";
                faculty_mail = "https://webmail.coi.pw.edu.pl/iwc_static/layout/login.html";
                break;
            case "PLOCK":
                polishFacultyName = "Wydział Budownictwa, Mechaniki i Petrochemii";
                englishFacultyName = "Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)";
                faculty_website = "http://www.pw.plock.pl";
                student_grades_website = "https://usosweb.usos.pw.edu.pl/kontroler.php";
                faculty_mail = "https://usosweb.usos.pw.edu.pl/kontroler.php";
                break;
            case "PLOCK2":
                polishFacultyName = "Kolegium Nauk Ekonomicznych i Społecznych";
                englishFacultyName = "College of Economics and Social Sciences ";
                faculty_website = "http://www.pw.plock.pl";
                student_grades_website = "https://usosweb.usos.pw.edu.pl/kontroler.php";
                faculty_mail = "https://usosweb.usos.pw.edu.pl/kontroler.php";
                break;
            case "WUTBUIS":
                polishFacultyName = "Szkoła Biznesu Politechniki Warszawskiej";
                englishFacultyName = "WUT Business School ";
                faculty_website = "http://www.biznes.edu.pl/";
                student_grades_website = "https://moodle.biznes.edu.pl/login/index.php";
                faculty_mail = "https://moodle.biznes.edu.pl/login/index.php";
                break;
            default:
                polishFacultyName = "";
                englishFacultyName = "";
                faculty_mail = "";
                faculty_website = "";
                student_grades_website = "";
        }
        list.add(polishFacultyName);
        list.add(englishFacultyName);
        list.add(faculty_mail);
        list.add(faculty_website);
        list.add(student_grades_website);

        return list;
    }
}
