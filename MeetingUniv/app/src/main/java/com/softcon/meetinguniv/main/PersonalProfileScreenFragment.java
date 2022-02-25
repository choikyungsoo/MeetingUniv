package com.softcon.meetinguniv.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.setting.ChangePersonalNameDialog;
import com.softcon.meetinguniv.main.setting.ChangePersonalProfileImageDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

import java.io.ByteArrayOutputStream;

public class PersonalProfileScreenFragment extends Fragment implements View.OnClickListener {
    private CircleImageView personal_profile_image;
    private Button backToMainFromPersonalProfile_BTN;
    private TextView personal_name;
    private String userID;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("회원정보");

    final private static String TAG = "GILBOMI"; Button btn_photo; ImageView iv_photo; final static int TAKE_PICTURE = 1; String mCurrentPhotoPath; final static int REQUEST_TAKE_PHOTO = 1;

    public PersonalProfileScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_profile_screen, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.personal_profile_image = view.findViewById(R.id.personal_profile_image);
        this.personal_profile_image.setOnClickListener(this);

        this.backToMainFromPersonalProfile_BTN = view.findViewById(R.id.backToMainFromPersonalProfile_BTN);
        this.backToMainFromPersonalProfile_BTN.setOnClickListener(this);

        this.personal_name = view.findViewById(R.id.personal_name);
        this.personal_name.setOnClickListener(this);

        this.userID = getArguments().getString("userID");
        //프로필 사진 가져오기
        storageRef.child(this.userID+ "/" + "프로필 사진.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(view).load(uri).into(personal_profile_image);
            }
        });
        //프로필 이름 가져오기
        this.M_databaseReference.child(String.valueOf(this.userID)).child("닉네임").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                personal_name.setText((CharSequence) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_profile_image:
                ChangePersonalProfileImageDialog changePersonalProfileImageDialog = new ChangePersonalProfileImageDialog(getActivity());
                ClickHandler clickHandler = new ClickHandler();
                changePersonalProfileImageDialog.changeProfileImageFunction(clickHandler);
//                changeProfileImage(0);
                break;
            case R.id.backToMainFromPersonalProfile_BTN:
                Navigation.findNavController(v).navigate(R.id.action_personalProfileScreenFragment_to_mainFragment);
                break;
            case R.id.personal_name:
                ChangePersonalNameDialog changePersonalNameDialog = new ChangePersonalNameDialog(getActivity());
                changePersonalNameDialog.giveUserID(this.userID);
                changePersonalNameDialog.changeNameFunction(this.personal_name);
                break;
        }
    }

//    public void changeProfileImage(int eventNum) {
//        if (eventNum == 1) {
//            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, TAKE_PICTURE);
//        } else if (eventNum == 2) {
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent, 200);
//        } else {
//            Toast.makeText(getContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
//        }
//    }

    public class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            View cameraBTN = changePersonalProfileImageDialog.getCameraBTN();
//            View albumBTN = changePersonalProfileImageDialog.getAlbumBTN();
//            View cancelBTN = changePersonalProfileImageDialog.getAlbumBTN();

            if (v.getId() == R.id.changeProfileImage_cameraBTN) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "권한 설정 완료");
                        Toast.makeText(getContext(), "권한 설정 완료됨", Toast.LENGTH_SHORT).show();

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                    } else {
//                        Toast.makeText(getContext(), "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
//                        Intent appDetailSetIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getActivity().getPackageName()));
//                        startActivity(appDetailSetIntent);

                        if (getActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            Toast.makeText(getContext(), "카메라 권한 허용없이 사용이 불가합니다.", Toast.LENGTH_SHORT).show();
                            Intent appDetailSetIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getActivity().getPackageName()));
                            startActivity(appDetailSetIntent);
                        } else {
                            Toast.makeText(getContext(), "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
                        }

//                        if (getActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                            Toast.makeText(getContext(), "카메라 권한 없이 사용 불가", Toast.LENGTH_SHORT).show();
//                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
//                        } else if (!getActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                            Log.d(TAG, "권한 설정 요청");
//                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
////                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
////                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                            Toast.makeText(getContext(), "권한을 설정해주세요.", Toast.LENGTH_SHORT).show();
//
//                            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                                Log.d(TAG, "권한 설정 완료");
//                                Toast.makeText(getContext(), "권한 설정?", Toast.LENGTH_SHORT).show();
//
////                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                                startActivityForResult(cameraIntent, TAKE_PICTURE);
//                            }
//                        }
                    }
                }

            } else if (v.getId() == R.id.changeProfileImage_albumBTN) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
            v.getRootView().setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            changeProfileintoFirebase(selectedImageUri);
            this.personal_profile_image.setImageURI(selectedImageUri);
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
//                FileOutputStream outputStream = new FileOutputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytedata = baos.toByteArray();
                    storageRef.child(this.userID + "/" + "프로필 사진.jpg").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                    storageRef.child(this.userID + "/" + "프로필 사진.jpg").putBytes(bytedata).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "프로필 사진이 변경되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                    this.personal_profile_image.setImageBitmap(bitmap);
                }
            }
        }
    }

    private void changeProfileintoFirebase(Uri selectedImageUri) {
        //기존의 이미지 삭제
        storageRef.child(this.userID + "/" + "프로필 사진.jpg").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        storageRef.child(this.userID + "/" + "프로필 사진.jpg").putFile(selectedImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "프로필 사진이 변경되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(getContext(), "완료!!", Toast.LENGTH_SHORT).show();
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
//                && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }
    }
}