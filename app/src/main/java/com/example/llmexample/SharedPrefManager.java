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

    // 🔽 Add this singleton block
    private static SharedPrefManager instance;

    public static SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context.getApplicationContext());
        }
        return instance;
    }

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
    public void saveStringList(String key, List<String> list) {
        Set<String> set = new HashSet<>(list);
        editor.putStringSet(key, set);
        editor.apply();
    }

    public Set<String> getStringList(String key) {
        return sharedPreferences.getStringSet(key, new HashSet<>());
    }

    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "User");
    }

    public void saveUpgradeLevel(String level) {
        editor.putString("upgrade_level", level);
        editor.apply();
    }

    public String getUpgradeLevel() {
        return sharedPreferences.getString("upgrade_level", "");
    }
}