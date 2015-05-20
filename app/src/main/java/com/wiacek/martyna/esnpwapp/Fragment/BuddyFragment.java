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


public class BuddyFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ProgressDialog dialog = null;
    TextView fName, lName;
    ListView listView1;
    View view;
    ArrayList<String> buddyData;

   // OnTaskCompleted listener;

    public BuddyFragment() {}


  // public void onTaskCompleted() { Log.d("lalala, "AAAA"");}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_buddy, container, false);
        fName = (TextView)view.findViewById(R.id.firstName);
        lName = (TextView)view.findViewById(R.id.lastName);
        listView1 = (ListView) view.findViewById(R.id.listView1);

        buddyData = new ArrayList<>();

        DownloadMentorInfoTask task = new DownloadMentorInfoTask(getActivity().getApplicationContext(),new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<String> strings) {
                buddyData = strings;
                Log.d("LALALA","LALALAL");
                fName.setText(buddyData.get(0));
                lName.setText(buddyData.get(1));

                MentorContact mentor_data[] = new MentorContact[]
                        {
                                new MentorContact(R.drawable.mobile, buddyData.get(3)),
                                new MentorContact(R.drawable.mail, buddyData.get(2)),
                                new MentorContact(R.drawable.fb, buddyData.get(4)),
                                new MentorContact(R.drawable.skype, buddyData.get(5)),
                                new MentorContact(R.drawable.whatsapp, buddyData.get(6))
                        };

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
                                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                                callIntent.setData(Uri.parse("tel:+" + data.trim()));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(callIntent);
                                break;
                            case 1:
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",data, null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(emailIntent, "Send email to your buddy:"));
                                break;
                            case 2:
                                GetDirectFbIdTask task = new GetDirectFbIdTask(getActivity().getApplicationContext(), getActivity());
                                task.execute(new String[] { data });
                                break;
                            case 3:
                                Intent skypeIntent = new Intent(Intent.ACTION_VIEW);
                                skypeIntent.setData(Uri.parse("skype:" + data.trim()+"?chat"));
                                skypeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                try{
                                    startActivity(skypeIntent);
                                }
                                catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Skype is not installed. Please install Skype!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:

                                Uri uri = Uri.parse("smsto:" + data.trim());
                                Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
                                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                                whatsappIntent.setPackage("com.whatsapp");

                                try{
                                    startActivity(Intent.createChooser(whatsappIntent, ""));
                                }
                                catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getActivity().getApplicationContext(),"Whatsapp is not installed. Please install Whatsapp!",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });

            }
        });
        task.execute(new String[]{ServerUrl.BASE_URL + "buddy.php"});
        return view;
    }

}
