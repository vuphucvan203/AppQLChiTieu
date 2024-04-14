package com.example.QLChiTieu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManagement {
    //Share preference
    private SharedPreferences mSharedPreference;
    //Editor for Shared preference
    private SharedPreferences.Editor mEditor;
    //context
    private Context mContext;
    //Shared pref mode
    int PRIVATE_MODE;
    //Shared pref name
    private static final String PREF_NAME = "SharedPrefName";
    //Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWOrD = "password";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_USERNAME= "username";
    public static final String KEY_CURRENCY= "currency";
    public SessionManagement(Context mContext) {
        this.mContext = mContext;
        mSharedPreference = this.mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreference.edit();
    }

    public void createLoginSession(String id, String email, String username, String password,String currency){
// Storing login value as TRUE
        mEditor.putBoolean(IS_LOGIN, true);
// Storing email
        mEditor.putString(KEY_EMAIL, email);
// Storing password
        mEditor.putString(KEY_PASSWOrD, password);
        mEditor.putString(KEY_ID_USER, id);

        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_CURRENCY, currency);
        mEditor.commit();
    }

    public void updatecurr(String currency){
        mEditor.putString(KEY_CURRENCY, currency);
        mEditor.apply();
    }


    public HashMap<String, String> getUserInformation(){
        HashMap<String, String> user = new HashMap<String, String>();
// user email
        user.put(KEY_EMAIL, mSharedPreference.getString(KEY_EMAIL, null));
// user password
        user.put(KEY_PASSWOrD, mSharedPreference.getString(KEY_PASSWOrD, null));
        user.put(KEY_ID_USER, mSharedPreference.getString(KEY_ID_USER, null));
        user.put(KEY_USERNAME, mSharedPreference.getString(KEY_USERNAME, null));
        user.put(KEY_CURRENCY, mSharedPreference.getString(KEY_CURRENCY, null));

// return user
        return user;
    }public boolean isLoggedIn(){
        return mSharedPreference.getBoolean(IS_LOGIN, false);
    }
    public void checkIsLogin() {
// Check login status
        if (!isLoggedIn()) {
// user is not logged in redirect to UpdateNote
            Intent i = new Intent(mContext, Login.class);// Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }
    public void logoutUser(){
// Clearing all data from Shared Preferences
        mEditor.clear();
        mEditor.commit();
// After logout redirect user to Login Activity
        Intent i = new Intent(mContext, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
    public  void delete(){
        mEditor.clear();
        mEditor.commit();
    }

}

