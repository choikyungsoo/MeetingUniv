package com.softcon.meetinguniv.login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.softcon.meetinguniv.JoinSelectUnivDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// >>>>>>> inbae:MeetingUniv/app/src/main/java/com/softcon/meetinguniv/login/JoinPersonalInfoScreenFragment.java
import com.softcon.meetinguniv.R;
import com.pedro.library.AutoPermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
// >>>>>>> inbae:MeetingUniv/app/src/main/java/com/softcon/meetinguniv/login/JoinPersonalInfoScreenFragment.java

import static android.app.Activity.RESULT_OK;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class JoinUnivVerifyScreenFragment extends Fragment implements AutoPermissionsListener {
    private View view;
    private InputMethodManager inputMethodManager;

    private Button gotoJoinProfileScreen_BTN, skip_BTN, selectUniv_BTN;
    private TextView join_univ;

    //popup
    private View dialogView;
    private Spinner join_UnivSpinner;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private JoinProfileFragment joinProfileFragment;
    private UserInfo userInfo;

    // for parsing
    private String schoolName;
    private ArrayList<String> schoolNames = new ArrayList<String>();
    private boolean inSchoolName = false;

    private ImageView studentIDImage;
    private File file;
    private boolean setStudentIDImage = false;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference().child("학생증");

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

        // bundle 데이터 받기
        this.userInfo = (UserInfo) (getArguments().getSerializable("Obj"));
//        Log.d("bundledata0",String.valueOf(this.userInfo.getUserID()));
//        Log.d("bundledata1",String.valueOf(this.userInfo.isPromotionInfoAgreementCheckbox()));
//        Log.d("bundledata2",String.valueOf(this.userInfo.isMeetingUnivAgreementCheckbox()));
//        Log.d("bundledata3",String.valueOf(this.userInfo.isPersonalInfoAgreementCheckbox()));
//        Log.d("bundledata4",String.valueOf(this.userInfo.isLocationInfoAgreementCheckbox()));

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
                    // 학교 정보
                    userInfo.setSchoolName(String.valueOf(join_univ.getText()));
                    databaseReference.child(String.valueOf(userInfo.getUserID())).child("학교").setValue(userInfo.getSchoolName());
                    // 학생증 정보
                    // 데이터 보내기
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Obj", (Serializable) userInfo);
                    Navigation.findNavController(view).navigate(R.id.action_joinPersonalInfoScreenFragment_to_join_profile, bundle);
//                    gotoJoinProfileScreen();
                }
            }
        });

        this.skip_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 학교 정보
                userInfo.setSchoolName(String.valueOf(join_univ.getText()));

                StorageReference studentIDCardReference = storageReference.child(String.valueOf(userInfo.getUserID())+".jpg");

                studentIDImage.setDrawingCacheEnabled(true);
                studentIDImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) studentIDImage.getDrawable()).getBitmap();
//                FileOutputStream outputStream = new FileOutputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = studentIDCardReference.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("학생증 사진", "실패");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("학생증 사진", "성공");
                    }
                });

//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("미팅대학 이용약관 동의").setValue(userInfo.isMeetingUnivAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").setValue(userInfo.isPersonalInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("위치정보 이용약관 동의").setValue(userInfo.isLocationInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("선택").child("프로모션 정보 수신 동의").setValue(userInfo.isPromotionInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("학교").setValue(userInfo.getSchoolName());
                // 학생증 정보
                // 데이터 보내기
                Bundle bundle = new Bundle();
                bundle.putSerializable("Obj", (Serializable) userInfo);
                Navigation.findNavController(view).navigate(R.id.action_joinPersonalInfoScreenFragment_to_join_profile, bundle);
            }
        });

        this.selectUniv_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                JoinSelectUnivDialogFragment joinSelectUnivDialogFragment = new JoinSelectUnivDialogFragment(getActivity());
//                joinSelectUnivDialogFragment.showJoinSelectUnivDialog();

                popUp();

                join_UnivSpinner = dialogView.findViewById(R.id.join_UnivSpinner);

                getSchoolNameXmlData();


//                Dialog dialog;
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                LayoutInflater inflater = requireActivity().getLayoutInflater();
//                builder.setView(inflater.inflate(R.layout.fragment_join_select_univ_dialog, null));
//
//                dialog = builder.create();
//                dialog.show();
            }
        });
    }

    private void popUp() {
        Dialog dialog;
        this.dialogView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_join_select_univ_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("조건 선택");
        builder.setView(this.dialogView);
        dialog = builder.create();
        dialog.show();
    }

    private void getSchoolNameXmlData() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

                URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=39e99a147405ffbc1ef3a36fee8a8ac9&svcType=api&svcCode=SCHOOL&contentType=xml&gubun=univ_list&thisPage=1&perPage=433");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //문제 발생
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "파싱 시작");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("schoolName")) {
                                this.inSchoolName = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            if(this.inSchoolName) {
                                this.schoolName = parser.getText();
                                this.inSchoolName = false;
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("content")) {
                                this.schoolNames.add(this.schoolName);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.schoolNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                this.join_UnivSpinner.setAdapter(adapter);
                                this.join_UnivSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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