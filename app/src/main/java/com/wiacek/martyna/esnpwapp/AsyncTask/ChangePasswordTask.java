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
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martyna on 2015-04-29.
 */public class ChangePasswordTask{

    private final Context mContext;
    private SessionManager sessionManager;
    private final String oldPassword;
    private final String newPassword;


    public ChangePasswordTask(String oldPassword, String newPassword, Context context) {
        mContext = context;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public void runVolley() {
        sessionManager = new SessionManager(mContext);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.POST, ServerUrl.BASE_URL +"changePassword.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("resp", response);
                if (!response.equals("Error"))
                    Toast.makeText(mContext, "Value updated!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(mContext, "Error. Value was not updated!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Value was not updated!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",sessionManager.getValueOfUserId());
                params.put("old_password",oldPassword);
                params.put("new_password", newPassword);

                return params;
            }
        };
        queue.add(sr);
    }
}
