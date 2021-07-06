package com.example.meetinguniv;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsScreenFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}