package com.softcon.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class InviteFriendElementFragment extends Fragment implements View.OnClickListener {
    private ArrayList<EditTeamRecycleritem> currentlist = new ArrayList<EditTeamRecycleritem>();
    private RecyclerView currentfriends;
    private Button inviteFriendBack_BTN;

    private EditTeamAdapterRecycleritem recyclerItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_friend_element, container, false);
        this.inviteFriendBack_BTN = view.findViewById(R.id.inviteFriendBack_BTN);
        this.inviteFriendBack_BTN.setOnClickListener(this);

        //리사이클러뷰 - 현재 팀원   /////////////////////////////////////////////////////////////
        this.currentfriends = view.findViewById(R.id.inviteFriendsList);
        EditTeamAdapterRecycleritem2 recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        //DB연결 시 addRecyclerItem2를 통해 친구목록 가져오기

        addRecyclerItem2(R.drawable.prot, "친구 1");
        addRecyclerItem2(R.drawable.prot2, "친구 2");
        addRecyclerItem2(R.drawable.prot3, "친구 3");

        return view;
    }

    //모든 친구 목록
    private void addRecyclerItem2(int profile, String memberlist){
        EditTeamRecycleritem recyclerItem = new EditTeamRecycleritem();
        recyclerItem.setE_memporife(profile);
        recyclerItem.setE_memname(memberlist);
        this.currentlist.add(recyclerItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inviteFriendBack_BTN:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(this, this)
                        .commit();
                imm.hideSoftInputFromWindow(addTeamSearch.getWindowToken(),0);
                break;
        }
    }
}