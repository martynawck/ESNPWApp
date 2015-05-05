package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.R;

public class PartySchedulerFragment extends Fragment {

    ImageView ivIcon;
    TextView tvItemName;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public PartySchedulerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_one, container,
                false);

        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        tvItemName = (TextView) view.findViewById(R.id.frag1_text);

        tvItemName.setText(getArguments().getString(ITEM_NAME));
        return view;
    }

}