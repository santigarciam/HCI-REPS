package com.example.reps.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.reps.R;

public class AppPreferences {

    private final String AUTH_TOKEN = "auth_token";
    private final String SHARED_RUT_ID = "shared_rut_id";

    private final SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void setSharedRutID(Integer id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_RUT_ID, id);
        editor.apply();
    }

    public Integer getSharedRutID() {
        return sharedPreferences.getInt(SHARED_RUT_ID, -1);
    }
}