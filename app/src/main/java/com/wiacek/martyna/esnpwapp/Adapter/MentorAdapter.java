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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Martyna on 2015-03-03.
 */
public class MentorAdapter extends ArrayAdapter<MentorContact> {
    private final int layoutResourceId;
    private MentorContact[] data = null;

    public MentorAdapter(Context context, int layoutResourceId, MentorContact[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MentorHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new MentorHolder(row);
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
        @InjectView(R.id.profilePic) ImageView imgIcon;
        @InjectView(R.id.name) TextView txtTitle;

        public MentorHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
