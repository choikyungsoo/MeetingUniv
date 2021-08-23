package com.softcon.meetinguniv;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.softcon.meetinguniv.R;

public class ChatRoomScreenFragmentVersion4 extends Fragment {

    private NestedScrollView contents;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room_screen_version4, container, false);
//        this.contents = view.findViewById(R.id.contents);

//        this.contents.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int w = contents.getLayoutParams().width;
//                int h = contents.getLayoutParams().height;
//
//                //스크롤뷰 내부 레이아웃 크기 고정 시키기
//                LinearLayout linearLayout;      //<-- 내부 레이아웃이라고 치고
//                linearLayout = view.findViewById(R.id.fixcontents);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
//                linearLayout.setLayoutParams(layoutParams);
//            }
//        });
        return view;
    }
}