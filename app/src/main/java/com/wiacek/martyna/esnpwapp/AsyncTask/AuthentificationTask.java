package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martyna on 2015-05-30.
 */
public class AuthentificationTask {

    private final Context mContext;
    private final String login;
    private final ProgressDialog progressDialog;
    private final String password;
    private final Activity activity;

    public AuthentificationTask(Context context, Activity activity, ProgressDialog progressDialog, SessionManager sessionManager, String login, String password) {
        mContext = context;
        this.login = login;
        this.progressDialog = progressDialog;
        this.password = password;
        this.activity = activity;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.POST, ServerUrl.BASE_URL +"check.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Integer.parseInt(response) != -1) {
                    SessionManager sessionManager = new SessionManager(mContext);
                    sessionManager.createSession(login, password, Integer.parseInt(response));
                    progressDialog.dismiss();

                    GetUserPersonalDataFromServerTask task2 = new GetUserPersonalDataFromServerTask(mContext, activity, progressDialog);
                    task2.runVolley();

                    GetBuddyFromServerTask buddy_task = new GetBuddyFromServerTask(mContext, progressDialog, sessionManager);
                    GetToDosFromServerTask todos_task = new GetToDosFromServerTask(mContext, progressDialog, sessionManager);
                    buddy_task.runVolley();
                    todos_task.runVolley();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Error. You cannot login right now, try it later!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Error. You cannot login right now, try it later!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",login);
                params.put("password",password);

                return params;
            }
        };
        queue.add(sr);

    }
}