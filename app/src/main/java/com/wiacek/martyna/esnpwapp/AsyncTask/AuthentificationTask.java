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
 */public class AuthentificationTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    StringBuffer buffer;
    // httpresponse;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    SessionManager sessionManager;
    String login;
    String password;
    ProgressDialog progressDialog;
    Activity activity;


    public AuthentificationTask (Context context, Activity activity, ProgressDialog progressDialog, SessionManager sessionManager, String login, String password) {
        mContext = context;
        this.login = login;
        this.progressDialog = progressDialog;
        this.password = password;
        this.activity = activity;
    }
    protected String doInBackground(String... urls) {
        try{

            sessionManager = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL +"check.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);

            //for posting android side and php side variables should be similar
            nameValuePairs.add(new BasicNameValuePair("username", login));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Execute HTTP Post Request
            //   httpresponse = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            Log.d("MyApp", "Response : " + response);

            Integer parsedInt = Integer.parseInt(response);
            if(parsedInt != -1){
                sessionManager.createSession(login, password, parsedInt);
                progressDialog.dismiss();

                //   httpresponse.getEntity().consumeContent();

                return parsedInt.toString();

            }else{
                progressDialog.dismiss();
            //    showAlert();
                return "-1";
            }

        }catch(Exception e){
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
            return "-1";
        }
    }

    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {

            GetUserPersonalDataFromServerTask task2 = new GetUserPersonalDataFromServerTask(mContext, activity, progressDialog);
            task2.execute();
            // StartAsyncTaskInParallel(task2);



            GetBuddyFromServerTask buddy_task = new GetBuddyFromServerTask(mContext, progressDialog);
            GetToDosFromServerTask todos_task = new GetToDosFromServerTask(mContext, progressDialog);
            buddy_task.execute();
            todos_task.execute();
            //StartAsyncTaskInParallel(buddy_task);//, new String[] {ServerUrl.BASE_URL + "buddy.php"});

        }
    }
}
