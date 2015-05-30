package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.Domain.ClickableLink;
import com.wiacek.martyna.esnpwapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Martyna on 2015-03-03.
 */
public class ClickableLinkAdapter extends ArrayAdapter<ClickableLink> {
    Context context;
    int layoutResourceId;
    ClickableLink data[] = null;

    public ClickableLinkAdapter(Context context, int layoutResourceId, ClickableLink[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ClickableLinkHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ClickableLinkHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (ClickableLinkHolder)row.getTag();
        }

        ClickableLink emergency = data[position];
        holder.name.setText(emergency.getName());
        holder.faculty.setText(emergency.getWebsite());

        return row;
    }

    static class ClickableLinkHolder
    {
        @InjectView(R.id.name) TextView name;
        @InjectView(R.id.faculty) TextView faculty;

        public ClickableLinkHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
