package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HelpFragment extends Fragment {

    @InjectView(R.id.button) ImageButton button;
    @InjectView(R.id.description) TextView description;
    @InjectView(R.id.email)TextView email;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private final String ESN_MAIL = "pw@esn.pl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container,
                false);
        ButterKnife.inject(this, view);

        description.setText("If you have any problems or enquiries please do contact us by visiting ESN PW Office during the office hours or via our" +
                "Facebook profile!");

        email.setText("In special cases contact us via e-mail using the envelope icon below! :)");

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