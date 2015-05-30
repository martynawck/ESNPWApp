package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martyna on 2015-04-29.
 */
public class GetToDosFromServerTask {

    private final ProgressDialog progressDialog;
    private Context mContext;
    ESNPWSQLHelper todoSQLHelper;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ArrayList<TodoTask> tasksToAdd;
    SessionManager sessionManager;

    public GetToDosFromServerTask (Context context, ProgressDialog progressDialog, SessionManager sessionManager) {
        mContext = context;
        this.progressDialog = progressDialog;
        this.sessionManager = sessionManager;
    }
    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String uri = String.format("todos.php?id=%1$s",
                sessionManager.getValueOfUserId());
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equalsIgnoreCase("null")){
                    try {
                        tasksToAdd = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String post = jsonObject.getString("post");
                            JSONObject postObject = new JSONObject(post);
                            TodoTask todoTask = JSONFunctions.JSONToToDo(postObject);
                            tasksToAdd.add(todoTask );
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Error. Cannot get ToDos list from server!", Toast.LENGTH_LONG).show();
                    }

                    todoSQLHelper = new ESNPWSQLHelper(mContext);
                    Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
                    for (TodoTask t : tasksToAdd) {
                        todoSQLHelper.insertToDo(userId, t);
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Error. Cannot get ToDos list from server!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Error. Cannot get ToDos list from server!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(sr);
    }

    /**
     * Created by Martyna on 2015-04-29.
     */

}