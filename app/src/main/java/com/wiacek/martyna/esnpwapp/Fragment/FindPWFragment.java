package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FindPWFragment extends Fragment {

    @InjectView(R.id.facebookButton)
    ImageButton facebook;
    @InjectView(R.id.twitterButton)
    ImageButton twitter;
    @InjectView(R.id.linkedinButton)
    ImageButton linkedin;
    @InjectView(R.id.instagramButton)
    ImageButton instagram;
    @InjectView(R.id.pinterestButton)
    ImageButton pinterest;
    @InjectView(R.id.wordpressButton)
    ImageButton wordpress;


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public FindPWFragment() {    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_pw, container, false);
        ButterKnife.inject(this, rootView);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/267478455347"));
                    startActivity(intent);
                } catch(ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.facebook.com/politechnika.warszawska"));
                    startActivity(intent);
                }
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=PW_edu"));
                    startActivity(intent);
                }catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.twitter.com/PW_edu"));
                    startActivity(intent);
                }
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("http://instagram.com/_u/politechnika_warszawska");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.instagram.com/politechnika_warszawska"));
                    startActivity(intent);
                }
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("linkedin://profile/politechnika-warszawska-15980"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://pl.linkedin.com/edu/politechnika-warszawska-15980"));
                    startActivity(intent);
                }
            }
        });

        pinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http:///www.pinterest.com/PW_edu"));
                startActivity(intent);
            }
        });

        wordpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.placpolitechniki1.wordpress.com"));
                startActivity(intent);
            }
        });

        return rootView;
    }
}
