package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;
import com.wiacek.martyna.esnpwapp.SQLHelpers.ESNPWSQLHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Martyna on 2015-05-22.
 */
public class UpdateToDosOnServerTask extends AsyncTask<String, Void, String> {

    private final Context mContext;
    private final SessionManager sessionManager;


    public UpdateToDosOnServerTask(Context context, SessionManager sessionManager) {
        mContext = context;
        this.sessionManager = sessionManager;
    }

    protected String doInBackground(String... urls) {

        ESNPWSQLHelper esnpwsqlHelper = new ESNPWSQLHelper(mContext);
        ArrayList<TodoTask> todoTasksAfter = esnpwsqlHelper.getTodosAfter(sessionManager.getValueOfUserId());
        ArrayList<TodoTask> todoTasksBefore = esnpwsqlHelper.getTodosBefore(sessionManager.getValueOfUserId());

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
        HttpConnectionParams.setSoTimeout(httpParameters, 10000);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);
        Log.d("user_id", sessionManager.getValueOfUserId());
        HttpPost httppost = new HttpPost(ServerUrl.BASE_URL+"updateTodos.php");


        try {
            JSONObject json = new JSONObject();
            json.put("user_id", Integer.parseInt(sessionManager.getValueOfUserId()));

            JSONArray jsonArray = new JSONArray();

            for (TodoTask t : todoTasksAfter) {
                JSONObject json1 = new JSONObject();
                json1.put("task_id", t.getTodo_id());
                json1.put("value", t.getValue());
                jsonArray.put(json1);
            }

            for (TodoTask t : todoTasksBefore) {
                JSONObject json2 = new JSONObject();
                json2.put("task_id", t.getTodo_id());
                json2.put("value", t.getValue());
                jsonArray.put(json2);
            }

            json.put("todos_array", jsonArray);

            httppost.setHeader("json",json.toString());
            httppost.getParams().setParameter("jsonpost",json);

            HttpResponse response = httpclient.execute(httppost);

            if(response != null)
            {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "0";


        }catch (ClientProtocolException e) {
            return "-1";
        } catch (IOException e) {
            return "-1";
        } catch (Exception e) {
            return "-1";

        }
    }

    protected void onPostExecute(String result) {
        if (result.equals("0"))
            sessionManager.destroySession();
        else
            Toast.makeText(mContext, "Error. Cannot update ToDos on server!", Toast.LENGTH_LONG).show();
    }

    /**
     * Created by Martyna on 2015-05-30.
     */

}
