package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Fragment.StudentProfileFragment;
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martyna on 2015-05-21.
 */


public class GetStudentDetails  {

    OnTaskCompleted listener;
    Student student;
    ArrayList<String> details;
    Context mContext;
    String id;

    public GetStudentDetails (String id, Context context, Student student, OnTaskCompleted listener){
        this.student = student;
        this.listener = listener;
        this.mContext = context;
        this.id = id;
    }

    public void runVolley() {
        details = new ArrayList<>();
        Log.d("ID",id);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String uri = String.format("studentDetails.php?id=%1$s", id);
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + uri , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!response.equalsIgnoreCase("null")){
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String post = jsonObject.getString("post");
                            JSONObject postObject = new JSONObject(post);
                            details = JSONFunctions.JSONToStudentDetails(postObject);
                        }
                    }
                    listener.onTaskCompleted(details);

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error. Cannot download student details!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot download student details!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(sr);
    }
}