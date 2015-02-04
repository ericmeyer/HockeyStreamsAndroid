package ericmeyer.hockeystreamssharedandroid.login;

import android.content.SharedPreferences;

import ericmeyer.hockeystreamssharedandroid.login.CurrentUser;
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

    @Override
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getCurrentToken() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public String getCurrentUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

}

