package com.softcon.meetinguniv.main.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softcon.meetinguniv.R;

public class ChangeNickContentFragment extends Fragment {
    private TextView nickname;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");
    private long userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_change_nick_content, container, false);
        this.nickname = rootView.findViewById(R.id.currentnick);
        Bundle bundle = getArguments();
        this.userID = bundle.getLong("userID");
//        Log.d("ChangNickContentFragment - 회원아이디", String.valueOf(this.userID));
//        this.databaseReference.child(String.valueOf(this.userID)).child("닉네임").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nickname.setText((CharSequence) snapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        return rootView;
    }
}