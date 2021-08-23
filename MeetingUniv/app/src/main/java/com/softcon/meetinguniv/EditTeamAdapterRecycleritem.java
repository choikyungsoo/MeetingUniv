package com.softcon.meetinguniv;

import android.content.Context;
import android.graphics.Color;
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

public class EditTeamAdapterRecycleritem extends  RecyclerView.Adapter<EditTeamAdapterRecycleritem.viewHolder> {
    private ArrayList<EditTeamRecycleritem> mData;
    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView E_memProfile;
        private TextView E_memberlist;
        private int RecyclerCount;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            E_memProfile = itemView.findViewById(R.id.chatprofile);
            E_memberlist = itemView.findViewById(R.id.memberlist);
            RecyclerCount = 0;
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        RecyclerCount++;
                        if(RecyclerCount > 0){
                            if(RecyclerCount %2 == 1){
                                v.setBackgroundColor(Color.rgb(234,234,234));
                            } else if(RecyclerCount %2 == 0){
                                v.setBackgroundColor(Color.WHITE);
                            }
                        }


                    }
                }
            });
        }
    }
    EditTeamAdapterRecycleritem(ArrayList<EditTeamRecycleritem> alllist) {
        this.mData = alllist;
    }

    @NonNull
    @Override
    public EditTeamAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        EditTeamAdapterRecycleritem.viewHolder vh = new EditTeamAdapterRecycleritem.viewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditTeamAdapterRecycleritem.viewHolder holder, int position) {
        EditTeamRecycleritem recyclerItem = mData.get(position) ;
        holder.E_memProfile.setImageResource(recyclerItem.getE_memporife());
        holder.E_memberlist.setText(recyclerItem.getE_memname()) ;
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
