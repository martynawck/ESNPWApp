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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Martyna on 2015-03-21.
 */
public class ToDoAfterFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ESNPWSQLHelper db;
    ArrayList<TodoTask> tasksAfterComing;
    ToDoAdapter adapter;
    @InjectView(R.id.listView1) ListView listTask;

    public ToDoAfterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.fragment_todo_list, container, false);
        ButterKnife.inject(this, rootView);
        db = new ESNPWSQLHelper(getActivity().getApplicationContext());
        tasksAfterComing = db.getTodosAfter(new SessionManager(getActivity().getApplicationContext()).getValueOfUserId());
        adapter = new ToDoAdapter(getActivity().getApplicationContext(), R.layout.listview_todo_view, tasksAfterComing);
        listTask.setAdapter(adapter);
        return rootView;
    }
}