package com.softcon.meetinguniv.login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.fonts.Font;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// >>>>>>> inbae:MeetingUniv/app/src/main/java/com/softcon/meetinguniv/login/JoinPersonalInfoScreenFragment.java
import com.softcon.meetinguniv.R;
import com.pedro.library.AutoPermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    private Spinner join_provinceSpinner;
    private Spinner join_univSpinner;
    private Spinner join_majorSpinner;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private JoinProfileFragment joinProfileFragment;
    private UserInfo userInfo;


    // for popup
    private ArrayAdapter<String> provinceAdapter;
    private ArrayAdapter<String> univAdapter;
    private ArrayAdapter<String> majorAdapter;

    // for province name parsing
    private String provinceName;
    private ArrayList<String> provinceNames = new ArrayList<String>();
    private boolean inCtp_kor_nm = false;
    private String provinceCode;
    private ArrayList<String> provinceCodes = new ArrayList<String>();
    private boolean inCtprvn_cd = false;
    private String provinceCodeForResult;
    private String provinceNameForResult;

    // for city name parsing
    private String cityName;
    private ArrayList<String> cityNames = new ArrayList<String>();
    private boolean inSig_kor_nm = false;
    private String cityCode;
    private ArrayList<String> cityCodes = new ArrayList<String>();
    private boolean inSig_cd = false;
    private String cityCodeForResult;
    private boolean matchProvinceCode = false;

    // for school name parsing
    private String campusName;
    private ArrayList<String> campusNames = new ArrayList<String>();
    private boolean inCampusName = false;
    private String schoolType;
    private ArrayList<String> schoolTypes = new ArrayList<String>();
    private boolean inSchoolType = false;
    private String schoolName;
    private String dumpSchoolName;
    private ArrayList<String> schoolNames = new ArrayList<String>();
    private boolean inSchoolName = false;
    private String schoolRegion;
    private ArrayList<String> schoolRegions = new ArrayList<String>();
    private boolean inRegion = false;
    private boolean matchSchoolRegion = false;
    private String schoolNameForResult;

    // for major name parsing
