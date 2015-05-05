package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Adapter.GetInTouchAdapter;
import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

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

public class GetInTouchFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private SearchView searchView;
    private ListView mListView;
    ProgressDialog progressDialog;
    ArrayList<Student> students = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_in_touch, container,
                false);

        students = new ArrayList<>();

        progressDialog = ProgressDialog.show(getActivity(), "","Updating students...", true);
        mListView = (ListView) view.findViewById(R.id.list_view);
        searchView = (SearchView) view.findViewById(R.id.search_view);



        GetStudentsFromServerTask task = new GetStudentsFromServerTask(getActivity().getApplicationContext(),progressDialog);
        task.execute();

        return view;

    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("First name, last name or faculty");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public class GetStudentsFromServerTask extends AsyncTask<String, Void, String> {

        private final ProgressDialog progressDialog;
        private Context mContext;
        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ArrayList<Student> students;
        SessionManager sessionManager;

        public GetStudentsFromServerTask (Context context, ProgressDialog progressDialog) {
            mContext = context;
            this.progressDialog = progressDialog;
        }
        protected String doInBackground(String... urls) {
            try{

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost(ServerUrl.BASE_URL + "students.php");

                sessionManager = new SessionManager(mContext);
                final String login_session = sessionManager.getValueOfUserId();
                nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("id", login_session));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                students = new ArrayList<>();

                if(!response.equalsIgnoreCase("null")){
                    Log.d("RESP","NOT NULL");
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    Log.d("length",Integer.toString(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String post = jsonObject.getString("post");
                        JSONObject postObject = new JSONObject(post);
                        Student student = new Student();
                        String faculty = postObject.getString("faculty");
                        Log.d("FACULTY",faculty);
                        String longFaculty = "";

                        switch(faculty) {
                            case "ANS":
                                longFaculty = "Faculty of Administration and Social Sciences";
                                break;
                            case "ARCH":
                                longFaculty = "Faculty of Architecture";
                                break;
                            case "CH":
                                longFaculty ="Faculty of Chemistry";
                                break;
                            case "ELKA":
                                longFaculty = "Faculty of Electronics and Information Technology";
                                break;
                            case "EE":
                                longFaculty = "Faculty of Electrical Engineering";
                                break;
                            case "FIZYKA":
                                longFaculty = "Faculty of Physics";
                                break;
                            case "GIK":
                                longFaculty = "Faculty of Geodesy and Cartography";
                                break;
                            case "ICHIP":
                                longFaculty = "Faculty of Chemical and Process Engineering";
                                break;
                            case "IL":
                                longFaculty = "Faculty of Civil Engineering";
                                break;
                            case "INMAT":
                                longFaculty = "Faculty of Materials Science and Engineering";
                                break;
                            case "WIP":
                                longFaculty = "Faculty of Production Engineering";
                                break;
                            case "IS":
                                longFaculty = "Faculty of Environmental Engineering";
                                break;
                            case "MINI":
                                longFaculty = "Faculty of Mathematics and Information Science";
                                break;
                            case "MEIL":
                                longFaculty = "Faculty of Power and Aeronautical Engineering";
                                break;
                            case "MCHTR":
                                longFaculty = "Faculty of Mechatronics";
                                break;
                            case "SIMR":
                                longFaculty = "Faculty of Automotive and Construction Machinery Engineering";
                                break;
                            case "WT":
                                longFaculty = "Faculty of Transport";
                                break;
                            case "WZ":
                                longFaculty = "Faculty of Management";
                                break;
                            case "PLOCK":
                                longFaculty = "Faculty of Civil Engineering, Mechanics and Petrochemistry (Plock)";
                                break;
                            case "PLOCK2":
                                longFaculty = "College of Economics and Social Sciences ";
                                break;
                            case "WUTBUIS":
                                longFaculty = "WUT Business School ";
                                break;
                            default:
                                longFaculty = "";
                        }

                        student.setFaculty(longFaculty);
                        student.setFirstname(postObject.getString("first_name"));
                        student.setLastname(postObject.getString("last_name"));
                        student.setImgUrl(postObject.getString("image"));
                        students.add(student);
                    }

                    progressDialog.dismiss();
                    return "0";
                }
            }catch(Exception e){
                progressDialog.dismiss();
                System.out.println("Exception : " + e.getMessage());
            }
            progressDialog.dismiss();
            return "-1";

        }


        protected void onPostExecute(String result) {

            if (!result.equals("-1")) {
                Log.d("HELLOO","HELLOOO2");
                mListView.setAdapter(new GetInTouchAdapter(getActivity().getApplicationContext(), getActivity(),
                        R.layout.listview_item_row_big_img,
                        students));
                mListView.setTextFilterEnabled(true);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // When clicked, show a toast with the TextView text
                        Student student = (Student) parent.getItemAtPosition(position);

                       // android.support.v4.app.FragmentTransaction ft =
                         //       getActivity().getSupportFragmentManager().beginTransaction();
                        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);


                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();



                        StudentProfileFragment fragment = new StudentProfileFragment();

                        Bundle bundles = new Bundle();

// ensure your object has not null
                        if (student != null) {
                            bundles.putString("profile_first_name",student.getFirstname());
                            bundles.putString("profile_last_name",student.getLastname());
                            bundles.putString("profile_faculty",student.getFaculty());
                            bundles.putString("profile_img_url",student.getImgUrl());
                            bundles.putString("profile_facebook_id",student.getFacebook());
                            bundles.putString("profile_phone_no",student.getPhone());
                            bundles.putString("profile_skype_id",student.getSkype());
                            bundles.putString("profile_whatsapp_id",student.getWhatsapp());
                            bundles.putString("profile_email",student.getEmail());
                            Log.e("aDivision", "is valid");
                        } else {
                            Log.e("aDivision", "is null");
                        }
                        fragment.setArguments(bundles);
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragment, "STUDENT_PROFILE").addToBackStack(null).commit();
                     //   ft.replace(android.R.id.content, frag);
                      ///  ft.addToBackStack(null);
                       // ft.commit();
                    }
                });
                setupSearchView();
            }
        }
    }
}
