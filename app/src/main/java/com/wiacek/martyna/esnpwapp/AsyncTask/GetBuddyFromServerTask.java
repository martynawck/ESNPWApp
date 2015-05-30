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
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martyna on 2015-05-30.
 */
class GetBuddyFromServerTask {

    private final ProgressDialog progressDialog;
    private final Context mContext;
    private Buddy buddy;
    private ESNPWSQLHelper buddySQLHelper;
    private final SessionManager sessionManager;

    public GetBuddyFromServerTask (Context context, ProgressDialog progressDialog, SessionManager sessionManager) {
        this.progressDialog = progressDialog;
        mContext = context;
        this.sessionManager = sessionManager;
    }

    public void runVolley() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String uri = String.format("buddy.php?id=%1$s",
                sessionManager.getValueOfUserId());
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + uri , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equalsIgnoreCase("null")){
                    try {
                        JSONObject json = new JSONObject(response);
                        buddy = JSONFunctions.JSONToBuddy(json);
                        progressDialog.dismiss();
                        buddySQLHelper = new ESNPWSQLHelper(mContext);
                        Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
                        buddySQLHelper.insertBuddy(userId, buddy);
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Error. Cannot get buddy data from server!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Error. Cannot get buddy data from server!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Error. Cannot get buddy data from server!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",sessionManager.getValueOfUserId());

                return params;
            }
        };
        queue.add(sr);

    }
}