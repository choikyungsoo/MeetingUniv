package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softcon.meetinguniv.R;


public class ChangePhoneVerfiyPWContentFragment extends Fragment implements View.OnClickListener{
    private EditText PhonePW;
    private Button PhoneCheckBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chang_phone_verfiy_p_w_content, container, false);
        this.PhonePW = view.findViewById(R.id.PhonePW);
        this.PhoneCheckBTN = view.findViewById(R.id.PhonecheckBTN);

        this.PhoneCheckBTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PhonecheckBTN:
                gotoChangePhone(v);
                break;
        }
    }

    private void gotoChangePhone(View v) {
        if(this.PhonePW.getText().toString().equals("")){
            //아이디가 존재할 경우와 아이디가 존재하지 않을 경우를 나누어야 함(파이어베이스 연결)
            Toast.makeText(getContext(),"비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            
        }
    }

}