package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-05-23.
 */
public class DeleteMarkerTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ESNPWSQLHelper esnpwsqlHelper;
    SessionManager sessionManager;


    public DeleteMarkerTask(Context context) {
        mContext = context;
        this.sessionManager = sessionManager;
    }
    protected String doInBackground(String... urls) {

       // HttpClient httpclient = new DefaultHttpClient();
       // HttpPost httppost = new HttpPost(ServerUrl.BASE_URL+"deleteMarker.php");


        try {
            httpclient = new DefaultHttpClient();
            //String url_endpoint;

            httppost = new HttpPost(ServerUrl.BASE_URL+"deleteMarker.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("id", urls[0]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(response.equals("true") ){
                return response;

            }else{
                return "-1";
            }



        }catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (Exception e) {

        }
        return "0";
    }

    protected void onPostExecute(String result) {
        Toast.makeText(mContext, "You deleted the chosen place!", Toast.LENGTH_SHORT).show();
        //dialog.dismiss();
    }
}
