package com.softcon.meetinguniv.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcon.meetinguniv.R;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof MySideViewHolder) {
            Log.i("position", String.valueOf(position));
            ((MySideViewHolder)viewHolder).mychattext.setText(mData.get(position).getMessage());
        }
        else if(viewHolder instanceof OtherSideViewHolder) {
            Log.i("position", String.valueOf(position));
            ((OtherSideViewHolder)viewHolder).otherchattext.setText(mData.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.mData.get(position).getViewType();
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
