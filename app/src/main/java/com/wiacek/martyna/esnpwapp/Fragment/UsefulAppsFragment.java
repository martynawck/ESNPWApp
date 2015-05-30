package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wiacek.martyna.esnpwapp.Adapter.UsefulAppAdapter;
import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UsefulAppsFragment extends Fragment {

    @InjectView(R.id.gridView)
    GridView gridView;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public UsefulAppsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_useful_apps, container,
                false);

        ButterKnife.inject(this, view);
        Context mContext = getActivity().getApplicationContext();

        UsefulAppsFragment.this.gridView.setAdapter(new UsefulAppAdapter(mContext));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                final String appPackageName = getPackageName(position);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        return view;
    }

    String getPackageName (int position) {

        String s = "";

        switch (position) {
            case 0:
                s = "com.citynav.jakdojade.pl.android";
                break;
            case 1:
                s = "com.ubercab";
                break;
            case 2:
                s = "com.geckolab.eotaxi.passenger";
                break;
            case 3:
                s = "pl.gdzieturilo.gdzieturiloapp";
                break;
            case 4:
                s = "com.tranzmate";
                break;
            case 5:
                s = "pl.mobilet.app";
                break;
            case 6:
                s = "pl.wtopolski.android.warsawbikepath";
                break;
            case 7:
                s = "pitupitu.pl";
                break;
            case 8:
                s = "com.babbel.mobile.android.pl";
                break;
            case 9:
                s = "app.alkomapa";
                break;
            case 10:
                s = "com.guidewithme.poland";
                break;
            case 11:
                s = "pl.edu.pw.pwwifi";
                break;
            case 12:
                s = "com.devineduck.kantor";
                break;
            case 13:
                s = "hu.pocketguide.bundle.Warsaw_lite";
                break;
            case 14:
                 s = "com.triposo.droidguide.warsaw";
                break;
        }
        return s;
    }

}