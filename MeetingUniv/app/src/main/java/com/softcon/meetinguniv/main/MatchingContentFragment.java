package com.softcon.meetinguniv.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.softcon.meetinguniv.AddTeamElementFragment;
import com.softcon.meetinguniv.EditTeamMemberElementFragment;
import com.softcon.meetinguniv.MTeamMemberRecyclerItem;
import com.softcon.meetinguniv.R;
import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class MatchingContentFragment extends Fragment implements View.OnClickListener, RangeSlider.OnChangeListener {

    private View view;
    private TextView textView;
    private Button collectBtn;
    private Button matchingBtn;
    private RangeSlider rangeSlider;
    private TextView ageFrom;
    private TextView ageTo;
    private Spinner univSpinner;

    private float valueFrom;
    private float valueTo;

    private ArrayList<TeamMemberRecyclerItem> list = new ArrayList<TeamMemberRecyclerItem>();

    ///test
    private ArrayList<MTeamMemberRecyclerItem> mlist = new ArrayList<MTeamMemberRecyclerItem>();

    private ArrayList<Uri> ImageSource = new ArrayList<Uri>();

    private ArrayList<Float> ageRange = new ArrayList<Float>();

    //?????? ?????? ??????, ??? ????????? ?????? ??? ????????? ????????? ???????????? ???
    private ArrayList<String> TeamMember;
    //????????? ?????? ?????? ????????? ?????? ?????? ?????? ??????????????? ????????? ???????????? ???
    private ArrayList<String> TeamPersonalMember;
    private View dialogView;

    private String schoolName;
    private String userID;
    private int TeamNum;

    private ArrayList<String> schoolNames = new ArrayList<String>();
    private boolean inSchoolName = false;

    private int settingposition;


    private EditTeamMemberElementFragment ETMfragment;
    private AddTeamElementFragment ATEfragment;
    private chooseteamElementFragment CTEfragment;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("????????????");
    private DatabaseReference T_databaseReference = database.getReference("?????????");

    private TeamMemberAdapterRecycleritem recyclerItemAdapter;

    public MatchingContentFragment() {
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.teamprofileRecycler) ;
        this.recyclerItemAdapter = new TeamMemberAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter);

        this.ETMfragment = new EditTeamMemberElementFragment();
//        this.ATEfragment = new AddTeamElementFragment(clickHandler);
        this.CTEfragment = new chooseteamElementFragment();
        this.settingposition = 0;
        this.userID = getArguments().getString("userID");
        // ????????? ?????? ????????? Bundle??? ?????? ?????? ???
