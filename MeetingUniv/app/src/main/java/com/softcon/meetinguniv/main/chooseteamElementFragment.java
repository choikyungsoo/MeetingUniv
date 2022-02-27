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
//    private ArrayList<chooseteamRecycleritem> list = new ArrayList<chooseteamRecycleritem>();
    private List<chooseteamRecycleritem> list;
    private chooseteamAdapterRecyleritem recyclerItemAdapter;
    private LinearLayout addTeamLinear;
    private RecyclerView recyclerView;
    private ConstraintLayout chooseteamConstraint;

    private AddTeamElementFragment ATEfragment;
    private ArrayList<Integer> ImageSource = new ArrayList<Integer>();

    //팀에 대한 정보, 팀 추가를 하면 팀 번호가 여기로 들어와야 함
    private ArrayList<String> TeamMember;
    //선택한 팀에 대한 팀원의 번호 혹은 개인 식별번호가 여기로 들어와야 함
    private ArrayList<String> TeamPersonalMember;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("회원정보");
    private DatabaseReference T_databaseReference = database.getReference("팀정보");

    private String userID;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.teamrecycler) ;

//        chooseteamAdapterRecyleritem recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter) ;

        this.list = new ArrayList<chooseteamRecycleritem>();
        this.userID = getArguments().getString("userID");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        TakeTeamDataFromFirebase();

//        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
//        addRecyclerItem("한양대", "1,2,3");
//        addRecyclerItem("연세대", "A,B,c");
//        addRecyclerItem("고려대", "가,나,다");
//        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
//        addRecyclerItem("한양대", "1,2,3");
//        addRecyclerItem("연세대", "A,B,c");
//        addRecyclerItem("고려대", "가,나,다");

        this.recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

//        recyclerItemAdapter.notifyDataSetChanged();

        this.editSearch = (EditText) view.findViewById(R.id.searchTeamEditText);


        // 엔터 눌렀을 때 키보드 숨기기
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

    private void TakeTeamDataFromFirebase() {
        this.TeamMember = new ArrayList<String>();
        this.TeamPersonalMember = new ArrayList<String>();
        chooseteamDataModel cteamDataModel = new chooseteamDataModel();
        ArrayList<chooseteamDataModel> TeamAllData = new ArrayList<chooseteamDataModel>();

        HashMap<String, Object> teamData = new HashMap<String, Object>();

        this.M_databaseReference.child(String.valueOf(this.userID)).child("팀").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamMember.addAll((Collection<? extends String>) snapshot.getValue());
                for(int i = 0; i < TeamMember.size(); i++) {
                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()) {
//                                System.out.println("칠드런 데이터 : " + ds.getKey().toString());
//                                teamData.put("팀 이름",  ds.getValue());
//                                System.out.println("팀 이름 : " + teamData.values());
                                if(ds.getKey().toString().equals("팀 이름")){
                                    cteamDataModel.setTeamName(ds.getValue().toString());
                                    System.out.println("팀 이름 : " + ds.getValue());
                                } else if(ds.getKey().toString().equals("팀원")){
                                    String teamMember = "";
                                    for(String TM : (ArrayList<String>)ds.getValue()){
                                        teamMember += TM + ",";
                                    }
                                    cteamDataModel.setTeamMember(teamMember);
//                                    cteamDataModel.setTeamMember((ArrayList<String>) ds.getValue());
                                    System.out.println("팀원 : " + ds.getValue());
                                } else if(ds.getKey().toString().equals("대기")){
                                    cteamDataModel.setMatchingState(ds.getValue().toString());
                                }

                            }
                            addRecyclerItem(cteamDataModel.getTeamName(), cteamDataModel.getTeamMember());
                            System.out.println("전체 데이터 목록 : " + cteamDataModel.getTeamName());
                            recyclerItemAdapter.notifyDataSetChanged();
//                            for(DataSnapshot ds : snapshot.getChildren()){
//                                chooseteamDataModel cteamDataModel = ds.getValue(chooseteamDataModel.class);
//                                TeamAllData.add(cteamDataModel);
//                            }
//                            System.out.println(TeamAllData);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            System.out.println(snapshot.getValue());
//                            System.out.println("팀 이름 나와야됨 " + teamData.get("팀 이름="));
////                            TeamAll.addAll((Collection<? extends String>) snapshot.getValue());
//                            System.out.println("팀전체 : " );
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).child("팀 이름").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            System.out.println("팀 이름 : " + snapshot.getValue());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    T_databaseReference.child(String.valueOf(TeamMember.get(i))).child("팀원").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            System.out.println("팀원 : "+snapshot.getValue());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addRecyclerItem( String name, String member){
        chooseteamRecycleritem recyclerItem = new chooseteamRecycleritem();
        recyclerItem.setName(name);
        recyclerItem.setMember(member);
        list.add(recyclerItem);
        System.out.println("제발 좀 돼라"+name);

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
//            AddTeamElementFragment addTeamElementFragment = new AddTeamElementFragment();
            @Override
            public void onClick(View v) {
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
                addRecyclerItem("추가됨", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
                recyclerItemAdapter.notifyDataSetChanged();
                Log.d(TAG, "-------------------------dsfsfsf---------------------");
                v.getRootView().setVisibility(View.GONE);
            }
        }
    }
}