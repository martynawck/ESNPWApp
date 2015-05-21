package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.R;

public class HelpFragment extends Fragment {

    ImageButton button;
    TextView description;
    TextView email;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private final String ESN_MAIL = "pw@esn.pl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container,
                false);

        description = (TextView) view.findViewById(R.id.description);
        description.setText("If you have any problems or enquiries please do contact us by visiting ESN PW Office during the office hours or via our" +
                "Facebook profile!");

        email = (TextView) view.findViewById(R.id.email);
        email.setText("In special cases contact us via e-mail using the envelope icon below! :)");

        button = (ImageButton) view.findViewById(R.id.button);
        button.setImageResource(R.drawable.envel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", ESN_MAIL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(emailIntent, "Send email to ESN PW:"));
            }
        });

        return view;
    }

}