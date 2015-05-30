package com.wiacek.martyna.esnpwapp;

/**
 * Created by Martyna on 2015-04-19.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.wiacek.martyna.esnpwapp.Domain.Buddy;
import com.wiacek.martyna.esnpwapp.Domain.ESNPartner;
import com.wiacek.martyna.esnpwapp.Domain.Event;
import com.wiacek.martyna.esnpwapp.Domain.FacultyNames;
import com.wiacek.martyna.esnpwapp.Domain.FunMapCategory;
import com.wiacek.martyna.esnpwapp.Domain.FunMapPlace;
import com.wiacek.martyna.esnpwapp.Domain.Student;
import com.wiacek.martyna.esnpwapp.Domain.TodoTask;

public class JSONFunctions {

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is != null ? is : null, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }

    public static ESNPartner JSONToPartner(JSONObject o) {

        try {

            ESNPartner esnPartner = new ESNPartner();
            esnPartner.setId(o.getString("id"));
            esnPartner.setName(o.getString("name"));
            esnPartner.setDescription(o.getString("description"));
            esnPartner.setDiscount(o.getString("discount"));
            esnPartner.setHowToUse(o.getString("how_to_use"));
            esnPartner.setWebsite(o.getString("website"));
            esnPartner.setImage(o.getString("img_url"));
            return  esnPartner;

        } catch (Exception e) {
            return null;
        }
    }

    public static FunMapPlace JSONToFunMapPlace(JSONObject o) {
        try {
            FunMapPlace place = new FunMapPlace();
            if (o.has("id"))
                place.setId(o.getString("id"));
             else
                place.setId("");
            place.setName(o.getString("name"));
            place.setDescription(o.getString("description"));
            place.setCoordinate(new LatLng(Double.parseDouble(o.getString("x_coordinate")),
                    Double.parseDouble(o.getString("y_coordinate"))));
            return place;
        } catch (Exception e){
            return  null;
        }
    }

    public static FunMapCategory JSONToFunMapCategory (JSONObject o) {
        try {
            FunMapCategory category = new FunMapCategory();
            category.setId(o.getString("id"));
            category.setName(o.getString("name"));
            return category;
        } catch (Exception e){
            return  null;
        }
    }

    public static Buddy JSONToBuddy (JSONObject o) {
        try {
            Buddy buddy = new Buddy();
            buddy.setFirstname(o.getString("firstname"));
            buddy.setLastname(o.getString("lastname"));
            buddy.setEmail(o.getString("email"));
            buddy.setSkype(o.getString("skype_id"));
            buddy.setFacebook(o.getString("facebook_id"));
            buddy.setPhone(o.getString("phone_number"));
            buddy.setWhatsapp(o.getString("whatsapp_id"));
            return  buddy;
        } catch (Exception e){
            return  null;
        }
    }

    public static ArrayList<String> JSONToStudentDetails (JSONObject o) {
        try {
            ArrayList<String> details = new ArrayList<>();
            details.add(o.getString("email"));
            details.add(o.getString("phone_number"));
            details.add(o.getString("skype_id"));
            details.add(o.getString("facebook_id"));
            details.add(o.getString("whatsapp_id"));
            return  details;
        } catch (Exception e){
            return  null;
        }
    }

    public static Student JSONToStudent (JSONObject o) {
        try {
            Student student = new Student();
            String faculty = o.getString("faculty");
            String longFaculty = new FacultyNames().returnLongName(faculty);
            student.setFaculty(longFaculty);
            student.setFirstname(o.getString("first_name"));
            student.setLastname(o.getString("last_name"));
            student.setImgUrl(o.getString("image"));
            student.setId(o.getString("id"));
            return student;
        } catch (Exception e){
            return  null;
        }
    }

    public static TodoTask JSONToToDo (JSONObject o) {
        try {
            TodoTask todoTask = new TodoTask();
            todoTask.setValue(o.getInt("value"));
            todoTask.setType(o.getInt("type"));
            todoTask.setDescription(o.getString("task_name"));
            todoTask.setTodo_id(o.getInt("id"));
            return todoTask;
        } catch (Exception e){
            return  null;
        }
    }

    public static Event JSONToEvent (JSONObject o) {
        try {
            Event e = new Event();
            e.setId(o.getString("id"));
            e.setName(o.getString("name"));

            if (o.has("place")) {
                JSONObject placeObject = o.getJSONObject("place");
                e.setPlace(placeObject.getString("name"));
                if (placeObject.has("city")) {
                    JSONObject locationObject = placeObject.getJSONObject("location");
                    e.setWhere(locationObject.getString("city"));
                } else {
                    e.setWhere("");
                }
            } else {
                e.setPlace("");
                e.setWhere("");
            }

            if (o.has("start_time")) {
                String stringStartTime = o.getString("start_time");
                SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                Date date;
                try {
                    date = incomingFormat.parse(stringStartTime);

                } catch (Exception ex ) {
                    SimpleDateFormat incomingFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    date = incomingFormat2.parse(stringStartTime);
                }
                e.setStartTime(date);
            }

            if (o.has("owner")) {
                JSONObject ownerObject = o.getJSONObject("owner");
                e.setOwner(ownerObject.getString("name"));
                e.setOwnerId(ownerObject.getString("id"));
            } else {
                e.setOwner("");
            }

            if (o.has("cover")) {
                JSONObject coverObject = o.getJSONObject("cover");
                e.setImageUrl(coverObject.getString("source"));
            } else {
                e.setImageUrl("");
            }
                return e;
        } catch (Exception e){
            return  null;
        }
    }
}