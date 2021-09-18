package com.softcon.meetinguniv;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.ChatRoomRecyclerAdapter;
import com.softcon.meetinguniv.main.ChatRoomRecyclerItem;

import java.util.ArrayList;


public class ChatRoomScreenFragmentVersion2 extends Fragment implements View.OnClickListener{
    private boolean openMenu = false;
    private LinearLayout basePage;
    private LinearLayout menuPage;
    private Button C_backBTN;
    private ImageView menubtn;

    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();
    private RecyclerView recyclerView;
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ChatRoomRecyclerItem chatRoomRecyclerItem;
    private EditText cET;

    //키보드 높이 구하기
    private ConstraintLayout ChatRoomScreen;
    private boolean isKeyboardShowing = false;
    private int keyboardheight;
    private int heigthConstraints;
    private View rootView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //recyclerview
        this.recyclerView = view.findViewById(R.id.chatRecyclerView);

        this.chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this.list);
        this.recyclerView.setAdapter(chatRoomRecyclerAdapter);

        this.ChatRoomScreen = view.findViewById(R.id.CMainScreen);
        this.heigthConstraints = this.ChatRoomScreen.getHeight()-keyboardheight;



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("테스트 메세지입니다.1", 0);
        addRecyclerItem("테스트 메세지입니다.2", 1);

        caculatekeyboard(view);

    }
    private void caculatekeyboard(View view) {
        Rect rectangle = new Rect();
        view.getWindowVisibleDisplayFrame(rectangle);
        int screenHeight = view.getRootView().getHeight();
        int tempkeyboardsize = screenHeight - rectangle.bottom;


        if (tempkeyboardsize > screenHeight * 0.1) {
//            대부분의 키보드 높이가 전체의 10프로이상차지해서 0.1로 정함
            keyboardheight  = screenHeight - rectangle.bottom;
            if (!isKeyboardShowing) {
//                키보드가 보여지는 경우
                isKeyboardShowing = true;
                Toast.makeText(getContext(), "키보드가 보여짐", Toast.LENGTH_SHORT).show();

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(120,140);
                this.ChatRoomScreen.setLayoutParams(params);

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_room_screen_version2, container, false);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.basePage = view.findViewById(R.id.basePage);
        this.menuPage = view.findViewById(R.id.menuPage);
        this.C_backBTN = view.findViewById(R.id.C_Backbutton);
        this.menubtn = view.findViewById(R.id.menubtn);

//        this.editText = view.findViewById(R.id.textView14);

        this.menubtn.setOnClickListener(this);
        this.basePage.setOnClickListener(this);
        this.C_backBTN.setOnClickListener(this);
        this.menuPage.setOnClickListener(this);
        return view;
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
            case R.id.C_Backbutton:
                Navigation.findNavController(v).navigate(R.id.action_chatRoomScreenFragmentVersion2_to_mainFragment);
                break;
            case R.id.menuPage:
                menuPage.setVisibility(View.VISIBLE);
//            case R.id.textView14:
//                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                this.editText.requestFocus();
//                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        }
    }
}