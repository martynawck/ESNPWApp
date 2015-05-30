package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
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
import com.wiacek.martyna.esnpwapp.Interface.OnTaskCompleted;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-21.
 */


public class GetStudentDetails  {

    private final OnTaskCompleted listener;
    private ArrayList<String> details;
    private final Context mContext;
    private final String id;

    public GetStudentDetails (String id, Context context, Student student, OnTaskCompleted listener){
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