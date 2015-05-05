package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {

    SessionManager sessionManager;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    // String faculty;
    TextView polishFacName, englishFacName;
    ListView listView1;
    View view;

    public FacultyFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_faculty, container, false);
        polishFacName = (TextView) view.findViewById(R.id.polishFacName);
        englishFacName = (TextView) view.findViewById(R.id.englishFacName);
        listView1 = (ListView)view.findViewById(R.id.listView1);
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        DownloadFacultyInfo task = new DownloadFacultyInfo(getActivity().getApplicationContext());
        task.execute(new String[] { ServerUrl.BASE_URL + "faculty.php" });
        return view;
    }

    private class DownloadFacultyInfo extends AsyncTask<String, Void, String> {

        private Context mContext;

        public DownloadFacultyInfo (Context context){
            mContext = context;
        }

        protected String doInBackground(String... urls) {
            try{
                return sessionManager.getValueOfFaculty();

            }catch(Exception e){
                dialog.dismiss();
                System.out.println("Exception : " + e.getMessage());
            }
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {

            CreateLinksTask task = new CreateLinksTask(mContext);
            task.execute(new String[] { result });
        }
    }

    private class CreateLinksTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        String faculty_website, student_grades_website, faculty_mail;
        String polishFacultyName, englishFacultyName;

        public CreateLinksTask (Context context) {
            mContext = context;
        }

        protected String doInBackground(String... urls) {

            switch(urls[0]) {
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

            Log.d("LALA", polishFacultyName);

            return "0";
        }

        protected void onPostExecute(String result) {

            polishFacName.setText(polishFacultyName);
            englishFacName.setText(englishFacultyName);
            MentorContact mentor_data[] = new MentorContact[]
                    {
                            new MentorContact(R.drawable.website, "Your faculty website"),
                            new MentorContact(R.drawable.university, "Your grades"),
                            new MentorContact(R.drawable.mail, "Your faculty e-mail"),
                    };

            MentorAdapter adapter = new MentorAdapter(mContext, R.layout.listview_mentor_item_row, mentor_data);
            Log.d("TTT","tutaj");
            Log.d("TTT@",polishFacultyName);


            listView1.setAdapter(adapter);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    switch (position) {
                        case 0:
                            Intent intent0 = new Intent(Intent.ACTION_VIEW, Uri.parse(faculty_website));
                            startActivity(intent0);
                            break;
                        case 1:
                            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(student_grades_website));
                            startActivity(intent1);
                            break;
                        case 2:
                            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(faculty_mail));
                            startActivity(intent2);
                            break;

                    }
                }
            });
        }
    }


}
