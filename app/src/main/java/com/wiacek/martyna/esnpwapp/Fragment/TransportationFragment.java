package com.wiacek.martyna.esnpwapp.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.JustifiedSpan;
import com.bluejamesbond.text.style.TextAlignment;
import com.wiacek.martyna.esnpwapp.Adapter.ToDosListsAdapter;
import com.wiacek.martyna.esnpwapp.Adapter.TransportationAdapter;
import com.wiacek.martyna.esnpwapp.Domain.RawFileReader;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.ViewEnhancements.ArticleBuilder;
import com.wiacek.martyna.esnpwapp.ViewEnhancements.MyLeadingMarginSpan2;

public class TransportationFragment extends Fragment {

    TransportationAdapter mSectionsPagerAdapter;
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container,
                false);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mSectionsPagerAdapter = new TransportationAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }
}