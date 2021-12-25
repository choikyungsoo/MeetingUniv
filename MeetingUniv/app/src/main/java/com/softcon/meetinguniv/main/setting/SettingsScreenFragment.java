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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_screen, container, false);
//        userID = getArguments().getLong("userID");
//        Bundle bundle = new Bundle();
//        bundle.putLong("userID", this.userID);

        return view;
    }
}