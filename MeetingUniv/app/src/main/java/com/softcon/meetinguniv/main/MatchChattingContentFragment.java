package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcon.meetinguniv.R;

import java.util.ArrayList;

public class MatchChattingContentFragment extends Fragment {
    private ArrayList<MatchingChatingRecycleritem> list = new ArrayList<MatchingChatingRecycleritem>();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist) ;
        MatchingChatingAdapterRecycleritem recyclerItemAdapter = new MatchingChatingAdapterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        recyclerItemAdapter.notifyDataSetChanged();
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