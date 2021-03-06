package com.wiacek.martyna.esnpwapp.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-04-17.
 */
public class ToDoAdapter extends ArrayAdapter<TodoTask>
{
    private final ESNPWSQLHelper db;
    private final Context context;
    private ArrayList<TodoTask> taskList = new ArrayList<>();
    private final SessionManager session;

    public ToDoAdapter(Context context, int layoutResourceId,
                     ArrayList<TodoTask> objects) {
        super(context, layoutResourceId, objects);
        this.taskList = objects;
        this.context = context;
        db = new ESNPWSQLHelper(context);
        session = new SessionManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox chk;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_todo_view,
                    parent, false);
            chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(chk);

            chk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    TodoTask changeTask = (TodoTask) cb.getTag();
                    changeTask.setValue(cb.isChecked() ? 1 : 0);
                    db.updateToDo(changeTask.getTodo_id(), Integer.parseInt(session.getValueOfUserId()), changeTask.getValue());
                    if (cb.isChecked())
                        cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    else
                        cb.setPaintFlags(cb.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                }

            });
        } else {
            chk = (CheckBox) convertView.getTag();
        }

        TodoTask current = taskList.get(position);
        chk.setText(current.getDescription());
        chk.setChecked(current.getValue() == 1);
        if (current.getValue() == 1)
            chk.setPaintFlags(chk.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        chk.setTag(current);
        return convertView;
    }

}