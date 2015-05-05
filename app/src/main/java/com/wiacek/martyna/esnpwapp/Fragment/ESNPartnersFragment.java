package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wiacek.martyna.esnpwapp.Adapter.PartnersAdapter;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.JSONFunctions;
import com.wiacek.martyna.esnpwapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ESNPartnersFragment extends Fragment {


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    ArrayList<ESNPartner> partners = new ArrayList<>();
    ListView listView1;
    ProgressDialog progressDialog;

    public ESNPartnersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_esn_partners, container,
                false);

        listView1 = (ListView) view.findViewById(R.id.listView1);
        progressDialog = ProgressDialog.show(getActivity(), "", "Updating offers...", true);

        new GetESNPartnersFromServerTask().execute();

        return view;
    }


    private class GetESNPartnersFromServerTask extends AsyncTask<Void, Void, String> {


        protected String doInBackground(Void... urls) {
            try {
                Log.d("start","start");
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

                    Log.d("NAME", esnPartner.getName());
                    Log.d("WEBISTe", esnPartner.getWebsite());
                    Log.d("ID",esnPartner.getId());
                }

                 Collections.sort(partners, new Comparator<ESNPartner>() {
                    public int compare(ESNPartner o1, ESNPartner o2) {
                        if (o1.getName() == null || o2.getName() == null)
                            return 0;
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                    progressDialog.dismiss();
                    return "0";


            } catch (Exception e) {
//            progressDialog.dismiss();
                progressDialog.dismiss();
                System.out.println("Exception : " + e.getMessage());
            }
            return "-1";

        }

        protected void onPostExecute(String result) {

            if (!result.equals("-1")) {
                //
                //  Log.d("lalal",Integer.toString(events.size() ));
                PartnersAdapter adapter = new PartnersAdapter(getActivity().getApplicationContext(), R.layout.listview_offers_group, partners);

                listView1.setAdapter(adapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Fragment detailFragment = new FragmentThree();

                        Bundle mBundle = new Bundle();
                        mBundle.putString(FragmentThree.ITEM_NAME, partners.get(position).getName());
                        detailFragment.setArguments(mBundle);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, detailFragment)
                                        // Add this transaction to the back stack
                                .addToBackStack(null)
                                .commit();

                        /*final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Configuration configuration = getActivity().getResources().getConfiguration();
                        int ori = configuration.orientation;
                        fragmentTransaction.replace(R.id.content_frame, detailFragment);

                        if (ori == configuration.ORIENTATION_PORTRAIT){
                            fragmentTransaction.addToBackStack(null);
                        }
                        fragmentTransaction.commit();*/
                        }
                });




            }
        }
    }

}