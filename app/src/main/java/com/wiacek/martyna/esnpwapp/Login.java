package com.wiacek.martyna.esnpwapp;

import com.wiacek.martyna.esnpwapp.AsyncTask.AuthentificationTask;
import com.wiacek.martyna.esnpwapp.AsyncTask.UpdateToDosOnServerTask;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Login extends Activity {
    @InjectView(R.id.username) EditText et;
    @InjectView(R.id.password) EditText pass;

    ProgressDialog dialog = null;
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_phpconnection_demo);
        ButterKnife.inject(this);

        session = new SessionManager(getApplicationContext());
    }

    @OnClick(R.id.Button01)
    public void OnClick() {
        dialog = ProgressDialog.show(Login.this, "","Validating user...", true);
        AuthentificationTask task = new AuthentificationTask(getApplicationContext(), Login.this, dialog, session, et.getText().toString().trim(), pass.getText().toString().trim());
        task.runVolley();
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