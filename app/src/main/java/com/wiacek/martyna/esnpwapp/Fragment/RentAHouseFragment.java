package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiacek.martyna.esnpwapp.Adapter.RentHouseAdapter;
import com.wiacek.martyna.esnpwapp.R;

public class RentAHouseFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container,
                false);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        RentHouseAdapter mSectionsPagerAdapter = new RentHouseAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }

}