package com.softcon.meetinguniv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.softcon.meetinguniv.main.MatchingContentFragment;
import com.softcon.meetinguniv.main.TeamMemberAdapterRecycleritem;
import com.softcon.meetinguniv.main.TeamMemberRecyclerItem;
import com.softcon.meetinguniv.main.chooseteamElementFragment;

import java.util.ArrayList;

public class AddTeamElementFragment extends Fragment implements View.OnClickListener {
    private Button addTeamBack_BTN;
    private TextView addTeamOk_BTN;
    private SearchView addTeamSearch;
    private InputMethodManager imm;

    private ArrayList<EditTeamRecycleritem1> alllist = new ArrayList<EditTeamRecycleritem1>();
    private ArrayList<EditTeamRecycleritem> currentlist = new ArrayList<EditTeamRecycleritem>();
    private ArrayList<TeamMemberRecyclerItem> storage = new ArrayList<TeamMemberRecyclerItem>();

    private ArrayList<Integer> ImageSource2 = new ArrayList<Integer>();
    private ArrayList<Integer> ImageSource1 = new ArrayList<Integer>();

    private RecyclerView allfriends;
    private RecyclerView currentfriends;

    private EditTeamAdapterRecycleritem recyclerItemAdapter;

    private chooseteamElementFragment fragment;

    private Boolean checkok = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_team_element, container, false);

        this.addTeamBack_BTN = view.findViewById(R.id.addTeamBack_BTN);
        this.addTeamOk_BTN = view.findViewById(R.id.addTeamOk_BTN);
        this.addTeamSearch = view.findViewById(R.id.addTeamSearch);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.addTeamBack_BTN.setOnClickListener(this);
        this.fragment = new chooseteamElementFragment();

        //리사이클러뷰 - 현재 팀원   /////////////////////////////////////////////////////////////
        this.allfriends = view.findViewById(R.id.addFriendsList);
        this.recyclerItemAdapter = new EditTeamAdapterRecycleritem(this.alllist);
        this.allfriends.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.allfriends.setLayoutManager(linearLayoutManager);


        this.addTeamOk_BTN.setOnClickListener(this);
//        Bundle bundle = getArguments();
//        if(bundle != null){
//            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//            for(int i=0; i<takeData.size(); i++){
//                addRecyclerItem(takeData.get(i));
//            }
//        }

        recyclerItemAdapter.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                DeleteCurrentDialog(position);
            }
        });

        this.currentfriends = view.findViewById(R.id.addPresentFriendsList);
        EditTeamAdapterRecycleritem2 recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        //DB연결 시 addRecyclerItem2를 통해 친구목록 가져오기

        addRecyclerItem2(R.drawable.prot, "친구 1");
        addRecyclerItem2(R.drawable.prot2, "친구 2");
        addRecyclerItem2(R.drawable.prot3, "친구 3");

        recyclerItemAdapter2.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                AddCurrentDialog(position);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////

        recyclerItemAdapter.notifyDataSetChanged();
        recyclerItemAdapter2.notifyDataSetChanged();
        return view;
    }

    private void AddCurrentDialog(int position) {
        for(EditTeamRecycleritem etr: this.currentlist){
            this.ImageSource1.add(etr.getE_memporife());
        }
    }

    private void DeleteCurrentDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("팀원 삭제");
        builder.setMessage("팀원을 삭제하시겠습니까?");
        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteCurrentTeam(position);
            }
        });
        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void DeleteCurrentTeam(int position) {
        this.alllist.remove(position);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    //현재 팀원
    private void addRecyclerItem(int profile){
        EditTeamRecycleritem1 recyclerItem = new EditTeamRecycleritem1();
        recyclerItem.setE_memporife(profile);
        this.alllist.add(recyclerItem);
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
            case R.id.addTeamBack_BTN:
                moveToChooseTeamElement();
                break;
            case R.id.addTeamOk_BTN:
                this.checkok = true;
                savechangememberInfo();
                givechangememberInfo();
                moveToChooseTeamElement();
                break;
        }
    }

    private void moveToChooseTeamElement() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.Framecontainer, this.fragment)
                .commit();
        imm.hideSoftInputFromWindow(addTeamSearch.getWindowToken(),0);
    }

    private void savechangememberInfo() {
        for(EditTeamRecycleritem1 etr1: this.alllist){
            this.ImageSource2.add(etr1.getE_memporife());
        }
    }

    private void givechangememberInfo() {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("changemember", this.ImageSource2);
        this.fragment.setArguments(bundle);
    }
}