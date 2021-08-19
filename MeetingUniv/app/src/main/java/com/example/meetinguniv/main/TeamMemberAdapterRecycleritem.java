package com.example.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetinguniv.R;

public class TeamMemberAdapterRecycleritem extends RecyclerView.Adapter<TeamMemberAdapterRecycleritem.viewHolder> {
    private ArrayList<TeamMemberRecyclerItem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView memProfile;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            memProfile = itemView.findViewById(R.id.memProfile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(pos == 3){
                            Toast.makeText(v.getContext(), "설정입니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
    TeamMemberAdapterRecycleritem(ArrayList<TeamMemberRecyclerItem> list){
        mData = list;
    }

    @Override
    public TeamMemberAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_members, parent, false) ;
        TeamMemberAdapterRecycleritem.viewHolder vh = new TeamMemberAdapterRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        TeamMemberRecyclerItem recyclerItem = mData.get(position) ;
        holder.memProfile.setImageResource(recyclerItem.getMemProfile()); ;
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
