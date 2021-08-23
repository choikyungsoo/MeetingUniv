package com.example.meetinguniv;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.meetinguniv.main.FriendsListAdapterRecycleritem;
import com.example.meetinguniv.main.FriendsListRecycleritem;
import com.example.meetinguniv.main.MatchingContentFragment;

import java.util.ArrayList;


public class EditTeamMemberElementFragment extends Fragment implements View.OnClickListener{
    private Button EditbackBTN;
    private SearchView editsearch;
    private InputMethodManager imm;

    private ArrayList<EditTeamRecycleritem> alllist = new ArrayList<EditTeamRecycleritem>();
    private ArrayList<EditTeamRecycleritem> currentlist = new ArrayList<EditTeamRecycleritem>();

    private RecyclerView allfriends;
    private RecyclerView currentfriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_team_member_element, container, false);
        this.EditbackBTN = view.findViewById(R.id.EditbackBTN);
        this.editsearch = view.findViewById(R.id.Editsearch);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.EditbackBTN.setOnClickListener(this);

        //리사이클러뷰 - 현재 팀원
        this.allfriends = view.findViewById(R.id.editfriendslist);
        EditTeamAdapterRecycleritem recyclerItemAdapter = new EditTeamAdapterRecycleritem(this.alllist);
        this.allfriends.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.allfriends.setLayoutManager(linearLayoutManager);

        for(int i =0; i<3; i++) {
            addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        }

        //리사이클러뷰 - 친구목록
        this.currentfriends = view.findViewById(R.id.editpresentfriendslist);
        EditTeamAdapterRecycleritem2 recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        for(int i =0; i<15; i++) {
            addRecyclerItem2(R.drawable.prot, "테스트용 입니다");
        }

        recyclerItemAdapter.notifyDataSetChanged();
        recyclerItemAdapter2.notifyDataSetChanged();
        return view;
    }

    private void addRecyclerItem(int profile, String memberlist){
        EditTeamRecycleritem recyclerItem = new EditTeamRecycleritem();
        recyclerItem.setE_memporife(profile);
        recyclerItem.setE_memname(memberlist);
        this.alllist.add(recyclerItem);
    }

    private void addRecyclerItem2(int profile, String memberlist){
        EditTeamRecycleritem recyclerItem = new EditTeamRecycleritem();
        recyclerItem.setE_memporife(profile);
        recyclerItem.setE_memname(memberlist);
        this.currentlist.add(recyclerItem);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.EditbackBTN:
                MatchingContentFragment fragment2= new MatchingContentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Framecontainer, fragment2)
                        .commit();
                imm.hideSoftInputFromWindow(editsearch.getWindowToken(),0);
                break;
        }
    }
}