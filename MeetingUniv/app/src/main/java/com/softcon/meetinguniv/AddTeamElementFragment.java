package com.softcon.meetinguniv;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

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
    private EditTeamAdapterRecycleritem2 recyclerItemAdapter2;

    private chooseteamElementFragment fragment;

    private Boolean checkok = false;
    private chooseteamElementFragment.ClickHandler clickHandler;

    private String userID;

    public AddTeamElementFragment(){

    }

    public AddTeamElementFragment(chooseteamElementFragment.ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userID = getArguments().getString("userID");
        chooseteamElementFragment ctE = new chooseteamElementFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("userID", this.userID);
        ctE.setArguments(bundle2);
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
        this.userID = getArguments().getString("userID");

        System.out.println("ATE Fragment UserID : " + this.userID);
        this.addTeamOk_BTN.setOnClickListener(this);
//        Bundle bundle = getArguments();
//        if(bundle != null){
//            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//            for(int i=0; i<takeData.size(); i++){
//                addRecyclerItem(takeData.get(i));
//            }
//        }
        Bundle bundle = getArguments();
        if(bundle != null){
//            Toast.makeText(getContext(), bundle.size() + "개", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), bundle.toString() + "", Toast.LENGTH_SHORT).show();
            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//            Toast.makeText(getContext(), takeData.size() + "개!!", Toast.LENGTH_SHORT).show();
//            for(int i=0; i<takeData.size(); i++){
//                addRecyclerItem(takeData.get(i));
//            }
        }

        this.recyclerItemAdapter.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                DeleteCurrentDialog(position);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////
        this.currentfriends = view.findViewById(R.id.addPresentFriendsList);
        this.recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        //DB연결 시 addRecyclerItem2를 통해 친구목록 가져오기
        addRecyclerItem2(R.drawable.prot, "친구 1");
        addRecyclerItem2(R.drawable.prot2, "친구 2");
        addRecyclerItem2(R.drawable.prot3, "친구 3");


        this.recyclerItemAdapter2.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (!v.isSelected()) {
                    AddCurrentDialog(position);
                    v.setSelected(true);
                } else {
                    for (int i=0; i<alllist.size(); i++) {
                        Bundle bundle = getArguments();
                        if (bundle != null) {
                            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//                            for (int j=0; j<takeData.size(); j++) {
                                Toast.makeText(getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "takeData:" + takeData.get(position) + "all" + alllist.get(i).getE_memporife(), Toast.LENGTH_SHORT).show();
//                                if (takeData.get(position) == alllist.get(i).getE_memporife()) {
//                                    DeleteCurrentDialog(i);
//                                    break;
//                                }
//                            }
//                            break;
                        }
                    }
                    v.setSelected(false);
                }
            }
        });

        this.recyclerItemAdapter.notifyDataSetChanged();
        this.recyclerItemAdapter2.notifyDataSetChanged();
        return view;
    }

    private void AddCurrentDialog(int position) {
//        EditTeamRecycleritem itemview = this.currentlist.get(position);
//        addRecyclerItem(itemview.getE_memporife());

        Bundle bundle = getArguments();
        if(bundle != null){
            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
            addRecyclerItem(takeData.get(position));
        }

//        addRecyclerItem(position);
        this.recyclerItemAdapter.notifyDataSetChanged();

//          Bundle bundle = getArguments();
//          if(bundle != null){
//              ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//              for(int i=0; i<takeData.size(); i++){
////                  addRecyclerItem(takeData.get(i));
//                  if (takeData.get(i) == position) {
//                      System.out.println("통과");
//                      addRecyclerItem(takeData.get(i));
//                      this.recyclerItemAdapter.notifyDataSetChanged();
//                  }
//              }
//          }

//        this.ImageSource1.add(itemview.getE_memporife());
////        Toast.makeText(getContext(), "됏니?"+position, Toast.LENGTH_SHORT).show();
//
//        EditTeamRecycleritem1 recyclerItem = new EditTeamRecycleritem1();
//        Bundle bundle = getArguments();
//        if(bundle != null) {
//            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
//
//            recyclerItem.setE_memporife(takeData.get(position));
//            this.alllist.add(recyclerItem);
//        }
////        EditTeamRecycleritem etr = new EditTeamRecycleritem();

        ///
//        for(EditTeamRecycleritem etr: this.currentlist) {
//            this.ImageSource1.add(etr.getE_memporife());
//        }
    }

    private void DeleteCurrentDialog(int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("팀원 삭제");
//        builder.setMessage("팀원을 삭제하시겠습니까?");
//        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                DeleteCurrentTeam(position);
//            }
//        });
//        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.show();
        DeleteCurrentTeam(position);
    }

    private void DeleteCurrentTeam(int position) {
//        if (this.alllist.size() == 1) {
//            Toast.makeText(getContext(), "x", Toast.LENGTH_SHORT).show();
//        }
//        else this.alllist.remove(position);
        this.alllist.remove(position);
        recyclerItemAdapter.notifyDataSetChanged();
    }

    //현재 팀원
    private void addRecyclerItem(int profile) {
        EditTeamRecycleritem1 recyclerItem = new EditTeamRecycleritem1();
//        recyclerItem.setE_memporife(profile);
//        for (int i=0; i<this.alllist.size(); i++) {
//            if (this.alllist.get(i)==recyclerItem) {
//                Toast.makeText(getContext(), "지워져라", Toast.LENGTH_SHORT).show();
//                DeleteCurrentDialog(i);
//            }
//        }
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
//                savechangememberInfo();
//                givechangememberInfo();
                AddTeamOkDialog addTeamOkDialog = new AddTeamOkDialog(getActivity());
                addTeamOkDialog.showDialog(clickHandler);
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
            Toast.makeText(getContext(), "savechangememberInfo 실행", Toast.LENGTH_SHORT).show();
//            this.ImageSource2.add(etr1.getE_memporife());
        }
    }

    private void givechangememberInfo() {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("changemember", this.ImageSource2);
        this.fragment.setArguments(bundle);
    }
}