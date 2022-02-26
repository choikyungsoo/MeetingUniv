package com.softcon.meetinguniv.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public static final int TYPE_2 = 2;

    public ChatRoomRecyclerAdapter(ArrayList<ChatRoomRecyclerItem> list){
        this.mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == this.TYPE_0) {
            view = inflater.inflate(R.layout.recycleritem_date, parent, false);
            return new DateViewHolder(view);
        }
        else if(viewType == this.TYPE_1) {
            view = inflater.inflate(R.layout.recycleritem_mychat, parent, false);
            return new MySideViewHolder(view);
        }
        else if(viewType == this.TYPE_2) {
            view = inflater.inflate(R.layout.recycleritem_otherchat, parent, false);
            return new OtherSideViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof DateViewHolder) {
            Log.i("position", String.valueOf(position));
            ((DateViewHolder)viewHolder).dateText.setText(mData.get(position).getDate());
        }
        else if(viewHolder instanceof MySideViewHolder) {
            Log.i("position", String.valueOf(position));
            ((MySideViewHolder)viewHolder).myChatUnCheckNum.setText(mData.get(position).getUncheck());
            ((MySideViewHolder)viewHolder).myChatTime.setText(mData.get(position).getTime());
            ((MySideViewHolder)viewHolder).myChatText.setText(mData.get(position).getMessage());
        }
        else if(viewHolder instanceof OtherSideViewHolder) {
            Log.i("position", String.valueOf(position));
            ((OtherSideViewHolder)viewHolder).otherChatProfileImage.setImageURI(mData.get(position).getProfileImage());
            ((OtherSideViewHolder)viewHolder).otherChatNickName.setText(mData.get(position).getNickname());
            ((OtherSideViewHolder)viewHolder).otherChatText.setText(mData.get(position).getMessage());
            ((OtherSideViewHolder)viewHolder).otherChatTime.setText(mData.get(position).getTime());
            ((OtherSideViewHolder)viewHolder).otherChatUnCheck.setText(mData.get(position).getUncheck());
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

    public class DateViewHolder extends RecyclerView.ViewHolder {

        TextView dateText;

        public DateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.dateText = (TextView) itemView.findViewById(R.id.dateText);
        }
    }

    public class MySideViewHolder extends RecyclerView.ViewHolder {

        TextView myChatUnCheckNum;
        TextView myChatTime;
        TextView myChatText;

        public MySideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.myChatUnCheckNum = (TextView) itemView.findViewById(R.id.myChatUnCheckNum);
            this.myChatTime = (TextView) itemView.findViewById(R.id.myChatTime);
            this.myChatText = (TextView) itemView.findViewById(R.id.myChatText);
        }
    }
    public class OtherSideViewHolder extends RecyclerView.ViewHolder {

        ImageView otherChatProfileImage;
        TextView otherChatNickName;
        TextView otherChatText;
        TextView otherChatTime;
        TextView otherChatUnCheck;

        public OtherSideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.otherChatProfileImage = (ImageView) itemView.findViewById(R.id.otherChatProfileImage);
            this.otherChatNickName = (TextView) itemView.findViewById(R.id.otherChatNickName);
            this.otherChatText = (TextView) itemView.findViewById(R.id.otherChatText);
            this.otherChatTime = (TextView) itemView.findViewById(R.id.otherChatTime);
            this.otherChatUnCheck = (TextView) itemView.findViewById(R.id.otherChatUnCheck);
        }
    }

}
