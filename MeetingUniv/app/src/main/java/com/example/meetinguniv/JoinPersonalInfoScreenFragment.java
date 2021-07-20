package com.example.meetinguniv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinPersonalInfoScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinPersonalInfoScreenFragment extends Fragment {
    Button gotoJoinProfileScreen_BTN;
    TextView join_id, join_password, join_passwordCheck, join_univ;
//    ImageView studentIDImage;
//    File file;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JoinPersonalInfoScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinPersonalInfoScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinPersonalInfoScreenFragment newInstance(String param1, String param2) {
        JoinPersonalInfoScreenFragment fragment = new JoinPersonalInfoScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_personal_info_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
                } else if(!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", join_password.getText().toString())){
                    Toast.makeText(getContext(),"비밀번호는 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합해주세요.", Toast.LENGTH_SHORT).show();
                } else if (join_passwordCheck.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (join_univ.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "학교를 선택하세요.", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });
//        studentIDImage = view.findViewById(R.id.studentIDImage);
//        studentIDImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePicture();
//            }
//        });
//    }
//
//    public void takePicture() {
//        if (file == null) {
//            file = createFile();
//        }
//
//        Uri fileUri = FileProvider.getUriForFile(this, "com.example.meetinguniv.fileprovider", file);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, 101);
//        }
//    }
//
//    private File createFile() {
//        String filename = "capture.jpg";
//        File storageDir = Environment.getExternalStorageState();
//        File outFile = new File(storageDir, filename);
//
//        return outFile;
//    }
//
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
    }
}