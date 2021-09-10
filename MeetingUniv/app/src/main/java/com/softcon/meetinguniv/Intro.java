package com.softcon.meetinguniv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.LoginActivity;
import com.softcon.meetinguniv.main.MainActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Intro extends AppCompatActivity {
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
                    Log.d("카카오톡","로그인 성공");
                    Intent intent = new Intent(Intro.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("카카오톡","로그인 실패");
                    Intent intent = new Intent(Intro.this, LoginActivity.class);
                    startActivity(intent);
                }
                return null;
            }
        });
    }
}
