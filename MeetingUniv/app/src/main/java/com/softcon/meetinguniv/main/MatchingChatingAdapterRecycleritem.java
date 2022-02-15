package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softcon.meetinguniv.R;

public class MatchingChatingAdapterRecycleritem extends RecyclerView.Adapter<MatchingChatingAdapterRecycleritem.viewHolder>{

//    private ArrayList<MatchingChatingRecycleritem> mData;
//
//
//    MatchingChatingAdapterRecycleritem(ArrayList<MatchingChatingRecycleritem> list){
//        this.mData = list;
//    }

    private List<MatchingChatingRecycleritem> items = null;
    private ArrayList<MatchingChatingRecycleritem> arrayList;

    private Context context;

    public MatchingChatingAdapterRecycleritem(Context context, List<MatchingChatingRecycleritem> items) {
        this.context = context;
        this.items = items;
        this.arrayList = new ArrayList<MatchingChatingRecycleritem>();
        this.arrayList.addAll(items);
//        this.mData = mData;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (MatchingChatingRecycleritem matchingChatingRecycleritem: arrayList) {
                String name = matchingChatingRecycleritem.getMemberlist();
                if (name.toLowerCase().contains(charText)) {
                    items.add(matchingChatingRecycleritem);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public MatchingChatingAdapterRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
//        MatchingChatingAdapterRecycleritem.viewHolder vh = new MatchingChatingAdapterRecycleritem.viewHolder(view) ;
//        return vh;
        return new MatchingChatingAdapterRecycleritem.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MatchingChatingAdapterRecycleritem.viewHolder holder, int position) {
        MatchingChatingRecycleritem recyclerItem = items.get(position) ;
        holder.chatprofile.setImageResource(recyclerItem.getChatingProfile());
        holder.memberlist.setText(recyclerItem.getMemberlist()) ;
    }

    @Override
    public int getItemCount() {
        return items.size();
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
                    Navigation.findNavController(itemView).navigate(R.id.action_mainFragment_to_chatRoomScreenFragmentPopupVer);
                }
            });
            chatprofile = itemView.findViewById(R.id.chatprofile);
            memberlist = itemView.findViewById(R.id.memberlist);

        }
    }
}
