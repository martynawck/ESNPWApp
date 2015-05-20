package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 2015-04-29.
 */
public class GetToDosFromServerTask extends AsyncTask<String, Void, String> {

    private final ProgressDialog progressDialog;
    private Context mContext;
    ESNPWSQLHelper todoSQLHelper;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ArrayList<TodoTask> tasksToAdd;
    SessionManager sessionManager;

    public GetToDosFromServerTask (Context context, ProgressDialog progressDialog) {
        mContext = context;
        this.progressDialog = progressDialog;
    }
    protected String doInBackground(String... urls) {
        try{

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(ServerUrl.BASE_URL + "todos.php");
            sessionManager = new SessionManager(mContext);
            final String login_session = sessionManager.getValueOfUserId();
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("id", login_session));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            tasksToAdd = new ArrayList<>();

            if(!response.equalsIgnoreCase("null")){
                Log.d("RESP","NOT NULL");
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    tasksToAdd.add(new TodoTask(postObject.getInt("id"),
                            postObject.getString("task_name"),postObject.getInt("value"),
                            postObject.getInt("type") ) );
                }

                for (TodoTask t : tasksToAdd)
                return "0";
            }
        }catch(Exception e){
            progressDialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
        return "-1";

    }


    protected void onPostExecute(String result) {

        if (!result.equals("-1")) {
            todoSQLHelper = new ESNPWSQLHelper(mContext);
            Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
            for (TodoTask t : tasksToAdd) {
                todoSQLHelper.insertToDo(userId, t);
            }

       //     ArrayList<TodoTask> testTasks = new ArrayList<>();
         //   testTasks = todoSQLHelper.getTodosAfter(sessionManager.getValueOfUserId());
        }
    }
}
