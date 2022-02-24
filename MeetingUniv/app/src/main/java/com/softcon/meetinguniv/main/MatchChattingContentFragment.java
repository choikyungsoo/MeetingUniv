package com.softcon.meetinguniv.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softcon.meetinguniv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MatchChattingContentFragment extends Fragment {
//    private ArrayList<MatchingChatingRecycleritem> list = new ArrayList<MatchingChatingRecycleritem>();
    private List<MatchingChatingRecycleritem> list;
    private MatchingChatingAdapterRecycleritem recyclerItemAdapter;
    private EditText editSearch;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;

//        MatchingChatingAdapterRecycleritem recyclerItemAdapter = new MatchingChatingAdapterRecycleritem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter) ;

        this.list = new ArrayList<MatchingChatingRecycleritem>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 데이터 읽는 방법
//        db.collection("users")
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("TAG", document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.w("TAG", "Error getting documents.", task.getException());
//                }
//            }
//        });

        //////////////firestore 연결하면서 주석 처리 해둔 부분/////////////////////////////////
//        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
//        addRecyclerItem(R.drawable.prot, "aaa1234");
//        addRecyclerItem(R.drawable.prot, "chae");
//        addRecyclerItem(R.drawable.prot, "yun");
        /////////////////////////////////////////////////////////////////////////////////

//        db.collection("Chatting").document("t5rqLmjoCuHPht1NkuYQ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();

//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        MatchingChatingRecycleritem recyclerItem = new MatchingChatingRecycleritem();
//                        recyclerItem.setChatingProfile(R.drawable.prot);
//                        recyclerItem.setMemberlist(document.getDate("title").toString());
//                        list.add(recyclerItem);
//                    }
//                }
//            }
//        });

        db.collection("Chatting")
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        MatchingChatingRecycleritem recyclerItem = new MatchingChatingRecycleritem();
//                        recyclerItem.setChatingProfile(R.drawable.prot);
                        recyclerItem.setDDay(document.get("dDay").toString());
                        recyclerItem.setMemberlist(document.get("title").toString());
                        recyclerItem.setChatMemberNum(document.get("membernum").toString());
                        recyclerItem.setNewMessageNum("4");
                        list.add(recyclerItem);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });


        this.recyclerItemAdapter = new MatchingChatingAdapterRecycleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

//        recyclerItemAdapter.notifyDataSetChanged();

        this.editSearch = (EditText) view.findViewById(R.id.searchMatchChattingEditText);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_chatting_content, container, false);
//        return rootView;
        return inflater.inflate(R.layout.fragment_match_chatting_content, container, false);
    }

    private void addRecyclerItem(int profile, String memberlist){
        MatchingChatingRecycleritem recyclerItem = new MatchingChatingRecycleritem();
//        recyclerItem.setChatingProfile(profile);
        recyclerItem.setMemberlist(memberlist);
        list.add(recyclerItem);

    }

}