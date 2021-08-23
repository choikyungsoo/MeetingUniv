package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softcon.meetinguniv.R;

public class OpenSourceLicenseInfoContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_source_license_info_content, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        Button ToServiceInfoFromOpenSourceLicenseInfo_BTN = view.findViewById(R.id.ToServiceInfoFromOpenSourceLicenseInfo_BTN);
//        ToServiceInfoFromOpenSourceLicenseInfo_BTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_openSourceLicenseInfoScreenFragment_to_serviceInfoContentFragment);
//            }
//        });
//    }
}