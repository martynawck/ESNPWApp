package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wiacek.martyna.esnpwapp.Adapter.OffersListAdapter;
import com.wiacek.martyna.esnpwapp.Adapter.QandAListAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OffersFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    ExpandableListView expandableListView;
    OffersListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    TreeMap<Integer, List<ESNPartner>> expandableListDetail;
    ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, container,
                false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        progressDialog = ProgressDialog.show(getActivity(), "", "Updating offers...", true);

        DownloadOffersTask task = new DownloadOffersTask(getActivity().getApplicationContext());
        task.execute();

        return view;

    }


    private class DownloadOffersTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        ProgressDialog mDialog;

        public DownloadOffersTask(Context context){
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

                Log.d("TUTAJ","UTAJ");
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

                  //  Log.d("NAME", esnPartner.getName());
                   // Log.d("WEBISTe", esnPartner.getWebsite());
                  //  Log.d("ID",esnPartner.getId());
                }

                Collections.sort(partners, new Comparator<ESNPartner>() {
                    public int compare(ESNPartner o1, ESNPartner o2) {
                        if (o1.getName() == null || o2.getName() == null)
                            return 0;
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                Log.d("ILOSC",Integer.toString(partners.size()));

                for (ESNPartner p : partners)
                Log.d("PATRNERZY POSORTOWANI", p.getName());

                int i = 0;
                for (ESNPartner p : partners) {
                    expandableListTitle.add(p.getName());
                    ArrayList<ESNPartner> pa = new ArrayList<>();
                    pa.add(p);
                    expandableListDetail.put(i, pa);
                    i++;

                }

               /* Map<String, ArrayList<ESNPartner>> map = new TreeMap<String, ArrayList<ESNPartner>>(expandableListDetail);
                System.out.println("After Sorting:");
                Set set2 = map.entrySet();
                Iterator iterator2 = set2.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry me2 = (Map.Entry)iterator2.next();
                    System.out.print(me2.getKey() + ": ");
                    System.out.println(me2.getValue());
                }*/



                for (String e: expandableListTitle)
                Log.d("TYTUL",e);
            //    Log.d("expansLD",Integer.toString(expandableListDetail.size()));
                progressDialog.dismiss();

                for (Integer part : expandableListDetail.keySet()) {

                    Log.d("NAME", expandableListDetail.get(part).get(0).getName());
                    Log.d("IMAGE", expandableListDetail.get(part).get(0).getImage());
                }

                    return "0";


            }catch(Exception e){
                progressDialog.dismiss();
                System.out.println("Exception : " + e.getMessage());
            }
            progressDialog.dismiss();
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            //mDialog.dismiss();
            //setProgressBarIndeterminateVisibility(false);
           // expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new OffersListAdapter(mContext, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

        }
    }

}
