package com.softcon.meetinguniv.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.MainActivity;

import java.io.Serializable;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class KakaoLoginFragment extends Fragment {

    private static final String TAG = "유저";
    private ImageView kakao_login_btn;
    private TextView logo;

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        checkLogin();
        this.root = inflater.inflate(R.layout.fragment_kakao_login, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.kakao_login_btn = view.findViewById(R.id.kakao_login_btn);

        this.kakao_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(root.getContext())) {
                        Log.d("카카오톡","카카오톡 설치됨");
                        UserApiClient.getInstance().loginWithKakaoTalk(root.getContext(), new Function2<OAuthToken, Throwable, Unit>() {
                            @Override
                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                                if(throwable != null) {
                                    Log.e("error",throwable.getLocalizedMessage());
                                }
                                if(oAuthToken !=null) {
                                    Log.i("success", "로그인 성공");
                                    Bundle bundle = new Bundle();
                                    UserInfo userInfo = updateUserInfo();

                                    Navigation.findNavController(view).navigate(R.id.action_kakao_login_to_joinAgreementScreenFragment, bundle);
                                }

//                                checkLogin();
                                return null;
                            }
                        });
                    } else {
                        Log.d("카카오톡","카카오톡 설치 안됨");
                        UserApiClient.getInstance().loginWithKakaoAccount(root.getContext(), new Function2<OAuthToken, Throwable, Unit>() {
                            @Override
                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                                if(throwable != null) {
                                    Log.e("error",throwable.getLocalizedMessage());
                                }
                                if(oAuthToken !=null) {
                                    Log.i("success", "로그인 성공");
                                    Bundle bundle = new Bundle();
                                    UserInfo userInfo = updateUserInfo();

                                    bundle.putSerializable("Obj", (Serializable) userInfo);
//                                    Log.d("0", String.valueOf(a.getUserID()));

                                    Navigation.findNavController(view).navigate(R.id.action_kakao_login_to_joinAgreementScreenFragment, bundle);
                                }
//                                checkLogin();
                                return null;
                            }
                        });
                    }
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
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
            }
        });
        this.logo = view.findViewById(R.id.logo);
        this.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private UserInfo updateUserInfo() {
        UserInfo userInfo = new UserInfo();
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {

            @Override
            public Unit invoke(User user, Throwable throwable) {
                userInfo.setUserID(user.getId());

                Log.d("UserID", String.valueOf(user.getId()));
                Log.d("StoredUserID", String.valueOf(userInfo.getUserID()));

                return null;
            }
        });
        return userInfo;
    }

    private void checkLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    Log.d("카카오톡","로그인 성공");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                return null;
            }
        });
    }
}