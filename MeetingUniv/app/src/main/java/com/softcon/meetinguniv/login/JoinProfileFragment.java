package com.softcon.meetinguniv.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softcon.meetinguniv.Intro;
import com.softcon.meetinguniv.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softcon.meetinguniv.main.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class JoinProfileFragment extends Fragment {
    private ImageView join_profile_image;
    private Button go_start;
    private EditText settingNickName;

    private FirebaseAuth mAuth;//
    private DatabaseReference mDatabase;

    private UserInfo userInfo;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");

    private Boolean notDuplicated = true;
    private String InviteCode;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // bundle 데이터 받기
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
                if (settingNickName.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
//                mAuth.createUserWithEmailAndPassword().addOnCompleteListener(this.getActivity(), task -> {
//
//                });
//                Navigation.findNavController(view).navigate(R.id.action_join_profile_to_login);
                    // 조건 추가해야됨
                    //프로필 사진 작업 필요

                    join_profile_image.setDrawingCacheEnabled(true);
                    join_profile_image.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) join_profile_image.getDrawable()).getBitmap();
//                FileOutputStream outputStream = new FileOutputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    userInfo.setProfileImage(data);

//                join_profile_image.
//                userInfo.setProfileImage();
                    //닉네임 등록
                    userInfo.setNickname(String.valueOf(settingNickName.getText()));
                    //추천인 코드 생성(난수 작업)
                    while (notDuplicated) {
                        InviteCode = makeInviteCode();
                        if (!InviteCode.equals(databaseReference.getDatabase().getReference().child("추천인코드"))) {
                            notDuplicated = false;
                        }
                    }
                    //추천인 코드 업로드

                    userInfo.setInviteCode(InviteCode);


                    //내가 초대된 코드는 바로 코드 주인에게 +1 하트
                    databaseReference.child(userInfo.getUserID()).child("약관동의").child("필수").child("미팅대학 이용약관 동의").setValue(userInfo.isMeetingUnivAgreementCheckbox());
                    databaseReference.child(userInfo.getUserID()).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").setValue(userInfo.isPersonalInfoAgreementCheckbox());
                    databaseReference.child(userInfo.getUserID()).child("약관동의").child("필수").child("위치정보 이용약관 동의").setValue(userInfo.isLocationInfoAgreementCheckbox());
                    databaseReference.child(userInfo.getUserID()).child("약관동의").child("선택").child("프로모션 정보 수신 동의").setValue(userInfo.isPromotionInfoAgreementCheckbox());
                    databaseReference.child(userInfo.getUserID()).child("학교").setValue(userInfo.getSchoolName());
                    StorageReference studentIDCardReference = storageReference.child(userInfo.getUserID()).child("학생증.jpg");

                    UploadTask uploadTask = studentIDCardReference.putBytes(userInfo.getStudentCard());
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("학생증 사진", "실패");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("학생증 사진", "성공");
                            databaseReference.child(userInfo.getUserID()).child("학생증").setValue(1);
                        }
                    });
                    databaseReference.child(userInfo.getUserID()).child("팀").child("0").setValue("null");
                    databaseReference.child(userInfo.getUserID()).child("친구목록").child("0").setValue("null");
                    databaseReference.child(userInfo.getUserID()).child("닉네임").setValue(userInfo.getNickname());
                    databaseReference.child(userInfo.getUserID()).child("추천인코드").setValue(userInfo.getInviteCode());

                    StorageReference profileImageReference = storageReference.child(userInfo.getUserID()).child("프로필 사진.jpg");
                    UploadTask uploadTask2 = profileImageReference.putBytes(userInfo.getProfileImage());
                    uploadTask2.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("프로필 사진", "실패");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("프로필 사진", "성공");
                        }
                    });

//                    auth.updateCurrentUser();

//                ArrayList<Integer> arrayList = new ArrayList<Integer>();
//                arrayList.add(0);
//                arrayList.add(1);
//                arrayList.add(2);
//
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("팀").setValue(arrayList);

                    // 아직 보낼 필요 없음?
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("Obj", (Serializable) userInfo);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(settingNickName.getWindowToken(),0);
                    Intent intent = new Intent(getActivity(), Intro.class);
                    startActivity(intent);
//                    startActivity(intent, bundle);
                    getActivity().finish();
                }
            }
        });

//        mAuth = FirebaseAuth.getInstance();//
//        mDatabase = FirebaseDatabase.getInstance().getReference();//

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.METHOD, method);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    private String makeInviteCode() {
        //난수 작업
        char[] english = new char[4];
        int[] number = new int[4];
        String numberValue = "";
        String englishC;
        for(int i =0; i < 4; i++) {
            english[i] = (char)((int)(Math.random()*26)+97);
            number[i] = (int) (Math.random() * 10);
            numberValue += Integer.toString(number[i]);
        }
        englishC = new String(english);
        return englishC + numberValue;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            this.join_profile_image.setImageURI(selectedImageUri);
        }
    }
}