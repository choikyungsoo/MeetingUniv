package com.example.meetinguniv;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class ChatRoomScreenFragmentVersion2 extends Fragment implements View.OnClickListener{
    private boolean openMenu = false;
    private LinearLayout basePage;
    private LinearLayout menuPage;
    private ImageView menubtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_room_screen_version2, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.basePage = view.findViewById(R.id.basePage);
        this.menuPage = view.findViewById(R.id.menuPage);

        this.menubtn = view.findViewById(R.id.menubtn);

//        this.editText = view.findViewById(R.id.textView14);

        this.menubtn.setOnClickListener(this);
        this.basePage.setOnClickListener(this);
        this.menuPage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menubtn:
                this.menuPage.setVisibility(View.VISIBLE);
                this.openMenu = true;
                break;
            case R.id.basePage:
                if (this.openMenu) {
                    menuPage.setVisibility(View.GONE);
                    this.openMenu = false;
//                    this.basePage.requestFocus();
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
                break;
            case R.id.menuPage:
                menuPage.setVisibility(View.VISIBLE);
//            case R.id.textView14:
//                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                this.editText.requestFocus();
//                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        }
    }
}