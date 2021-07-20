package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinAgreementScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinAgreementScreenFragment extends Fragment {
    CheckBox meetingUnivAgreementCheckbox, personalInfoAgreementCheckbox,
            locationInfoAgreementCheckbox, promotionInfoAgreementCheckbox, allAgreementCheckbox;
    ScrollView meetingUnivAgreementContent, personalInfoAgreementContent,
            locationInfoAgreementContent, promotionInfoAgreementContent;
    Button gotoOwnCertificationScreen_BTN;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JoinAgreementScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinAgreementScreenFragment newInstance(String param1, String param2) {
        JoinAgreementScreenFragment fragment = new JoinAgreementScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_agreement_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        meetingUnivAgreementCheckbox = view.findViewById(R.id.meetingUnivAgreementCheckbox);
        meetingUnivAgreementContent = view.findViewById(R.id.meetingUnivAgreementContent);
        personalInfoAgreementCheckbox = view.findViewById(R.id.personalInfoAgreementCheckbox);
        personalInfoAgreementContent = view.findViewById(R.id.personalInfoAgreementContent);
        locationInfoAgreementCheckbox = view.findViewById(R.id.locationInfoAgreementCheckbox);
        locationInfoAgreementContent = view.findViewById(R.id.locationInfoAgreementContent);
        promotionInfoAgreementCheckbox = view.findViewById(R.id.promotionInfoAgreementCheckbox);
        promotionInfoAgreementContent = view.findViewById(R.id.promotionInfoAgreementContent);
        allAgreementCheckbox = view.findViewById(R.id.allAgreementCheckbox);

        if (meetingUnivAgreementCheckbox.isChecked()) {
            meetingUnivAgreementContent.setVisibility(View.GONE);
        } else if (!meetingUnivAgreementCheckbox.isChecked()) {
            meetingUnivAgreementContent.setVisibility(View.VISIBLE);
        }
        if (personalInfoAgreementCheckbox.isChecked()) {
            personalInfoAgreementContent.setVisibility(View.GONE);
        } else if (!personalInfoAgreementCheckbox.isChecked()) {
            personalInfoAgreementContent.setVisibility(View.VISIBLE);
        }
        if (locationInfoAgreementCheckbox.isChecked()) {
            locationInfoAgreementContent.setVisibility(View.GONE);
        } else if (!locationInfoAgreementCheckbox.isChecked()) {
            locationInfoAgreementContent.setVisibility(View.VISIBLE);
        }
        if (promotionInfoAgreementCheckbox.isChecked()) {
            promotionInfoAgreementContent.setVisibility(View.GONE);
        } else if (!promotionInfoAgreementCheckbox.isChecked()) {
            promotionInfoAgreementContent.setVisibility(View.VISIBLE);
        }
        if (allAgreementCheckbox.isChecked()) {
            meetingUnivAgreementCheckbox.setChecked(true);
            personalInfoAgreementCheckbox.setChecked(true);
            locationInfoAgreementCheckbox.setChecked(true);
            promotionInfoAgreementCheckbox.setChecked(true);

            meetingUnivAgreementContent.setVisibility(View.GONE);
            personalInfoAgreementContent.setVisibility(View.GONE);
            locationInfoAgreementContent.setVisibility(View.GONE);
            promotionInfoAgreementContent.setVisibility(View.GONE);
        }


    }
}