package com.wiacek.martyna.esnpwapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wiacek.martyna.esnpwapp.AsyncTask.UpdateUserDataTask;
import com.wiacek.martyna.esnpwapp.Domain.ServerUrl;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;
import com.wiacek.martyna.esnpwapp.ViewEnhancements.ProfilePictureDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class SettingsFragment extends PreferenceFragment {


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        EditTextPreference email = (EditTextPreference) findPreference("email");
        EditTextPreference whatsapp = (EditTextPreference) findPreference("whatsapp");
        EditTextPreference skype = (EditTextPreference) findPreference("skype");
        EditTextPreference facebook = (EditTextPreference) findPreference("facebook");
        EditTextPreference phone = (EditTextPreference) findPreference("phone");
        CheckBoxPreference visible = (CheckBoxPreference) findPreference("checkbox_visibility");

        skype.setTitle("Skype: "+sessionManager.getValueOfProfileSkype());

        whatsapp.setText(sessionManager.getValueOfProfileWhatsapp());
        email.setText(sessionManager.getValueOfProfileEmail());
        skype.setText(sessionManager.getValueOfProfileSkype());
        facebook.setText(sessionManager.getValueOfProfileFacebook());
        phone.setText(sessionManager.getValueOfProfilePhone());

        whatsapp.setTitle("Whatsapp Id:\t"+sessionManager.getValueOfProfileWhatsapp());
        email.setTitle("E-mail:\t"+sessionManager.getValueOfProfileEmail());
        skype.setTitle("Skype Id:\t"+sessionManager.getValueOfProfileSkype());
        facebook.setTitle("Facebook Id:\t"+sessionManager.getValueOfProfileFacebook());
        phone.setTitle("Phone number:\t"+sessionManager.getValueOfProfilePhone());

        if (sessionManager.getValueOfProfileVisibility().equals("1")) {
            visible.setChecked(true);
        }
        else {
            visible.setChecked(false);
        }

        visible.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                Log.d("VALUE",newValue.toString());
                sessionManager.setValueOfSkype(newValue.toString());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "visibility", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        email.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                sessionManager.setValueOfEmail(newValue.toString());
                preference.setTitle("E-mail:\t"+sessionManager.getValueOfProfileEmail());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "email", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        whatsapp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                sessionManager.setValueOfWhatsapp(newValue.toString());
                preference.setTitle("Whatsapp Id:\t"+sessionManager.getValueOfProfileWhatsapp());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "whatsapp", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        skype.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                sessionManager.setValueOfSkype(newValue.toString());
                preference.setTitle("Skype Id:\t"+sessionManager.getValueOfProfileSkype());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "skype", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();

                return true;
            }

        });

        facebook.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                sessionManager.setValueOfFacebook(newValue.toString());
                preference.setTitle("Facebook Id:\t"+sessionManager.getValueOfProfileFacebook());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "facebook", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        phone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                Log.d("TAG", "Working!");
                sessionManager.setValueOfPhone(newValue.toString());
                preference.setTitle("Phone number:\t"+sessionManager.getValueOfProfilePhone());
                UpdateUserDataTask task = new UpdateUserDataTask(getActivity().getApplicationContext());
                task.execute(new String[] { "phone", newValue.toString()});
                Toast.makeText(getActivity().getApplicationContext(), "Value updated!", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        return view;
    }
}