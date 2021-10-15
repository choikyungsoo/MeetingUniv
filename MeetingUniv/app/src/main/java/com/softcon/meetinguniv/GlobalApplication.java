package com.softcon.meetinguniv;

import android.app.Application;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("getKeyHash",Utility.INSTANCE.getKeyHash(this));

        // Kakao Sdk 초기화
        KakaoSdk.init(this, "e73b9463cb4d98b9d5460cc48a8fabb0");
//        Log.i("INITS", "Kakao Sdk 초기화");

    }
}
