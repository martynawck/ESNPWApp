package com.wiacek.martyna.esnpwapp.dummy;

import android.content.Context;

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
import java.util.Map;

import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ESNPartnerContent {

   // Context _context;
    /**
     * An array of sample (dummy) items.
     */
    public static List<EsnPartner> ITEMS = new ArrayList<EsnPartner>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, EsnPartner> ITEM_MAP = new HashMap<String, EsnPartner>();

    public static void setContext(Context context) {
        //_context = new Context();

        try{

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ServerUrl.BASE_URL + "partners.php");
            HttpResponse response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response2 = httpclient.execute(httppost, responseHandler);

            if(!response2.equalsIgnoreCase("null")){
                JSONArray jsonArray = new JSONArray(response2);
                JSONObject jsonObject;

                ITEM_MAP.clear();
                ITEMS.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String post = jsonObject.getString("post");
                    JSONObject postObject = new JSONObject(post);
                    String id = postObject.getString("id");
                    String name = postObject.getString("name");
                    String description = postObject.getString("description");
                    String website = postObject.getString("image_name");
                    addItem(new EsnPartner(id,name,website,description));
                }

            }

        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
        }

    }
    /*static {
        // Add 3 sample items.
        addItem(new EsnPartner("1", "Pizza Hut", "www.w.pl", "dddd"));
        addItem(new EsnPartner("2", "Dominium","www.w.pl", "dddd"));
        addItem(new EsnPartner("3", "Tralala","www.w.pl", "dddd"));
    }*/

    private static void addItem(EsnPartner item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class EsnPartner {
        public String id;
        public String name;
        public String website;
        public String description;

        public EsnPartner(String id, String name, String website, String description) {
            this.id = id;
            this.name = name;
            this.website = website;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
