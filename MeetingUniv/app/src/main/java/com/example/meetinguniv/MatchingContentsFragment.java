package com.example.meetinguniv;

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


public class MatchingContentsFragment extends Fragment implements View.OnClickListener{

    private PopupFragment pfragment;
    private View view;
    private Button collectBtn;
    private Button matchingBtn;
    private boolean checkup;
    private Animation translateup;
    private View slideView;
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

        collectBtn =  (Button)view.findViewById(R.id.teambtn);
        matchingBtn = (Button)view.findViewById(R.id.matchbtn);

        checkup = false;
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
//                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
//                LayoutInflater inflater = requireActivity().getLayoutInflater();
//
//                ad.setView(inflater.inflate(R.layout.popup_match, null));
//                ad.create();
                Dialog();
                break;
        }
    }


    private void Dialog() {
        pfragment = new PopupFragment(getActivity());
        pfragment.setCancelable(true);
        pfragment.getWindow();
        pfragment.show();
    }

    private View.OnClickListener leftListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };
}