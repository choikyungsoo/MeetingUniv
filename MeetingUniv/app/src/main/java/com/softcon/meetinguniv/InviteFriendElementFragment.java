package com.softcon.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InviteFriendElementFragment extends Fragment {
    private ArrayList<EditTeamRecycleritem1> alllist = new ArrayList<EditTeamRecycleritem1>();
    private RecyclerView allfriends;

    private EditTeamAdapterRecycleritem recyclerItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_friend_element, container, false);

        //리사이클러뷰 - 현재 팀원   /////////////////////////////////////////////////////////////
        this.allfriends = view.findViewById(R.id.inviteFriendsList);
        this.recyclerItemAdapter = new EditTeamAdapterRecycleritem(this.alllist);
        this.allfriends.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.allfriends.setLayoutManager(linearLayoutManager);

        return view;
    }
}