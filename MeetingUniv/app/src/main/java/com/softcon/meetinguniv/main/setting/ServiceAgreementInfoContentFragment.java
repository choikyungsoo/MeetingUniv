package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.softcon.meetinguniv.R;

public class ServiceAgreementInfoContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_agreement_info_content, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        Button ToServiceInfoFromServiceAgreementInfo_BTN = view.findViewById(R.id.ToServiceInfoFromServiceAgreementInfo_BTN);
//        ToServiceInfoFromServiceAgreementInfo_BTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_serviceAgreementInfoScreenFragment_to_serviceInfoContentFragment);
//            }
//        });
//    }
}
