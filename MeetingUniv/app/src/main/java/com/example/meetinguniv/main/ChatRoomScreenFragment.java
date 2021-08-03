package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.example.meetinguniv.R;

import java.util.ArrayList;

public class ChatRoomScreenFragment extends Fragment implements View.OnClickListener{

    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();

    private boolean openMenu = false;

    private Animation translateLeftAnim;
    private ConstraintLayout basePage;
    private LinearLayout menuPage;
    private ImageView menubtn;
    private RecyclerView recyclerView;
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ChatRoomRecyclerItem chatRoomRecyclerItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recyclerview
        this.recyclerView = view.findViewById(R.id.chatRecyclerView);

        ChatRoomRecyclerAdapter chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this.list);
        this.recyclerView.setAdapter(chatRoomRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("테스트 메세지입니다.", 0);
        addRecyclerItem("테스트 메세지입니다.", 1);

//        this.chatRoomRecyclerAdapter.notifyDataSetChanged();

        //the other
        this.basePage = view.findViewById(R.id.basePage);
        this.menuPage = view.findViewById(R.id.menuPage);

        this.menubtn = view.findViewById(R.id.menubtn);
        this.menubtn.setOnClickListener(this);

        this.basePage.setOnClickListener(this);
    }

    private void addRecyclerItem(String message, int viewType) {
        this.chatRoomRecyclerItem = new ChatRoomRecyclerItem();
        this.chatRoomRecyclerItem.setMessage(message);
        this.chatRoomRecyclerItem.setViewType(viewType);
        this.list.add(this.chatRoomRecyclerItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menubtn:
                this.menuPage.setVisibility(View.VISIBLE);
                this.openMenu = true;
                break;
            case R.id.basePage:
                if (this.openMenu) {
                    menuPage.setVisibility(View.GONE);
                }
                break;
        }
    }
}