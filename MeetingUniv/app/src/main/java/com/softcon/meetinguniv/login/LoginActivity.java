package com.softcon.meetinguniv.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.widget.Space;

import com.kakao.sdk.common.util.Utility;
import com.softcon.meetinguniv.R;
import com.kakao.sdk.common.KakaoSdk;

import static android.content.ContentValues.TAG;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private Space status_bar_space;

    private int statusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Log.d("GET_KEYHASH", Utility.INSTANCE.getKeyHash(this));

        this.statusBarHeight = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId>0){
            this.statusBarHeight = getResources().getDimensionPixelSize(resId);
        }

        this.status_bar_space = findViewById(R.id.status_bar_space_login);

        this.status_bar_space.getLayoutParams().height = statusBarHeight;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }
}