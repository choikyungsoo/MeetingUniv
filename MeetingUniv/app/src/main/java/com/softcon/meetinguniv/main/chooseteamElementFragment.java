package com.softcon.meetinguniv.main;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.softcon.meetinguniv.AddTeamElementFragment;
import com.softcon.meetinguniv.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class chooseteamElementFragment extends Fragment {
    private ImageButton backbtn;
    private EditText editSearch;
    private InputMethodManager imm;
    private List<chooseteamRecycleritem> list;
    private chooseteamAdapterRecyleritem recyclerItemAdapter;
    private LinearLayout addTeamLinear;
    private RecyclerView recyclerView;
    private ConstraintLayout chooseteamConstraint;

    private AddTeamElementFragment ATEfragment;
    private MatchingContentFragment MCF;
    private chooseteamDataModel cteamDataModel;
    private ArrayList<Integer> ImageSource = new ArrayList<Integer>();

    //?????? ?????? ??????, ??? ????????? ?????? ??? ????????? ????????? ???????????? ???
    private ArrayList<String> TeamMember  = new ArrayList<String>();
    private ArrayList<String> TeamMemberID_V2;
    //????????? ?????? ?????? ????????? ?????? ?????? ?????? ??????????????? ????????? ???????????? ???
    private ArrayList<String> TeamPersonalMember = new ArrayList<String>();
    private ArrayList<String> TeamPersonalMember_V2;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("????????????");
    private DatabaseReference T_databaseReference = database.getReference("?????????");

    private String userID;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.teamrecycler) ;
        this.list = new ArrayList<chooseteamRecycleritem>();
        this.userID = getArguments().getString("userID");
        this.MCF = new MatchingContentFragment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
//
        TakeTeamDataFromFirebase();
//        Test2();
        this.recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

        this.editSearch = (EditText) view.findViewById(R.id.searchTeamEditText);


        // ?????? ????????? ??? ????????? ?????????
        this.editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( editSearch.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        this.recyclerItemAdapter.setOnItemClickListener(new chooseteamAdapterRecyleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                GiveChooseTeamData_String();
                M_databaseReference.child(userID).child("?????? ???").setValue(pos);
                //????????? ?????? ?????? ????????? MacthingContentFragment??? Bundle??? ????????? ???
//                GiveChooseTeamData_Integer(pos);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                recyclerItemAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        ClickHandler clickHandler = new ClickHandler();
        this.ATEfragment = new AddTeamElementFragment(clickHandler);
    }

    private void GiveChooseTeamData_String() {
        Bundle bundle = new Bundle();
        System.out.println("chooseteamEl UserId : "+ this.userID);
        bundle.putString("userID", this.userID);
        this.MCF.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.Framecontainer, this.MCF)
                .commit();
        //????????? ???????????? ????????? ?????? ??????
        imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
    }

