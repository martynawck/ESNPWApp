package com.wiacek.martyna.esnpwapp.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wiacek.martyna.esnpwapp.Adapter.EmergencyAdapter;
import com.wiacek.martyna.esnpwapp.Adapter.EventsAdapter;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ESNEventsFragment extends Fragment {

    ImageView ivIcon;
    TextView tvItemName;
    private ListView listView1;
    private ProgressDialog progressDialog;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private ArrayList<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_esn_events, container,
                false);
        listView1 = (ListView) view.findViewById(R.id.listView1);
        progressDialog = ProgressDialog.show(getActivity(), "","Updating Events...", true);

    //    tvItemName = (TextView) view.findViewById(R.id.frag1_text);

//        tvItemName.setText(getArguments().getString(ITEM_NAME));
        new GetEventsFromServerTask().execute();

        return view;

    }

    private class GetEventsFromServerTask extends AsyncTask<Void, Void, String> {

        private Context mContext;
        HttpPost httppost;
        HttpClient httpclient;
        Fragment fragment;


        protected String doInBackground(Void... urls) {
            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost(ServerUrl.BASE_URL + "events.php");

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);


                if (!response.equalsIgnoreCase("null")) {
                    Log.d("RESP", "NOT NULL");
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        Event e = new Event();
                        e.setId(jsonObject.getString("id"));
                        e.setName(jsonObject.getString("name"));

                        if (jsonObject.has("place")) {
                            JSONObject placeObject = jsonObject.getJSONObject("place");
                            e.setPlace(placeObject.getString("name"));
                            JSONObject locationObject = placeObject.getJSONObject("location");
                            e.setWhere(locationObject.getString("city"));
                            //it has it, do appropriate processing
                        } else {
                            e.setWhere("");
                            e.setPlace("");
                        }

                        if (jsonObject.has("start_time")) {

                            String stringStartTime = jsonObject.getString("start_time");

                            SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                            Date date = new Date();
                            try {
                                date = incomingFormat.parse(stringStartTime);

                            } catch (Exception ex ) {
                                SimpleDateFormat incomingFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                                date = incomingFormat2.parse(stringStartTime);
                            }
                            //    SimpleDateFormat outgoingFormat = new SimpleDateFormat(" EEEE, dd MMMM yyyy, HH:mm", Locale.ENGLISH);
                            e.setStartTime(date);
                            //   outgoingFormat.

                            //     Log.d("lalala",outgoingFormat.format(date));
                        }

                        if (jsonObject.has("owner")) {
                            JSONObject ownerObject = jsonObject.getJSONObject("owner");
                            e.setOwner(ownerObject.getString("name"));
                            e.setOwnerId(ownerObject.getString("id"));
                        } else {
                            e.setOwner("");
                        }

                        if (jsonObject.has("cover")) {
                            JSONObject coverObject = jsonObject.getJSONObject("cover");
                            e.setImageUrl(coverObject.getString("source"));

                        } else {
                            e.setImageUrl("");
                        }

                        Log.d("asdad",e.getName());
                        events.add(e);

                    }

                    Collections.sort(events, new Comparator<Event>() {
                        public int compare(Event o1, Event o2) {
                            if (o1.getStartTime() == null || o2.getStartTime() == null)
                                return 0;
                            return o1.getStartTime().compareTo(o2.getStartTime());
                        }
                    });

                    progressDialog.dismiss();
                    return "0";
                }

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
                EventsAdapter adapter = new EventsAdapter(getActivity().getApplicationContext(), R.layout.listview_item_row_event, events);

                listView1.setAdapter(adapter);

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String eventId = events.get(position).getId();
                        try {
                            //fb://messaging/
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://event/"+ eventId));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://www.facebook.com/"+ eventId));
                            startActivity(intent);
                        }
                    }
                });




            }
        }
    }
}