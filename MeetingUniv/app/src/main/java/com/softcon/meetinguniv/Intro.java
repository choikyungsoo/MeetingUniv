package com.softcon.meetinguniv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.login.LoginActivity;

public class Intro extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
