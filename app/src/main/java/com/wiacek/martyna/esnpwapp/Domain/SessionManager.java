package com.wiacek.martyna.esnpwapp.Domain;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Martyna on 2015-03-20.
 */
public class SessionManager {
    SharedPreferences _sharedPreferences;
    SharedPreferences.Editor _editor;
    Context _context;
    // Sharedpref file name
    private static final String SESSION_NAME = "ESN_PW_APP";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "USER_IS_LOGGED_IN";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "LOGIN";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_FACULTY = "FACULTY";
    public static final String KEY_FIRST_NAME = "FIRST_NAME";
    public static final String KEY_LAST_NAME = "LAST_NAME";
    public static final String KEY_PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String KEY_PROFILE_EMAIL = "PROFILE_EMAIL";
    public static final String KEY_PROFILE_PHONE = "PROFILE_PHONE";
    public static final String KEY_PROFILE_FACEBOOK = "PROFILE_FACEBOOK";
    public static final String KEY_PROFILE_WHATSAPP = "PROFILE_WHATSAPP";
    public static final String KEY_PROFILE_SKYPE = "PROFILE_SKYPE";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        _sharedPreferences = _context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        _editor =_sharedPreferences.edit();
    }

    public void createSession(String username, String password, Integer id) {
        _editor.putString(KEY_USERNAME,username);
        _editor.putString(KEY_PASSWORD,password);
        _editor.putString(KEY_USER_ID,id.toString());
        _editor.commit();
    }

    public String getValueOfLogin() {
        return _sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getValueOfPassword() {
        return _sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getValueOfUserId() { return _sharedPreferences.getString(KEY_USER_ID, ""); }

    public String getValueOfFaculty() { return _sharedPreferences.getString(KEY_FACULTY, "") ;}

    public String getValueOfFirstName() { return _sharedPreferences.getString(KEY_FIRST_NAME, ""); }

    public String getValueOfLastName() { return _sharedPreferences.getString(KEY_LAST_NAME, ""); }

    public String getValueOfProfileImage() { return _sharedPreferences.getString(KEY_PROFILE_IMAGE, "");}

    public String getValueOfProfileEmail() { return _sharedPreferences.getString(KEY_PROFILE_EMAIL, "");}

    public String getValueOfProfilePhone() { return _sharedPreferences.getString(KEY_PROFILE_PHONE, "");}

    public String getValueOfProfileFacebook() { return _sharedPreferences.getString(KEY_PROFILE_FACEBOOK, "");}

    public String getValueOfProfileWhatsapp() { return _sharedPreferences.getString(KEY_PROFILE_WHATSAPP, "");}

    public String getValueOfProfileSkype() { return _sharedPreferences.getString(KEY_PROFILE_SKYPE, "");}

    public void setValueOfEmail(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_EMAIL, s);
        editor.apply();
    }

    public void setValueOfFacebook(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_FACEBOOK, s);
        editor.apply();
    }

    public void setValueOfWhatsapp(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_WHATSAPP, s);
        editor.apply();
    }

    public void setValueOfSkype(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_SKYPE, s);
        editor.apply();
    }

    public void setValueOfPhone(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_PHONE, s);
        editor.apply();
    }

    public void setValueOfFaculty(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_FACULTY, s);
        editor.apply();
    }

    public void setValueOfFirstName(String s) {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME,s);
        editor.apply();
    }

    public void setValueOfLastName(String s) {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_LAST_NAME,s);
        editor.apply();
    }

    public void setValueOfProfileImage(String s) {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_IMAGE,s);
        editor.apply();
    }

    public void setValueOfPassword(String s) {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PASSWORD,s);
        editor.apply();
    }

    public boolean resumeSession(){
        if (_sharedPreferences.contains(KEY_USERNAME) && _sharedPreferences.contains(KEY_PASSWORD))
        {
           // Intent i = new Intent(_context,com.example.martyna.androidphp.UserPage.class);
           // _context.startActivity(i);
            return true;
        }
        return false;
    }

    public void destroySession() {
        _editor.clear();
        _editor.commit();
    }
}
