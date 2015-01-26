package ericmeyer.hockeystreamsandroid.login;

import android.content.SharedPreferences;

import hockeystreamsclient.login.Response;

public class LocalCurrentUser implements CurrentUser {

    public static final String USERNAME = "username";
    public static final String TOKEN = "token";
    public static final String CURRENT_USER_TAG = "current_user";
    private final SharedPreferences sharedPreferences;

    public LocalCurrentUser(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void put(Response loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, loginResponse.getToken());
        editor.putString(USERNAME, loginResponse.getUsername());
        editor.commit();
    }

    public String getCurrentToken() {
        return sharedPreferences.getString(TOKEN, "DEFAULT");
    }

    public String getCurrentUsername() {
        return sharedPreferences.getString(USERNAME, "DEFAULT");
    }

}