//    private boolean inmajorName;
    private boolean inTotalCount = false;
    private int totalMajorNum;

    private String majorName;
    private ArrayList<String> majorNames = new ArrayList<String>();
    private boolean inMajorName = false;
    private String majorStatus;
    private ArrayList<String> majorStatuses = new ArrayList<String>();
    private boolean inMajorStatus = false;
    private String schoolNameOfMajor;
    private ArrayList<String> schoolNamesOfMajor = new ArrayList<String>();
    private boolean inSchoolNameOfMajor = false;
    private String pastTagName;
    private String pastSchoolName;


    private ImageView studentIDImage;
    private File file;
    private boolean setStudentIDImage = false;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("????????????");

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference().child("?????????");

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
                Log.d(TAG, "?????? ?????? ??????");
            } else {
                Log.d(TAG, "?????? ?????? ??????");
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // bundle ????????? ??????
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
//                    Toast.makeText(getContext(), "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
//                } else if (join_password.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
//                } else if (join_password.getText().toString().length() < 8) {
//                    Toast.makeText(getContext(), "??????????????? 8??? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
//                } else if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", join_password.getText().toString())) {
//                    Toast.makeText(getContext(), "??????????????? ??????/??????/????????????(?????? ??????)??? ????????????, 2??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
//                } //Only ??????
//                else if (join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && !join_password.getText().toString().matches(".*[0-9]+.*")
//                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only ??????", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//                //Only ??????
//                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && join_password.getText().toString().matches(".*[0-9]+.*")
//                        && !join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only ??????", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                }
//                //Only ????????????
//                else if (!join_password.getText().toString().matches(".*[a-zA-Z]+.*")
//                        && !join_password.getText().toString().matches(".*[0-9]+.*")
//                        && join_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+.*")) {
//                    Toast.makeText(getContext(), "Only ????????????", Toast.LENGTH_SHORT).show();
//                    join_password.requestFocus();
//                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                } else if (join_passwordCheck.getText().toString().equals("")) {
//                    Toast.makeText(getContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
//                } else
                if (join_univ.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "????????? ???????????????.", Toast.LENGTH_SHORT).show();
                } else if (!setStudentIDImage) {
                    Toast.makeText(getContext(), "????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                } else {
                    // ?????? ??????
                    userInfo.setSchoolName(String.valueOf(join_univ.getText()));
//                    databaseReference.child(String.valueOf(userInfo.getUserID())).child("??????").setValue(userInfo.getSchoolName());
                    // ????????? ??????
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
//                            Log.d("????????? ??????", "??????");
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Log.d("????????? ??????", "??????");
//                        }
//                    });
                    // ????????? ?????????
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
                // ?????? ??????
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
//                        Log.d("????????? ??????", "??????");
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Log.d("????????? ??????", "??????");
//                    }
//                });

//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("????????????").child("??????").child("???????????? ???????????? ??????").setValue(userInfo.isMeetingUnivAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("????????????").child("??????").child("???????????? ?????? ??? ?????? ??????").setValue(userInfo.isPersonalInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("????????????").child("??????").child("???????????? ???????????? ??????").setValue(userInfo.isLocationInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("????????????").child("??????").child("???????????? ?????? ?????? ??????").setValue(userInfo.isPromotionInfoAgreementCheckbox());
//                databaseReference.child(String.valueOf(userInfo.getUserID())).child("??????").setValue(userInfo.getSchoolName());
                // ????????? ??????
                // ????????? ?????????
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


                join_provinceSpinner = dialogView.findViewById(R.id.join_provinceSpinner);

                join_univSpinner = dialogView.findViewById(R.id.join_univSpinner);

                join_majorSpinner = dialogView.findViewById(R.id.join_majorSpinner);

                joinSelectUnivOk_button = dialogView.findViewById(R.id.joinSelectUnivOk_button);

                joinSelectUnivOk_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        selected_univ = (String) join_UnivSpinner.getSelectedItem();
//                        System.out.println(selected_univ);
                        join_univ.setText((CharSequence) join_univSpinner.getSelectedItem());
                        dialog.dismiss();
                    }
                });

                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(join_univSpinner);
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    System.out.println(window.getHeight());
                    window.setHeight(1000);
                    System.out.println(window.getHeight());
                    univAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getProvinceNameXmlData();
//                getMajorNameXmlData();

                provinceAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, provinceNames);
//                provinceAdapter.get
//                {
//                    @Override
//                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                        if (position == 0) {
//                            parent.setLayoutParams(new LinearLayout.LayoutParams(-1, 500));
//                        }
//                        View v = super.getDropDownView(position, convertView, parent);
//                        TextView textView = (TextView) v;
//                        textView.setTextSize(Font.getSize() + 2);
//                        textView.setWidth(nativeSpinner.getWidth());
//                        textView.setGravity(alignment);
//                        return v;
//
////                        return super.getDropDownView(position, convertView, parent);
//                    }
//                };
                provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                join_provinceSpinner.setAdapter(provinceAdapter);

                univAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, schoolNames);
                univAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                join_univSpinner.setAdapter(univAdapter);

                majorAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, majorNames);
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                join_majorSpinner.setAdapter(majorAdapter);


                join_provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        provinceNameForResult = provinceNames.get(position);
//                        provinceCodeForResult = provinceCodes.get(position);
//                        cityCodes.clear();
//                        cityNames.clear();
//                        getCityNameXmlData();
//                        cityAdapter.notifyDataSetChanged();
                        schoolNames.clear();
                        getSchoolNameXmlData();
                        univAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


//                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        cityCodeForResult = cityCodes.get(position);
////                        schoolNames.clear();
//                        getSchoolNameXmlData();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

                join_univSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        schoolNameForResult = schoolNames.get(position);
                        System.out.println(schoolNameForResult);
                        majorNames.clear();
                        System.out.println("///////////////////////////////////////////////////");

                        getMajorNameXmlData();
                        majorAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                join_majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

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
//                builder.setTitle("?????? ??????");
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
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "???????????? ?????? ??????");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            System.out.println("A");
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("ctp_kor_nm")) {
                                this.inCtp_kor_nm = true;
                                System.out.println("B");
                            }
