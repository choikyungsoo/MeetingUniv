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


public class ShopScreenFragment extends Fragment {
    private ArrayList<ShopScreenRecycleritem> list = new ArrayList<ShopScreenRecycleritem>();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.shopitemRecycler) ;
        ShopScreenAdatpterRecycleritem recyclerItemAdapter = new ShopScreenAdatpterRecycleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("하트 1개", "1,200원");
        addRecyclerItem("하트 5개", "6,000원");
        addRecyclerItem("하트 10개", "10,000원");
        addRecyclerItem("하트 20개", "17,000원");
        addRecyclerItem("10일동안 매일 하트 1개씩", "8,500원");
        addRecyclerItem("30일동안 매일 하트 1개씩", "24,000원");
        addRecyclerItem("1개월동안 무제한", "35,000원");
        recyclerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_shop_screen, container, false);
        return rootView;
    }

    private void addRecyclerItem(String items, String price){
        ShopScreenRecycleritem recyclerItem = new ShopScreenRecycleritem();
        recyclerItem.setItems(items);
        recyclerItem.setPrice(price);
        list.add(recyclerItem);

    }
}