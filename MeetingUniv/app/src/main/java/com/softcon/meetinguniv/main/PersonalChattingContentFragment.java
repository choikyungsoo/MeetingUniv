package com.softcon.meetinguniv.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.softcon.meetinguniv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PersonalChattingContentFragment extends Fragment implements View.OnClickListener {

//    private ArrayList<PersonalChatingRecycleritem> list = new ArrayList<PersonalChatingRecycleritem>();

    private List<PersonalChatingRecycleritem> list;
    private PersonalChatingAdapterRecycleritem recyclerItemAdapter;
    private EditText editSearch;
    private FloatingActionButton invitingBTN;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;
        this.invitingBTN = view.findViewById(R.id.invitingfriends);
        
//        PersonalChatingAdapterRecycleritem recyclerItemAdapter = new PersonalChatingAdapterRecycleritem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter);

        this.list = new ArrayList<PersonalChatingRecycleritem>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        addRecyclerItem(R.drawable.prot, "aaa");

        this.recyclerItemAdapter = new PersonalChatingAdapterRecycleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

//        recyclerItemAdapter.notifyDataSetChanged();

        this.invitingBTN.setOnClickListener(this);

        this.editSearch = (EditText) view.findViewById(R.id.searchPersonalChattingEditText);

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