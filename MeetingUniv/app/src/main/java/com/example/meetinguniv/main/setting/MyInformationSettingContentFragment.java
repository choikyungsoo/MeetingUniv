package com.example.meetinguniv.main.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.meetinguniv.R;

public class MyInformationSettingContentFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_my_information_content, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Preference NICK_preference = this.findPreference("P_nickchange");
        Preference PHONE_preference = this.findPreference("P_phonechange");

        NICK_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_myInformationSettingContentFragment_to_changeNickContentFragment);
                return false;
            }
        });
        PHONE_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_myInformationSettingContentFragment_to_changPhoneVerfiyPWContentFragment);
                return false;
            }
        });
    }
}