package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softcon.meetinguniv.ChargeCashDialogFragment;
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

        // , 때문에 cashDialog 오류 나서 일단 지웠음 !!
        addRecyclerItem("하트 1개", "100 캐시");
        addRecyclerItem("하트 5개", "500 캐시");
        addRecyclerItem("하트 10개", "1000 캐시");
        addRecyclerItem("하트 20개", "1800 캐시");
        addRecyclerItem("하트 30개", "2500 캐시");
        addRecyclerItem("1개월 무제한", "5000 캐시");
        recyclerItemAdapter.notifyDataSetChanged();

        recyclerItemAdapter.setOnItemClickListener(new ShopScreenAdatpterRecycleritem.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), list.get(position).getItems().toString(), Toast.LENGTH_SHORT).show();

                String itemName = list.get(position).getItems().toString();
                String itemPrice = list.get(position).getPrice().toString();

                ChargeCashDialogFragment chargeCashDialogFragment = new ChargeCashDialogFragment(getActivity());
                chargeCashDialogFragment.showChargeCashDialogFunction(itemName, itemPrice, "0 캐시");
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

    public class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}