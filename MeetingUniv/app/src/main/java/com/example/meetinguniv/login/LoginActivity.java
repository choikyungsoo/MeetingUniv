package com.example.meetinguniv.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Space;

import com.example.meetinguniv.R;

public class LoginActivity extends AppCompatActivity {

    private Space status_bar_space;

    private int statusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.statusBarHeight = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId>0){
            this.statusBarHeight = getResources().getDimensionPixelSize(resId);
        }

        this.status_bar_space = findViewById(R.id.status_bar_space_login);

        this.status_bar_space.getLayoutParams().height = statusBarHeight;
    }


}