package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-04-29.
 */public class ChangePasswordTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    SessionManager sessionManager;


    public ChangePasswordTask(Context context) {
        mContext = context;
    }
    protected String doInBackground(String... urls) {
        try{

            sessionManager = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL +"changePassword.php");
            nameValuePairs = new ArrayList<>(2);

            nameValuePairs.add(new BasicNameValuePair("id", sessionManager.getValueOfUserId()));
            nameValuePairs.add(new BasicNameValuePair("old_password", urls[0]));
            Log.d("URL0",urls[0]);
            Log.d("URL1",urls[1]);
            nameValuePairs.add(new BasicNameValuePair("new_password", urls[1]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            Log.d("MyApp", "Response : " + response);

            if(response != "Error"){
              //  sessionManager.setValueOfPassword(urls[1]);
                return "Success";
            }else{
                return "-1";
            }

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
            return "-1";
        }
    }

    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
        }
    }
}
