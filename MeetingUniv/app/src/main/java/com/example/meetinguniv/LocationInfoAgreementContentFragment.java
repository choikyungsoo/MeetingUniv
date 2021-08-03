package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LocationInfoAgreementContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_info_agreement_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button ToServiceInfoFromLocationInfoAgreement_BTN = view.findViewById(R.id.ToServiceInfoFromLocationInfoAgreement_BTN);
        ToServiceInfoFromLocationInfoAgreement_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_locationInfoAgreementScreenFragment_to_serviceInfoContentFragment);
            }
        });
    }
}