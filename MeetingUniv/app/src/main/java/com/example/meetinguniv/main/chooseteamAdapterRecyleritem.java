package com.example.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetinguniv.R;

public class chooseteamAdapterRecyleritem extends RecyclerView.Adapter<chooseteamAdapterRecyleritem.viewHolder> {
    private ArrayList<chooseteamRecycleritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView member;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teamname);
            member = itemView.findViewById(R.id.teammember);

        }
    }
    chooseteamAdapterRecyleritem(ArrayList<chooseteamRecycleritem> list){
        mData = list;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chooseteam, parent, false) ;
        chooseteamAdapterRecyleritem.viewHolder vh = new chooseteamAdapterRecyleritem.viewHolder(view) ;

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull chooseteamAdapterRecyleritem.viewHolder holder, int position) {
        chooseteamRecycleritem recyclerItem = mData.get(position) ;
        holder.name.setText(recyclerItem.getName()) ;
        holder.member.setText(recyclerItem.getMember()) ;
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
