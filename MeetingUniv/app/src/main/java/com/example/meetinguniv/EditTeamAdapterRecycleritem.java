package com.example.meetinguniv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetinguniv.main.FriendsListAdapterRecycleritem;
import com.example.meetinguniv.main.FriendsListRecycleritem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditTeamAdapterRecycleritem extends  RecyclerView.Adapter<EditTeamAdapterRecycleritem.viewHolder> {
    private ArrayList<EditTeamRecycleritem1> mData;
    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView E_memProfile;
        private int RecyclerCount;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            E_memProfile = itemView.findViewById(R.id.chatprofile5); }
    }
    EditTeamAdapterRecycleritem(ArrayList<EditTeamRecycleritem1> alllist) {
        this.mData = alllist;
    }

    @NonNull
    @Override
    public EditTeamAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_currentteammember, parent, false) ;
        EditTeamAdapterRecycleritem.viewHolder vh = new EditTeamAdapterRecycleritem.viewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditTeamAdapterRecycleritem.viewHolder holder, int position) {
        EditTeamRecycleritem1 recyclerItem = mData.get(position) ;
        holder.E_memProfile.setImageResource(recyclerItem.getE_memporife());
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
