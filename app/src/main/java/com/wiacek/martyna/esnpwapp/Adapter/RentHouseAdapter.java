package com.wiacek.martyna.esnpwapp.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wiacek.martyna.esnpwapp.Fragment.RentAHouseInfoFragment;
import com.wiacek.martyna.esnpwapp.Fragment.RentAHouseLinksFragment;

import java.util.Locale;

public class RentHouseAdapter extends FragmentPagerAdapter {

    public RentHouseAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new RentAHouseInfoFragment();
            case 1:
                // Games fragment activity
                return new RentAHouseLinksFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Info".toUpperCase(l);
            case 1:
                return "Links".toUpperCase(l);
        }
        return null;
    }

}

