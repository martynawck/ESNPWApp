package com.wiacek.martyna.esnpwapp.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Interface.OnOffersDownload;
import com.wiacek.martyna.esnpwapp.JSONFunctions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Martyna on 2015-05-21.
 */

public class DownloadOffersTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    ProgressDialog mDialog;
    OnOffersDownload listener;
    TreeMap<Integer, List<ESNPartner>> expandableListDetail;
    List<String> expandableListTitle;

    public DownloadOffersTask(Context context, OnOffersDownload listener){
        mContext = context;
        this.listener = listener;
     }

    protected void onPreExecute ( ) {
    }

    protected String doInBackground(String... urls) {
        try{

            expandableListDetail = new TreeMap<>();
            expandableListTitle = new ArrayList<>();

            ArrayList<ESNPartner> partners = new ArrayList<>();
            JSONObject jsonQuery = JSONFunctions
                    .getJSONfromURL(ServerUrl.BASE_URL + "include/partners_data.txt");

            JSONObject jsonObject;
            JSONArray jsonArray = jsonQuery.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ESNPartner esnPartner = new ESNPartner();
                esnPartner.setId(jsonObject.getString("id"));
                esnPartner.setName(jsonObject.getString("name"));
                esnPartner.setDescription(jsonObject.getString("description"));
                esnPartner.setDiscount(jsonObject.getString("discount"));
                esnPartner.setHowToUse(jsonObject.getString("how_to_use"));
                esnPartner.setWebsite(jsonObject.getString("website"));
                esnPartner.setImage(jsonObject.getString("img_url"));

                partners.add(esnPartner);
            }

            Collections.sort(partners, new Comparator<ESNPartner>() {
                public int compare(ESNPartner o1, ESNPartner o2) {
                    if (o1.getName() == null || o2.getName() == null)
                        return 0;
                    return o1.getName().compareTo(o2.getName());
                }
            });

            int i = 0;
            for (ESNPartner p : partners) {
                expandableListTitle.add(p.getName());
                ArrayList<ESNPartner> pa = new ArrayList<>();
                pa.add(p);
                expandableListDetail.put(i, pa);
                i++;
            }

            return "0";

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return "1";
    }

    @Override
    protected void onPostExecute(String result) {

        listener.onTaskCompleted(expandableListTitle, expandableListDetail);
    }
}
