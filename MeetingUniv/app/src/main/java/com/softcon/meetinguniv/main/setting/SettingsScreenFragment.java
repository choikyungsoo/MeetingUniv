package com.softcon.meetinguniv.main.setting;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcon.meetinguniv.R;

public class SettingsScreenFragment extends Fragment {
    private long userID;
    private Bundle bundle1;
    private Bundle bundle2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_screen, container, false);
        this.bundle1 = getArguments();
        this.userID = bundle1.getLong("userID");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        long preferenceValue = prefs.get(key, 14);
        this.bundle2 = new Bundle();
        bundle2.putLong("userID", this.userID);
        SettingsContentFragment settingsContentFragment = new SettingsContentFragment();
        settingsContentFragment.setArguments(bundle2);
//        this.userID = bundle2.getLong("userID");
//        System.out.println(userID);
    }
}