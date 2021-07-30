package com.example.meetinguniv.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatRoomRecyclerItem> mData;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomRecyclerItem> list){
        this.mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class otherSideViewHolder extends RecyclerView.ViewHolder {
        TextView otherchattext;

        public otherSideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.otherchattext = (TextView) itemView.findViewById(R.id.otherchattext);
        }
    }
    public class mySideviewHolder extends RecyclerView.ViewHolder {
        TextView mychattext;

        public mySideviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.mychattext = (TextView) itemView.findViewById(R.id.mychattext);
        }
    }
}
