package com.softcon.meetinguniv.main;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.softcon.meetinguniv.EditTeamMemberElementFragment;
import com.softcon.meetinguniv.MTeamMemberRecyclerItem;
import com.softcon.meetinguniv.R;
import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class MatchingContentFragment extends Fragment implements View.OnClickListener, RangeSlider.OnChangeListener {

    private View view;
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
    ////

    private ArrayList<Integer> ImageSource = new ArrayList<Integer>();

    private ArrayList<Float> ageRange = new ArrayList<Float>();
    private View dialogView;

    private String schoolName;

    private ArrayList<String> schoolNames = new ArrayList<String>();
    private boolean inSchoolName = false;

    private int settingposition;


    private EditTeamMemberElementFragment ETMfragment;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.teamprofileRecycler) ;
        TeamMemberAdapterRecycleritem recyclerItemAdapter = new TeamMemberAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        this.ETMfragment = new EditTeamMemberElementFragment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        this.settingposition = 0;
//        addRecyclerItem(123);
        //팀원 수와 편집에 따라 변함(유동적으로 변함)
        Bundle bundle = getArguments();
        if(bundle != null){
            ArrayList<Integer> takeData = bundle.getIntegerArrayList("changemember");
            for(int i=0; i<takeData.size(); i++){
                addRecyclerItem(takeData.get(i),0);
            }
        } else {
            addRecyclerItem(R.drawable.prot, 0);
            addRecyclerItem(R.drawable.prot2, 0);
            addRecyclerItem(R.drawable.prot3, 0);
        }

        //팀원 편집 아이콘(필수적으로 고정)
        addRecyclerItem(R.drawable.settingicon, 1);
        recyclerItemAdapter.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position == settingposition){
                    giveRecycleritemData();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                            .replace(R.id.Framecontainer, ETMfragment)
                            .commit();
                }
            }
        });
        recyclerItemAdapter.notifyDataSetChanged();

    }

    private void giveRecycleritemData() {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("currentteam", this.ImageSource);
        this.ETMfragment.setArguments(bundle);
    }

    private void addRecyclerItem(int profile, int verfiycode){
        TeamMemberRecyclerItem recyclerItem = new TeamMemberRecyclerItem();
        recyclerItem.setMemProfile(profile);
        list.add(recyclerItem);
        System.out.println("프로필프로필프로필"+ profile);
        if(verfiycode == 0) {
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(new File("Test")));

                printWriter.println(profile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.settingposition++;
            this.ImageSource.add(profile);
        }
        ///Test
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


//
        collectBtn =  (Button)view.findViewById(R.id.teambtn);
        matchingBtn = (Button)view.findViewById(R.id.matchbtn);

        //슬라이딩 애니메이션이 필요한 버튼
        collectBtn.setOnClickListener(this);
        //팝업창 구현이 필요한 버튼
        matchingBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        chooseteamElementFragment fragment = new chooseteamElementFragment();
        CurrentContentsFragment test = new CurrentContentsFragment();
        switch (v.getId()){
            case R.id.teambtn:
                //팀선택 버튼
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                        .replace(R.id.Framecontainer, fragment)
                        .commit();

                break;
            case R.id.matchbtn:
                //매칭하기 버튼
                showPopUp();
                this.ageFrom = this.dialogView.findViewById(R.id.chatRoomMembers);
                this.ageTo = this.dialogView.findViewById(R.id.valueTo);
                this.rangeSlider = this.dialogView.findViewById(R.id.rangeSlider);
                this.univSpinner = this.dialogView.findViewById(R.id.join_UnivSpinner);

                this.rangeSlider.addOnChangeListener(this);

                //using API
                getSchoolNameXmlData();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                location = getLocationXmlData();
//                        schoolName = getSchoolNameXmlData();
                        getSchoolNameXmlData();
                        Log.i("parsing", "파싱 완료");

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
        builder.setTitle("조건 선택");
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onValueChange(@NonNull @NotNull RangeSlider slider, float value, boolean fromUser) {
        this.ageRange = (ArrayList<Float>) this.rangeSlider.getValues();

        this.valueFrom = this.ageRange.get(0);
        this.valueTo = this.ageRange.get(1);

        this.ageFrom.setText(String.valueOf((int)valueFrom));
        this.ageTo.setText(String.valueOf((int)valueTo));
    }
}