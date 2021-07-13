package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CurrentContentsFragment extends Fragment {
    private ArrayList<CurrentRecycleritem> list = new ArrayList<CurrentRecycleritem>();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler1) ;
        CurrentAdapterRecyclerItem recyclerItemAdapter = new CurrentAdapterRecyclerItem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("~님 외 * 명 + ~ 님 외 * 명", "성공", "2020/07/14 05:10");
        recyclerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_current_contents, container, false);
        return rootView;
    }

    private void addRecyclerItem(String description, String check, String data){
        CurrentRecycleritem recyclerItem = new CurrentRecycleritem();
        recyclerItem.setDescription(description);
        recyclerItem.setCheck(check);
        recyclerItem.setDate(data);
        list.add(recyclerItem);

    }
}