package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softcon.meetinguniv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MatchChattingContentFragment extends Fragment {
//    private ArrayList<MatchingChatingRecycleritem> list = new ArrayList<MatchingChatingRecycleritem>();
    private List<MatchingChatingRecycleritem> list;
    private MatchingChatingAdapterRecycleritem recyclerItemAdapter;
    private EditText editSearch;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;

//        MatchingChatingAdapterRecycleritem recyclerItemAdapter = new MatchingChatingAdapterRecycleritem(this.list);
//        recyclerView.setAdapter(recyclerItemAdapter) ;

        this.list = new ArrayList<MatchingChatingRecycleritem>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");

        this.recyclerItemAdapter = new MatchingChatingAdapterRecycleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

//        recyclerItemAdapter.notifyDataSetChanged();

        this.editSearch = (EditText) view.findViewById(R.id.searchMatchChattingEditText);

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_match_chatting_content, container, false);
        return rootView;
    }

    private void addRecyclerItem(int profile, String memberlist){
        MatchingChatingRecycleritem recyclerItem = new MatchingChatingRecycleritem();
        recyclerItem.setChatingProfile(profile);
        recyclerItem.setMemberlist(memberlist);
        list.add(recyclerItem);

    }

}