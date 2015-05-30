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
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martyna on 2015-05-23.
 */public class DeleteMarkerTask {

    private final Context mContext;
    private final String id;


    public DeleteMarkerTask(String id, Context context) {
        mContext = context;
        this.id = id;
    }

    public void runVolley() {
        SessionManager sessionManager = new SessionManager(mContext);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest sr = new StringRequest(Request.Method.POST, ServerUrl.BASE_URL +"deleteMarker.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("resp", response);
                if (!response.equals("false"))
                    Toast.makeText(mContext, "You deleted the chosen place!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext, "Error. Chosen place cannot be deleted!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error. Chosen place cannot be deleted!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",id);

                return params;
            }
        };
        queue.add(sr);
    }
}
