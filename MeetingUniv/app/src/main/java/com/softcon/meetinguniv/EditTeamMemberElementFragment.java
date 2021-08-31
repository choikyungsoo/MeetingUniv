package com.softcon.meetinguniv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.softcon.meetinguniv.main.FriendsListAdapterRecycleritem;
import com.softcon.meetinguniv.main.FriendsListRecycleritem;
import com.softcon.meetinguniv.main.MatchingContentFragment;
import com.softcon.meetinguniv.main.TeamMemberAdapterRecycleritem;
import com.softcon.meetinguniv.main.TeamMemberRecyclerItem;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.MatchingContentFragment;

import java.util.ArrayList;


public class EditTeamMemberElementFragment extends Fragment implements View.OnClickListener{
    private Button EditbackBTN;
    private TextView CheckTC;
    private SearchView editsearch;
    private InputMethodManager imm;

    private ArrayList<EditTeamRecycleritem1> alllist = new ArrayList<EditTeamRecycleritem1>();
    private ArrayList<EditTeamRecycleritem> currentlist = new ArrayList<EditTeamRecycleritem>();
    private ArrayList<TeamMemberRecyclerItem> storage = new ArrayList<TeamMemberRecyclerItem>();

    private RecyclerView allfriends;
    private RecyclerView currentfriends;

    private EditTeamAdapterRecycleritem recyclerItemAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_team_member_element, container, false);
        this.EditbackBTN = view.findViewById(R.id.EditbackBTN);
        this.CheckTC = view.findViewById(R.id.TCCheck);
        this.editsearch = view.findViewById(R.id.Editsearch);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.EditbackBTN.setOnClickListener(this);

        //리사이클러뷰 - 현재 팀원   /////////////////////////////////////////////////////////////
        this.allfriends = view.findViewById(R.id.editfriendslist);
        this.recyclerItemAdapter = new EditTeamAdapterRecycleritem(this.alllist);
        this.allfriends.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.allfriends.setLayoutManager(linearLayoutManager);


        Bundle bundle = getArguments();
        if(bundle != null){
            ArrayList<Integer> takeData = bundle.getIntegerArrayList("currentteam");
            for(int i=0; i<takeData.size(); i++){
                addRecyclerItem(takeData.get(i));
            }
        }

        recyclerItemAdapter.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
               DeleteCurrentDialog(position);
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////


        //리사이클러뷰 - 친구목록///////////////////////////////////////////////////////////////////
        this.currentfriends = view.findViewById(R.id.editpresentfriendslist);
        EditTeamAdapterRecycleritem2 recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        //DB연결 시 addRecyclerItem2를 통해 친구목록 가져오기!
        for(int i =0; i<15; i++) {
            addRecyclerItem2(R.drawable.prot, "테스트용 입니다");
        }
        /////////////////////////////////////////////////////////////////////////////////////////


        recyclerItemAdapter.notifyDataSetChanged();
        recyclerItemAdapter2.notifyDataSetChanged();
        return view;
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
            case R.id.EditbackBTN:
                MatchingContentFragment fragment2= new MatchingContentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Framecontainer, fragment2)
                        .commit();
                imm.hideSoftInputFromWindow(editsearch.getWindowToken(),0);
                break;
            case R.id.TCCheck:

                break;
        }
    }

}