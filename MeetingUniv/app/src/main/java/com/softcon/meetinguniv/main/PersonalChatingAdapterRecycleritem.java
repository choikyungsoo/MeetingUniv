package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softcon.meetinguniv.R;

public class PersonalChatingAdapterRecycleritem extends RecyclerView.Adapter<PersonalChatingAdapterRecycleritem.viewHolder> {
    private ArrayList<PersonalChatingRecycleritem> mData;

    public PersonalChatingAdapterRecycleritem(ArrayList<PersonalChatingRecycleritem> list){
        this.mData = list;
    }




    @Override
    public PersonalChatingAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
//        PersonalChatingAdapterRecycleritem.viewHolder vh = new PersonalChatingAdapterRecycleritem.viewHolder(view) ;

        return new PersonalChatingAdapterRecycleritem.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PersonalChatingAdapterRecycleritem.viewHolder holder, int position) {
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

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView chatprofile;
        private TextView memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Navigation.findNavController(itemView).navigate(R.id.action_mainFragment_to_chatRoomScreenFragment);
                    Navigation.findNavController(itemView).navigate(R.id.action_mainFragment_to_chatRoomScreenFragmentVersion2);
                }
            });
            chatprofile = itemView.findViewById(R.id.chatprofile);
            memberlist = itemView.findViewById(R.id.memberlist);

        }
    }
}
