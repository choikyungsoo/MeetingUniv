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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return inflater.inflate(R.layout.fragment_chat_room_screen_popup_ver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.chatRoomMembers = view.findViewById(R.id.chatRoomMembers);
        this.numOfChatRoomMembers = view.findViewById(R.id.join_selectAreaText);
        this.chatRoomEditText = view.findViewById(R.id.join_selectUnivText);
        this.inputChatRoom = view.findViewById(R.id.inputChatRoom);
        this.ChatRoomScreen = view.findViewById(R.id.constraintLayoutOfchatRoomPopup);

        this.chatRoomMembers.setOnClickListener(this);
        this.chatRoomEditText.setOnClickListener(this);

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