//                            if(parser.getName().equals("ctprvn_cd")) {
//                                this.inCtprvn_cd = true;
//                                System.out.println("b");
//                            }
//                            else if(parser.getName().equals("ctp_kor_nm")) {
//                                this.inCtp_kor_nm = true;
//                                System.out.println("B");
//                            }
                            break;

                        case XmlPullParser.TEXT:
                            if(this.inCtp_kor_nm) {
                                this.provinceName = parser.getText();
                                this.inCtp_kor_nm = false;
                                System.out.println("C");
                            }
//                            if(this.inCtprvn_cd) {
//                                this.provinceCode = parser.getText();
//                                this.inCtprvn_cd = false;
//                                System.out.println("c");
//                            }
//                            else if(this.inCtp_kor_nm) {
//                                this.provinceName = parser.getText();
//                                this.inCtp_kor_nm = false;
//                                System.out.println("C");
//                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("gml:featureMember")) {
//                                this.provinceCodes.add(this.provinceCode);
//                                System.out.println("d");
                                this.provinceNames.add(this.provinceName);
                                System.out.println("D");
//                                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.provinceNames);
//                                provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                                this.province_spinner.setAdapter(provinceAdapter);
//                                this.province_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        provinceCodeForResult = provinceCodes.get(position);
//                                        getCityNameXmlData();
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
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
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "????????? ?????? ??????");

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
                                    this.matchProvinceCode = true;
//                                    this.inSig_cd = false;
                                    System.out.println("cc");
                                }
                                else {
                                    this.inSig_kor_nm = false;
                                    this.matchProvinceCode = false;
                                }
                                this.inSig_cd = false;
