package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.CreateFacultyLinksTask;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadFacultyInfo;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnSingleStringTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FacultyFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private final ProgressDialog dialog = null;
    @InjectView(R.id.polishFacName)
    TextView polishFacName;
    @InjectView(R.id.englishFacName)
    TextView englishFacName;
    @InjectView(R.id.listView1)
    ListView listView1;

    public FacultyFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        ButterKnife.inject(this, view);

        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());

        DownloadFacultyInfo task = new DownloadFacultyInfo(getActivity().getApplicationContext(),  dialog, sessionManager, new OnSingleStringTaskCompleted() {
            @Override
            public void onTaskCompleted(String string) {
                CreateFacultyLinksTask task = new CreateFacultyLinksTask(getActivity().getApplicationContext(), new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(ArrayList<String> strings) {
                        final ArrayList<String> data = strings;
                        polishFacName.setText(data.get(0));
                        englishFacName.setText(data.get(1));
                        MentorContact mentor_data[] = new MentorContact[]
                                {
                                        new MentorContact(R.drawable.website, "Your faculty website"),
                                        new MentorContact(R.drawable.university, "Your grades"),
                                        new MentorContact(R.drawable.mail, "Your faculty e-mail"),
                                };

                        MentorAdapter adapter = new MentorAdapter(getActivity().getApplicationContext(), R.layout.listview_mentor_item_row, mentor_data);

                        listView1.setAdapter(adapter);

                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {

                                switch (position) {
                                    case 0:
                                        Intent intent0 = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(3)));
                                        startActivity(intent0);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(4)));
                                        startActivity(intent1);
                                        break;
                                    case 2:
                                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(2)));
                                        startActivity(intent2);
                                        break;
                                }
                            }
                        });
                    }
                });
                task.execute(string);
            }
        });
        task.execute(ServerUrl.BASE_URL + "faculty.php");
        return view;
    }
}
