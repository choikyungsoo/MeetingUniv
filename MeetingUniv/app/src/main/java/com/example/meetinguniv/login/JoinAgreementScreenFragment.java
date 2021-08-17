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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinAgreementScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinAgreementScreenFragment extends Fragment {
    private CheckBox meetingUnivAgreementCheckbox, personalInfoAgreementCheckbox,
            locationInfoAgreementCheckbox, promotionInfoAgreementCheckbox, allAgreementCheckbox;
    private ScrollView meetingUnivAgreementContent, personalInfoAgreementContent,
            locationInfoAgreementContent, promotionInfoAgreementContent;
    private Button gotoOwnCertificationScreen_BTN;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private JoinPersonalInfoScreenFragment joinPersonalInfoScreenFragment;

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
        this.joinPersonalInfoScreenFragment = new JoinPersonalInfoScreenFragment();

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
            }
        });

        gotoOwnCertificationScreen_BTN = view.findViewById(R.id.gotoOwnCertificationScreen_BTN);
        gotoOwnCertificationScreen_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingUnivAgreementCheckbox.isChecked() && personalInfoAgreementCheckbox.isChecked()
                        && locationInfoAgreementCheckbox.isChecked()) {
                    Navigation.findNavController(view).navigate(R.id.action_joinAgreementScreenFragment_to_joinPhoneVerfiyImportScreenFragment);
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