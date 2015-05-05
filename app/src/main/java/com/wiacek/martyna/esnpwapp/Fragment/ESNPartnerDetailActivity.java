package com.wiacek.martyna.esnpwapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.wiacek.martyna.esnpwapp.R;


/**
 * An activity representing a single ESNPartner detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ESNPartnerListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ESNPartnerDetailFragment}.
 */
public class ESNPartnerDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esnpartner_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putString(ESNPartnerDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ESNPartnerDetailFragment.ARG_ITEM_ID));
            ESNPartnerDetailFragment fragment = new ESNPartnerDetailFragment();
            fragment.setArguments(arguments);
        /*    getSupportFragmentManager().beginTransaction()
                    .add(R.id.esnpartner_detail_container, fragment)
                    .commit();*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ESNPartnerListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
