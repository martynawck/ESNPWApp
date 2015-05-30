package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Interface.OnStudentsListTaskCompleted;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */
public class GetStudentsFromServerTask extends AsyncTask<String, Void, String> {

    private final OnStudentsListTaskCompleted listener;
    private final ProgressDialog progressDialog;
    private final Context mContext;
    private ArrayList<Student> students;

    public GetStudentsFromServerTask (Context context, ProgressDialog progressDialog, OnStudentsListTaskCompleted listener) {
        mContext = context;
        this.listener = listener;
        this.progressDialog = progressDialog;
    }

    protected String doInBackground(String... urls) {
        try{

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "students.php");

            SessionManager sessionManager = new SessionManager(mContext);
            final String login_session = sessionManager.getValueOfUserId();
            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("id", login_session));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            students = new ArrayList<>();

            if(!response.equalsIgnoreCase("null")){
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                Log.d("length",Integer.toString(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    Student student = JSONFunctions.JSONToStudent(postObject);
                    students.add(student);
                }
            }
            progressDialog.dismiss();
            return "0";
        }catch(Exception e){
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
            return "-1";
        }

    }


    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
            listener.onTaskCompleted(students);

        } else {
            Toast.makeText(mContext, "Error. Cannot get students list from server!", Toast.LENGTH_LONG).show();
        }
    }
}
