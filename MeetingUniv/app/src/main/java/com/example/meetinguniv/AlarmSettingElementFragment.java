package com.example.meetinguniv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.firebase.messaging.FirebaseMessaging;

public class AlarmSettingElementFragment extends PreferenceFragmentCompat {
    private SwitchPreference alarmOnAndOff;
    private ListPreference alarmMode;
    private Preference alarmSong;

    private String TAG = "FirebaseService";
    private String FCM_TOPIC_NAME = "allowPushNotification";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_alarm_setting_element, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        this.alarmOnAndOff = this.findPreference("alarmOnAndOff");
        this.alarmMode = this.findPreference("alarm_modeSetting");
        this.alarmSong = this.findPreference("alarm_songSetting");

        this.alarmOnAndOff.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (alarmOnAndOff.isChecked()) {
                    // 푸시 알림 수신 O
                    Log.d(TAG, "subscribeToTopic()");
                    Toast.makeText(getContext(), "알림 설정", Toast.LENGTH_SHORT).show();
                    firebaseMessaging.subscribeToTopic(FCM_TOPIC_NAME);

                    // 알림 모드, 알림음 설정 열기
                    alarmMode.setEnabled(true);
                    alarmSong.setEnabled(true);
                } else {
                    // 푸시 알림 수신 X
                    Log.d(TAG, "unsubscribeToTopic()");
                    Toast.makeText(getContext(), "알림 해제", Toast.LENGTH_SHORT).show();
                    firebaseMessaging.unsubscribeFromTopic(FCM_TOPIC_NAME);

                    // 알림 모드, 알림음 설정 닫기
                    alarmMode.setEnabled(false);
                    alarmSong.setEnabled(false);
                }
                return true;
            }
        });

        alarmMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                setAlarmMode(newValue.toString());
                return true;
            }
        });
    }

    public void setAlarmMode(String selectedAlarmMode) {
        if (selectedAlarmMode.equals("0")) {
            Toast.makeText(getContext(), "소리+진동", Toast.LENGTH_SHORT).show();
        } else if (selectedAlarmMode.equals("1")) {
            Toast.makeText(getContext(), "소리만", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "진동만", Toast.LENGTH_SHORT).show();
        }
    }
}