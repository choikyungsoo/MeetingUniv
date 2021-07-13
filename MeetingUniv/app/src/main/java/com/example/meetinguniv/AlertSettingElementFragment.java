package com.example.meetinguniv;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class AlertSettingElementFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_alert_setting_element, rootKey);
    }
}


