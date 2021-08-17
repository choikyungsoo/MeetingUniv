package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.example.meetinguniv.main.ChatRoomRecyclerAdapter;
import com.example.meetinguniv.main.ChatRoomRecyclerItem;

public class chatRoomScreenFragmentVersion3 extends Fragment {

    private Toolbar chattingtitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_room_screen_version3, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        this.chattingtitle = view.findViewById(R.id.chattingtitlebar);
//        this.chattingtitle.setTitle("박채윤,문인배,최경수");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}