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
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsListAdapterRecycleritem extends RecyclerView.Adapter<FriendsListAdapterRecycleritem.viewHolder> {
//    private ArrayList<FriendsListRecycleritem> mData;
    private List<FriendsListRecycleritem> items = null;
    private ArrayList<FriendsListRecycleritem> arrayList;

    private Context context;

    public FriendsListAdapterRecycleritem(Context context, List<FriendsListRecycleritem> items) {
        this.context = context;
        this.items = items;
        this.arrayList = new ArrayList<FriendsListRecycleritem>();
        this.arrayList.addAll(items);
//        this.mData = mData;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (FriendsListRecycleritem friendsListRecycleritem: arrayList) {
                String name = friendsListRecycleritem.getFriendNameList();
                if (name.toLowerCase().contains(charText)) {
                    items.add(friendsListRecycleritem);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView friendListProfile;
        private TextView friendNameList;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
//////////////////
//            F_chatprofile = itemView.findViewById(R.id.chatprofile);
//            F_memberlist = itemView.findViewById(R.id.memberlist);
//            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        moveToFPF(v);
                    }
                }
            });
            friendListProfile = itemView.findViewById(R.id.friendListProfile);
            friendNameList = itemView.findViewById(R.id.friendNameList);

        }
    }

    private void moveToFPF(View v) {
        Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_friendsProfileScreenFragment);
    }

//    FriendsListAdapterRecycleritem(ArrayList<FriendsListRecycleritem> list){
//        this.mData = list;
//    }

    @Override
    public FriendsListAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_friendslist, parent, false) ;
//        FriendsListAdapterRecycleritem.viewHolder vh = new FriendsListAdapterRecycleritem.viewHolder(view) ;
        return new FriendsListAdapterRecycleritem.viewHolder(view);
//        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FriendsListAdapterRecycleritem.viewHolder holder, int position) {
        FriendsListRecycleritem recyclerItem = items.get(position) ;
        holder.friendListProfile.setImageResource(recyclerItem.getFriendListProfile());
        holder.friendNameList.setText(recyclerItem.getFriendNameList()) ;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
