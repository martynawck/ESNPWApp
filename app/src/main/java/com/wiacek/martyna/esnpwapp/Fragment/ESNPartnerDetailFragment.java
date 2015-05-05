package com.wiacek.martyna.esnpwapp.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.dummy.ESNPartnerContent;


public class ESNPartnerDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ESNPartnerContent.EsnPartner mItem;

    public ESNPartnerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = ESNPartnerContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_esnpartner_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.esnpartner_detail)).setText(mItem.name);
        }

        return rootView;
    }
}
