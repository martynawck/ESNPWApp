package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnQADownload;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */


public class DownloadQandATask extends AsyncTask<String, Void, String> {

    OnQADownload listener;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public DownloadQandATask(OnQADownload listener){
        this.listener = listener;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            expandableListDetail = new HashMap<>();
            JSONObject jsonQuery = JSONFunctions
                    .getJSONfromURL(ServerUrl.BASE_URL + "include/qanda_data.txt");

            JSONObject jsonObject;
            JSONArray jsonArray = jsonQuery.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String question = jsonObject.getString("question");
                String answer = jsonObject.getString("answer");
                List<String> list = new ArrayList<>();
                list.add(answer);
                expandableListDetail.put(question, list);
            }
            return "0";


        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return "1";
    }

    @Override
    protected void onPostExecute(String result) {

        listener.onTaskCompleted(expandableListTitle, expandableListDetail);
    }
}
