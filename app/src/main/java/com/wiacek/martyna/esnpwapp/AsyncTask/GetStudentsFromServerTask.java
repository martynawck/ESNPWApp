package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wiacek.martyna.esnpwapp.Adapter.GetInTouchAdapter;
import com.wiacek.martyna.esnpwapp.Domain.FacultyNames;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Fragment.StudentProfileFragment;
import com.wiacek.martyna.esnpwapp.Interface.OnStudentsListTaskCompleted;
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
import java.util.List;

/**
 * Created by Martyna on 2015-05-21.
 */
public class GetStudentsFromServerTask extends AsyncTask<String, Void, String> {

    OnStudentsListTaskCompleted listener;
    private final ProgressDialog progressDialog;
    private Context mContext;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ArrayList<Student> students;
    SessionManager sessionManager;

    public GetStudentsFromServerTask (Context context, ProgressDialog progressDialog, OnStudentsListTaskCompleted listener) {
        mContext = context;
        this.listener = listener;
        this.progressDialog = progressDialog;
    }
    protected String doInBackground(String... urls) {
        try{

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "students.php");

            sessionManager = new SessionManager(mContext);
            final String login_session = sessionManager.getValueOfUserId();
            nameValuePairs = new ArrayList<>(1);
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
                    Student student = new Student();
                    String faculty = postObject.getString("faculty");
                    String longFaculty = new FacultyNames().returnLongName(faculty);

                    student.setFaculty(longFaculty);
                    student.setFirstname(postObject.getString("first_name"));
                    student.setLastname(postObject.getString("last_name"));
                    student.setImgUrl(postObject.getString("image"));
                    student.setId(postObject.getString("id"));
                    students.add(student);
                }

                progressDialog.dismiss();
                return "0";
            }
        }catch(Exception e){
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
        progressDialog.dismiss();
        return "-1";

    }


    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
            listener.onTaskCompleted(students);

        }
    }
}
