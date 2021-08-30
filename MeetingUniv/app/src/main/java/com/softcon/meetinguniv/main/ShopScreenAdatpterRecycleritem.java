package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcon.meetinguniv.R;

public class ShopScreenAdatpterRecycleritem  extends RecyclerView.Adapter<ShopScreenAdatpterRecycleritem.viewHolder>{
    private ArrayList<ShopScreenRecycleritem> mData;
    private OnItemClickListener itemClickHandler = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickHandler) {
        this.itemClickHandler = itemClickHandler ;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView items;
        private TextView price;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            items = itemView.findViewById(R.id.item);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if (itemClickHandler != null) {
                            itemClickHandler.onItemClick(v, pos);
                        }
                    }
                }
            });

        }
    }

    ShopScreenAdatpterRecycleritem(ArrayList<ShopScreenRecycleritem> list){
        this.mData = list;
    }

    @Override
    public ShopScreenAdatpterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_shop, parent, false) ;
        ShopScreenAdatpterRecycleritem.viewHolder vh = new ShopScreenAdatpterRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShopScreenAdatpterRecycleritem.viewHolder holder, int position) {
        ShopScreenRecycleritem recyclerItem = mData.get(position) ;
        holder.items.setText(recyclerItem.getItems()) ;
        holder.price.setText(recyclerItem.getPrice()) ;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
