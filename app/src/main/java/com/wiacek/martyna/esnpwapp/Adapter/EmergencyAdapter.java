package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.Domain.EmergencyContact;
import com.wiacek.martyna.esnpwapp.R;

/**
 * Created by Martyna on 2015-03-03.
 */
public class EmergencyAdapter extends ArrayAdapter<EmergencyContact> {
    Context context;
    int layoutResourceId;
    EmergencyContact data[] = null;

    public EmergencyAdapter(Context context, int layoutResourceId, EmergencyContact[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EmergencyHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EmergencyHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.profilePic);
            holder.txtTitle = (TextView)row.findViewById(R.id.name);
            holder.phoneNumber = (TextView) row.findViewById(R.id.faculty);

            row.setTag(holder);
        }
        else
        {
            holder = (EmergencyHolder)row.getTag();
        }

        EmergencyContact emergency = data[position];
        holder.txtTitle.setText(emergency.name);
        holder.phoneNumber.setText(emergency.telephone_number);
        holder.imgIcon.setImageResource(emergency.icon);

        return row;
    }

    static class EmergencyHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView phoneNumber;
    }
}
