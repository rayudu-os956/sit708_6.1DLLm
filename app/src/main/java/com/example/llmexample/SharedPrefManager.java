package com.example.llmexample;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPrefManager {

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save a single string value
    public void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    // Retrieve a single string value
    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save a list of strings
    public void saveStringList(String key, List<String> list) {
        Set<String> set = new HashSet<>(list);
        editor.putStringSet(key, set);
        editor.apply();
    }

    // Retrieve a list of strings
    public Set<String> getStringList(String key) {
        return sharedPreferences.getStringSet(key, new HashSet<>());
    }

    // Save username
    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Retrieve username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "User");
    }
}