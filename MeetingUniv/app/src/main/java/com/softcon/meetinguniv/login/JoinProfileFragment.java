package com.softcon.meetinguniv.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.softcon.meetinguniv.Intro;
import com.softcon.meetinguniv.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softcon.meetinguniv.main.MainActivity;

import java.io.Serializable;

public class JoinProfileFragment extends Fragment {
    private ImageView join_profile_image;
    private Button go_start;
    private EditText settingNickName;

    private FirebaseAuth mAuth;//
    private DatabaseReference mDatabase;

    private UserInfo userInfo;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();;
    private DatabaseReference databaseReference = database.getReference("회원정보");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.userInfo = (UserInfo) (getArguments().getSerializable("Obj"));

        Log.d("bundledata0",String.valueOf(this.userInfo.getUserID()));
        Log.d("bundledata1",String.valueOf(this.userInfo.isPromotionInfoAgreementCheckbox()));
        Log.d("bundledata2",String.valueOf(this.userInfo.isMeetingUnivAgreementCheckbox()));
        Log.d("bundledata3",String.valueOf(this.userInfo.isPersonalInfoAgreementCheckbox()));
        Log.d("bundledata4",String.valueOf(this.userInfo.isLocationInfoAgreementCheckbox()));
        if(this.userInfo.getSchoolName()=="")
            Log.d("bundledata5","null");

        return inflater.inflate(R.layout.fragment_join_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.settingNickName = view.findViewById(R.id.settingNickName);

        this.join_profile_image = view.findViewById(R.id.join_profile_image);
        this.join_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
        });

        this.go_start = view.findViewById(R.id.go_start);
        this.go_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAuth.createUserWithEmailAndPassword().addOnCompleteListener(this.getActivity(), task -> {
//
//                });
//                Navigation.findNavController(view).navigate(R.id.action_join_profile_to_login);
                // 조건 추가해야됨
                //프로필 사진 작업 필요
                //닉네임 등록
                userInfo.setNickname(String.valueOf(settingNickName.getText()));
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("미팅대학 이용약관 동의").setValue(userInfo.isMeetingUnivAgreementCheckbox());
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").setValue(userInfo.isPersonalInfoAgreementCheckbox());
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("위치정보 이용약관 동의").setValue(userInfo.isLocationInfoAgreementCheckbox());
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("선택").child("프로모션 정보 수신 동의").setValue(userInfo.isPromotionInfoAgreementCheckbox());
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("학교").setValue(userInfo.getSchoolName());
                databaseReference.child(String.valueOf(userInfo.getUserID())).child("닉네임").setValue(userInfo.getNickname());
                // 아직 보낼 필요 없음?
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("Obj", (Serializable) userInfo);

                Intent intent = new Intent(getActivity(), Intro.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        mAuth = FirebaseAuth.getInstance();//
        mDatabase = FirebaseDatabase.getInstance().getReference();//

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.METHOD, method);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            this.join_profile_image.setImageURI(selectedImageUri);
        }
    }
}