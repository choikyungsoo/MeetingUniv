package com.example.meetinguniv.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meetinguniv.R;

public class LoginFragment extends Fragment {

    private Button login_btn;
    private TextView join_btn;
    private TextView findingID_btn;
    private TextView findingPW_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.login_btn = view.findViewById(R.id.login_btn);
        this.join_btn = view.findViewById(R.id.joinBTN);
        this.findingID_btn = view.findViewById(R.id.FindingIDBTN);
        this.findingPW_btn = view.findViewById(R.id.FindingPWBTN);

        this.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        this.join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_join_profile);
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