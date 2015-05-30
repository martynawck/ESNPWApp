package com.wiacek.martyna.esnpwapp.Domain;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Martyna on 2015-03-20.
 */
public class SessionManager {
    private final SharedPreferences _sharedPreferences;
    private final SharedPreferences.Editor _editor;
    private static final String SESSION_NAME = "ESN_PW_APP";

    private static final String IS_LOGIN = "USER_IS_LOGGED_IN";
    private static final String KEY_USERNAME = "LOGIN";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_FACULTY = "FACULTY";
    private static final String KEY_FIRST_NAME = "FIRST_NAME";
    private static final String KEY_LAST_NAME = "LAST_NAME";
    private static final String KEY_PROFILE_IMAGE = "PROFILE_IMAGE";
    private static final String KEY_PROFILE_EMAIL = "PROFILE_EMAIL";
    private static final String KEY_PROFILE_PHONE = "PROFILE_PHONE";
    private static final String KEY_PROFILE_FACEBOOK = "PROFILE_FACEBOOK";
    private static final String KEY_PROFILE_WHATSAPP = "PROFILE_WHATSAPP";
    private static final String KEY_PROFILE_SKYPE = "PROFILE_SKYPE";
    private static final String KEY_VISIBILITY = "PROFILE_VISIBILITY";


    public SessionManager(Context context){
        Context _context = context;
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

    public String getValueOfProfileVisibility() { return _sharedPreferences.getString(KEY_VISIBILITY, "");}


    public void setValueOfEmail(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_PROFILE_EMAIL, s);
        editor.apply();
    }

    public void setValueOfProfileVisibility(String s) {

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString(KEY_VISIBILITY, s);
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
        return _sharedPreferences.contains(KEY_USERNAME) && _sharedPreferences.contains(KEY_PASSWORD);
    }

    public void destroySession() {
        _editor.clear();
        _editor.commit();
    }
}
