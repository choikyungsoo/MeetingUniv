package com.example.meetinguniv;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class ServiceInformationContentFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_service_information_content, rootKey);
    }
}