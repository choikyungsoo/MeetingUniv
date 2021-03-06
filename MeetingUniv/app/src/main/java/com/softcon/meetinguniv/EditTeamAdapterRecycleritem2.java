package com.softcon.meetinguniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.TeamMemberAdapterRecycleritem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditTeamAdapterRecycleritem2 extends  RecyclerView.Adapter<EditTeamAdapterRecycleritem2.viewHolder> {
    private ArrayList<EditTeamRecycleritem> mData;
    private TeamMemberAdapterRecycleritem.OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public void setOnItemClickListener(TeamMemberAdapterRecycleritem.OnItemClickListener listener){
        this.mListener = listener;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private LinearLayout E_friendslist;
        private ImageView E_memProfile;
        private CheckBox E_memberlist;
        private int RecyclerCount;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            E_friendslist = itemView.findViewById(R.id.friendsList);
            E_memProfile = itemView.findViewById(R.id.memprofile3);
            E_memberlist = itemView.findViewById(R.id.memberlist3);

//            E_friendslist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION){
//                        if(mListener != null){
//                            mListener.onItemClick(v,pos);
//                            System.out.println("클릭"+pos);
//                        }
//                    }
//                }
//            });

            E_memProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                            System.out.println("클릭"+pos);
                        }
                    }
                }
            });

            E_memberlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                            System.out.println("클릭"+pos);
                        }
                    }
                }
            });
            RecyclerCount = 0;
//            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION){
//                        if(mListener != null){
//                            mListener.onItemClick(v,pos);
//                            System.out.println("클릭"+pos);
//                        }
//                    }
//                }
//            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION){
//                        RecyclerCount++;
//                        if(RecyclerCount > 0){
//                            if(RecyclerCount %2 == 1){
//                                v.setBackgroundColor(Color.rgb(234,234,234));
//                            } else if(RecyclerCount %2 == 0){
//                                v.setBackgroundColor(Color.WHITE);
//                            }
//                        }
//
//
//                    }
//                }
//            });
        }
    }
    EditTeamAdapterRecycleritem2(ArrayList<EditTeamRecycleritem> alllist) {
        this.mData = alllist;
    }

    @NonNull
    @Override
    public EditTeamAdapterRecycleritem2.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chooseteamtojoin, parent, false) ;
        EditTeamAdapterRecycleritem2.viewHolder vh = new EditTeamAdapterRecycleritem2.viewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EditTeamAdapterRecycleritem2.viewHolder holder, int position) {
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
