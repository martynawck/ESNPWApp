package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Martyna on 2015-03-03.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    Context context;
    int layoutResourceId;
    ArrayList<Event> data = new ArrayList<>();

    public EventsAdapter(Context context, int layoutResourceId, ArrayList<Event> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventHolder();
            holder.name = (TextView)row.findViewById(R.id.name);
            holder.date = (TextView) row.findViewById(R.id.date);
            holder.owner = (TextView) row.findViewById(R.id.owner);
            holder.place = (TextView) row.findViewById(R.id.place);
            holder.where = (TextView) row.findViewById(R.id.where);


            row.setTag(holder);
        }
        else
        {
            holder = (EventHolder)row.getTag();
        }

        Event event = data.get(position);
        holder.name.setText(event.getName());

        ImageView image = (ImageView) row.findViewById(R.id.image);
        Picasso.with(getContext()).load(event.getImageUrl()).into(image);
        SimpleDateFormat outgoingFormat = new SimpleDateFormat(" EEEE, dd MMMM yyyy, HH:mm", Locale.ENGLISH);
        holder.date.setText(outgoingFormat.format(event.getStartTime()));
        holder.owner.setText(event.getOwner());
        holder.place.setText(event.getPlace());
        holder.where.setText(event.getWhere());

        return row;
    }

    static class EventHolder
    {
        TextView name;
        TextView date;
        TextView place;
        TextView where;
        TextView owner;
    }
}
