package com.wiacek.martyna.esnpwapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.R;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class GetInTouchAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private final int layoutResourceId;
    private final ArrayList<Student> originalList;
    private ArrayList<Student> studentList;
    private GetInTouchFilter filter;

    public GetInTouchAdapter(Context context, Activity activity, int layoutResourceId, ArrayList<Student> studentList) {
        super(context, layoutResourceId, studentList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.studentList = new ArrayList<>();
        this.studentList.addAll(studentList);
        this.originalList = new ArrayList<>();
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
        GetInTouchHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GetInTouchHolder(row);
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
        @InjectView(R.id.profilePic) ImageView imgIcon;
        @InjectView(R.id.name) TextView name;
        @InjectView(R.id.faculty) TextView faculty;

        public GetInTouchHolder(View view){
            ButterKnife.inject(this, view);
        }
    }

    private class GetInTouchFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint.toString().length() > 0)
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
