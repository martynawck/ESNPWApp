package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.wiacek.martyna.esnpwapp.AsyncTask.GetDirectFbIdTask;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentProfileFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private final String NOT_AVAILABLE = "NOT AVAILABLE";
    @InjectView(R.id.firstlastname)
    TextView name;
    @InjectView(R.id.faculty)
    TextView faculty;
    @InjectView(R.id.profilePic)
    ImageView image;
    @InjectView(R.id.listView1)
    ListView listView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_profile, container,
                false);
        ButterKnife.inject(this, view);

        Bundle bundle = getArguments();

        name.setText(bundle.getString("profile_first_name") + " " +bundle.getString("profile_last_name"));
        faculty.setText(bundle.getString("profile_faculty"));

        Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL+bundle.getString("profile_img_url")).into(image);

        MentorContact data[] = new MentorContact[5];
        String NULL = "null";
        if (bundle.getString("profile_phone_no").isEmpty() ||
                bundle.getString("profile_phone_no").equals(NULL) )
            bundle.putString("profile_phone_no", NOT_AVAILABLE);

        if (bundle.getString("profile_email").isEmpty() ||
                bundle.getString("profile_email").equals(NULL))
            bundle.putString("profile_email", NOT_AVAILABLE);

        if (bundle.getString("profile_facebook_id").isEmpty() ||
                bundle.getString("profile_facebook_id").equals(NULL))
            bundle.putString("profile_facebook_id", NOT_AVAILABLE);

        if (bundle.getString("profile_skype_id").isEmpty() ||
                bundle.getString("profile_skype_id").equals(NULL))
            bundle.putString("profile_skype_id", NOT_AVAILABLE);

        if (bundle.getString("profile_whatsapp_id").isEmpty() ||
                bundle.getString("profile_whatsapp_id").equals(NULL))
            bundle.putString("profile_whatsapp_id", NOT_AVAILABLE);


        data[0] = new MentorContact(R.drawable.mobile, bundle.getString("profile_phone_no"));
        data[1] = new MentorContact(R.drawable.mail, bundle.getString("profile_email"));
        data[2] = new MentorContact(R.drawable.fb, bundle.getString("profile_facebook_id"));
        data[3] = new MentorContact(R.drawable.skype, bundle.getString("profile_skype_id"));
        data[4] = new MentorContact(R.drawable.whatsapp, bundle.getString("profile_whatsapp_id"));

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
                        if (!data.equals(NOT_AVAILABLE)) {
                            Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.setData(Uri.parse("tel:+" + data.trim()));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "User has provided no phone number!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (!data.equals(NOT_AVAILABLE)) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", data, null));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Intent.createChooser(emailIntent, "Send email to your buddy:"));
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "User has provided no email address!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (!data.equals(NOT_AVAILABLE)) {
                            GetDirectFbIdTask task = new GetDirectFbIdTask(getActivity().getApplicationContext(), getActivity(), data);
                            task.runVolley();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "User has provided no Facebook username!",
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
                        } else  {
                            Toast.makeText(getActivity().getApplicationContext(), "User has provided no Skype username!",
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
                            Toast.makeText(getActivity().getApplicationContext(), "User has provided no Whatsapp username!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        return view;
    }
}