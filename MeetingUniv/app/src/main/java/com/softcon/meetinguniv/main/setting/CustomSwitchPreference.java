package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.softcon.meetinguniv.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class CustomSwitchPreference extends Fragment {
    private View view;
    private Switch customswitch;
    private int onoff;
    private SettingsContentFragment SCF;
    private SettingsContentFragment.CheckedChangeHandler checkedChangeHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_switchpreference, container, false);
        this.customswitch = view.findViewById(R.id.customswitch);

//        this.SCF = new SettingsContentFragment();
//        this.customswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    onoff = 1;
//                } else{
//                    onoff = 0;
//                }
//            }
//        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        this.customswitch = view.findViewById(R.id.customswitch);
//
//        if(this.customswitch != null) {
//            this.customswitch.setOnCheckedChangeListener(checkedChangeHandler);
//            Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "없음", Toast.LENGTH_SHORT).show();
//        }
    }

    public void verfiyonoff(SettingsContentFragment.CheckedChangeHandler checkedChangeHandler) {
//        this.customswitch = view.findViewById(R.id.customswitch);

        if(this.customswitch != null) {
            this.customswitch.setOnCheckedChangeListener(checkedChangeHandler);
        }
//        return this.onoff;
    }
}
