package com.example.meetinguniv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ServiceAgreementInfoContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_agreement_info_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
