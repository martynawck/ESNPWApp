package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Fragment.StudentProfileFragment;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-21.
 */


public class GetStudentDetails extends AsyncTask<String, Void, String> {

    OnTaskCompleted listener;
    Student student;
    ArrayList<String> details;
    ArrayList<NameValuePair> nameValuePairs;

    public GetStudentDetails (Student student, OnTaskCompleted listener){
        this.student = student;
        this.listener = listener;
    }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            details = new ArrayList<>();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "studentDetails.php");

            nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("id", urls[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(!response.equalsIgnoreCase("null")){
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    details.add(postObject.getString("email"));
                    details.add(postObject.getString("phone_number"));
                    details.add(postObject.getString("skype_id"));
                    details.add(postObject.getString("facebook_id"));
                    details.add(postObject.getString("whatsapp_id"));
                }
            }

        }catch(Exception e){
        }
        return "0";
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onTaskCompleted(details);
    }
}