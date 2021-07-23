package com.example.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsListAdapterRecycleritem extends RecyclerView.Adapter<FriendsListAdapterRecycleritem.viewHolder> {
    private ArrayList<FriendsListRecycleritem> mData;

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView F_chatprofile;
        private TextView F_memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            F_chatprofile = itemView.findViewById(R.id.chatprofile);
            F_memberlist = itemView.findViewById(R.id.memberlist);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        moveToFPF(v);

                    }
                }
            });

        }
    }

    private void moveToFPF(View v) {
        Navigation.findNavController(v).navigate(R.id.action_friendsListScreenFragment_to_friendsProfileScreenFragment);
    }

    FriendsListAdapterRecycleritem(ArrayList<FriendsListRecycleritem> list){
        this.mData = list;
    }

    @Override
    public FriendsListAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        FriendsListAdapterRecycleritem.viewHolder vh = new FriendsListAdapterRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FriendsListAdapterRecycleritem.viewHolder holder, int position) {
        FriendsListRecycleritem recyclerItem = mData.get(position) ;
        holder.F_chatprofile.setImageResource(recyclerItem.getF_chatingProfile());
        holder.F_memberlist.setText(recyclerItem.getF_memberlist()) ;
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
