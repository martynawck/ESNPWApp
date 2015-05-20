package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Adapter.MentorAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.MentorContact;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-20.
 */

public class DownloadMentorInfoTask extends AsyncTask<String, Void, String> {

    private OnTaskCompleted listener;
    private Context mContext;
    ArrayList <String> buddyData;
    private View mView;
    ESNPWSQLHelper buddySQLHelper;
    SessionManager sessionManager;

    public DownloadMentorInfoTask (Context context, OnTaskCompleted listener){
        this.listener = listener;
        mContext = context;
    }

    protected String doInBackground(String... urls) {
        try{

            sessionManager = new SessionManager(mContext);
            buddySQLHelper = new ESNPWSQLHelper(mContext);
            buddyData = new ArrayList<>();
            Buddy buddy = new Buddy();
            buddy = buddySQLHelper.getBuddyInfo(sessionManager.getValueOfUserId());
            buddyData.add(buddy.getFirstname());
            buddyData.add(buddy.getLastname());
            buddyData.add(buddy.getEmail());
            buddyData.add(buddy.getPhone());
            buddyData.add(buddy.getSkype());
            buddyData.add(buddy.getFacebook());
            buddyData.add(buddy.getWhatsapp());

        }catch(Exception e){
    //        dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
        return "-1";
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onTaskCompleted(buddyData);


    }
}