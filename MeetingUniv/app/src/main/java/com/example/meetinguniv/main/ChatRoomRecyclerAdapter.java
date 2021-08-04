package com.example.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatRoomRecyclerItem> mData = null;

    public static final int TYPE_0 = 0;
    public static final int TYPE_1 = 1;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomRecyclerItem> list){
        this.mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == this.TYPE_0) {
            view = inflater.inflate(R.layout.recycleritem_mychat, parent, false);
            return new MySideViewHolder(view);
        }
        else if(viewType == this.TYPE_1) {
            view = inflater.inflate(R.layout.recycleritem_otherchat, parent, false);
            return new OtherSideViewHolder(view);
        }


//        switch (viewType) {
//            case TYPE_PAGER:
//                return new PagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false));
//            case TYPE_NORMAL:
//                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyler, parent, false));
//        }


//        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.re)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof MySideViewHolder) {
            ((MySideViewHolder)viewHolder).mychattext.setText(mData.get(position).getMessage());
        }
        else if(viewHolder instanceof OtherSideViewHolder) {
            ((OtherSideViewHolder)viewHolder).otherchattext.setText(mData.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MySideViewHolder extends RecyclerView.ViewHolder {
        TextView mychattext;

        public MySideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.mychattext = (TextView) itemView.findViewById(R.id.mychattext);
        }
    }
    public class OtherSideViewHolder extends RecyclerView.ViewHolder {
        TextView otherchattext;

        public OtherSideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.otherchattext = (TextView) itemView.findViewById(R.id.otherchattext);
        }
    }
}