package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QAFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ExpandableListView expandableListView;
    QandAListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, container,
                false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        DownloadQandATask task = new DownloadQandATask(getActivity().getApplicationContext());
        task.execute(new String[] { ServerUrl.BASE_URL + "/include/file.json" });

        return view;

    }


    private class DownloadQandATask extends AsyncTask<String, Void, String> {

        private Context mContext;
        ProgressDialog mDialog;

        public DownloadQandATask(Context context){
            mContext = context;

/*            mDialog = new ProgressDialog(context);
            mDialog.setCancelable(false);
  */      }

        protected void onPreExecute ( ) {
            // mDialog.show ( ) ;
//            requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            //           setProgressBarIndeterminateVisibility(true);
        }

        protected String doInBackground(String... urls) {
            try{

                expandableListDetail = new HashMap<>();


                JSONObject jsonQuery = JSONFunctions
                        .getJSONfromURL(ServerUrl.BASE_URL + "include/qanda_data.txt");



                  //  JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;
                JSONArray jsonArray = jsonQuery.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String question = jsonObject.getString("question");
                        String answer = jsonObject.getString("answer");
                        List<String> list = new ArrayList<>();
                        list.add(answer);

                        expandableListDetail.put(question, list);
                    }
                    return "0";


            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {

            //mDialog.dismiss();
            //setProgressBarIndeterminateVisibility(false);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new QandAListAdapter(mContext, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

        }
    }

}
