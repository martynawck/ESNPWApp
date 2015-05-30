package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.wiacek.martyna.esnpwapp.Adapter.GetInTouchAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetStudentDetails;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetStudentsFromServerTask;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Interface.OnStudentsListTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GetInTouchFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @InjectView(R.id.search_view)
    SearchView searchView;
    @InjectView(R.id.list_view)
    ListView mListView;
    private ArrayList<Student> students = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_in_touch, container,
                false);
        ButterKnife.inject(this, view);

        students = new ArrayList<>();
        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Updating students...", true);

        GetStudentsFromServerTask task = new GetStudentsFromServerTask(getActivity().getApplicationContext(), progressDialog, new OnStudentsListTaskCompleted() {
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
                                StudentProfileFragment fragment = new StudentProfileFragment();
                                Bundle bundles = new Bundle();
                                bundles.putString("profile_first_name",student.getFirstname());
                                bundles.putString("profile_last_name",student.getLastname());
                                bundles.putString("profile_faculty",student.getFaculty());
                                bundles.putString("profile_img_url",student.getImgUrl());
                                bundles.putString("profile_facebook_id",strings.get(3));
                                bundles.putString("profile_phone_no",strings.get(1));
                                bundles.putString("profile_skype_id",strings.get(2));
                                bundles.putString("profile_whatsapp_id", strings.get(4));
                                bundles.putString("profile_email",strings.get(0));

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
            mListView.setFilterText(newText);
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }






}