//                                this.matchProvinceCode = false;

                            }
                            if(this.inSig_kor_nm && this.matchProvinceCode) {
                                this.cityName = parser.getText();
                                this.inSig_kor_nm = false;
                                System.out.println("CC");
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("gml:featureMember") && this.matchProvinceCode) {
                                System.out.println(this.cityCode+" "+this.provinceCodeForResult);
                                this.cityCodes.add(this.cityCode);
                                this.matchProvinceCode = false;
                                System.out.println("********"+this.cityCode+" "+this.provinceCodeForResult);
                                System.out.println("DD");
                                this.cityNames.add(this.cityName);
//                                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.cityNames);
//                                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                                this.city_spinner.setAdapter(cityAdapter);
//                                this.city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        cityCodeForResult = cityCodes.get(position);
//                                        getSchoolNameXmlData();
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
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

                URL url = new URL("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=39e99a147405ffbc1ef3a36fee8a8ac9&svcType=api&svcCode=SCHOOL&contentType=xml&gubun=univ_list&thisPage=1&perPage=442");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "?????? ??????");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("campusName")) {
                                this.inCampusName = true;
                            }
                            else if(parser.getName().equals("schoolType")) {
                                this.inSchoolType = true;
                            }
                            else if(parser.getName().equals("schoolName")) {
//                                System.out.println("schoolName");
                                this.inSchoolName = true;
                            }
                            else if(parser.getName().equals("region")) {
//                                System.out.println("region");
                                this.inRegion = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            if(this.inCampusName) {
                                System.out.println("inCampusName");
                                this.campusName = parser.getText();
                                System.out.println(this.campusName);
//                                if(this.campusName == "??????") {
//                                    this.campusName = null;
//                                }
                                this.inCampusName = false;
                            }
                            if(this.inSchoolType) {
                                System.out.println("inSchoolType");
                                this.schoolType = parser.getText();
                                System.out.println(this.schoolType);
                                this.inSchoolType = false;
                            }
                            if(this.inSchoolName) {
                                System.out.println("inSchoolName");
                                this.schoolName = parser.getText();
//                                if(this.campusName != null && this.schoolName.contains(this.campusName)) {
//                                    this.campusName = null;
//                                }
                                this.inSchoolName = false;
                            }
                            if(this.inRegion) {
                                System.out.println("inRegion");
                                if(parser.getText().equals(this.provinceNameForResult))
                                    this.matchSchoolRegion = true;
//                                else
//                                    this.campusName = null;
//                                    this.schoolName = null;
                                this.inRegion = false;
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("content") && this.matchSchoolRegion) {
                                if(this.campusName.equals("??????") || this.schoolType.equals("????????????(???????????????)") || this.schoolName.contains(this.campusName)) {
                                    this.schoolNames.add(this.schoolName);
                                }
                                else {
                                    this.schoolNames.add(this.schoolName+" "+this.campusName);
                                }

                                this.matchSchoolRegion = false;
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.schoolNames);
//                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                                this.join_UnivSpinner.setAdapter(adapter);
//                                this.join_UnivSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
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

    private void getMajorNameXmlData() {
//        schoolNameForResult
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

                URL url = new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=1&perPage=1&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "?????? ??????");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
//                            if(parser.getName().equals("col")){
//                                if(parser.getAttributeValue(0).equals("?????????")) {
//                                    this.inSchoolNameOfMajor = true;
//                                }
//                            }
                            if (parser.getName().equals("totalCount")) {
                                this.inTotalCount = true;
                            }
                            break;

                        case XmlPullParser.TEXT:
//                            if(this.inSchoolNameOfMajor) {
//                                this.schoolNameOfMajor = parser.getText();
//                                this.inSchoolNameOfMajor = false;
//                            }
                            if (this.inTotalCount) {
                                System.out.println("inTotalCount");
                                this.totalMajorNum = Integer.parseInt(parser.getText());
                                System.out.println(String.valueOf(this.totalMajorNum));
                                this.inTotalCount = false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
//                            System.out.println("E");
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        System.out.println(schoolNameOfMajor.compareTo(schoolNameForResult)); // ??? ?????? ??????, ??? ????????? ??????, ????????? 0

//        if(schoolNameForResult.compareTo(schoolNameOfMajor) > 0) {
//            getMajorNameXmlDataforSearch();
//        }

        int perpage = 1000;
        int repeatCount = this.totalMajorNum/perpage; // ??? ????????? ???(???????????? ?????? ???) ex 48
        if(this.totalMajorNum%perpage>0) {
            repeatCount++;                              // 49=48+1
        }

        // for b tree

        // example
//        int start = 1;
//        int end = repeatCount;
//        int middle = -1;
//        while(start<=end) {
//            int middle = (start+end)/2;
//            if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) == 0)
//                break;
////                return middle;
//            else if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) < 0)
//                end = middle-1;
//            else
//                start = middle+1;
//        }
//        System.out.println(middle);

        int start = 1;
        int end = repeatCount;
        int middle = -1;
        while(start<=end) {
            if(end-start<=2){

            }
            middle = (start+end)/2;
            try {
                System.out.println(middle);
                URL url = new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(middle)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D");
                getMajorNameXmlDataForSearch(url);
                if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) == 0) {
                    getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=" + String.valueOf(middle - 1) + "&perPage=" + String.valueOf(perpage) + "&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"), middle);
                    break;
                }
//                return middle;
                else if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) < 0){
                    end = middle-1;
                }
                else if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) > 0) {
                    if(getMajorNameEachXmlData(url, middle+1))
                        break;
                    start = middle + 1;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(middle);

//        int middleIndex = repeatCount/2; // 24
//        try {
//            getMajorNameXmlDataForSearch(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(middleIndex)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//
//            if (this.schoolNameForResult.compareTo(this.schoolNameOfMajor) < 0) { // schooNameForResult??? ??? ?????? ?????? ??????
//                middleIndex = middleIndex/2; // 12
//                getMajorNameXmlDataForSearch(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(middleIndex)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//            } else if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) == 0) { // ?????? ??????
//                getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(middleIndex-1)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"), middleIndex);
//            } else if(this.schoolNameForResult.compareTo(this.schoolNameOfMajor) > 0) { // schooNameForResult??? ??? ?????? ?????? ??????
//                middleIndex+1+repeatCount/2
//            }
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


//        while(middleIndex/2)

//        for(int i=middleIndex; i<=1; i/2)

        // for b tree


//        for(int i=1; i<=repeatCount; i++) {
//            try {
//                System.out.println("88888888888888888888888888888888888888888888");
//                System.out.println(this.schoolNameForResult);
//                System.out.println(this.schoolNameOfMajor);
//                System.out.println("77777777777777777777777777777777777777777777");
//                getMajorNameXmlDataForSearch(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(i)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//                if (this.schoolNameForResult.compareTo(this.schoolNameOfMajor) <= 0) {
//                    try {
//                        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(i-1)+"&perPage="+String.valueOf(perpage)+"&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"), i);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }

//        for(int i=1; i<=repeatCount; i++) {
//            if (schoolNameForResult.compareTo(schoolNameOfMajor) > 0) {
//                try {
//                    getMajorNameXmlDataForSearch(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(i)+"&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//            }
//            else if(schoolNameForResult.compareTo(schoolNameOfMajor) <= 0){
//                try {
//                    getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(i-1)+"&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"), i);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }

//        for(int i=1; i<=repeatCount; i++){
//            try {
//                getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page="+String.valueOf(i)+"&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }


//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=1&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=2&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=3&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=4&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=5&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=6&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));
//        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=7&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"));

    }

    private void getMajorNameXmlDataForSearch(URL url) {
//        schoolNameForResult
        System.out.println("66666666666666666666666666666666666666666666");
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

//                URL url = new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=1&perPage=1&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "?????? ??????");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("col")) {
//                                System.out.println()
                                if (parser.getAttributeValue(0).equals("?????????")) {
                                    this.inSchoolNameOfMajor = true;
                                }
                            }
