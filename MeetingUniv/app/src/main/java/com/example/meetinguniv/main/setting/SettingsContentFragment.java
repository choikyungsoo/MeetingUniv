package com.example.meetinguniv.main.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.meetinguniv.R;
import com.example.meetinguniv.login.LoginActivity;

public class SettingsContentFragment extends PreferenceFragmentCompat {

    private Preference my_information;
    private Preference alarm;
    private Preference alarmTest;
    private Preference service_information;
    private Preference logout;
    private Preference withdrawal;

    private CustomSwitchPreference CSP;
    private int onoff;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings_content, rootKey);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.my_information = this.findPreference("my_information");
        this.alarmTest = this.findPreference("alarmsetting_test");
        this.service_information = this.findPreference("service_information");
        this.logout = this.findPreference("logout");
        this.withdrawal = this.findPreference("withdrawal");

        CheckedChangeHandler checkedChangeHandler = new CheckedChangeHandler();
        this.CSP = new CustomSwitchPreference();

        this.CSP.verfiyonoff(checkedChangeHandler);

//        this.alarmTest = this.findPreference("alarmsettingtest");
//        this.alarmTest.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                if (alarmTest.isChecked()) {
//                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "0입니다", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });


//        if(this.onoff == 1){
//            Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//        } else{
//            Toast.makeText(getContext(), "0입니다", Toast.LENGTH_SHORT).show();
//        }
        this.my_information.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_settingsContentFragment_to_myInformationSettingContentFragment);
                return true;
            }
        });

        ///////////////////
        this.alarmTest.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(view).navigate(R.id.action_settingsContentFragment_to_alarmSettingElementFragment3);
                return true;
            }
        });
        //////////////////
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
                b.setMessage("로그아웃 하시겠습니까?");

                b.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //logout
                        //turn back to LoginActivity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

                return true;
            }
        });

        this.withdrawal.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Dialog dialog;
                final String[] str = getResources().getStringArray(R.array.withdrawal);
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("회원 탈퇴");
                b.setMessage("회원 탈퇴 하시겠습니까?");

                b.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //withDrawal
                        //turn back to LoginActivity
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

                return true;
            }
        });

    }

    public class CheckedChangeHandler implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                onoff = 1;
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
            } else {
                onoff = 0;
                Toast.makeText(getContext(), "0입니다", Toast.LENGTH_SHORT).show();
            }
        }
    }
}