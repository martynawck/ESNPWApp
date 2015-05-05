package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class StudentProfileFragment extends Fragment {

  //  ImageView ivIcon;
    Student s;
    MentorContact data[] = null;


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_profile, container,
                false);

        Bundle bundle = getArguments();


     //   ivIcon = (ImageView) view.findViewById(R.id.frag3_icon);
        TextView name = (TextView) view.findViewById(R.id.firstlastname);
        name.setText(bundle.getString("profile_first_name") + " " +bundle.getString("profile_last_name"));
        TextView faculty = (TextView) view.findViewById(R.id.faculty);
        faculty.setText(bundle.getString("profile_faculty"));

        ImageView image = (ImageView) view.findViewById(R.id.profilePic);
        Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL+bundle.getString("profile_img_url")).into(image);

        ListView listView1 = (ListView) view.findViewById(R.id.listView1);

        MentorContact data[] = new MentorContact[]
                {
                        new MentorContact(R.drawable.mobile, "23453"),
                        new MentorContact(R.drawable.mail, "34234234"),
                        new MentorContact(R.drawable.fb, "23123d"),
                        new MentorContact(R.drawable.skype, "3423423$"),
                        new MentorContact(R.drawable.whatsapp, "34324")
                };


        MentorAdapter adapter = new MentorAdapter(getActivity().getApplicationContext(), R.layout.listview_student_item_row, data);

        listView1.setAdapter(adapter);
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
                        GetDirectFbIdTask task = new GetDirectFbIdTask(getActivity().getApplicationContext());
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
                            Toast.makeText(getActivity().getApplicationContext(), "Skype is not installed. Please install Skype!",
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
                            Toast.makeText(getActivity().getApplicationContext(),"Whatsapp is not installed. Please install Whatsapp!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                }

            }
        });
//        ivIcon.setImageDrawable(view.getResources().getDrawable(
        //        getArguments().getInt(IMAGE_RESOURCE_ID)));
        return view;
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