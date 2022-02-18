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
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.model.KakaoSdkError;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.JoinAgreementScreenFragment;
import com.softcon.meetinguniv.login.LoginActivity;
import com.softcon.meetinguniv.login.UserInfo;
import com.softcon.meetinguniv.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Intro extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Use this map to send parameters to your Cloud Code function
                // Just push the parameters you want into it
//                Map<String, String> parameters = new HashMap<String, String>();
//
//                // This calls the function in the Cloud Code
//                ParseCloud.callFunctionInBackground("verifyToken", parameters, new FunctionCallback<Map<String, Object>>() {
//                    @Override
//                    public void done(Map<String, Object> mapObject, ParseException e) {
//                        if (e == null) {
//                            // Everything is alright
//                            Toast.makeText(Intro.this, "Answer = " + mapObject.get("answer").toString(), Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            // Something went wrong
//                        }
//                    }
//                });
//                Intent intent = new Intent(Intro.this, LoginActivity.class);
//                startActivity(intent);

                FirebaseUser currentUser = auth.getCurrentUser();
                checkLogin(currentUser);
//                finish();
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
            Intent intent = new Intent(Intro.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


//        System.out.println("111111111111111111111");
//        System.out.println(currentUser.getUid());
//        currentUser.delete();
//        if(currentUser == null) {
//            this.auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("TAG", "signInAnonymously:success");
//                        FirebaseUser user = auth.getCurrentUser();
//                        checkLogin(user);
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w("TAG", "signInAnonymously:failure", task.getException());
////                        Toast.makeText(getContext, "Authentication failed.",
////                                Toast.LENGTH_SHORT).show();
//                        checkLogin(null);
//                    }
//                }
//            });
//        }

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
                            if (snapshot.hasChild(auth.getUid())) {
                                if(snapshot.child(auth.getUid()).child("약관동의").child("선택").hasChild("프로모션 정보 수신 동의")
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").hasChild("개인정보 수집 및 이용 동의")
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").hasChild("미팅대학 이용약관 동의")
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").hasChild("위치정보 이용약관 동의")
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").getValue().equals(true)
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").child("미팅대학 이용약관 동의").getValue().equals(true)
                                        && snapshot.child(auth.getUid()).child("약관동의").child("필수").child("위치정보 이용약관 동의").getValue().equals(true)
                                        && snapshot.child(auth.getUid()).child("닉네임").exists()
                                        && snapshot.child(auth.getUid()).child("추천인코드").exists()) {
                                    Log.d("카카오톡", "로그인 성공");
                                    updateToken(currentUser);
//                                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
//                                        @Override
//                                        public Unit invoke(User user, Throwable throwable) {
//                                            if(user != null) {
//                                                Log.d("카카오톡","로그인 성공");
//                                                Intent intent = new Intent(Intro.this, MainActivity.class);
//                                                intent.putExtra("userID", user.getId());
//                                                startActivity(intent);
//                                                finish();
//                                            }
//                                            return null;
//                                        }
//                                    });
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

    private void updateToken(FirebaseUser currentUser) {
        if(AuthApiClient.getInstance().hasToken()) {
            UserApiClient.getInstance().accessTokenInfo((accessTokenInfo, throwable) -> {
                if (throwable != null) {
                    System.out.println("error");
                    Log.d("카카오 로그인", "에러");
//                    AuthApiClient.getInstance().refreshAccessToken(accessTokenInfo, (oAuthToken, throwable1) -> {
//                       auth.
//                    });
//                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
//                        //로그인 필요
//                    }
//                    else {
//                        //기타 에러
//                    }
//                    if(throwable.equals(throwable.))
//                    Intent intent = new Intent(Intro.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
                }
                else if (accessTokenInfo !=  null) {
//                    accessTokenInfo.get
                    System.out.println("accessTokenInfo");

                    Intent intent = new Intent(Intro.this, MainActivity.class);
                    intent.putExtra("userID", currentUser.getUid());
                    startActivity(intent);


//                    if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this.getApplicationContext())) {
//                    if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
//                        System.out.println("checkLogin2");
//                        //                        Auth.creat
////                        UserApiClient.getInstance().loginWithKakaoTalk()
//                        Log.d("카카오톡", "카카오톡 설치됨");
//                        UserApiClient.getInstance().loginWithKakaoTalk(this.getBaseContext(), new Function2<OAuthToken, Throwable, Unit>() {
//                            @Override
//                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                                if (throwable != null) {
//                                    Log.e("error", throwable.getLocalizedMessage());
//                                }
//                                if (oAuthToken != null) {
//                                    String accessToken = oAuthToken.getAccessToken();
//                                    System.out.println(accessToken);
//                                    switchtoJWT(accessToken);
//
////                                    updateUserInfo(view);
//
//                                }
////                            checkLogin();
//                                return null;
//                            }
//                        });
//                    } else {
//                        System.out.println("checkLogin2");
//                        //                        Auth.creat
////                        UserApiClient.getInstance().loginWithKakaoTalk()
//                        Log.d("카카오톡", "카카오톡 설치안됨");
//                        UserApiClient.getInstance().loginWithKakaoAccount(this.getApplicationContext(), new Function2<OAuthToken, Throwable, Unit>() {
//                            @Override
//                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                                if (throwable != null) {
//                                    Log.e("error", throwable.getLocalizedMessage());
//                                }
//                                if (oAuthToken != null) {
//                                    String accessToken = oAuthToken.getAccessToken();
//                                    System.out.println(accessToken);
//                                    switchtoJWT(accessToken);
//
////                                    updateUserInfo(view);
//
//                                }
////                            checkLogin();
//                                return null;
//                            }
//                        });
//                    }
                }
                return null;
            });
        }
    }

//    private void switchtoJWT(String accessToken) {
//        System.out.println("A"+accessToken);
//        Map<String, String> parameters = new HashMap<String, String>();
////        parameters.put("token", accessToken);
//        parameters.put("token", accessToken);
////
////                // This calls the function in the Cloud Code
//        ParseCloud.callFunctionInBackground("verifyToken", parameters, new FunctionCallback<Map<String, Object>>() {
//            @Override
//            public void done(Map<String, Object> mapObject, ParseException e) {
//                if (e == null) {
//                    // Everything is alright
//                    String answer = mapObject.get("answer").toString();
//                    System.out.println("success");
//                    System.out.println(answer);
//                    Toast.makeText(Intro.this, "Answer = " + answer, Toast.LENGTH_LONG).show();
//                    auth.signInWithCustomToken(answer);
//
//                    Log.d("카카오톡","로그인 성공");
//                    Intent intent = new Intent(Intro.this, MainActivity.class);
//                    intent.putExtra("userID", auth.getUid());
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    // Something went wrong
//                    System.out.println("fail");
//                    System.out.println(e.getLocalizedMessage());
//                }
//            }
//
//        });
//    }



}
