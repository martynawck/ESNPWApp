package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.R;

/**
 * Created by Martyna on 2015-03-03.
 */
public class MentorAdapter extends ArrayAdapter<MentorContact> {
    Context context;
    int layoutResourceId;
    MentorContact data[] = null;

    public MentorAdapter(Context context, int layoutResourceId, MentorContact[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MentorHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MentorHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.profilePic);
            holder.txtTitle = (TextView)row.findViewById(R.id.name);
            row.setTag(holder);
        }
        else
        {
            holder = (MentorHolder)row.getTag();
        }

        MentorContact mentor = data[position];
        holder.txtTitle.setText(mentor.name);
        holder.imgIcon.setImageResource(mentor.icon);

        return row;
    }

    static class MentorHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
