package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Martyna on 2015-03-03.
 */
class PartnersAdapter extends ArrayAdapter<ESNPartner> {
    private final int layoutResourceId;
    private ArrayList<ESNPartner> data = new ArrayList<>();

    public PartnersAdapter(Context context, int layoutResourceId, ArrayList<ESNPartner> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ESNPartnerHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ESNPartnerHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ESNPartnerHolder)row.getTag();
        }

        ESNPartner partner = data.get(position);
        holder.name.setText(partner.getName());
        Picasso.with(getContext()).load(ServerUrl.BASE_URL + partner.getImage()).into(holder.image);

        return row;
    }

    static class ESNPartnerHolder
    {
        @InjectView(R.id.image) ImageView image;
        @InjectView(R.id.name) TextView name;

        public ESNPartnerHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
