package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.PWFacultyMarkerList;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-21.
 */


public class CreateFacultyLinksTask extends AsyncTask<String, Void, String> {

    private OnTaskCompleted listener;
    ArrayList<String> data;

    public CreateFacultyLinksTask (OnTaskCompleted listener) {
        this.listener = listener;
    }

    protected String doInBackground(String... urls) {

        data = new PWFacultyMarkerList().getDetailedData(urls[0]);

        return "0";
    }

    protected void onPostExecute(String result) {
        listener.onTaskCompleted(data);
    }
}
