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
                return new ToDoAfterFragment();
            case 1:
                return new ToDoBeforeFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
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