//                            if (parser.getName().equals("totalCount")) {
//                                this.inTotalCount = true;
//                            }
                            break;

                        case XmlPullParser.TEXT:
                            if (this.inSchoolNameOfMajor) {
                                this.schoolNameOfMajor = parser.getText();
                                this.inSchoolNameOfMajor = false;
                            }
//                            if (this.inTotalCount) {
//                                System.out.println("inTotalCount");
//                                this.totalMajorNum = Integer.parseInt(parser.getText());
//                                System.out.println(String.valueOf(this.totalMajorNum));
//                                this.inTotalCount = false;
//                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item")) {
                                return;
                            }
//                            System.out.println("E");
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean getMajorNameEachXmlData(URL url, int i) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {

//                URL url = new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=1&perPage=500&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D");
//            InputStream is= url.openStream();

                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

//            parser.setInput(new InputStreamReader(is, "UTF-8"));
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "?????? ?????? ?????? ??????");

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            System.out.println("START_DOCUMENT");
                            break;
                        case XmlPullParser.START_TAG:
                            System.out.println("START_TAG");
                            if (parser.getName().equals("col")) {
                                System.out.println("in col tag");
//                                System.out.println(parser.getAttributeValue(0));
                                switch (parser.getAttributeValue(0)) {
                                    case "???????????(??????)???":
                                        System.out.println("???????????(??????)???");
                                        this.inMajorName = true;
                                        break;
                                    case "????????????":
                                        System.out.println("????????????");
                                        this.inMajorStatus = true;
                                        break;
                                    case "?????????":
                                        System.out.println("?????????");
                                        this.inSchoolNameOfMajor = true;
                                        break;
                                }
//                                System.out.println("///////////////////////////////////////////////");
//                                System.out.println(parser.getAttributeValue(0));
                            }
//                            if(parser.getAttributeName()) {
//
//                            }
//                            if(parser.getName().equals("campusName")) {
//                                this.inCampusName = true;
//                            }
//                            else if(parser.getName().equals("schoolType")) {
//                                this.inSchoolType = true;
//                            }
//                            else if(parser.getName().equals("schoolName")) {
////                                System.out.println("schoolName");
//                                this.inSchoolName = true;
//                            }
//                            else if(parser.getName().equals("region")) {
////                                System.out.println("region");
//                                this.inRegion = true;
//                            }
                            break;

                        case XmlPullParser.TEXT:
                            System.out.println("TEXT");
                            if(this.inMajorName) {
                                System.out.println("inMajorName");
                                this.majorName = parser.getText();
                                this.inMajorName = false;
                            }
                            if(this.inMajorStatus) {
                                System.out.println("inMajorStatus");
                                this.majorStatus = parser.getText();
                                this.inMajorStatus = false;
                            }
                            if(this.inSchoolNameOfMajor) {
                                System.out.println("inSchoolNameOfMajor");
                                this.schoolNameOfMajor = parser.getText();
                                this.inSchoolNameOfMajor = false;
                            }
