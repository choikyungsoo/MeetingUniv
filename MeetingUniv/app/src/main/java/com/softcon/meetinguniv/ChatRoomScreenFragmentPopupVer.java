package com.softcon.meetinguniv;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRoomScreenFragmentPopupVer extends Fragment implements View.OnClickListener {
    private TextView chatRoomMembers;
    private TextView numOfChatRoomMembers;
    private EditText chatRoomEditText;
    private LinearLayout inputChatRoom;
    private Button backFromChatRoomPop_BTN, leaveChatRoom_BTN;

    private ConstraintLayout ChatRoomScreen;
    private int keyboardheight;
    private boolean isKeyboardShowing = false;
    private View rootView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AndroidBug5497Workaround.assistActivity(this.getActivity());
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_chat_room_screen_popup_ver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.chatRoomMembers = view.findViewById(R.id.chatRoomMembers);
        this.numOfChatRoomMembers = view.findViewById(R.id.join_selectAreaText);
//        this.chatRoomEditText = view.findViewById(R.id.join_selectUnivText);
//        this.inputChatRoom = view.findViewById(R.id.inputChatRoom);
        this.ChatRoomScreen = view.findViewById(R.id.constraintLayoutOfchatRoomPopup);
        this.backFromChatRoomPop_BTN = view.findViewById(R.id.backFromChatRoomPop_BTN);
        this.leaveChatRoom_BTN = view.findViewById(R.id.leaveChatRoom_BTN);

        this.chatRoomMembers.setOnClickListener(this);
        this.backFromChatRoomPop_BTN.setOnClickListener(this);
        this.leaveChatRoom_BTN.setOnClickListener(this);
//        this.chatRoomEditText.setOnClickListener(this);

//        setChatRoomScreen();
    }
//
//    public void setChatRoomScreen() {
//        this.fragmentManager = this.getActivity().getSupportFragmentManager();
//        this.fragmentTransaction = this.fragmentManager.beginTransaction();
//
//        ChatRoomScreenPopup2Fragment chatRoomScreenPopup2Fragment = new ChatRoomScreenPopup2Fragment();
//        fragmentTransaction.replace(R.id.chatRoomScreenPopFragmentContainer, chatRoomScreenPopup2Fragment);
//
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatRoomMembers:
                ChatRoomMembersDialogFragment chatRoomMembersDialogFragment = new ChatRoomMembersDialogFragment(getActivity());
                chatRoomMembersDialogFragment.showThisChatRoomMembers(numOfChatRoomMembers);
                break;
            case R.id.backFromChatRoomPop_BTN:
                Navigation.findNavController(v).navigate(R.id.action_chatRoomScreenFragmentPopupVer_to_mainFragment);
                break;
            case R.id.leaveChatRoom_BTN:
                break;
        }
    }
}