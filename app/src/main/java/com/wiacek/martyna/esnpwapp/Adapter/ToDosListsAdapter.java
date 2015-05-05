package com.wiacek.martyna.esnpwapp.Adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wiacek.martyna.esnpwapp.Fragment.ToDoAfterFragment;
import com.wiacek.martyna.esnpwapp.Fragment.ToDoBeforeFragment;

import java.util.Locale;

public class ToDosListsAdapter extends FragmentPagerAdapter {

    public ToDosListsAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new ToDoAfterFragment();
            case 1:
                // Games fragment activity
                return new ToDoBeforeFragment();
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
                return "After arrival".toUpperCase(l);
            case 1:
                return "Before leaving".toUpperCase(l);
        }
        return null;
    }

}

