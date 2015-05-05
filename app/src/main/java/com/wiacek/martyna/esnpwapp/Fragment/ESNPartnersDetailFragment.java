package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.R;

public class ESNPartnersDetailFragment extends Fragment {

    // ivIcon;
    TextView tvItemName;
    TextView tvItemName2;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public ESNPartnersDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_esn_partners_detail, container,
                false);

        tvItemName = (TextView) view.findViewById(R.id.name);
        tvItemName2 = (TextView) view.findViewById(R.id.lastname);

        tvItemName.setText(getArguments().getString(ITEM_NAME));
        tvItemName2.setText(getArguments().getString(ITEM_NAME));

        return view;
    }

}