//                            if(this.inCampusName) {
//                                System.out.println("inCampusName");
//                                this.campusName = parser.getText();
//                                System.out.println(this.campusName);
////                                if(this.campusName == "??????") {
////                                    this.campusName = null;
////                                }
//                                this.inCampusName = false;
//                            }
//                            if(this.inSchoolType) {
//                                System.out.println("inSchoolType");
//                                this.schoolType = parser.getText();
//                                System.out.println(this.schoolType);
//                                this.inSchoolType = false;
//                            }
//                            if(this.inSchoolName) {
//                                System.out.println("inSchoolName");
//                                this.schoolName = parser.getText();
////                                if(this.campusName != null && this.schoolName.contains(this.campusName)) {
////                                    this.campusName = null;
////                                }
//                                this.inSchoolName = false;
//                            }
//                            if(this.inRegion) {
//                                System.out.println("inRegion");
//                                if(parser.getText().equals(this.provinceNameForResult))
//                                    this.matchSchoolRegion = true;
////                                else
////                                    this.campusName = null;
////                                    this.schoolName = null;
//                                this.inRegion = false;
//                            }
                            break;

                        case XmlPullParser.END_TAG:
                            System.out.println("END_TAG");
                            System.out.println(parser.getName());
                            if(parser.getName().equals("item")) {
                                System.out.println("in item");
                                System.out.println(this.schoolNameForResult);
                                System.out.println(this.schoolNameOfMajor);
                                System.out.println(this.majorStatus);
                                if(this.schoolNameOfMajor.equals(this.schoolNameForResult) && (this.majorStatus.equals("??????") || this.majorStatus.equals("??????")|| this.majorStatus.equals("??????[??????]") || this.majorStatus.equals("??????[??????]") || this.majorStatus.equals("??????[??????]"))) {
                                    System.out.println("pass");
                                    this.majorNames.add(this.majorName);
                                    System.out.println(this.majorNames.size());
                                }

                                // ?????????
                                if(this.schoolNameOfMajor.equals(this.schoolNameForResult)) {
                                    this.pastSchoolName = this.schoolNameOfMajor;
                                    System.out.println("pastSchoolName : "+this.pastSchoolName);
                                    System.out.println("schoolNameOfMajor : "+this.schoolNameOfMajor);
                                }
                                else {
                                    if(this.pastSchoolName != null) {
                                        if (this.pastSchoolName.equals(this.schoolNameForResult)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                            if(parser.getName().equals("data")) {
                                System.out.println("in data");
                                if(this.pastSchoolName != null) {
                                    if (this.pastSchoolName.equals(this.schoolNameForResult)) {
                                        getMajorNameEachXmlData(new URL("https://api.odcloud.kr/api/15014632/v1/uddi:d6552229-9686-4565-a421-ab303156f076_202004101338?page=" + String.valueOf(i) + "&perPage=1000&returnType=XML&serviceKey=mmci4cpi6htFTp4xCJ7AAeYWR3C2wwWkFHLfGM68mA6iNo%2BGuIQ8dVtgzXv5GL5DTQfZb0YMMj0hV7pq4ScxlQ%3D%3D"), i + i);
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                                else {
                                    // ?????? ????????? ?????? ????????? ???
                                    this.majorNames.add("-");
                                    System.out.println(this.majorNames.size());
                                }
                            }

//                            if(this.schoolNameOfMajor.equals(this))
//                            if(parser.getName().equals("content") && this.matchSchoolRegion) {
//                                if(this.campusName.equals("??????") || this.schoolType.equals("????????????(???????????????)") || this.schoolName.contains(this.campusName)) {
//                                    this.schoolNames.add(this.schoolName);
//                                }
//                                else {
//                                    this.schoolNames.add(this.schoolName+" "+this.campusName);
//                                }
//
//                                this.matchSchoolRegion = false;
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.schoolNames);
//                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                                this.join_UnivSpinner.setAdapter(adapter);
//                                this.join_UnivSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
//                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
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