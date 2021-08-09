package com.example.meetinguniv.main.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.meetinguniv.R;

import androidx.fragment.app.Fragment;

public class CustomSwitchPreference extends Fragment {
    private View view;
    private Switch customswitch;
    private int onoff;
    private SettingsContentFragment SCF;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_switchpreference, container, false);
        this.customswitch = view.findViewById(R.id.customswitch);
        this.customswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getContext(), "체크됨", Toast.LENGTH_SHORT).show();
                    onoff = 1;
                }
                else {
                    Toast.makeText(getContext(), "체크안됨", Toast.LENGTH_SHORT).show();
                    onoff = 2;
                }
            }
        });
        this.SCF = new SettingsContentFragment();
        this.SCF.sendVerfiynum(this.onoff);
        this.SCF.sendCustomView(view);

        return view;
    }
}
