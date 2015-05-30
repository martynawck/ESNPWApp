package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.FacultyNames;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-21.
 */


public class CreateFacultyLinksTask extends AsyncTask<String, Void, String> {

    private final OnTaskCompleted listener;
    private final Context mContext;
    private ArrayList<String> data;

    public CreateFacultyLinksTask (Context context, OnTaskCompleted listener) {
        this.listener = listener;
        this.mContext = context;
    }

    protected String doInBackground(String... urls) {

        try {
            data = new FacultyNames().getDetailedData(urls[0]);
        }
        catch (Exception e) {
            return "-1";
        }
        return "0";
    }

    protected void onPostExecute(String result) {

        if (result.equals("0"))
            listener.onTaskCompleted(data);
        else
            Toast.makeText(mContext, "Error. Cannot create faculty links!", Toast.LENGTH_LONG).show();
    }
}
