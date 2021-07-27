package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FriendsProfileScreenFragment extends Fragment implements View.OnClickListener{

    private Button F_bBTN;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_profile_screen, container, false);
        this.F_bBTN = view.findViewById(R.id.F_backBTN);
        this.F_bBTN.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.F_backBTN:
                backtoFriends(v);
                break;
        }
    }

    private void backtoFriends(View v) {
        Navigation.findNavController(v).navigate(R.id.action_friendsProfileScreenFragment_to_mainFragment);
    }
}