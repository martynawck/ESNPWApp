package com.wiacek.martyna.esnpwapp.Fragment;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.AsyncTask.DownloadMentorInfoTask;
import com.wiacek.martyna.esnpwapp.AsyncTask.GetDirectFbIdTask;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class BuddyFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @InjectView(R.id.firstName) TextView fName;
    @InjectView(R.id.lastName) TextView lName;
    @InjectView(R.id.listView1) ListView listView1;
    View view;
    ArrayList<String> buddyData;
    final String NOT_AVAILABLE = "NOT AVAILABLE";
    final String NULL = "null";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_buddy, container, false);
        ButterKnife.inject(this, view);

        buddyData = new ArrayList<>();

        DownloadMentorInfoTask task = new DownloadMentorInfoTask(getActivity().getApplicationContext(),new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<String> strings) {
                buddyData = strings;
                fName.setText(buddyData.get(0));
                lName.setText(buddyData.get(1));

                MentorContact mentor_data[] = new MentorContact[5];
                if (buddyData.get(3).isEmpty() ||
                        buddyData.get(3).equals(NULL))
                    buddyData.set(3, NOT_AVAILABLE);

                if (buddyData.get(2).isEmpty() ||
                        buddyData.get(2).equals(NULL))
                    buddyData.set(2, NOT_AVAILABLE);

                if (buddyData.get(5).isEmpty() ||
                        buddyData.get(5).equals(NULL))
                    buddyData.set(5, NOT_AVAILABLE);

                if (buddyData.get(4).isEmpty() ||
                        buddyData.get(4).equals(NULL))
                    buddyData.set(4, NOT_AVAILABLE);

                if (buddyData.get(6).isEmpty() ||
                        buddyData.get(6).equals(NULL))
                    buddyData.set(6, NOT_AVAILABLE);
                mentor_data[0] = new MentorContact(R.drawable.mobile, buddyData.get(3));
                mentor_data[1] = new MentorContact(R.drawable.mail, buddyData.get(2));
                mentor_data[2] = new MentorContact(R.drawable.fb, buddyData.get(5));
                mentor_data[3] = new MentorContact(R.drawable.skype, buddyData.get(4));
                mentor_data[4] = new MentorContact(R.drawable.whatsapp, buddyData.get(6));

                MentorAdapter adapter = new MentorAdapter(getActivity().getApplicationContext(), R.layout.listview_mentor_item_row, mentor_data);

                BuddyFragment.this.listView1.setAdapter(adapter);


                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        TextView v = (TextView) view.findViewById(R.id.name);
                        String data = v.getText().toString();
                        switch (position) {
                            case 0:
                                if (!data.equals(NOT_AVAILABLE)) {
                                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                                    callIntent.setData(Uri.parse("tel:+" + data.trim()));
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(callIntent);
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your buddy has provided no phone number!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                if (!data.equals(NOT_AVAILABLE)) {
                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", data, null));
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(Intent.createChooser(emailIntent, "Send email to your buddy:"));
                                }
                                else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your buddy has provided no email address!",
                                        Toast.LENGTH_SHORT).show();
                            }
                                break;
                            case 2:
                                if (!data.equals(NOT_AVAILABLE)) {
                                    GetDirectFbIdTask task = new GetDirectFbIdTask(getActivity().getApplicationContext(), getActivity(), data);
                                    task.runVolley();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your buddy has provided no Facebook username!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 3:
                                if (!data.equals(NOT_AVAILABLE)) {
                                    Intent skypeIntent = new Intent(Intent.ACTION_VIEW);
                                    skypeIntent.setData(Uri.parse("skype:" + data.trim() + "?chat"));
                                    skypeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    try {
                                        startActivity(skypeIntent);
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Skype is not installed. Please install Skype!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your buddy has provided no Skype username!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:

                                if (!data.equals(NOT_AVAILABLE)) {
                                    Uri uri = Uri.parse("smsto:" + data.trim());
                                    Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
                                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                                    whatsappIntent.setPackage("com.whatsapp");

                                    try {
                                        startActivity(Intent.createChooser(whatsappIntent, ""));
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Whatsapp is not installed. Please install Whatsapp!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Your buddy has provided no Whatsapp username!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });

            }
        });
        task.execute(new String[]{});
        return view;
    }

}
