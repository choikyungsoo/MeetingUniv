package com.softcon.meetinguniv;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRoomScreenFragmentPopupVer extends Fragment implements View.OnClickListener {
    private TextView chatRoomMembers;
    private TextView numOfChatRoomMembers;
    private EditText chatRoomEditText;
    private LinearLayout inputChatRoom;

    private ConstraintLayout ChatRoomScreen;
    private int keyboardheight;
    private boolean isKeyboardShowing = false;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_screen_popup_ver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        this.chatRoomMembers = view.findViewById(R.id.chatRoomMembers);
        this.numOfChatRoomMembers = view.findViewById(R.id.join_selectAreaText);
        this.chatRoomEditText = view.findViewById(R.id.join_selectUnivText);
        this.inputChatRoom = view.findViewById(R.id.inputChatRoom);
        this.ChatRoomScreen = view.findViewById(R.id.constraintLayoutOfchatRoomPopup);

        this.chatRoomMembers.setOnClickListener(this);
        this.chatRoomEditText.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_selectUnivText:
                break;
            case R.id.chatRoomMembers:
                ChatRoomMembersDialogFragment chatRoomMembersDialogFragment = new ChatRoomMembersDialogFragment(getActivity());
                chatRoomMembersDialogFragment.showThisChatRoomMembers(numOfChatRoomMembers);
        }
    }
}