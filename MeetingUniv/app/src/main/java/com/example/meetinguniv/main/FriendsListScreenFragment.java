package com.example.meetinguniv.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetinguniv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendsListScreenFragment extends Fragment implements View.OnClickListener {
    private ArrayList<FriendsListRecycleritem> list = new ArrayList<FriendsListRecycleritem>();
    private FloatingActionButton inviteFriends;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;
        FriendsListAdapterRecycleritem recyclerItemAdapter = new FriendsListAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        recyclerItemAdapter.notifyDataSetChanged();
    }

    private void addRecyclerItem(int profile, String memberlist){
        FriendsListRecycleritem recyclerItem = new FriendsListRecycleritem();
        recyclerItem.setF_chatingProfile(profile);
        recyclerItem.setF_memberlist(memberlist);
        list.add(recyclerItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_friends_list_screen, container, false);
        this.inviteFriends = rootView.findViewById(R.id.invitingfriends);
        this.inviteFriends.setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invitingfriends:
                InviteFriendDialog(v);
                break;
        }
    }

    private void InviteFriendDialog(View v) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.popup_invite, null));

        dialog = builder.create();
        dialog.show();
    }
}