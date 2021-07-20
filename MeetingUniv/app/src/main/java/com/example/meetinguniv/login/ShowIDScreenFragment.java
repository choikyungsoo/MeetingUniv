package com.example.meetinguniv.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meetinguniv.R;

public class ShowIDScreenFragment extends Fragment implements View.OnClickListener{
    private Button login;
    private Button pwM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_show_i_d_screen, container, false);
       this.login = view.findViewById(R.id.moveToLoginBTN);
       this.pwM = view.findViewById(R.id.moveToPwBTN);

       this.login.setOnClickListener(this);
       this.pwM.setOnClickListener(this);
       return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.moveToLoginBTN:
                Navigation.findNavController(v).navigate(R.id.login);
                break;
            case R.id.moveToPwBTN:
                Navigation.findNavController(v).navigate(R.id.findingPWScreenFragment);
                break;
        }
    }
}