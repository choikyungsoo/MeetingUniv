package com.example.meetinguniv;

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

public class PersonalChatingAdapterRecycleritem extends RecyclerView.Adapter<PersonalChatingAdapterRecycleritem.viewHolder> {
    private ArrayList<PersonalChatingRecycleritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView chatprofile;
        private TextView memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatprofile = itemView.findViewById(R.id.chatprofile);
            memberlist = itemView.findViewById(R.id.memberlist);

        }
    }
    PersonalChatingAdapterRecycleritem(ArrayList<PersonalChatingRecycleritem> list){
        this.mData = list;
    }

    @Override
    public PersonalChatingAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        PersonalChatingAdapterRecycleritem.viewHolder vh = new PersonalChatingAdapterRecycleritem.viewHolder(view) ;

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull PersonalChatingAdapterRecycleritem.viewHolder holder, int position) {
        PersonalChatingRecycleritem recyclerItem = mData.get(position) ;
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
