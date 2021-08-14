package com.example.meetinguniv.main.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

public class ServiceInfoContentFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_service_info_content, rootKey);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Preference service_agreement_info = this.findPreference("service_agreement_info");
        service_agreement_info.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_serviceAgreementInfoScreenFragment);
                return true;
            }
        });
        Preference personal_info_agreement = this.findPreference("personal_info_agreement");
        personal_info_agreement.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_personalInfoAgreementScreenFragment);
                return true;
            }
        });
        Preference location_info_agreement = this.findPreference("location_info_agreement");
        location_info_agreement.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_locationInfoAgreementScreenFragment);
                return true;
            }
        });
        Preference open_source_license_info = this.findPreference("open_source_license_info");
        open_source_license_info.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_openSourceLicenseInfoScreenFragment);
                return true;
            }
        });
//        Preference ToSettingContentFromServiceInfo_BTN = this.findPreference("ToSettingContentFromServiceInfo_BTN");
//        ToSettingContentFromServiceInfo_BTN.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_settingsContentFragment);
//                return true;
//            }
//        });
//        Button button = view.findViewById(R.id.button13);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_serviceInfoContentFragment_to_settingsContentFragment);
//            }
//        });
    }
}