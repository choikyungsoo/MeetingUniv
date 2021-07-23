package com.example.meetinguniv.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.meetinguniv.R;

public class SettingsContentFragment extends PreferenceFragmentCompat {

    private Preference service_information;
    private Preference logout;
    private Preference withdrawal;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings_content, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.service_information = this.findPreference("service_information");
        this.logout = this.findPreference("logout");
        this.withdrawal = this.findPreference("withdrawal");


//        Preference preference = this.findPreference("alertSwitch");
//        preference.setOnPreferenceClickListener();
//

        this.service_information.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_settingsContentFragment_to_serviceInfoContentFragment);
                return true;
            }
        });

        this.logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Dialog dialog;
                final String[] str = getResources().getStringArray(R.array.logout);
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("로그아웃");
                b.setItems(str, null);
//                        new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int position){
//                        Log.i("Clicked the AlertDialog" ,str[position]);
//
//                    }
//                }
//                );

                b.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //logout
                    }
                });
                b.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });


                dialog = b.create();
                dialog.show();

                return false;
            }
        });

        this.withdrawal.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Dialog dialog;
                final String[] str = getResources().getStringArray(R.array.withdrawal);
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("회원 탈퇴");
                b.setItems(str, null);
//                        new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int position){
//                        Log.i("Clicked the AlertDialog" ,str[position]);
//
//                    }
//                }
//                );

                b.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //logout
                    }
                });
                b.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });


                dialog = b.create();
                dialog.show();

                return false;
            }
        });

    }
}