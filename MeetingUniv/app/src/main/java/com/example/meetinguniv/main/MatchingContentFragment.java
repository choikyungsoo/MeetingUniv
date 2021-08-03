package com.example.meetinguniv.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.example.meetinguniv.MatchPopup;
import com.example.meetinguniv.R;
import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MatchingContentFragment extends Fragment implements View.OnClickListener, RangeSlider.OnChangeListener {

    private View view;
    private Button collectBtn;
    private Button matchingBtn;
    private RangeSlider rangeSlider;
    private TextView ageFrom;
    private TextView ageTo;

    private float valueFrom;
    private float valueTo;

    private ArrayList<TeamMemberRecyclerItem> list = new ArrayList<TeamMemberRecyclerItem>();
    private ArrayList<Float> ageRange = new ArrayList<Float>();
    private View dialogView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.teamprofileRecycler) ;
        TeamMemberAdapterRecycleritem recyclerItemAdapter = new TeamMemberAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        addRecyclerItem(123);
        for(int i=0; i<4; i++) {
            addRecyclerItem(R.drawable.prot);
        }
        recyclerItemAdapter.notifyDataSetChanged();

    }

    private void addRecyclerItem(int profile){
        TeamMemberRecyclerItem recyclerItem = new TeamMemberRecyclerItem();
        recyclerItem.setMemProfile(profile);
        list.add(recyclerItem);

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
                this.ageFrom = this.dialogView.findViewById(R.id.valueFrom);
                this.ageTo = this.dialogView.findViewById(R.id.valueTo);
                this.rangeSlider = this.dialogView.findViewById(R.id.rangeSlider);
                this.rangeSlider.addOnChangeListener(this);
//                Dialog dialog;
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("조건 선택");
//                builder.setItems()
////                , android.R.style.Theme_DeviceDefault_Light_Dialog
////                LayoutInflater inflater = requireActivity().getLayoutInflater();
////
////                ad.setView(inflater.inflate(R.layout.popup_match, null));
////                ad.create();
//                Dialog();
//                break;
        }
    }

    private void showPopUp() {
        Dialog dialog;
        this.dialogView = requireActivity().getLayoutInflater().inflate(R.layout.popup_match, null);
//        CrystalRangeSeekbar rangeSeekbar = new CrystalRangeSeekbar(getContext());
//        Spinner spinner = new Spinner(getContext());
//        LinearLayout linearLayout = view.findViewById(R.id.popup_match);
//        SeekBar seekBar = new SeekBar(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater =
        builder.setTitle("조건 선택");
//        builder.setItems(seekBar);
//        builder.setView(rangeSeekbar);
        builder.setView(dialogView);
//        builder.setView(inflater.inflate(R.layout.popup_match, null));
//        builder.setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setPositiveButton("매칭하기", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 매칭하기 기능
//            }
//        });
        dialog = builder.create();
        dialog.show();
//        MatchPopup matchPopup = new MatchPopup(getContext());
//        this.rangeSlider = view.findViewById(R.id.rangeSlider);
//        this.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull @NotNull RangeSlider slider, float value, boolean fromUser) {
//                ageRange = (ArrayList<Float>) rangeSlider.getValues();
////        this.ageRange.get(0);
//                Log.i("valuefrom", ageRange.get(0).toString());
//                Log.i("valueto", ageRange.get(1).toString());
//            }
//        });
    }

//    private void Dialog() {
////        pfragment = new PopupFragment(getActivity());
////        pfragment.setCancelable(true);
////        pfragment.getWindow();
////        pfragment.show();
//    }

    private View.OnClickListener leftListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onValueChange(@NonNull @NotNull RangeSlider slider, float value, boolean fromUser) {
        this.ageRange = (ArrayList<Float>) this.rangeSlider.getValues();

        this.valueFrom = this.ageRange.get(0);
        this.valueTo = this.ageRange.get(1);

        this.ageFrom.setText(String.valueOf((int)valueFrom));
        this.ageTo.setText(String.valueOf((int)valueTo));
    }
}