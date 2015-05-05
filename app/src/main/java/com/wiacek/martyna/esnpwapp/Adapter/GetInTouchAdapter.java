package com.wiacek.martyna.esnpwapp.Adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-03-03.
 */
public class GetInTouchAdapter extends ArrayAdapter<Student> {
    Context context;
    Activity activity;
    int layoutResourceId;
   // ArrayList<Student> data = null;
    ArrayList<Student> originalList;
    ArrayList<Student> studentList;
    private GetInTouchFilter filter;

    public GetInTouchAdapter(Context context, Activity activity, int layoutResourceId, ArrayList<Student> studentList) {
        super(context, layoutResourceId, studentList);
        //super();
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.studentList = new ArrayList<Student>();
        this.studentList.addAll(studentList);
        this.originalList = new ArrayList<Student>();
        this.originalList.addAll(studentList);
    }

    public Filter getFilter() {
        if (filter == null){
            filter  = new GetInTouchFilter();
        }
        return filter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GetInTouchHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GetInTouchHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.profilePic);
            holder.name = (TextView)row.findViewById(R.id.name);
            holder.faculty = (TextView) row.findViewById(R.id.faculty);

            row.setTag(holder);
        }
        else
        {
            holder = (GetInTouchHolder)row.getTag();
        }

        Student student = studentList.get(position);
        holder.name.setText(student.getFirstname() + " " + student.getLastname());
        holder.faculty.setText(student.getFaculty());

        Picasso.with(context).load(ServerUrl.BASE_URL + student.getImgUrl()).into(holder.imgIcon);

        return row;
    }

    static class GetInTouchHolder
    {
        ImageView imgIcon;
        TextView name;
        TextView faculty;
    }

    private class GetInTouchFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Student> filteredItems = new ArrayList<>();

                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                    Student student = originalList.get(i);
                    if(student.getFirstname().toLowerCase().contains(constraint) ||
                            student.getLastname().toLowerCase().contains(constraint) ||
                                student.getFaculty().toLowerCase().contains(constraint))
                        filteredItems.add(student);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            studentList = (ArrayList<Student>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = studentList.size(); i < l; i++)
                add(studentList.get(i));
            notifyDataSetInvalidated();
        }
    }
}
