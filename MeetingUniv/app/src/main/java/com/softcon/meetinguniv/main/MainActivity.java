package com.softcon.meetinguniv.main;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Space;
import android.widget.Toast;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.onBackPressedListener;

import java.util.List;
import java.util.NavigableMap;

public class MainActivity extends AppCompatActivity {

    private Space status_bar_space;

    private int statusBarHeight;
    public onBackPressedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.statusBarHeight = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId>0){
            this.statusBarHeight = getResources().getDimensionPixelSize(resId);
        }

        this.status_bar_space = findViewById(R.id.status_bar_space);

        this.status_bar_space.getLayoutParams().height = statusBarHeight;
    }

    //////////////////////////////////////

//    public interface onKeyBackPressedListener {
//        void onBackKey();
//    }
//
//    private onKeyBackPressedListener mOnKeyBackPressedListener;
//
//    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
//        mOnKeyBackPressedListener = listener;
//    } //메인에서 토스트를 띄우며 종료확인을 하기 위해 필드선언 EndToast endToast = new EndToast(this);
//
//    @Override
//    public void onBackPressed() {
//        if (mOnKeyBackPressedListener != null) {
//            mOnKeyBackPressedListener.onBackKey();
//        } else { //쌓인 BackStack 여부에 따라 Toast를 띄울지, 뒤로갈지
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) { //* 종료 EndToast Bean 사용
//                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }

    private long lastTimeBackPressed;
    public void setOnBackPressedListener(onBackPressedListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        //프래그먼트 onBackPressedListener사용
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragmentList){
            if(fragment instanceof onBackPressedListener){
                ((onBackPressedListener)fragment).onBackPressed();
                return;
            }
        }

        //두 번 클릭시 어플 종료
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
//        super.onBackPressed();
        Toast.makeText(this,"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

    }
}