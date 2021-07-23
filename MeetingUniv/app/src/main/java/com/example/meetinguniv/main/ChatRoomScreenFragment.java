package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.example.meetinguniv.R;

public class ChatRoomScreenFragment extends Fragment {
    boolean openMenu = false;

    private Animation translateLeftAnim;
    private LinearLayout basePage;
    private LinearLayout menuPage;
    private ImageView menubtn;
    private Space status_bar_space;
    private int statusBarHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.statusBarHeight = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId>0){
            this.statusBarHeight = getResources().getDimensionPixelSize(resId);
        }

        this.status_bar_space = view.findViewById(R.id.statusspace);

        this.status_bar_space.getLayoutParams().height = statusBarHeight;

        basePage = view.findViewById(R.id.basePage);
        menuPage = view.findViewById(R.id.menuPage);

        menubtn = view.findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPage.setVisibility(View.VISIBLE);
                openMenu = true;
            }
        });

        basePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMenu) {
                    menuPage.setVisibility(View.GONE);
                }
            }
        });
    }
}