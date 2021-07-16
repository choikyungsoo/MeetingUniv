package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.meetinguniv.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class chooseteamElementFragment extends Fragment {
    private ImageButton backbtn;
    private ArrayList<chooseteamRecycleritem> list = new ArrayList<chooseteamRecycleritem>();
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.teamrecycler) ;
        chooseteamAdapterRecyleritem recyclerItemAdapter = new chooseteamAdapterRecyleritem(this.list);
        recyclerView.setAdapter(recyclerItemAdapter) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
        addRecyclerItem("한양대", "1,2,3");
        addRecyclerItem("연세대", "A,B,c");
        addRecyclerItem("고려대", "가,나,다");
        addRecyclerItem("명지대", "ㄱㄱ,ㄴㄴ,ㄷㄷ");
        addRecyclerItem("한양대", "1,2,3");
        addRecyclerItem("연세대", "A,B,c");
        addRecyclerItem("고려대", "가,나,다");
        recyclerItemAdapter.notifyDataSetChanged();

    }

    private void addRecyclerItem( String name, String member){
        chooseteamRecycleritem recyclerItem = new chooseteamRecycleritem();
        recyclerItem.setName(name);
        recyclerItem.setMember(member);
        list.add(recyclerItem);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MatchingContentFragment fragment = new MatchingContentFragment();
        View view = inflater.inflate(R.layout.fragment_chooseteam_element, container, false);
        backbtn = view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Framecontainer, fragment)
                        .commit();
            }
        });
        return view;
    }
}