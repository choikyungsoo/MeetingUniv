package com.softcon.meetinguniv.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcon.meetinguniv.R;

public class chooseteamAdapterRecyleritem extends RecyclerView.Adapter<chooseteamAdapterRecyleritem.viewHolder> {
//    private ArrayList<chooseteamRecycleritem> mData;

    private List<chooseteamRecycleritem> items = null;
    private ArrayList<chooseteamRecycleritem> arrayList;

    private Context context;

    public chooseteamAdapterRecyleritem(Context context, List<chooseteamRecycleritem> items) {
        this.context = context;
        this.items = items;
        this.arrayList = new ArrayList<chooseteamRecycleritem>();
        this.arrayList.addAll(items);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
            Toast.makeText(context, "ddd", Toast.LENGTH_SHORT).show();
        } else {
            for (chooseteamRecycleritem teamRecycleritem: arrayList) {
                String name = teamRecycleritem.getMember();
                if (name.toLowerCase().contains(charText)) {
                    items.add(teamRecycleritem);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView member;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teamname);
            member = itemView.findViewById(R.id.teammember);

        }
    }
//    chooseteamAdapterRecyleritem(ArrayList<chooseteamRecycleritem> list){
//        mData = list;
//    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycleritem_chooseteam, parent, false) ;
        chooseteamAdapterRecyleritem.viewHolder vh = new chooseteamAdapterRecyleritem.viewHolder(view) ;

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull chooseteamAdapterRecyleritem.viewHolder holder, int position) {
        chooseteamRecycleritem recyclerItem = items.get(position) ;
        holder.name.setText(recyclerItem.getName()) ;
        holder.member.setText(recyclerItem.getMember()) ;
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
