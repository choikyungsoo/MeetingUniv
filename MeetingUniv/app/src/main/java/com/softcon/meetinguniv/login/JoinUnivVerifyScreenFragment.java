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
    private Button joinSelectUnivOk_button;
    private TextView join_univ;
    private Dialog dialog;

    //popup
    private View dialogView;

    private Spinner province_spinner;
    private Spinner city_spinner;
    private Spinner join_UnivSpinner;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private JoinProfileFragment joinProfileFragment;
    private UserInfo userInfo;

    // for province name parsing
    private String provinceName;
    private ArrayList<String> provinceNames = new ArrayList<String>();
    private boolean inCtp_kor_nm = false;
    private String provinceCode;
    private ArrayList<String> provinceCodes = new ArrayList<String>();
    private boolean inCtprvn_cd = false;
    private String provinceCodeForResult;

    // for city name parsing
    private String cityName;
    private ArrayList<String> cityNames = new ArrayList<String>();
    private boolean inSig_kor_nm = false;
    private String cityCode;
    private ArrayList<String> cityCodes = new ArrayList<String>();
    private boolean inSig_cd = false;
    private String cityCodeForResult;

    // for school name parsing
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
//                    databaseReference.child(String.valueOf(userInfo.getUserID())).child("학교").setValue(userInfo.getSchoolName());
                    // 학생증 정보
                    userInfo.setSchoolName(String.valueOf(join_univ.getText()));

//                    StorageReference studentIDCardReference = storageReference.child(String.valueOf(userInfo.getUserID())+".jpg");

                    studentIDImage.setDrawingCacheEnabled(true);
                    studentIDImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) studentIDImage.getDrawable()).getBitmap();
//                FileOutputStream outputStream = new FileOutputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    userInfo.setStudentCard(data);

//                    UploadTask uploadTask = studentIDCardReference.putBytes(data);
//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("학생증 사진", "실패");
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Log.d("학생증 사진", "성공");
//                        }
//                    });
                    // 데이터 보내기
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Obj", (Serializable) userInfo);
                    Navigation.findNavController(view).navigate(R.id.action_joinUnivVerifyScreenFragment_to_joinProfileFragment, bundle);
//                    gotoJoinProfileScreen();
                }
            }
        });

        this.skip_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 학교 정보
//                userInfo.setSchoolName(String.valueOf(join_univ.getText()));
//
//                StorageReference studentIDCardReference = storageReference.child(String.valueOf(userInfo.getUserID())+".jpg");
//
//                studentIDImage.setDrawingCacheEnabled(true);
//                studentIDImage.buildDrawingCache();
//                Bitmap bitmap = ((BitmapDrawable) studentIDImage.getDrawable()).getBitmap();
////                FileOutputStream outputStream = new FileOutputStream();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] data = baos.toByteArray();
//
//                UploadTask uploadTask = studentIDCardReference.putBytes(data);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("학생증 사진", "실패");
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Log.d("학생증 사진", "성공");
//                    }
//                });

//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("미팅대학 이용약관 동의").setValue(userInfo.isMeetingUnivAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("개인정보 수집 및 이용 동의").setValue(userInfo.isPersonalInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("필수").child("위치정보 이용약관 동의").setValue(userInfo.isLocationInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("약관동의").child("선택").child("프로모션 정보 수신 동의").setValue(userInfo.isPromotionInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("학교").setValue(userInfo.getSchoolName());
                // 학생증 정보
                // 데이터 보내기
                Bundle bundle = new Bundle();
                bundle.putSerializable("Obj", (Serializable) userInfo);
                Navigation.findNavController(view).navigate(R.id.action_joinUnivVerifyScreenFragment_to_joinProfileFragment, bundle);
            }
        });

        this.selectUniv_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                JoinSelectUnivDialogFragment joinSelectUnivDialogFragment = new JoinSelectUnivDialogFragment(getActivity());
