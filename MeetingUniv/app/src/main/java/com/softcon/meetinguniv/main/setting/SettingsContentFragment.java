package com.softcon.meetinguniv.main.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.sdk.user.UserApiClient;
import com.softcon.meetinguniv.Intro;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.LoginActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingsContentFragment extends PreferenceFragmentCompat {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private Preference my_information;
    private Preference alarm;
    private Preference alarmTest;
    private Preference service_information;
    private Preference logout;
    private Preference withdrawal;

    private CustomSwitchPreference CSP;
    private int onoff;

    private long userID;
    private Bundle bundle;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.fragment_settings_content, rootKey);
//        this.bundle = getArguments();
//        this.userID = bundle.getLong("userID");
    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        this.bundle = getArguments();
//        this.userID = bundle.getLong("userID");
//        View view = inflater.inflate(R.xml.fragment_settings_content, container, false);
//        return view;
////        return super.onCreateView(inflater, container, savedInstanceState);
//    }

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

//        this.bundle = getArguments();
//        this.userID = bundle.getLong("userID");
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

//                MyInformationSettingContentFragment msc = new MyInformationSettingContentFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//                transaction.replace(R.id.screenfragmentContainer, msc);
//                transaction.addToBackStack(null);
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
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Intent intent = new Intent(getActivity(), Intro.class);
                                startActivity(intent);
                                getActivity().finish();
                                return null;
                            }
                        });
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