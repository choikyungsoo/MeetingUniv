package com.softcon.meetinguniv;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.softcon.meetinguniv.main.FriendsListAdapterRecycleritem;
import com.softcon.meetinguniv.main.FriendsListRecycleritem;
import com.softcon.meetinguniv.main.TeamMemberAdapterRecycleritem;
import com.softcon.meetinguniv.main.TeamMemberRecyclerItem;
import com.softcon.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditTeamAdapterRecycleritem extends  RecyclerView.Adapter<EditTeamAdapterRecycleritem.viewHolder> {
    private ArrayList<EditTeamRecycleritem1> mData;
    private TeamMemberAdapterRecycleritem.OnItemClickListener mListener = null;
    private View view;
    private Context context;
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public void setOnItemClickListener(TeamMemberAdapterRecycleritem.OnItemClickListener listener){
        this.mListener = listener;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView E_memProfile;
        private Button E_friendcancel;
        private int RecyclerCount;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            E_memProfile = itemView.findViewById(R.id.chatprofile5);
            E_friendcancel = itemView.findViewById(R.id.friendcancel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
        }
    }
    EditTeamAdapterRecycleritem(ArrayList<EditTeamRecycleritem1> alllist) {
        this.mData = alllist;
    }

    @NonNull
    @Override
    public EditTeamAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        this.view = inflater.inflate(R.layout.recycleritem_currentteammember, parent, false) ;
        EditTeamAdapterRecycleritem.viewHolder vh = new EditTeamAdapterRecycleritem.viewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditTeamAdapterRecycleritem.viewHolder holder, int position) {
        EditTeamRecycleritem1 recyclerItem = mData.get(position) ;
        System.out.println("????????? ?????? : " + holder.E_memProfile);
        Glide.with(this.context).load(recyclerItem.getE_memporife()).into(holder.E_memProfile);
//        holder.E_memProfile.setImageResource(recyclerItem.getE_memporife());
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
