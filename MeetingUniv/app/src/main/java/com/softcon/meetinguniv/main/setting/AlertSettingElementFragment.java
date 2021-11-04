package com.softcon.meetinguniv.main.setting;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.MainFragment;
import com.softcon.meetinguniv.onBackPressedListener;

import java.util.Objects;

public class AlertSettingElementFragment extends PreferenceFragmentCompat implements onBackPressedListener {

    private Preference alarmsong;
    private MainFragment mainFragment;

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        this.alarmsong = this.findPreference("alarm_Setting");
        if (preference.getKey().equals(alarmsong)) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, Settings.System.DEFAULT_NOTIFICATION_URI);

//            String existingValue = getRingtonePreferenceValue(); // TODO
//            if (existingValue != null) {
//                if (existingValue.length() == 0) {
//                    // Select "Silent"
//                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
//                } else {
//                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(existingValue));
//                }
//            } else {
//                // No ringtone has been selected, set to the default
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Settings.System.DEFAULT_NOTIFICATION_URI);
//            }
//
//            startActivityForResult(intent, REQUEST_CODE_ALERT_RINGTONE);
            return true;
        } else {
            return super.onPreferenceTreeClick(preference);
        }
    }

//    private String getRingtonePreferenceValue() {
//        return 2
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE_ALERT_RINGTONE && data != null) {
//            Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//            if (ringtone != null) {
////                setRingtonPreferenceValue(ringtone.toString()); // TODO
//            } else {
//                // "Silent" was selected
////                setRingtonPreferenceValue(""); // TODO
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_alert_setting_element, rootKey);
    }

    @Override
    public void onBackPressed() {

    }
}


