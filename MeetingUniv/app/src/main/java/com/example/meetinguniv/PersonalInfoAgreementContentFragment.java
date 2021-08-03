package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PersonalInfoAgreementContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info_agreement_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button ToServiceInfoFromPersonalInfoAgreement_BTN = view.findViewById(R.id.ToServiceInfoFromPersonalInfoAgreement_BTN);
        ToServiceInfoFromPersonalInfoAgreement_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_personalInfoAgreementScreenFragment_to_serviceInfoContentFragment);
            }
        });
    }
}