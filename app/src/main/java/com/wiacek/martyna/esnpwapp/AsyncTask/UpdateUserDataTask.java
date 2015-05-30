package com.wiacek.martyna.esnpwapp.AsyncTask;

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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martyna on 2015-04-29.
 */public class UpdateUserDataTask {

    private final String type;
    private final String value;
    private final Context mContext;
    private SessionManager sessionManager;


    public UpdateUserDataTask(String type, String value, Context context) {
        mContext = context;
        this.type = type;
        this.value = value;
    }

    public void runVolley() {

        sessionManager = new SessionManager(mContext);

        final String url_endpoint;
        switch (type) {

            case "skype":
                url_endpoint = "updateSkype.php";
                break;
            case "phone":
                url_endpoint = "updatePhone.php";
                break;
            case "whatsapp":
                url_endpoint = "updateWhatsapp.php";
                break;
            case "facebook":
                url_endpoint = "updateFacebook.php";
                break;
            case "email":
                url_endpoint = "updateEmail.php";
                break;
            case "visibility":
                url_endpoint = "updateVisibility.php";
                break;
            case "password":
                url_endpoint = "updatePassword.php";
                break;

            default:
                url_endpoint = "";
                break;
        }

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.POST, ServerUrl.BASE_URL + url_endpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")) {
                    Toast.makeText(mContext, "Value updated!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Error. Value could not be updated!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Value could not be updated!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", sessionManager.getValueOfUserId());
                params.put("value", value);

                return params;
            }
        };
        queue.add(sr);
    }
}
