package com.example.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class PopupFragment extends Dialog {
    private View view;
//    private Fragment fragment;
//
//    public PopupFragment(){
//
//    }
//
//    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.popup_match, container, false);
        return view;
    }
PopupFragment m_oDialog;
    public PopupFragment(Context context)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

//        setContentView(R.layout.popup_match);
//
//        m_oDialog = this;

//        TextView oView = (TextView) this.findViewById(R.id.textView);
//        oView.setText("Custom Dialog\n테스트입니다.");
//
//        Button oBtn = (Button)this.findViewById(R.id.btnOK);
//        oBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                onClickBtn(v);
//            }
//        });
//    }
//
//    public void onClickBtn(View _oView)
//    {
//        this.dismiss();
//    }
    }

}
