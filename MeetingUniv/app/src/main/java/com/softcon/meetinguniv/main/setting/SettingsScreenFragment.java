package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

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
        this.bundle2 = new Bundle();
        this.userID = bundle1.getLong("userID");
//        bundle2.putLong("userID", this.userID);
//        SettingsContentFragment settingsContentFragment = new SettingsContentFragment();
//        settingsContentFragment.setArguments(bundle2);
        return view;
    }
}