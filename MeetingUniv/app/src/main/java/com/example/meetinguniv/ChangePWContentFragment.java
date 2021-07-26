package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePWContentFragment extends Fragment implements View.OnClickListener {

    private EditText F_ID;
    private Button F_nextBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_p_w_content, container, false);
        this.F_ID = view.findViewById(R.id.newPW);
        this.F_nextBTN = view.findViewById(R.id.PW_changeBTN);

        this.F_nextBTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PW_changeBTN:
                F_checkID(v);
                break;
        }
    }

    private void F_checkID(View v) {
        if(this.F_ID.getText().toString().equals("")){
            //아이디가 존재할 경우와 아이디가 존재하지 않을 경우를 나누어야 함(파이어베이스 연결)
            Toast.makeText(getContext(),"아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            Navigation.findNavController(v).navigate(R.id.action_changePWContentFragment_to_changePWVerifyContentFragment);
        }
    }

}