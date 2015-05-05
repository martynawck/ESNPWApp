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
    Buddy buddy;
    // SessionManager session;
    ESNPWSQLHelper todoSQLHelper;
    HttpPost httppost;
    StringBuffer buffer;
    //HttpResponse response;
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
            //       session = new SessionManager(mContext);

            sessionManager = new SessionManager(mContext);
            final String login_session = sessionManager.getValueOfUserId();
            // Log.d("LALA2",login_session);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("id", login_session));

            Log.d("LALA", login_session);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //response = httpclient.execute(httppost);

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
                    Log.d("TASKS",t.getDescription());
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
            Log.d("HELLOO","HELLOOO2");
            //              todoSQLHelper = new ESNPWSQLHelper(mContext);
            //todoSQLHelper.delete();

            todoSQLHelper = new ESNPWSQLHelper(mContext);
            Integer userId = Integer.parseInt(sessionManager.getValueOfUserId());
            for (TodoTask t : tasksToAdd) {
                Log.d("TASKS",t.getDescription());
                todoSQLHelper.insertToDo(userId, t);
                Log.d("HAHA","TUT");
            }

//                Log.d("IF EXISTS", Boolean.toString(todoSQLHelper.doesBuddyTableExist()) );
            //               Log.d("IF EXISTS", Boolean.toString(todoSQLHelper.doesTodoTableExist()) );

            ArrayList<TodoTask> testTasks = new ArrayList<>();
            testTasks = todoSQLHelper.getTodosAfter(sessionManager.getValueOfUserId());
            String s = Integer.toString(testTasks.size());
            Log.d("SIZE", s);




            //buddySQLHelper.insertBuddy(userId, buddy);
            //  buddySQLHelper = new BuddySQLHelper(mContext);
            // Integer userId = Integer.parseInt(session.getValueOfUserId());
            // buddySQLHelper.insertBuddy(userId, buddy);

               /* runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AndroidPHPConnectionDemo.this,"You are now logged on!", Toast.LENGTH_SHORT).show();
                    }
                });
*/
            //   Intent i = new Intent(mContext, EntirelyNewDrawer.class);
            //  startActivity(i);
        }
    }
}
