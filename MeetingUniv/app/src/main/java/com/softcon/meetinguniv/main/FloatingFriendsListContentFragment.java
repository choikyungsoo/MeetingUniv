package com.softcon.meetinguniv.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;

import com.softcon.meetinguniv.R;

import java.util.ArrayList;


public class FloatingFriendsListContentFragment extends Fragment implements View.OnClickListener{

    private ArrayList<FloatingFriendsRecylceritem> list = new ArrayList<FloatingFriendsRecylceritem>();
    private Button backtofriendBTN;
    private SearchView P_search;
    private InputMethodManager imm;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.Popup_friendlist) ;
        FloatingFriendsAdatperRecycleritem recyclerItemAdapter = new FloatingFriendsAdatperRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        recyclerItemAdapter.notifyDataSetChanged();

        this.backtofriendBTN = view.findViewById(R.id.backtochatroom);
        this.P_search = view.findViewById(R.id.P_search);
        this.backtofriendBTN.setOnClickListener(this);
    }

    private void addRecyclerItem(int profile, String memberlist){
        FloatingFriendsRecylceritem recyclerItem = new FloatingFriendsRecylceritem();
        recyclerItem.setPop_profile(profile);
        recyclerItem.setPop_name(memberlist);
        list.add(recyclerItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_floating_friends_list_content, container, false);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.backtochatroom:
                backtochatroom(v);
                break;
        }
    }

    private void backtochatroom(View v) {
        PersonalChattingContentFragment fragment2 = new PersonalChattingContentFragment();
        this.imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containfriendlist, fragment2)
                .commit();
        imm.hideSoftInputFromWindow(P_search.getWindowToken(),0);
    }
}