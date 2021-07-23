package com.example.meetinguniv.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.meetinguniv.R;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}