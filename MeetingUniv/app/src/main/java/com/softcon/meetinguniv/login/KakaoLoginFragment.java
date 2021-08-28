package com.softcon.meetinguniv.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.MainActivity;

public class KakaoLoginFragment extends Fragment {

    private static final String TAG = "유저";
    private ImageView kakao_login_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kakao_login, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.kakao_login_btn = view.findViewById(R.id.kakao_login_btn);

        this.kakao_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserApiClient.getInstance().loginWithKakaoTalk(this,(oAuthToken, error) -> {
//                    if(error != null){
//                        Log.e(TAG, "로그인 실패", error);
//                    }
//                    else if(oAuthToken != null){
//                        Log.i(TAG, "로그인 성공 ${token.accessToken}");
//
//                        //사용자 정보 요청
//                        UserApiClient.getInstance().me((user, meError) -> {
//                            if(meError != null){
//                                Log.e(TAG, "사용자 정보 요청 실패", meError);
//                            }
//                            else{
//                                System.out.println("로그인됐다.");
//                                Log.e(TAG, user.toString());
//
//                                Account user1 = user.getKakaoAccount();
//                                System.out.println("유저 어카운트"+user1);
//                            }
//                            return null;
//                        });
//
//                    }
//                    return null;
//                });
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}