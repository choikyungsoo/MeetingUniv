package com.example.meetinguniv.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.meetinguniv.R;
import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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