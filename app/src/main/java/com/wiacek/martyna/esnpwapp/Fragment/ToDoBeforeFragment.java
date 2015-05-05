package com.wiacek.martyna.esnpwapp.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.Adapter.ToDoAdapter;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-03-21.
 */
public class ToDoBeforeFragment extends Fragment {


    public static final String ARG_SECTION_NUMBER = "section_number";
    ESNPWSQLHelper db;
    ArrayList<TodoTask> tasksAfterComing;
    ToDoAdapter adapter;

    public ToDoBeforeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todo_list, container, false);
        db = new ESNPWSQLHelper(getActivity().getApplicationContext());
        tasksAfterComing = db.getTodosBefore(new SessionManager(getActivity().getApplicationContext()).getValueOfUserId());
        adapter = new ToDoAdapter(getActivity().getApplicationContext(), R.layout.listview_todo_view, tasksAfterComing);
        ListView listTask = (ListView) rootView.findViewById(R.id.listView1);
        listTask.setAdapter(adapter);
        return rootView;
    }
}
