package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softcon.meetinguniv.R;

import java.util.ArrayList;

public class ChatRoomScreenFragment extends Fragment implements View.OnClickListener{

    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();

    private boolean openMenu = false;

    private Animation translateLeftAnim;
    private ConstraintLayout basePage;
    private LinearLayout menuPage;
    private ImageView menubtn;

    private EditText editText;
    private RecyclerView recyclerView;
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ChatRoomRecyclerItem chatRoomRecyclerItem;
    private Button backFromChatRoom_BTN;

    /// 키보드 이슈
    private View rootView;
    private int viewHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //recyclerview
        this.recyclerView = view.findViewById(R.id.chatRecyclerView);

        this.chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this.list);
        this.recyclerView.setAdapter(chatRoomRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("테스트 메세지입니다.1", 0);
        addRecyclerItem("테스트 메세지입니다.2", 1);

        this.chatRoomRecyclerAdapter.notifyDataSetChanged();

        //the other
        this.basePage = view.findViewById(R.id.basePage);
        this.menuPage = view.findViewById(R.id.menuPage);

        this.menubtn = view.findViewById(R.id.menubtn);

//        this.editText = view.findViewById(R.id.textView14);

        this.backFromChatRoom_BTN = view.findViewById(R.id.backFromChatRoom_BTN);

        this.menubtn.setOnClickListener(this);
        this.basePage.setOnClickListener(this);
        this.menuPage.setOnClickListener(this);
        this.backFromChatRoom_BTN.setOnClickListener(this);
//        this.editText.setOnClickListener(this);

        this.rootView = getActivity().getWindow().getDecorView();
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int currentViewHeight = rootView.getHeight();
                if (currentViewHeight > viewHeight) {
                    viewHeight = currentViewHeight;
                }
            }
        });

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
                    this.openMenu = false;
//                    this.basePage.requestFocus();
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
                break;
            case R.id.menuPage:
                menuPage.setVisibility(View.VISIBLE);
                break;
            case R.id.backFromChatRoom_BTN:
                Navigation.findNavController(v).navigate(R.id.action_chatRoomScreenFragment_to_mainFragment);
                break;
//            case R.id.textView14:
//                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                this.editText.requestFocus();
//                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        }
    }
}