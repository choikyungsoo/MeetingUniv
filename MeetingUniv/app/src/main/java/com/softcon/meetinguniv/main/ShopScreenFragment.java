package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        addRecyclerItem("하트 1개", "400캐시");
        addRecyclerItem("하트 5개", "2,000캐시");
        addRecyclerItem("하트 10개", "3,600캐시");
        addRecyclerItem("하트 20개", "6,800캐시");
        addRecyclerItem("하트 30개", "9,600캐시");
        addRecyclerItem("1개월 무제한", "15,000캐시");
        addRecyclerItem("10일동안 매일 하트 1개씩", "3,000캐시");
        addRecyclerItem("30일동안 매일 하트 1개씩", "8,000캐시");
        recyclerItemAdapter.notifyDataSetChanged();

        recyclerItemAdapter.setOnItemClickListener(new ShopScreenAdatpterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {
                    Toast.makeText(getContext(), "하트 1개 구매", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getContext(), "하트 5개 구매", Toast.LENGTH_SHORT).show();
                }
            }
        });

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