package com.example.meetinguniv;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.compose.animation.core.Animation;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MatchingContentsFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button collectBtn;
    private Button matchingBtn;

    private ArrayList<TeamMemberRecyclerItem> list = new ArrayList<TeamMemberRecyclerItem>();

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
        view = inflater.inflate(R.layout.fragment_matching_contents, container, false);


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
//        CrystalRangeSeekbar rangeSeekbar = new CrystalRangeSeekbar(getContext());
//        Spinner spinner = new Spinner(getContext());
//        LinearLayout linearLayout = view.findViewById(R.id.popup_match);
//        SeekBar seekBar = new SeekBar(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setTitle("조건 선택");
//        builder.setItems(seekBar);
//        builder.setView(rangeSeekbar);
//        builder.setView(spinner);
        builder.setView(inflater.inflate(R.layout.popup_match, null));
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
}