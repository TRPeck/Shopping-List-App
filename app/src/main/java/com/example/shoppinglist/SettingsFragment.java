package com.example.shoppinglist;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

// todo: figure out settings, and how we want to display the window. pretty well everything.

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}