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
    ESNPWSQLHelper buddySQLHelper;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    SessionManager sessionManager;

    public GetBuddyFromServerTask (Context context, ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        mContext = context;
    }
    protected String doInBackground(String... urls) {
        try{
            Log.d("GET BUDDY","GET BUDDY");
            sessionManager = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "buddy.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            final String login_session = sessionManager.getValueOfUserId();
            nameValuePairs.add(new BasicNameValuePair("id", login_session));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            if(!response.equalsIgnoreCase("null")){
                JSONObject json = new JSONObject(response);
                buddy = new Buddy();
                buddy.setFirstname(json.getString("firstname"));
                buddy.setLastname(json.getString("lastname"));
                buddy.setEmail(json.getString("email"));
                buddy.setSkype(json.getString("skype_id"));
                buddy.setFacebook(json.getString("facebook_id"));
                buddy.setPhone(json.getString("phone_number"));
                buddy.setWhatsapp(json.getString("whatsapp_id"));
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
            buddySQLHelper = new ESNPWSQLHelper(mContext);
            Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
            buddySQLHelper.insertBuddy(userId, buddy);
        }
    }
}