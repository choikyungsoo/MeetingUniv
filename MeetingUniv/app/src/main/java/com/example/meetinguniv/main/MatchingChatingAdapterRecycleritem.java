package com.example.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetinguniv.R;

public class MatchingChatingAdapterRecycleritem extends RecyclerView.Adapter<MatchingChatingAdapterRecycleritem.viewHolder>{

    private ArrayList<MatchingChatingRecycleritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView chatprofile;
        private TextView memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatprofile = itemView.findViewById(R.id.chatprofile);
            memberlist = itemView.findViewById(R.id.memberlist);

        }
    }
    MatchingChatingAdapterRecycleritem(ArrayList<MatchingChatingRecycleritem> list){
        this.mData = list;
    }

    @Override
    public MatchingChatingAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        MatchingChatingAdapterRecycleritem.viewHolder vh = new MatchingChatingAdapterRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MatchingChatingAdapterRecycleritem.viewHolder holder, int position) {
        MatchingChatingRecycleritem recyclerItem = mData.get(position) ;
        holder.chatprofile.setImageResource(recyclerItem.getChatingProfile());
        holder.memberlist.setText(recyclerItem.getMemberlist()) ;
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
