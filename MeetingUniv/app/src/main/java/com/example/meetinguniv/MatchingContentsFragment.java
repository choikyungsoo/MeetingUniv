package com.example.meetinguniv;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.compose.animation.core.Animation;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;

public class MatchingContentsFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button collectBtn;
    private Button matchingBtn;
    private boolean checkup;
    private Animation translateup;
    private View slideView;
    private ArrayList<CurrentRecycleritem> list = new ArrayList<CurrentRecycleritem>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        RecyclerView recyclerView = view.findViewById(R.id.recycler1) ;
//        CurrentAdapterRecyclerItem recyclerItemAdapter = new CurrentAdapterRecyclerItem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter) ;
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        addRecyclerItem("~님 외 * 명 + ~ 님 외 * 명", "성공", "2020/07/14 05:10");
//        recyclerItemAdapter.notifyDataSetChanged();

    }

    private void addRecyclerItem(String description, String check, String data){
        CurrentRecycleritem recyclerItem = new CurrentRecycleritem();
        recyclerItem.setDescription(description);
        recyclerItem.setCheck(check);
        recyclerItem.setDate(data);
        list.add(recyclerItem);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matching_contents, container, false);


//
        collectBtn =  (Button)view.findViewById(R.id.teambtn);
        matchingBtn = (Button)view.findViewById(R.id.matchbtn);

        checkup = false;
        //슬라이딩 애니메이션이 필요한 버튼
        collectBtn.setOnClickListener(this);
        //팝업창 구현이 필요한 버튼
        matchingBtn.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

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
        }
    }

    private void showPopUp() {
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setTitle("조건 선택");

        builder.setView(inflater.inflate(R.layout.popup_match, null));

        dialog = builder.create();
        dialog.show();
    }
}