//                joinSelectUnivDialogFragment.showJoinSelectUnivDialog();

                popUp();


                province_spinner = dialogView.findViewById(R.id.province_spinner);
                city_spinner = dialogView.findViewById(R.id.city_spinner);

                join_UnivSpinner = dialogView.findViewById(R.id.join_UnivSpinner);
                joinSelectUnivOk_button = dialogView.findViewById(R.id.joinSelectUnivOk_button);

                joinSelectUnivOk_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        selected_univ = (String) join_UnivSpinner.getSelectedItem();
//                        System.out.println(selected_univ);
                        join_univ.setText((CharSequence) join_UnivSpinner.getSelectedItem());
                        dialog.dismiss();
                    }
                });

                getProvinceNameXmlData();
//                getCityNameXmlData();
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
        this.dialogView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_join_select_univ_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("조건 선택");
        builder.setView(this.dialogView);
        this.dialog = builder.create();
        dialog.show();
    }

    private void getProvinceNameXmlData() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

                URL url = new URL("https://api.vworld.kr/req/data?key=6E466C16-C44E-3029-89F0-EB726855EEAC&service=data&version=2.0&request=getfeature&format=xml&size=1000&page=1&geometry=false&attribute=true&crs=EPSG:3857&geomfilter=BOX(13663271.680031825,3894007.9689600193,14817776.555251127,4688953.0631258525)&data=LT_C_ADSIDO_INFO");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //문제 발생
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "광역시도 파싱 시작");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            System.out.println("A");
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("ctprvn_cd")) {
                                this.inCtprvn_cd = true;
                                System.out.println("b");
                            }
                            else if(parser.getName().equals("ctp_kor_nm")) {
                                this.inCtp_kor_nm = true;
                                System.out.println("B");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            if(this.inCtprvn_cd) {
                                this.provinceCode = parser.getText();
                                this.inCtprvn_cd = false;
                                System.out.println("c");
                            }
                            else if(this.inCtp_kor_nm) {
                                this.provinceName = parser.getText();
                                this.inCtp_kor_nm = false;
                                System.out.println("C");
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("gml:featureMember")) {
                                this.provinceCodes.add(this.provinceCode);
                                System.out.println("d");
                                this.provinceNames.add(this.provinceName);
                                System.out.println("D");
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.provinceNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                this.province_spinner.setAdapter(adapter);
                                this.province_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        provinceCodeForResult = provinceCodes.get(position);
                                        getCityNameXmlData();
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

    private void getCityNameXmlData() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

                URL url = new URL("https://api.vworld.kr/req/data?key=6E466C16-C44E-3029-89F0-EB726855EEAC&service=data&version=2.0&request=getfeature&format=xml&size=1000&page=1&geometry=false&attribute=true&crs=EPSG:3857&geomfilter=BOX(13663271.680031825,3894007.9689600193,14817776.555251127,4688953.0631258525)&data=LT_C_ADSIGG_INFO");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //문제 발생
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "시군구 파싱 시작");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            System.out.println("AA");
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("sig_cd")) {
                                this.inSig_cd = true;
                                System.out.println("bb");
                            }
                            else if(parser.getName().equals("sig_kor_nm")) {
                                this.inSig_kor_nm = true;
                                System.out.println("BB");
                            }
                            break;

                        case XmlPullParser.TEXT:
                            if(this.inSig_cd) {
                                if(parser.getText().matches(this.provinceCodeForResult+".*")) {
                                    this.cityCode = parser.getText();
//                                    this.inSig_cd = false;
                                    System.out.println("cc");
                                }
                                else {
                                    this.inSig_kor_nm = false;
                                }
                                this.inSig_cd = false;

                            }
                            if(this.inSig_kor_nm) {
                                this.cityName = parser.getText();
                                this.inSig_kor_nm = false;
                                System.out.println("CC");
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            System.out.println(this.cityCode+" "+this.provinceCodeForResult);
                            if(parser.getName().equals("gml:featureMember")) {
                                System.out.println("********"+this.cityCode+" "+this.provinceCodeForResult);
                                System.out.println("DD");
                                this.cityNames.add(this.cityName);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.cityNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                this.city_spinner.setAdapter(adapter);
                                this.city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        cityCodeForResult = cityCodes.get(position);
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