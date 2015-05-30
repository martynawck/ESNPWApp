package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnSingleStringTaskCompleted;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-21.
 */
public class DownloadFacultyInfo extends AsyncTask<String, Void, String> {

    private SessionManager sessionManager;
    private ProgressDialog dialog;
    private OnSingleStringTaskCompleted listener;
    String faculty;
    private Context mContext;

    public DownloadFacultyInfo (Context context, ProgressDialog dialog, SessionManager sessionManager, OnSingleStringTaskCompleted listener){
        this.sessionManager = sessionManager;
        this.dialog = dialog;
        this.listener = listener;
        this.mContext = context;
    }

    protected String doInBackground(String... urls) {
        try{
            faculty = sessionManager.getValueOfFaculty();
            return sessionManager.getValueOfFaculty();

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
            return "-1";
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
            listener.onTaskCompleted(faculty);
        } else {
            Toast.makeText(mContext, "Error. Cannot download faculty info!", Toast.LENGTH_LONG).show();
        }
    }
}
