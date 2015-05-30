package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */

public class DownloadMentorInfoTask extends AsyncTask<String, Void, String> {

    private final OnTaskCompleted listener;
    private final Context mContext;
    private ArrayList <String> buddyData;

    public DownloadMentorInfoTask (Context context, OnTaskCompleted listener){
        this.listener = listener;
        mContext = context;
    }

    protected String doInBackground(String... urls) {
        try{

            SessionManager sessionManager = new SessionManager(mContext);
            ESNPWSQLHelper buddySQLHelper = new ESNPWSQLHelper(mContext);
            buddyData = new ArrayList<>();

            Buddy buddy = buddySQLHelper.getBuddyInfo(sessionManager.getValueOfUserId());
            buddyData.add(buddy.getFirstname());
            buddyData.add(buddy.getLastname());
            buddyData.add(buddy.getEmail());
            buddyData.add(buddy.getPhone());
            buddyData.add(buddy.getSkype());
            buddyData.add(buddy.getFacebook());
            buddyData.add(buddy.getWhatsapp());

            return "0";
        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return "-1";
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("0"))
            listener.onTaskCompleted(buddyData);
        else
            Toast.makeText(mContext, "Error. Cannot download buddy info!", Toast.LENGTH_LONG).show();
    }
}