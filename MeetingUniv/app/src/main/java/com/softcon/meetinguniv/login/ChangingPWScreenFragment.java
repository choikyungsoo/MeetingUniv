package com.softcon.meetinguniv.login;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softcon.meetinguniv.R;

import java.util.regex.Pattern;

public class ChangingPWScreenFragment extends Fragment implements View.OnClickListener{
    private EditText newPW;
    private EditText CheckNewPW;

    private TextView overE;
    private TextView comBi;
    private TextView checkP;

    private Button ChangPWBTN;

    private String PWinput;
    private String PwCheckinput;

    private boolean checkOverE;
    private boolean checkcomBi;
    private boolean checkcheckP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_changing_p_w_screen, container, false);
        this.newPW = view.findViewById(R.id.newPW);
        this.CheckNewPW = view.findViewById(R.id.newPW_check);

        this.overE = view.findViewById(R.id.overEight);
        this.comBi = view.findViewById(R.id.combinum);
        this.checkP = view.findViewById(R.id.check_PW);

        this.ChangPWBTN = view.findViewById(R.id.PW_changeBTN);
        
        this.checkOverE = false;
        this.checkcomBi = false;
        this.checkcheckP = false;
        this.ChangPWBTN.setOnClickListener(this);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        this.newPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeText(view);
                checkcheckP = false;
                checkDulicapted(view);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.CheckNewPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkDulicapted(view);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkDulicapted(View view) {
        this.PwCheckinput = this.CheckNewPW.getText().toString();
        if(this.PWinput.equals(this.PwCheckinput)){
            this.checkP.setText("비밀번호가 일치합니다");
            this.checkP.setTextColor(Color.parseColor("#0054FF"));
            this.checkcheckP = true;
        } else if(this.PwCheckinput.equals("")){
            this.checkP.setText(" ");
        }
        else{
            this.checkP.setText("비밀번호가 일치 하지 않습니다");
            this.checkP.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    private void changeText(View view) {
        this.PWinput = this.newPW.getText().toString();
        if(this.PWinput.length() >= 8){
            this.overE.setTextColor(Color.parseColor("#0054FF"));
            this.checkOverE = true;
            //(?=.*[a-zA-Z])(?=.*[0-9])
            if(Pattern.matches("^.*(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", this.PWinput)){
                this.comBi.setTextColor(Color.parseColor("#0054FF"));
                this.checkcomBi = true;
            } else if(Pattern.matches("^.*(?=.*[a-zA-Z])(?=.*[0-9]).*$", this.PWinput)){
                this.comBi.setTextColor(Color.parseColor("#0054FF"));
                this.checkcomBi = true;
            } else if(Pattern.matches("^.*(?=.*[0-9])(?=.*[!@#$%^&+=]).*$", this.PWinput)){
                this.comBi.setTextColor(Color.parseColor("#0054FF"));
                this.checkcomBi = true;
            }else if(Pattern.matches("^.*(?=^.{8,20}$)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", this.PWinput)){
                this.comBi.setTextColor(Color.parseColor("#0054FF"));
                this.checkcomBi = true;
            } else{
                this.comBi.setTextColor(Color.parseColor("#7E7E7E"));
            }
        } else if(this.PWinput.length() < 8){
            this.overE.setTextColor(Color.parseColor("#7E7E7E"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PW_changeBTN:
                changPW(v);
                break;
        }
    }

    private void changPW(View v) {
        if(checkOverE && checkcomBi && checkcheckP){
            //이 상태일경우에만 변경하기 버튼이 작동되도록!!
            Toast.makeText(getContext(),"변경완료", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getContext(),"조건을 완성해주세요", Toast.LENGTH_SHORT).show();
        }

    }
}