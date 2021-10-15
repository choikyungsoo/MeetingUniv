package com.softcon.meetinguniv.main.setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.UserInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PopupInvite extends Fragment {
    private View view;
    private TextView inviteCode;
    private UserInfo userInfo;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.popup_invite, container, false);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inviteCode = this.view.findViewById(R.id.invitecode);
        String invite = databaseReference.child(String.valueOf(userInfo.getUserID())).child("추천인코드").get().toString();
        System.out.println(invite);
        this.inviteCode.setText(invite);
    }
}
