package com.softcon.meetinguniv.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.setting.ChangePersonalNameDialog;
import com.softcon.meetinguniv.main.setting.ChangePersonalProfileImageDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class PersonalProfileScreenFragment extends Fragment implements View.OnClickListener {
    private CircleImageView personal_profile_image;
    private Button backToMainFromPersonalProfile_BTN;
    private TextView personal_name;

    final private static String TAG = "GILBOMI"; Button btn_photo; ImageView iv_photo; final static int TAKE_PICTURE = 1; String mCurrentPhotoPath; final static int REQUEST_TAKE_PHOTO = 1;

    private String mParam1;
    private String mParam2;

    public PersonalProfileScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_profile_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.personal_profile_image = view.findViewById(R.id.personal_profile_image);
        this.personal_profile_image.setOnClickListener(this);

        this.backToMainFromPersonalProfile_BTN = view.findViewById(R.id.backToMainFromPersonalProfile_BTN);
        this.backToMainFromPersonalProfile_BTN.setOnClickListener(this);

        this.personal_name = view.findViewById(R.id.personal_name);
        this.personal_name.setOnClickListener(this);
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
            this.personal_profile_image.setImageURI(selectedImageUri);
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    this.personal_profile_image.setImageBitmap(bitmap);
                }
            }
        }
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