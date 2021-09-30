package com.example.smartparking.storage;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartparking.models.User;

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }


    public void saveUser(User user) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getPk());
        editor.putString("full_name", user.getFull_name());
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putString("role", user.getRole());
        editor.putString("phone_number", user.getPhone_number());
        editor.putBoolean("Logged in", true);
        editor.apply();


    }

    public boolean isLoggedIn() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Logged in", false);

    }

    public User getUser() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("full_name", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone_number", null),
                sharedPreferences.getString("role", null));

    }

    public void logout() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}