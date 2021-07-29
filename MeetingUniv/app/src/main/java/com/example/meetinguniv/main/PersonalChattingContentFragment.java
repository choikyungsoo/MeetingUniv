package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetinguniv.FloatingFriendsListContentFragment;
import com.example.meetinguniv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PersonalChattingContentFragment extends Fragment implements View.OnClickListener {

    private ArrayList<PersonalChatingRecycleritem> list = new ArrayList<PersonalChatingRecycleritem>();
    private FloatingActionButton invitingBTN;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;
        this.invitingBTN = view.findViewById(R.id.invitingfriends);
        
        PersonalChatingAdapterRecycleritem recyclerItemAdapter = new PersonalChatingAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        recyclerItemAdapter.notifyDataSetChanged();

        this.invitingBTN.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_personal_chatting_content, container, false);
//        return rootView;
        return inflater.inflate(R.layout.fragment_personal_chatting_content, container, false);
    }

    private void addRecyclerItem(int profile, String memberlist){
        PersonalChatingRecycleritem recyclerItem = new PersonalChatingRecycleritem();
        recyclerItem.setChatingProfile(profile);
        recyclerItem.setMemberlist(memberlist);
        list.add(recyclerItem);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.invitingfriends:
                moveToFriendsList(v);
                break;
        }
    }

    private void moveToFriendsList(View v) {
        FloatingFriendsListContentFragment fragment= new FloatingFriendsListContentFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                .replace(R.id.containfriendlist, fragment)
                .commit();
    }
}