package com.example.meetinguniv.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetinguniv.R;
import com.example.meetinguniv.login.JoinProfileFragment;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.regex.Pattern;

public class JoinPersonalInfoScreenFragment extends Fragment implements AutoPermissionsListener {
    View view;
    private InputMethodManager inputMethodManager;

    private Button gotoJoinProfileScreen_BTN;
    private TextView join_id, join_password, join_passwordCheck, join_univ;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private JoinProfileFragment joinProfileFragment;
    ImageView studentIDImage;
    File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.joinProfileFragment = new JoinProfileFragment();

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_join_personal_info_screen, container, false);
        studentIDImage = this.view.findViewById(R.id.studentIDImage);
        studentIDImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(getActivity(), 101);
        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        this.join_id = view.findViewById(R.id.join_id);
        this.join_password = view.findViewById(R.id.join_password);
        this.join_passwordCheck = view.findViewById(R.id.join_passwordCheck);
        this.join_univ = view.findViewById(R.id.join_univ);
        this.gotoJoinProfileScreen_BTN = view.findViewById(R.id.gotoJoinProfileScreen_BTN);

        this.gotoJoinProfileScreen_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (join_id.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (join_password.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (join_password.getText().toString().length() < 8) {
                    Toast.makeText(getContext(), "비밀번호를 8자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", join_password.getText().toString())) {
                    Toast.makeText(getContext(), "비밀번호는 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합해주세요.", Toast.LENGTH_SHORT).show();
                } //Only 영문
                else if (join_password.getText().toString().matches(".*[a-zA-Z]+.*")
                        && !join_password.getText().toString().matches(".*[0-9]+.*")
                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
                    Toast.makeText(getContext(), "Only 영문", Toast.LENGTH_SHORT).show();
                    join_password.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
                //Only 숫자
                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
                        && join_password.getText().toString().matches(".*[0-9]+.*")
                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
                    Toast.makeText(getContext(), "Only 숫자", Toast.LENGTH_SHORT).show();
                    join_password.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
                //Only 특수문자
                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
                        && !join_password.getText().toString().matches(".*[0-9]+.*")
                        && join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
                    Toast.makeText(getContext(), "Only 특수문자", Toast.LENGTH_SHORT).show();
                    join_password.requestFocus();
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if (join_passwordCheck.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (join_univ.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "학교를 선택하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_joinPersonalInfoScreenFragment_to_join_profile);
//                    gotoJoinProfileScreen();
                }
            }
        });
    }

    public void takePicture() {
        if (file == null) {
            file = createFile();
        }

        Uri fileUri = FileProvider.getUriForFile(getActivity(), "com.example.meetinguniv.fileprovider", file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 101);
        }
    }

    private File createFile() {
        String filename = "capture.jpg";
        File storageDir = new File(Environment.getExternalStorageState());
        File outFile = new File(storageDir, filename);

        return outFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 & resultCode == Activity.RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            studentIDImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(getActivity(), requestCode, permissions, this);
    }

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