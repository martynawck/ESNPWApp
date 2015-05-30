package com.wiacek.martyna.esnpwapp.Fragment;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiacek.martyna.esnpwapp.AsyncTask.UpdateUserDataTask;
import com.wiacek.martyna.esnpwapp.Domain.SessionManager;
import com.wiacek.martyna.esnpwapp.R;


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
                sessionManager.setValueOfSkype(newValue.toString());
                UpdateUserDataTask task = new UpdateUserDataTask("visibility", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        email.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                sessionManager.setValueOfEmail(newValue.toString());
                preference.setTitle("E-mail:\t"+sessionManager.getValueOfProfileEmail());
                UpdateUserDataTask task = new UpdateUserDataTask("email", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        whatsapp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                sessionManager.setValueOfWhatsapp(newValue.toString());
                preference.setTitle("Whatsapp Id:\t"+sessionManager.getValueOfProfileWhatsapp());
                UpdateUserDataTask task = new UpdateUserDataTask("whatsapp", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        skype.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                sessionManager.setValueOfSkype(newValue.toString());
                preference.setTitle("Skype Id:\t"+sessionManager.getValueOfProfileSkype());
                UpdateUserDataTask task = new UpdateUserDataTask("skype", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        facebook.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                sessionManager.setValueOfFacebook(newValue.toString());
                preference.setTitle("Facebook Id:\t"+sessionManager.getValueOfProfileFacebook());
                UpdateUserDataTask task = new UpdateUserDataTask("facebook", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        phone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                sessionManager.setValueOfPhone(newValue.toString());
                preference.setTitle("Phone number:\t"+sessionManager.getValueOfProfilePhone());
                UpdateUserDataTask task = new UpdateUserDataTask( "phone", newValue.toString(), getActivity().getApplicationContext());
                task.runVolley();
                return true;
            }

        });

        return view;
    }
}