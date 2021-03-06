package com.softcon.meetinguniv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.softcon.meetinguniv.main.MatchingContentFragment;
import com.softcon.meetinguniv.main.TeamMemberAdapterRecycleritem;
import com.softcon.meetinguniv.main.TeamMemberRecyclerItem;

import java.util.ArrayList;
import java.util.Collection;


public class EditTeamMemberElementFragment extends Fragment implements View.OnClickListener{
    private Button EditbackBTN;
    private TextView CheckTC;
    private SearchView editsearch;
    private InputMethodManager imm;
    private String userID;
    private int TeamNum;

    private ArrayList<EditTeamRecycleritem1> alllist = new ArrayList<EditTeamRecycleritem1>();
    private ArrayList<EditTeamRecycleritem> currentlist = new ArrayList<EditTeamRecycleritem>();
    private ArrayList<TeamMemberRecyclerItem> storage = new ArrayList<TeamMemberRecyclerItem>();

    private ArrayList<Integer> ImageSource2 = new ArrayList<Integer>();
    private ArrayList<Integer> ImageSource1 = new ArrayList<Integer>();

    private ArrayList<String> TeamMember = new ArrayList<String>();
    private ArrayList<String> TeamPersonalMember = new ArrayList<String>();

    private ArrayList<String> FriendList = new ArrayList<String>();

    private RecyclerView allfriends;
    private RecyclerView currentfriends;

    private EditTeamAdapterRecycleritem recyclerItemAdapter;

    private MatchingContentFragment fragment;

    private Boolean checkok = false;

    private FirebaseStorage ETM_storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = ETM_storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("????????????");
    private DatabaseReference T_databaseReference = database.getReference("?????????");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userID = getArguments().getString("userID");
        System.out.println("ETM : " + this.userID);
        //?????? ?????? ????????? ???????????? ?????? ????????? ???????????? ???
        M_databaseReference.child(this.userID).child("?????? ???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamNum = Integer.parseInt(String.valueOf(snapshot.getValue().toString()));
                Current_TakeDataFromFirebase();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //????????? ?????? ???????????? ?????? ????????? ???????????? ???




        All_TakeDataFromFirebase();
    }

    private void All_TakeDataFromFirebase() {
//        this.M_databaseReference.child(String.valueOf(this.userID)).child("????????????").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                FriendList.addAll((Collection<? extends String>) snapshot.getValue());
//                System.out.println("ETM ?????? ?????? : " + FriendList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    private void Current_TakeDataFromFirebase() {
        this.M_databaseReference.child(String.valueOf(this.userID)).child("???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamMember.addAll((Collection<? extends String>) snapshot.getValue());
                T_databaseReference.child(String.valueOf(TeamMember.get(TeamNum))).child("??????").addValueEventListener(new ValueEventListener() {
                    //???????????? ?????? ?????? ????????? ???????????? ???
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("????????? ?????? ??? ?????????:" + snapshot.getValue());
                        TeamPersonalMember.addAll((Collection<? extends String>) snapshot.getValue());
                        //?????? ????????? ????????? ???????????? ?????? ?????????
                        for(int i = 0; i < TeamPersonalMember.size(); i++) {
                            System.out.println("TeamPersonalMember:" + TeamPersonalMember.get(i));
                            storageRef.child(TeamPersonalMember.get(i) + "/" + "????????? ??????.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println("ETM ??? ??? ?????????:" + uri);
                                    addRecyclerItem(uri);
                                    recyclerItemAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_team_member_element, container, false);
        this.EditbackBTN = view.findViewById(R.id.EditbackBTN);
        this.CheckTC = view.findViewById(R.id.CheckTC);
        this.editsearch = view.findViewById(R.id.editsearch);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.EditbackBTN.setOnClickListener(this);
        this.fragment = new MatchingContentFragment();

        //?????????????????? - ?????? ??????   /////////////////////////////////////////////////////////////
        this.allfriends = view.findViewById(R.id.editFriendsList);
        this.recyclerItemAdapter = new EditTeamAdapterRecycleritem(this.alllist);
        this.allfriends.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.allfriends.setLayoutManager(linearLayoutManager);

        this.CheckTC.setOnClickListener(this);
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

        //////////////////////////////////////////////////////////////////////////////////////


        //?????????????????? - ????????????///////////////////////////////////////////////////////////////////
        this.currentfriends = view.findViewById(R.id.editPresentFriendsList);
        EditTeamAdapterRecycleritem2 recyclerItemAdapter2 = new EditTeamAdapterRecycleritem2(this.currentlist);
        this.currentfriends.setAdapter(recyclerItemAdapter2) ;

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        this.currentfriends.setLayoutManager(linearLayoutManager2);

        //DB?????? ??? addRecyclerItem2??? ?????? ???????????? ????????????!

//        addRecyclerItem2(R.drawable.prot, "?????? 1");
//        addRecyclerItem2(R.drawable.prot2, "?????? 2");
//        addRecyclerItem2(R.drawable.prot3, "?????? 3");

        recyclerItemAdapter2.setOnItemClickListener(new TeamMemberAdapterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                AddCurrentDialog(position);
//            }
//        });
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
//                            if (takeData.get(position) == alllist.get(i).getE_memporife()) {
//                                DeleteCurrentDialog(i);
//                                break;
//                            }
//                            }
//                            break;
                        }
                    }
                    v.setSelected(false);
                }
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
        builder.setTitle("?????? ??????");
        builder.setMessage("????????? ?????????????????????????");
        builder.setNegativeButton("???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteCurrentTeam(position);
            }
        });
        builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void DeleteCurrentTeam(int position) {
        this.alllist.remove(position);
        System.out.println("????????? ?????? : " + this.alllist.get(position));
        recyclerItemAdapter.notifyDataSetChanged();
    }

    //?????? ??????
    private void addRecyclerItem(Uri profile){
        EditTeamRecycleritem1 recyclerItem = new EditTeamRecycleritem1();
        recyclerItem.setE_memporife(profile);
        this.alllist.add(recyclerItem);
        System.out.println("?????? ?????? : " + this.alllist);
    }

    //?????? ?????? ??????
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
                moveToMachingcontent(v);
                break;
            case R.id.CheckTC:
                this.checkok = true;
                savechangememberInfo();
//                givechangememberInfo();
                moveToMachingcontent(v);
                break;
        }
    }

    private void moveToMachingcontent(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("userID", this.userID);
//        Navigation.findNavController(v).navigate(R.id.matchingContentsFragment,bundle);
        this.fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.Framecontainer, this.fragment)
                .commit();
        imm.hideSoftInputFromWindow(editsearch.getWindowToken(),0);
    }

    private void savechangememberInfo() {
        for(EditTeamRecycleritem1 etr1: this.alllist){
//            this.ImageSource2.add(etr1.getE_memporife());
        }
    }

    private void givechangememberInfo() {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("changemember", this.ImageSource2);
        this.fragment.setArguments(bundle);
    }

}