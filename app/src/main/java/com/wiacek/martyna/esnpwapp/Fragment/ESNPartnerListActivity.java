package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiacek.martyna.esnpwapp.R;


/**
 * An activity representing a list of ESNPartners. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link com.wiacek.martyna.esnpwapp.Fragment.ESNPartnerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ESNPartnerListFragment} and the item details
 * (if present) is a {@link com.wiacek.martyna.esnpwapp.Fragment.ESNPartnerDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ESNPartnerListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ESNPartnerListActivity {
    private boolean mTwoPane;
/*
    @Override
    protected View onCreate(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_esnpartner_list);

        View view = inflater.inflate(R.layout.activity_esnpartner_list, container,
                false);

        if (view.findViewById(R.id.esnpartner_detail_container) != null) {
            mTwoPane = true;
/*
            ((ESNPartnerListFragment) getFragmentManager()
                    .findFragmentById(R.id.esnpartner_list))
                    .setActivateOnItemClick(true);*/

           // ( (ESNPartnerListFragment) getChildFragmentManager().findFragmentById(R.id.esnpartner_list)).setActivateOnItemClick(true);
  //      }

//        return view;


  //  }
/*
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {

            Bundle arguments = new Bundle();
            arguments.putString(ESNPartnerDetailFragment.ARG_ITEM_ID, id);
            ESNPartnerDetailFragment fragment = new ESNPartnerDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.esnpartner_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(getActivity().getApplicationContext(), ESNPartnerDetailActivity.class);
            detailIntent.putExtra(ESNPartnerDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }*/
}
