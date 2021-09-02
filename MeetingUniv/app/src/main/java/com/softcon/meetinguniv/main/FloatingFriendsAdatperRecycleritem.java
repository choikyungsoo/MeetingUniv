package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softcon.meetinguniv.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FloatingFriendsAdatperRecycleritem extends RecyclerView.Adapter<FloatingFriendsAdatperRecycleritem.viewHolder>{
//    private ArrayList<FloatingFriendsRecylceritem> mData;

    private List<FloatingFriendsRecylceritem> items = null;
    private ArrayList<FloatingFriendsRecylceritem> arrayList;

    private Context context;

    public FloatingFriendsAdatperRecycleritem(Context context, List<FloatingFriendsRecylceritem> items) {
        this.context = context;
        this.items = items;
        this.arrayList = new ArrayList<FloatingFriendsRecylceritem>();
        this.arrayList.addAll(items);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (FloatingFriendsRecylceritem floatingFriendsRecylceritem: arrayList) {
                String name = floatingFriendsRecylceritem.getPop_name();
                if (name.toLowerCase().contains(charText)) {
                    items.add(floatingFriendsRecylceritem);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView pop_chatprofile;
        private TextView pop_memberlist;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pop_chatprofile = itemView.findViewById(R.id.chatprofile);
            pop_memberlist = itemView.findViewById(R.id.memberlist);

        }
    }

//    FloatingFriendsAdatperRecycleritem(ArrayList<FloatingFriendsRecylceritem> list){
//        this.mData = list;
//    }

    @Override
    public FloatingFriendsAdatperRecycleritem.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chatmembers, parent, false) ;
        FloatingFriendsAdatperRecycleritem.viewHolder vh = new FloatingFriendsAdatperRecycleritem.viewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FloatingFriendsAdatperRecycleritem.viewHolder holder, int position) {
        FloatingFriendsRecylceritem recyclerItem = items.get(position) ;
        holder.pop_chatprofile.setImageResource(recyclerItem.getPop_profile());
        holder.pop_memberlist.setText(recyclerItem.getPop_name()) ;
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
