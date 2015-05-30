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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wiacek.martyna.esnpwapp.Adapter.GetInTouchAdapter;
import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetStudentDetails;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetStudentsFromServerTask;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.FacultyNames;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.Interface.OnStudentsListTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
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
import java.util.TreeMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GetInTouchFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @InjectView(R.id.search_view)SearchView searchView;
    @InjectView(R.id.list_view) ListView mListView;
    ProgressDialog progressDialog;
    ArrayList<Student> students = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_in_touch, container,
                false);
        ButterKnife.inject(this, view);

        students = new ArrayList<>();
        progressDialog = ProgressDialog.show(getActivity(), "","Updating students...", true);

        GetStudentsFromServerTask task = new GetStudentsFromServerTask(getActivity().getApplicationContext(),progressDialog, new OnStudentsListTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Student> strings) {
                students = strings;
                mListView.setAdapter(new GetInTouchAdapter(getActivity().getApplicationContext(), getActivity(),
                        R.layout.listview_item_row_big_img,
                        students));
                mListView.setTextFilterEnabled(true);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        final Student student = (Student) parent.getItemAtPosition(position);
                        GetStudentDetails task = new GetStudentDetails(student.getId(), getActivity().getApplicationContext(), student, new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(ArrayList<String> strings) {
                                ArrayList<String>details = strings;
                                StudentProfileFragment fragment = new StudentProfileFragment();
                                Bundle bundles = new Bundle();
                                if (student != null) {
                                    bundles.putString("profile_first_name",student.getFirstname());
                                    bundles.putString("profile_last_name",student.getLastname());
                                    bundles.putString("profile_faculty",student.getFaculty());
                                    bundles.putString("profile_img_url",student.getImgUrl());
                                    bundles.putString("profile_facebook_id",details.get(3));
                                    bundles.putString("profile_phone_no",details.get(1));
                                    bundles.putString("profile_skype_id",details.get(2));
                                    bundles.putString("profile_whatsapp_id", details.get(4));
                                    bundles.putString("profile_email",details.get(0));
                                }

                                fragment.setArguments(bundles);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, fragment, "STUDENT_PROFILE").addToBackStack(null).commit();
                            }
                        });

                        task.runVolley();
                    }
                });
                setupSearchView();
            }
        });
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






}
