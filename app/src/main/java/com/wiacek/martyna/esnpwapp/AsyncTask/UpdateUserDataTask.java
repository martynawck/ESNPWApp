package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 */public class UpdateUserDataTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    SessionManager sessionManager;
    String login;
    String password;
    ProgressDialog progressDialog;
    Activity activity;


    public UpdateUserDataTask(Context context) {
        mContext = context;
    }
    protected String doInBackground(String... urls) {
        try{
            sessionManager = new SessionManager(mContext);
            httpclient = new DefaultHttpClient();
            String url_endpoint;

            switch (urls[0]) {

                case "skype":
                    url_endpoint = "updateSkype.php";
                    break;
                case "phone":
                    url_endpoint = "updatePhone.php";
                    break;
                case "whatsapp":
                    url_endpoint = "updateWhatsapp.php";
                    break;
                case "facebook":
                    url_endpoint = "updateFacebook.php";
                    break;
                case "email":
                    url_endpoint = "updateEmail.php";
                    break;
                case "visibility":
                    url_endpoint = "updateVisibility.php";
                    break;
                case "password":
                    url_endpoint = "updatePassword.php";
                    break;

                default:
                    url_endpoint = "";
                    break;


            }
            httppost = new HttpPost(ServerUrl.BASE_URL +url_endpoint);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("id", sessionManager.getValueOfUserId()));
            nameValuePairs.add(new BasicNameValuePair("value", urls[1]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(response == "Success" ){
                return response;

            }else{
                return "-1";
            }

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
            return "-1";
        }
    }

    protected void onPostExecute(String result) {
    }
}
