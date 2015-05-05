package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Login;
import com.wiacek.martyna.esnpwapp.NavigationDrawer;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-04-29.
 */
public class GetUserPersonalDataFromServerTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    StringBuffer buffer;
    //HttpResponse response;
    HttpClient httpclient;
    SessionManager session;
    List<NameValuePair> nameValuePairs;
    private Activity activity;
    ProgressDialog progressDialog;

    public GetUserPersonalDataFromServerTask (Context context, Activity activity, ProgressDialog progressDialog) {
        mContext = context;
        this.activity = activity;
        this.progressDialog = progressDialog;
    }
    protected String doInBackground(String... urls) {
        try{
            session = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "faculty.php");

            final String login_session = session.getValueOfUserId();
            nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("id",login_session));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //      response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(!response.equalsIgnoreCase("null")){
                JSONObject json = new JSONObject(response);

                String faculty = json.getString("faculty");
                String firstname = json.getString("first_name");
                String lastname = json.getString("last_name");
                String profile_image = json.getString("image");

                session.setValueOfFaculty(faculty);
                session.setValueOfFirstName(firstname);
                session.setValueOfLastName(lastname);
                session.setValueOfProfileImage(profile_image);

                return "0";
            }

        }catch(Exception e){
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
        return "-1";

    }

    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {


            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(mContext, "You are now logged on!", Toast.LENGTH_SHORT).show();
                }
            });

            Intent i = new Intent(mContext, NavigationDrawer.class);
            activity.startActivity(i);

            //GetBuddyFromServerTask task3 = new GetBuddyFromServerTask();
            //StartAsyncTaskInParallel(task3);
            //GetToDosFromServerTask task4 = new GetToDosFromServerTask();
            //StartAsyncTaskInParallel(task4);

        }
    }
}