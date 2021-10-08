package com.softcon.meetinguniv.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softcon.meetinguniv.JoinSelectUnivDialogFragment;
import com.softcon.meetinguniv.R;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class JoinUnivVerifyScreenFragment extends Fragment implements AutoPermissionsListener {
    private View view;
    private InputMethodManager inputMethodManager;

    private Button gotoJoinProfileScreen_BTN, skip_BTN, selectUniv_BTN;
    private TextView join_univ;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private JoinProfileFragment joinProfileFragment;

    private ImageView studentIDImage;
    private File file;
    private boolean setStudentIDImage = false;

    final private static String TAG = "GILBOMI"; Button btn_photo; ImageView iv_photo; final static int TAKE_PICTURE = 1; String mCurrentPhotoPath; final static int REQUEST_TAKE_PHOTO = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.joinProfileFragment = new JoinProfileFragment();

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_join_univ_verify_screen, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(this.getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && this.getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        studentIDImage = this.view.findViewById(R.id.studentIDImage);
        studentIDImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);

//                takePicture();
            }
        });

//        AutoPermissions.Companion.loadAllPermissions(getActivity(), 101);
        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        this.join_univ = view.findViewById(R.id.join_univ);
        this.gotoJoinProfileScreen_BTN = view.findViewById(R.id.gotoJoinProfileScreen_BTN);
        this.skip_BTN = view.findViewById(R.id.skip_BTN);
        this.selectUniv_BTN = view.findViewById(R.id.selectUniv_BTN);

        this.gotoJoinProfileScreen_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (join_id.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
//                } else if (join_password.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
//                } else if (join_password.getText().toString().length() < 8) {
//                    Toast.makeText(getContext(), "비밀번호를 8자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
//                } else if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", join_password.getText().toString())) {
//                    Toast.makeText(getContext(), "비밀번호는 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합해주세요.", Toast.LENGTH_SHORT).show();
//                } //Only 영문
//                else if (join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && !join_password.getText().toString().matches(".*[0-9]+.*")
//                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only 영문", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//                //Only 숫자
//                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && join_password.getText().toString().matches(".*[0-9]+.*")
//                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only 숫자", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//                //Only 특수문자
//                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && !join_password.getText().toString().matches(".*[0-9]+.*")
//                        && join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only 특수문자", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                } else if (join_passwordCheck.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
//                } else
                if (join_univ.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "학교를 선택하세요.", Toast.LENGTH_SHORT).show();
                } else if (!setStudentIDImage) {
                    Toast.makeText(getContext(), "학생증 인증을 완료하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_joinPersonalInfoScreenFragment_to_join_profile);
//                    gotoJoinProfileScreen();
                }
            }
        });

        this.skip_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_joinPersonalInfoScreenFragment_to_join_profile);
            }
        });

        this.selectUniv_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinSelectUnivDialogFragment joinSelectUnivDialogFragment = new JoinSelectUnivDialogFragment(getActivity());
                joinSelectUnivDialogFragment.showJoinSelectUnivDialog();
            }
        });
    }


//    public void takePicture() {
//        if (this.file == null) {
//            this.file = createFile();
//        }
//
//        Uri fileUri = FileProvider.getUriForFile(this.getActivity(), "com.example.meetinguniv.fileprovider", this.file);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        if (intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
//            startActivityForResult(intent, 101);
//        }
//    }
//
//    private File createFile() {
//        String filename = "capture.jpg";
//        File storageDir = Environment.getExternalStorageDirectory();
//        File outFile = new File(storageDir, filename);
//
//        return outFile;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        studentIDImage.setImageBitmap(bitmap);
                        this.setStudentIDImage = true;
                    }
                }
                break;
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 101 & resultCode == Activity.RESULT_OK) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 8;
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//
//            studentIDImage.setImageBitmap(bitmap);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        AutoPermissions.Companion.parsePermissions(getActivity(), requestCode, permissions, this);
//    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(getActivity(), "permissions denied : " + permissions.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(getActivity(), "permissions granted : " + permissions.length, Toast.LENGTH_SHORT).show();
    }

//    public void gotoJoinProfileScreen() {
//        this.fragmentManager = this.getActivity().getSupportFragmentManager();
//        this.fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.joinPersonalInfoScreen, this.joinProfileFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}