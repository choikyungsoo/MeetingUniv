package com.softcon.meetinguniv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.JoinAgreementScreenFragment;
import com.softcon.meetinguniv.login.LoginActivity;
import com.softcon.meetinguniv.main.MainActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Intro extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                Intent intent = new Intent(Intro.this, LoginActivity.class);
//                startActivity(intent);
                FirebaseUser currentUser = auth.getCurrentUser();
                checkLogin(currentUser);
                finish();
            }
        }, 1000);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = this.auth.getCurrentUser();
//        updateUI();
//    }

    private void checkLogin(FirebaseUser currentUser) {
        if(currentUser == null) {
            this.auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInAnonymously:success");
                        FirebaseUser user = auth.getCurrentUser();
                        checkLogin(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInAnonymously:failure", task.getException());
//                        Toast.makeText(getContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
                        checkLogin(null);
                    }
                }
            });
        }
//        if(AuthApiClient.getInstance().hasToken()) {
//            Log.d("사용자 토큰", String.valueOf(AuthApiClient.getInstance().hasToken()));
//            UserApiClient.getInstance().accessTokenInfo((accessTokenInfo, throwable) -> {
//                if (throwable != null) {
//                    Log.d("카카오 로그인", "에러");
//                }
//                else {
//                    Log.d("사용자 토큰", String.valueOf(accessTokenInfo));
////                    if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this.getApplicationContext()))
//                }
//                return null;
//            });
//        }
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    Log.d("사용자 정보", user.toString());
                    Log.d("카카오톡","로그인 되어 있음");
                    Log.d("로그인된 사용자 아이디", String.valueOf(user.getId()));
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.d("aaaaaa", storageReference.child(String.valueOf(user.getId())).
                            if (snapshot.hasChild(String.valueOf(user.getId()))) {
                                if(snapshot.child(String.valueOf(user.getId())).child("약관동의").child("선택").hasChild("프로모션 정보 수신 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("개인정보 수집 및 이용 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("미팅대학 이용약관 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").hasChild("위치정보 이용약관 동의")
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").getValue().equals(true)
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("미팅대학 이용약관 동의").getValue().equals(true)
                                        && snapshot.child(String.valueOf(user.getId())).child("약관동의").child("필수").child("위치정보 이용약관 동의").getValue().equals(true)
                                        && snapshot.child(String.valueOf(user.getId())).child("닉네임").exists()
                                        && snapshot.child(String.valueOf(user.getId())).child("추천인코드").exists()) {
                                    Log.d("카카오톡", "로그인 성공");
                                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                        @Override
                                        public Unit invoke(User user, Throwable throwable) {
                                            if(user != null) {
                                                Log.d("카카오톡","로그인 성공");
                                                Intent intent = new Intent(Intro.this, MainActivity.class);
                                                intent.putExtra("userID", user.getId());
                                                startActivity(intent);
                                                finish();
                                            }
                                            return null;
                                        }
                                    });
                                }
                                else {
                                    Log.d("카카오톡","로그인은 되어있으나 가입 시 문제가 발생한 적이 있음");
                                    Intent intent = new Intent(Intro.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else {
                                Log.d("카카오톡","로그인은 되어있으나 가입 시 문제가 발생한 적이 있음");
                                Intent intent = new Intent(Intro.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    Log.d("카카오톡","로그인은 되어있으나 가입 시 문제가 발생한 적이 있음");
//                    Intent intent = new Intent(Intro.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
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