//        this.TeamNum = getArguments().getInt("TeamNum");
        M_databaseReference.child(this.userID).child("?????? ???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamNum = Integer.parseInt(String.valueOf(snapshot.getValue().toString()));
                TakeDataFromFirebaseDatabase(TeamNum);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this.textView = view.findViewById(R.id.M_TeamName);
        System.out.println("??? ?????? ?????? ????????? ?????? : " + this.userID);
        // UserID??? ?????? ????????? ??????
        Bundle bundle = new Bundle();
        bundle.putString("userID", this.userID);
        this.ETMfragment.setArguments(bundle);
        this.CTEfragment.setArguments(bundle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //???????????????????????? ????????? ????????? ????????????

        this.recyclerItemAdapter.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position == settingposition){
                    // ????????????????????? ?????? ??????
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                            .replace(R.id.Framecontainer, ETMfragment)
                            .commit();
                }
            }
        });
        this.recyclerItemAdapter.notifyDataSetChanged();

    }


    private void TakeDataFromFirebaseDatabase(int teamNum) {
        this.TeamMember = new ArrayList<String>();
        this.TeamPersonalMember = new ArrayList<String>();
        //?????? ????????? ??? ???????????? ??? ????????? ???????????? ???
        this.M_databaseReference.child(String.valueOf(this.userID)).child("???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        TeamMember.addAll((Collection<? extends String>) snapshot.getValue());
                        T_databaseReference.child(String.valueOf(TeamMember.get(teamNum))).child("??? ??????").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue() != null) {
                                    textView.setText((CharSequence) snapshot.getValue());
                                }
                                else
                                    textView.setText("?????? ???????????????");

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        T_databaseReference.child(String.valueOf(TeamMember.get(teamNum))).child("??????").addValueEventListener(new ValueEventListener() {
                            //???????????? ?????? ?????? ????????? ???????????? ???
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue() != null) {
                                    System.out.println("????????? ?????? ??? ?????????:" + snapshot.getValue());
                                    TeamPersonalMember.addAll((Collection<? extends String>) snapshot.getValue());
                                    Uri Settinguri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/meetinguniv-d9983.appspot.com/o/TestSetting%2Fsettingicon.png?alt=media&token=0a096377-239f-405f-b923-9c3b796e59fc");
                                    //?????? ????????? ????????? ???????????? ?????? ?????????
                                    for (int i = 0; i < TeamPersonalMember.size(); i++) {
                                        storageRef.child(TeamPersonalMember.get(i) + "/" + "????????? ??????.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                System.out.println("??? ??? ?????????:" + uri);
                                                addRecyclerItem(uri, 0);
                                                recyclerItemAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                    addRecyclerItem(Settinguri, 1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else
                        System.out.println("?????? X");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addRecyclerItem(Uri profile, int verfiycode){
        TeamMemberRecyclerItem recyclerItem = new TeamMemberRecyclerItem();
        recyclerItem.setMemProfile(profile);
        list.add(recyclerItem);
        if(verfiycode == 0){
            this.ImageSource.add(profile);
        }
    }

    private void moveToEditFriends(View v) {
        EditTeamMemberElementFragment fragment2 = new EditTeamMemberElementFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                .replace(R.id.Framecontainer, fragment2)
                .commit();
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
                parser.setInput(url.openStream(), "UTF-8"); //?????? ??????
//            parser.next();
                int parserEvent = parser.getEventType();
                Log.i("parsing", "?????? ??????");

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
                                this.univSpinner.setAdapter(adapter);
                                this.univSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matching_content, container, false);
        collectBtn =  (Button)view.findViewById(R.id.teambtn);
        matchingBtn = (Button)view.findViewById(R.id.matchbtn);
        //???????????? ?????????????????? ????????? ??????
        collectBtn.setOnClickListener(this);
        //????????? ????????? ????????? ??????
        matchingBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        CurrentContentsFragment test = new CurrentContentsFragment();
        switch (v.getId()){
            case R.id.teambtn:
                //????????? ??????
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                        .replace(R.id.Framecontainer, this.CTEfragment)
                        .commit();
                break;
            case R.id.matchbtn:
                //???????????? ??????
                showPopUp();
                this.ageFrom = this.dialogView.findViewById(R.id.chatRoomMembers);
                this.ageTo = this.dialogView.findViewById(R.id.valueTo);
                this.rangeSlider = this.dialogView.findViewById(R.id.rangeSlider);
                this.univSpinner = this.dialogView.findViewById(R.id.join_univSpinner);
                this.rangeSlider.addOnChangeListener(this);
                //using API
                getSchoolNameXmlData();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                location = getLocationXmlData();
//                        schoolName = getSchoolNameXmlData();
                        getSchoolNameXmlData();
                        Log.i("parsing", "?????? ??????");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                        ArrayList<String> adapter = new ArrayList<String>(R.Layout.)
//                        univSpinner.
                            }
                        });
                    }
                });
        }
    }

    private void showPopUp() {
        Dialog dialog;
        this.dialogView = requireActivity().getLayoutInflater().inflate(R.layout.popup_match, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("?????? ??????");
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onValueChange(@NonNull @NotNull RangeSlider slider, float value, boolean fromUser) {
        this.ageRange = (ArrayList<Float>) this.rangeSlider.getValues();

        this.valueFrom = this.ageRange.get(0);
        this.valueTo = this.ageRange.get(1);

        this.ageFrom.setText(String.valueOf((int)valueFrom));
        this.ageTo.setText(String.valueOf((int)valueTo));
    }
}