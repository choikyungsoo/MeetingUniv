package com.example.meetinguniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CurrentAdapterRecyclerItem  extends RecyclerView.Adapter<CurrentAdapterRecyclerItem.viewHolder> {
    private ArrayList<CurrentRecycleritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView description;
        private TextView check;
        private TextView date;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
            check = itemView.findViewById(R.id.check);
            date = itemView.findViewById(R.id.date);

        }
    }
    CurrentAdapterRecyclerItem(ArrayList<CurrentRecycleritem> list){
        this.mData = list;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_current, parent, false) ;
        CurrentAdapterRecyclerItem.viewHolder vh = new CurrentAdapterRecyclerItem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        CurrentRecycleritem recyclerItem = mData.get(position) ;
        holder.description.setText(recyclerItem.getDescription()) ;
        holder.check.setText(recyclerItem.getCheck()) ;
        holder.date.setText(recyclerItem.getDate()) ;
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
