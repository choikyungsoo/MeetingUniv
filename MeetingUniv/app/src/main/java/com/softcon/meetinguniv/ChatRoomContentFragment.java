package com.softcon.meetinguniv;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.ChatRoomRecyclerAdapter;
import com.softcon.meetinguniv.main.ChatRoomRecyclerItem;

import java.util.ArrayList;


public class ChatRoomContentFragment extends Fragment {
    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();
    private RecyclerView recyclerView;
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ChatRoomRecyclerItem chatRoomRecyclerItem;
    private EditText cET;

    //키보드 높이 구하기
    private boolean isKeyboardShowing = false;
    private int keyboardheight;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room_content, container, false);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.cET = view.findViewById(R.id.chatroomET);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //recyclerview
        this.recyclerView = view.findViewById(R.id.chatRecyclerView);

        this.chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this.list);
        this.recyclerView.setAdapter(chatRoomRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("테스트 메세지입니다.1", 0);
        addRecyclerItem("테스트 메세지입니다.2", 1);

        caculatekeyboard(view);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void caculatekeyboard(View view) {
        Rect rectangle = new Rect();
        view.getWindowVisibleDisplayFrame(rectangle);
        int screenHeight = view.getRootView().getHeight();
        int tempkeyboardsize = screenHeight - rectangle.bottom;


            if (tempkeyboardsize > screenHeight * 0.1) {
//            대부분의 키보드 높이가 전체의 10프로이상차지해서 0.1로 정함
                this.keyboardheight  = screenHeight - rectangle.bottom;
                if (!isKeyboardShowing) {
//                키보드가 보여지는 경우
                    isKeyboardShowing = true;
                    Toast.makeText(getContext(), "키보드가 보여짐", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                // 키보드가 안보이는 경우
                if (isKeyboardShowing) {
                    isKeyboardShowing = false;
                    Toast.makeText(getContext(), "키보드가 닫힘", Toast.LENGTH_SHORT).show();
                }
            }



        this.rootView = getActivity().getWindow().getDecorView();
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                caculatekeyboard(rootView);
            }
        });

    }

    private void addRecyclerItem(String message, int viewType) {
        this.chatRoomRecyclerItem = new ChatRoomRecyclerItem();
        this.chatRoomRecyclerItem.setMessage(message);
        this.chatRoomRecyclerItem.setViewType(viewType);
        this.list.add(this.chatRoomRecyclerItem);
    }

}