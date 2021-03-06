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


public class ChangeNickVerfiyPWContentFragment extends Fragment implements View.OnClickListener{

    private EditText NickPW;
    private Button NickCheckBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chang_nick_verfiy_p_w_content, container, false);
        this.NickPW = view.findViewById(R.id.Nick_verfitPW);
        this.NickCheckBTN = view.findViewById(R.id.NickPWCheckBTN);

        this.NickCheckBTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.NickPWCheckBTN:
                gotoChangeNick(v);
                break;
        }
    }

    private void gotoChangeNick(View v) {
        if(this.NickPW.getText().toString().equals("")){
            //아이디가 존재할 경우와 아이디가 존재하지 않을 경우를 나누어야 함(파이어베이스 연결)
            Toast.makeText(getContext(),"비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        } 
    }
}