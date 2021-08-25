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

public class ChangePWVerifyContentFragment extends Fragment implements View.OnClickListener {


    private Button PW_nextBTN;
    private EditText PW_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_p_w_verify_content, container, false);
        this.PW_nextBTN = view.findViewById(R.id.ID_checkBTN);
        this.PW_name = view.findViewById(R.id.Nameinput);
        this.PW_nextBTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ID_checkBTN:
                pw_checkList(v);
                break;
        }
    }

    private void pw_checkList(View v) {
        if(this.PW_name.getText().toString().equals("")){
            Toast.makeText(getContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            Navigation.findNavController(v).navigate(R.id.action_changePWVerifyContentFragment_to_changePWCompleteContentFragment);
        }
    }
}