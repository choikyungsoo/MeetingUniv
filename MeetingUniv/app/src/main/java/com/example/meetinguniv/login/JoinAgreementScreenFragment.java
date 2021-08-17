package com.example.meetinguniv.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.meetinguniv.R;

public class JoinAgreementScreenFragment extends Fragment {
    private CheckBox meetingUnivAgreementCheckbox, personalInfoAgreementCheckbox,
            locationInfoAgreementCheckbox, promotionInfoAgreementCheckbox, allAgreementCheckbox;
    private ScrollView meetingUnivAgreementContent, personalInfoAgreementContent,
            locationInfoAgreementContent, promotionInfoAgreementContent;
    private Button gotoOwnCertificationScreen_BTN;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private JoinPersonalInfoScreenFragment joinPersonalInfoScreenFragment;

    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.joinPersonalInfoScreenFragment = new JoinPersonalInfoScreenFragment();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_agreement_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.user = new User();

        meetingUnivAgreementCheckbox = view.findViewById(R.id.meetingUnivAgreementCheckbox);
        meetingUnivAgreementContent = view.findViewById(R.id.meetingUnivAgreementContent);
        personalInfoAgreementCheckbox = view.findViewById(R.id.personalInfoAgreementCheckbox);
        personalInfoAgreementContent = view.findViewById(R.id.personalInfoAgreementContent);
        locationInfoAgreementCheckbox = view.findViewById(R.id.locationInfoAgreementCheckbox);
        locationInfoAgreementContent = view.findViewById(R.id.locationInfoAgreementContent);
        promotionInfoAgreementCheckbox = view.findViewById(R.id.promotionInfoAgreementCheckbox);
        promotionInfoAgreementContent = view.findViewById(R.id.promotionInfoAgreementContent);
        allAgreementCheckbox = view.findViewById(R.id.allAgreementCheckbox);

        this.user.setMeetingUnivAgreementCheckbox(meetingUnivAgreementCheckbox.isChecked());
        this.user.setPersonalInfoAgreementCheckbox(personalInfoAgreementCheckbox.isChecked());
        this.user.setLocationInfoAgreementCheckbox(locationInfoAgreementCheckbox.isChecked());
        this.user.setPromotionInfoAgreementCheckbox(promotionInfoAgreementCheckbox.isChecked());

        meetingUnivAgreementCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingUnivAgreementCheckbox.isChecked()) {
                    meetingUnivAgreementContent.setVisibility(View.GONE);
                    if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                        && locationInfoAgreementCheckbox.isChecked() && promotionInfoAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(true);
                    }
                } else {
                    meetingUnivAgreementContent.setVisibility(View.VISIBLE);
                    if (allAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(false);
                    }
                }
                user.setMeetingUnivAgreementCheckbox(meetingUnivAgreementCheckbox.isChecked());
            }
        });
        personalInfoAgreementCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personalInfoAgreementCheckbox.isChecked()) {
                    personalInfoAgreementContent.setVisibility(View.GONE);
                    if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                            && locationInfoAgreementCheckbox.isChecked() && promotionInfoAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(true);
                    }
                } else {
                    personalInfoAgreementContent.setVisibility(View.VISIBLE);
                    if (allAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(false);
                    }
                }
                user.setPersonalInfoAgreementCheckbox(personalInfoAgreementCheckbox.isChecked());
            }
        });
        locationInfoAgreementCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationInfoAgreementCheckbox.isChecked()) {
                    locationInfoAgreementContent.setVisibility(View.GONE);
                    if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                            && locationInfoAgreementCheckbox.isChecked() && promotionInfoAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(true);
                    }
                } else {
                    locationInfoAgreementContent.setVisibility(View.VISIBLE);
                    if (allAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(false);
                    }
                }
                user.setLocationInfoAgreementCheckbox(locationInfoAgreementCheckbox.isChecked());
            }
        });
        promotionInfoAgreementCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (promotionInfoAgreementCheckbox.isChecked()) {
                    promotionInfoAgreementContent.setVisibility(View.GONE);
                    if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                            && locationInfoAgreementCheckbox.isChecked() && promotionInfoAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(true);
                    }
                } else {
                    promotionInfoAgreementContent.setVisibility(View.VISIBLE);
                    if (allAgreementCheckbox.isChecked()) {
                        allAgreementCheckbox.setChecked(false);
                    }
                }
                user.setPromotionInfoAgreementCheckbox(promotionInfoAgreementCheckbox.isChecked());
            }
        });

        allAgreementCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allAgreementCheckbox.isChecked()) {
                    meetingUnivAgreementCheckbox.setChecked(true);
                    personalInfoAgreementCheckbox.setChecked(true);
                    locationInfoAgreementCheckbox.setChecked(true);
                    promotionInfoAgreementCheckbox.setChecked(true);

                    meetingUnivAgreementContent.setVisibility(View.GONE);
                    personalInfoAgreementContent.setVisibility(View.GONE);
                    locationInfoAgreementContent.setVisibility(View.GONE);
                    promotionInfoAgreementContent.setVisibility(View.GONE);
                } else {
                    meetingUnivAgreementCheckbox.setChecked(false);
                    personalInfoAgreementCheckbox.setChecked(false);
                    locationInfoAgreementCheckbox.setChecked(false);
                    promotionInfoAgreementCheckbox.setChecked(false);

                    meetingUnivAgreementContent.setVisibility(View.VISIBLE);
                    personalInfoAgreementContent.setVisibility(View.VISIBLE);
                    locationInfoAgreementContent.setVisibility(View.VISIBLE);
                    promotionInfoAgreementContent.setVisibility(View.VISIBLE);
                }
                user.setMeetingUnivAgreementCheckbox(meetingUnivAgreementCheckbox.isChecked());
                user.setPersonalInfoAgreementCheckbox(personalInfoAgreementCheckbox.isChecked());
                user.setLocationInfoAgreementCheckbox(locationInfoAgreementCheckbox.isChecked());
                user.setPromotionInfoAgreementCheckbox(promotionInfoAgreementCheckbox.isChecked());
            }
        });

        gotoOwnCertificationScreen_BTN = view.findViewById(R.id.gotoOwnCertificationScreen_BTN);
        gotoOwnCertificationScreen_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                        && locationInfoAgreementCheckbox.isChecked()) {
                    Navigation.findNavController(view).navigate(R.id.action_joinAgreementScreenFragment_to_joinPersonalInfoScreenFragment);
//                    gotojoinPersonalInfoScreen();
                } else {
                    Toast.makeText(getContext(), "필수 약관에 모두 동의하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void gotojoinPersonalInfoScreen() {
//        this.fragmentManager = this.getActivity().getSupportFragmentManager();
//        this.fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.joinAgreementScreen, this.joinPersonalInfoScreenFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}