//    private void GiveChooseTeamData_Integer(int pos) {
//        MatchingContentFragment fragment2 = new MatchingContentFragment();
//        M_databaseReference.child(this.userID).child("?????? ???").setValue(pos);
//        System.out.println("chooseTeamElementFragment ??? ?????? " + pos);
//        Bundle bundle2 = new Bundle();
//        bundle2.putInt("TeamNum", pos);
//        fragment2.setArguments(bundle2);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.Framecontainer, fragment2)
//                .commit();
//        //????????? ???????????? ????????? ?????? ??????
//        imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
//
//    }

    private void Test2() {
        this.cteamDataModel = new chooseteamDataModel();
        this.M_databaseReference.child(String.valueOf(this.userID)).child("???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamMember.addAll((Collection<? extends String>) snapshot.getValue());
                for(int i = 0; i < TeamMember.size(); i++) {
                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).child("??? ??????").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            cteamDataModel.setTeamName((String) snapshot.getValue());
                            System.out.println("???????????? ??? : " + cteamDataModel.getTeamName());
                            recyclerItemAdapter.notifyDataSetChanged();
                        }

                        
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).child("??????").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            TeamMemberID_V2 = new ArrayList<String>();

                            TeamMemberID_V2.addAll((Collection<? extends String>) snapshot.getValue());
                            System.out.println("??? ?????? ????????? : " + snapshot.getValue());
                            System.out.println("????????? ?????? : " + TeamMemberID_V2);
                            for (int i = 0; i < TeamMemberID_V2.size(); i++) {
                                M_databaseReference.child(TeamMemberID_V2.get(i)).child("?????????").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        System.out.println("?????? ????????? ??? : " + snapshot.getValue());
                                        TeamPersonalMember_V2 = new ArrayList<String>();
                                        TeamPersonalMember_V2.add((String) snapshot.getValue());
                                        String teamMember = "";
                                        for(String TM : TeamPersonalMember_V2){
                                            teamMember += TM + ",";
                                        }
                                        cteamDataModel.setTeamMember(teamMember);
                                        addRecyclerItem(cteamDataModel.getTeamName(), cteamDataModel.getTeamMember());
                                        recyclerItemAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void TakeTeamDataFromFirebase() {
        this.cteamDataModel = new chooseteamDataModel();
        ArrayList<String> TeamMemberID = new ArrayList<String>();
        String nickTest;

        this.M_databaseReference.child(String.valueOf(this.userID)).child("???").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamMember.addAll((Collection<? extends String>) snapshot.getValue());
                for(int i = 0; i < TeamMember.size(); i++) {
                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                if(ds.getKey().toString().equals("??? ??????")){
                                    cteamDataModel.setTeamName(ds.getValue().toString());
                                } else if(ds.getKey().toString().equals("??????")){
                                    //?????? 1
//                                  TakeTeamListDataFromFirebase((ArrayList<String>)ds.getValue());
                                    //?????? 2 --> ?????? ?????? ????????? ?????? but ????????? ????????? ??????
//                                    String teamMember = "";
//                                    for(String TM : (ArrayList<String>)ds.getValue()){
//                                        teamMember += TM + " ";
//                                    }
//                                    cteamDataModel.setTeamMember(teamMember);
//                                    System.out.println("************************ ??? ?????? : " + cteamDataModel.getTeamMember());
                                    //?????? 3

                                } else if(ds.getKey().toString().equals("??????")){
                                    cteamDataModel.setMatchingState(ds.getValue().toString());
                                }
                            }
                            addRecyclerItem(cteamDataModel.getTeamName(), cteamDataModel.getTeamMember());
                            recyclerItemAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void TakeTeamListDataFromFirebase(ArrayList<String> TeamMember) {
        for(int i =0; i < TeamMember.size(); i++) {
            System.out.println("????????? ?????? ?????? : " + TeamMember.get(i));
            this.M_databaseReference.child(TeamMember.get(i)).child("?????????").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println("??? ???????????? ?????? ?????? : " + snapshot.getValue());
                    TeamPersonalMember.add(snapshot.getValue().toString());
                    String teamMember = "";
                    for(String TM : TeamPersonalMember){
                        teamMember += TM + ",";
                    }
                    cteamDataModel.setTeamMember(teamMember);
                    System.out.println("#################### ??? ?????? : " + cteamDataModel.getTeamMember());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void addRecyclerItem( String name, String member){
        chooseteamRecycleritem recyclerItem = new chooseteamRecycleritem();
        recyclerItem.setName(name);
        recyclerItem.setMember(member);
        list.add(recyclerItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chooseteam_element, container, false);
        this.backbtn = view.findViewById(R.id.backbtn);
        this.editSearch = view.findViewById(R.id.searchTeamEditText);
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.addTeamLinear = view.findViewById(R.id.addTeamLinear);
        this.chooseteamConstraint = view.findViewById(R.id.chooseteamConstraint);

        this.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchingContentFragment fragment1 = new MatchingContentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                fragment1.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Framecontainer, fragment1)
                        .commit();
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
            }
        });

        this.addTeamLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTeamElementFragment addTeamElementFragment = new AddTeamElementFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                addTeamElementFragment.setArguments(bundle);
                giveRecycleritemData();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                        .replace(R.id.chooseteamFrame, ATEfragment)
                        .commit();
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(),0);
            }
        });
        return view;
    }

    private void giveRecycleritemData() {
        Bundle bundle = getArguments();
//        bundle.putIntegerArrayList("currentteam", this.ImageSource);
        this.ATEfragment.setArguments(bundle);
    }

    public class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addTeamOk_okBTN) {
                addRecyclerItem("?????????", "??????,??????,??????");
                recyclerItemAdapter.notifyDataSetChanged();
                Log.d(TAG, "-------------------------dsfsfsf---------------------");
                v.getRootView().setVisibility(View.GONE);
            }
        }
    }
}