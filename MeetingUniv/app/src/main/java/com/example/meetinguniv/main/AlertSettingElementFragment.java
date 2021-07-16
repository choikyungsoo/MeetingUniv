package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.meetinguniv.R;

public class AlertSettingElementFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_alert_setting_element, rootKey);
    }
}


