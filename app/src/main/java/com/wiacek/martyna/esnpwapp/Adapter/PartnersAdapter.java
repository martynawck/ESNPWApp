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

/**
 * Created by Martyna on 2015-03-03.
 */
public class PartnersAdapter extends ArrayAdapter<ESNPartner> {
    Context context;
    int layoutResourceId;
    ArrayList<ESNPartner> data = new ArrayList<>();

    public PartnersAdapter(Context context, int layoutResourceId, ArrayList<ESNPartner> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ESNPartnerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ESNPartnerHolder();
            holder.image = (ImageView)row.findViewById(R.id.image);
            holder.name = (TextView)row.findViewById(R.id.name);

            row.setTag(holder);
        }
        else
        {
            holder = (ESNPartnerHolder)row.getTag();
        }

        ESNPartner partner = data.get(position);
        holder.name.setText(partner.getName());

        ImageView image = (ImageView) row.findViewById(R.id.image);
        Picasso.with(getContext()).load(ServerUrl.BASE_URL + partner.getImage()).into(image);

        return row;
    }

    static class ESNPartnerHolder
    {
        ImageView image;
        TextView name;
    }

}
