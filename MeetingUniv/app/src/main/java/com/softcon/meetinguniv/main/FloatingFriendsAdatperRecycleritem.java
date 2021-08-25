package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softcon.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FloatingFriendsAdatperRecycleritem extends RecyclerView.Adapter<FloatingFriendsAdatperRecycleritem.viewHolder>{
    private ArrayList<FloatingFriendsRecylceritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView pop_chatprofile;
        private TextView pop_memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pop_chatprofile = itemView.findViewById(R.id.chatprofile);
            pop_memberlist = itemView.findViewById(R.id.memberlist);

        }
    }

    FloatingFriendsAdatperRecycleritem(ArrayList<FloatingFriendsRecylceritem> list){
        this.mData = list;
    }

    @Override
    public FloatingFriendsAdatperRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        FloatingFriendsAdatperRecycleritem.viewHolder vh = new FloatingFriendsAdatperRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FloatingFriendsAdatperRecycleritem.viewHolder holder, int position) {
        FloatingFriendsRecylceritem recyclerItem = mData.get(position) ;
        holder.pop_chatprofile.setImageResource(recyclerItem.getPop_profile());
        holder.pop_memberlist.setText(recyclerItem.getPop_name()) ;
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
