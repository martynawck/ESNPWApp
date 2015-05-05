package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

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
public class GetBuddyFromServerTask extends AsyncTask<String, Void, String> {

    private final ProgressDialog progressDialog;
    private Context mContext;
    Buddy buddy;
    //    SessionManager session;
    ESNPWSQLHelper buddySQLHelper;
    HttpPost httppost;
    StringBuffer buffer;
    //  HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    SessionManager sessionManager;

    public GetBuddyFromServerTask (Context context, ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        mContext = context;
    }
    protected String doInBackground(String... urls) {
        try{
            sessionManager = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "buddy.php");
//                session = new SessionManager(mContext);


            final String login_session = sessionManager.getValueOfUserId();
            Log.d("LALA2", login_session);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("id", login_session));

            Log.d("LALA",login_session);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(!response.equalsIgnoreCase("null")){
                JSONObject json = new JSONObject(response);
                buddy = new Buddy(json.getString("firstname"), json.getString("lastname"), json.getString("email"),
                        json.getString("skype_id"), json.getString("facebook_id"), json.getString("phone_number"),json.getString("whatsapp_id"));
                Log.d("nnn",buddy.getFirstname());
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
            Log.d("HELLO", "HELLOOOO");
            //   getApplicationContext().deleteDatabase("com.wiacek.martyna.esnpw.database");

    /*            File dbFile = getApplicationContext().getDatabasePath("com.wiacek.martyna.esnpw.database");
                if (dbFile.exists())
                    Log.d("EXIST","TRUE");
                else
                    Log.d("EXIST","FALSE");*/
            //
            //  buddySQLHelper.delete();
            // buddySQLHelper.deleteBuddyTable();
            // buddySQLHelper.deleteTodosTable();
            buddySQLHelper = new ESNPWSQLHelper(mContext);
            Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
            Log.d("IF EXISTS", Boolean.toString(buddySQLHelper.doesBuddyTableExist()) );
            Log.d("IF EXISTS", Boolean.toString(buddySQLHelper.doesTodoTableExist()) );
            buddySQLHelper.insertBuddy(userId, buddy);
            Log.d("IF EXISTS", Boolean.toString(buddySQLHelper.doesBuddyTableExist()) );
            Log.d("IF EXISTS", Boolean.toString(buddySQLHelper.doesTodoTableExist()) );

            Buddy b = buddySQLHelper.getBuddyInfo(sessionManager.getValueOfUserId());

            Log.d("Buddy name", b.getFirstname());

            //B//uddy buddy2 = buddySQLHelper.getBuddyInfo(session.getValueOfUserId());
            //Log.d("IMIE",buddy2.getFirstname());

               /* runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AndroidPHPConnectionDemo.this,"You are now logged on!", Toast.LENGTH_SHORT).show();
                    }
                });
*/
            //   Intent i = new Intent(mContext, EntirelyNewDrawer.class);
            //  startActivity(i);
        }
    }
}