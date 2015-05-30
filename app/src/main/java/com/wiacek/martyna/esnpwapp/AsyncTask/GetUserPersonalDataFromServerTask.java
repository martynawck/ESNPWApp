package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.NavigationDrawer;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martyna on 2015-04-29.
 */
class GetUserPersonalDataFromServerTask {

    private final Context mContext;
    private SessionManager session;
    private final Activity activity;

    public GetUserPersonalDataFromServerTask (Context context, Activity activity, ProgressDialog progressDialog) {
        mContext = context;
        this.activity = activity;
    }

    public void runVolley() {

        session = new SessionManager(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String uri = String.format("faculty.php?id=%1$s",session.getValueOfUserId());
        StringRequest sr = new StringRequest(Request.Method.GET, ServerUrl.BASE_URL + uri , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(!response.equalsIgnoreCase("null")) {

                        JSONObject json = new JSONObject(response);

                        session.setValueOfFaculty((json.getString("faculty").equals("null")) ? "" : json.getString("faculty"));
                        session.setValueOfFirstName((json.getString("first_name").equals("null")) ? "" : json.getString("first_name"));
                        session.setValueOfLastName((json.getString("last_name").equals("null")) ? "" : json.getString("last_name"));
                        session.setValueOfProfileImage((json.getString("image").equals("null")) ? "" : json.getString("image"));
                        session.setValueOfEmail((json.getString("email").equals("null")) ? "" : json.getString("email"));
                        session.setValueOfFacebook((json.getString("facebook_id").equals("null")) ? "" : json.getString("facebook_id"));
                        session.setValueOfPhone((json.getString("phone_number").equals("null")) ? "" : json.getString("phone_number"));
                        session.setValueOfSkype((json.getString("skype_id").equals("null")) ? "" : json.getString("skype_id"));
                        session.setValueOfWhatsapp((json.getString("whatsapp_id").equals("null")) ? "" : json.getString("whatsapp_id"));
                        session.setValueOfProfileVisibility(json.getString("share_data"));


                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Facebook is not installed. Please install Facebook!",
                            Toast.LENGTH_SHORT).show();
                }

                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(mContext, "You are now logged in!", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(mContext, NavigationDrawer.class);
                activity.startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Cannot get user personal data from server!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",session.getValueOfUserId());

                return params;
            }
        };
        queue.add(sr);
    }
}