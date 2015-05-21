package com.wiacek.martyna.esnpwapp;

import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;


import java.util.ArrayList;
import java.util.List;

import com.wiacek.martyna.esnpwapp.AsyncTask.AuthentificationTask;

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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

public class Login extends Activity {
    Button b;
    EditText et,pass;
    ProgressDialog dialog = null;
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_phpconnection_demo);
        session = new SessionManager(getApplicationContext());

        b = (Button)findViewById(R.id.Button01);
        et = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);

        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(Login.this, "","Validating user...", true);

                AuthentificationTask task = new AuthentificationTask(getApplicationContext(), Login.this, dialog, session, et.getText().toString().trim(),pass.getText().toString().trim());
                task.execute();
            }
        });
    }

    protected void onResume() {
        if (session.resumeSession()){
            Intent i = new Intent(this, NavigationDrawer.class);
            startActivity(i);
        }

        super.onResume();
    }

    public void showAlert(){
        Login.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Login Error");
                builder.setMessage("User not found")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncTask task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }

}