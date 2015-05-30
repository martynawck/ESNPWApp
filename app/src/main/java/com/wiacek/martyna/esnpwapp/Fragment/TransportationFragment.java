package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiacek.martyna.esnpwapp.Adapter.TransportationAdapter;
import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransportationFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    @InjectView(R.id.pager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container,
                false);
        ButterKnife.inject(this, view);
        TransportationAdapter mSectionsPagerAdapter = new TransportationAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }
}