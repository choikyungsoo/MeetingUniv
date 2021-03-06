package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softcon.meetinguniv.R;


public class FriendsProfileScreenFragment extends Fragment implements View.OnClickListener{

    private Button F_bBTN;
    private Button goToFriendChatRoom_BTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_profile_screen, container, false);
        this.F_bBTN = view.findViewById(R.id.F_backBTN);
        this.goToFriendChatRoom_BTN = view.findViewById(R.id.goToFriendChatRoom_BTN);
        this.F_bBTN.setOnClickListener(this);
        this.goToFriendChatRoom_BTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.F_backBTN:
                backtoFriends(v);
                break;
            case R.id.goToFriendChatRoom_BTN:
                goToFriendChatRoom(v);
                break;
        }
    }

    private void backtoFriends(View v) {
        Navigation.findNavController(v).navigate(R.id.action_friendsProfileScreenFragment_to_mainFragment);
    }

    private void goToFriendChatRoom(View v) {
        Navigation.findNavController(v).navigate(R.id.action_friendsProfileScreenFragment_to_chatRoomScreenFragmentPopupVer);
    }
}