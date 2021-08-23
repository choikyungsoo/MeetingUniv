package com.softcon.meetinguniv.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.MainActivity;

public class KakaoLoginFragment extends Fragment {

    private Button login_btn;
    private TextView join_btn;
    private TextView findingID_btn;
    private TextView findingPW_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kakao_login, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.login_btn = view.findViewById(R.id.login_btn);
        this.join_btn = view.findViewById(R.id.joinBTN);
        this.findingID_btn = view.findViewById(R.id.FindingIDBTN);
        this.findingPW_btn = view.findViewById(R.id.FindingPWBTN);

        this.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        this.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_joinAgreementScreenFragment);
            }
        });

        this.findingID_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_findIDScreenFragment);
            }
        });

        this.findingPW_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_findingPWScreenFragment);
            }
        });
    }
}