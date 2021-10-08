package com.softcon.meetinguniv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.JoinAgreementScreenFragment;
import com.softcon.meetinguniv.login.LoginActivity;
import com.softcon.meetinguniv.main.MainActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Intro extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();;
    private DatabaseReference databaseReference = database.getReference("회원정보");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
//                Intent intent = new Intent(Intro.this, LoginActivity.class);
//                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void checkLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    Log.d("카카오톡","로그인 되어 있음");
                    Log.d("로그인된 사용자 아이디", String.valueOf(user.getId()));
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(String.valueOf(user.getId()))) {
                                if(snapshot.child(String.valueOf(user.getId())).child("약관동의").child("선택").hasChild("프로모션 정보 수신 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("개인정보 수집 및 이용 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("미팅대학 이용약관 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("위치정보 이용약관 동의")) {
                                    if (snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").getValue().equals(true)
                                            && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("미팅대학 이용약관 동의").getValue().equals(true)
                                            && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("위치정보 이용약관 동의").getValue().equals(true)) {
                                        Log.d("카카오톡", "로그인 성공");
                                        Intent intent = new Intent(Intro.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Log.d("카카오톡","로그인은 되어있으나 가입 시 문제가 발생한 적이 있음");
                    Intent intent = new Intent(Intro.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d("카카오톡","로그인 안되어 있음");
                    Intent intent = new Intent(Intro.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return null;
            }
        });
    }
}
