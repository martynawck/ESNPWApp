package com.wiacek.martyna.esnpwapp.Fragment;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
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

import java.util.List;


public class BuddyFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    String firstname, lastname, email, phone_number, skype_id, facebook_id, whatsapp_id;
    TextView fName, lName;
    ListView listView1;
    View view;
    ESNPWSQLHelper buddySQLHelper;
    SessionManager sessionManager;

    public BuddyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_buddy, container, false);
        fName = (TextView)view.findViewById(R.id.firstName);
        lName = (TextView)view.findViewById(R.id.lastName);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        buddySQLHelper = new ESNPWSQLHelper(getActivity().getApplicationContext());
        listView1 = (ListView) view.findViewById(R.id.listView1);

        DownloadMentorInfoTask task = new DownloadMentorInfoTask(getActivity().getApplicationContext());
        task.execute(new String[]{ServerUrl.BASE_URL + "buddy.php"});
        return view;
    }

    private class DownloadMentorInfoTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private View mView;


        public DownloadMentorInfoTask (Context context){
            mContext = context;
        }

        protected String doInBackground(String... urls) {
            try{

                Buddy buddy = new Buddy();
                buddy = buddySQLHelper.getBuddyInfo(sessionManager.getValueOfUserId());
                //
                // Log.d("BUDDY IMIE", buddy.getFirstname());
                firstname = buddy.getFirstname();
                lastname = buddy.getLastname();
                email = buddy.getEmail();
                phone_number = buddy.getPhone();
                skype_id = buddy.getSkype();
                facebook_id = buddy.getFacebook();
                whatsapp_id = buddy.getWhatsapp();

            }catch(Exception e){
                dialog.dismiss();
                System.out.println("Exception : " + e.getMessage());
            }
            return "-1";
        }

        @Override
        protected void onPostExecute(String result) {

            fName.setText(firstname);
            lName.setText(lastname);

            MentorContact mentor_data[] = new MentorContact[]
                    {
                            new MentorContact(R.drawable.mobile, phone_number),
                            new MentorContact(R.drawable.mail, email),
                            new MentorContact(R.drawable.fb, facebook_id),
                            new MentorContact(R.drawable.skype, skype_id),
                            new MentorContact(R.drawable.whatsapp, whatsapp_id)
                    };

            MentorAdapter adapter = new MentorAdapter(mContext, R.layout.listview_mentor_item_row, mentor_data);


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
                            GetDirectFbIdTask task = new GetDirectFbIdTask(mContext);
                            task.execute(new String[] { data });
                            break;
                        case 3:
                            Intent skypeIntent = new Intent(Intent.ACTION_VIEW);
                            skypeIntent.setData(Uri.parse("skype:" + data.trim()+"?chat"));
                            skypeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //startActivity(skypeIntent);
                            try{
                                startActivity(skypeIntent);
                            }
                            catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(mContext, "Skype is not installed. Please install Skype!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 4:

                            Uri uri = Uri.parse("smsto:" + data.trim());
                            Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                            whatsappIntent.setPackage("com.whatsapp");
                            //whatsappIntent.setType("text/plain");


                            // Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            //sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi this is demo");
                            //sendIntent.setPackage("com.whatsapp");
                            //startActivity(Intent.createChooser(sendIntent, ""));
                            try{
                                startActivity(Intent.createChooser(whatsappIntent, ""));
                            }
                            catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(mContext,"Whatsapp is not installed. Please install Whatsapp!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;

                    }

                }
            });
        }
    }

    private class GetDirectFbIdTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private String ID;

        public GetDirectFbIdTask (Context context) {
            mContext = context;
        }
        protected String doInBackground(String... urls) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://graph.facebook.com/" + urls[0]);
            // replace with your url
            String response = "";
            //HttpResponse response = null;
            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = client.execute(request, responseHandler);
                //response = client.execute(request);
                JSONObject jsonObject = new JSONObject(response);
                ID = jsonObject.getString("id");

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ID;
        }

        protected void onPostExecute(String result) {

            if (ID != null) {

                try {
                    //fb://messaging/
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/"+ID));
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(mContext,"Facebook is not installed. Please install Facebook!",
                            Toast.LENGTH_SHORT).show(); }
            }
        }
    }


}
