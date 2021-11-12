package com.softcon.meetinguniv;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softcon.meetinguniv.main.ChattingScreenFragment;
import com.softcon.meetinguniv.main.MainFragment;
import com.softcon.meetinguniv.main.PersonalProfileScreenFragment;

import java.util.Objects;

public class ChatRoomScreenFragmentPopupVer extends Fragment implements View.OnClickListener, onBackPressedListener {
    private TextView chatRoomMembers;
    private TextView numOfChatRoomMembers;
    private EditText chatRoomEditText;
    private LinearLayout inputChatRoom;
    private Button backFromChatRoomPop_BTN;
    private ImageView leaveChatRoom_BTN;

    private ConstraintLayout ChatRoomScreen;
    private int keyboardheight;
    private boolean isKeyboardShowing = false;
    private View rootView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private InviteFriendElementFragment inviteFriendElementFragment = new InviteFriendElementFragment();
    private ChattingScreenFragment chattingScreenFragment;

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

        chattingScreenFragment = new ChattingScreenFragment();

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
                ClickHandler clickHandler = new ClickHandler();
                chatRoomMembersDialogFragment.showThisChatRoomMembers(numOfChatRoomMembers, clickHandler);
                break;
            case R.id.backFromChatRoomPop_BTN:
                Navigation.findNavController(v).navigate(R.id.action_chatRoomScreenFragmentPopupVer_to_mainFragment);
                break;
            case R.id.leaveChatRoom_BTN:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("채팅방을 나가면 대화내용이 모두 사라집니다.\n퇴장하시겠습니까?");
                builder.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 채팅방 나가는 코드 추가하기
                        Navigation.findNavController(v).navigate(R.id.action_chatRoomScreenFragmentPopupVer_to_mainFragment);
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.create().show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getActivity(), "왜 안 돼!!!", Toast.LENGTH_SHORT).show();
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().remove(ChatRoomScreenFragmentPopupVer.this).commit();
//        fragmentManager.popBackStack();
        /////////////////////
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
//                .replace(R.id.constraintLayoutOfchatRoomPopup, chattingScreenFragment)
//                .commit();
    }

    public class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.chatRoomInvite_BTN) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                            .replace(R.id.constraintLayoutOfchatRoomPopup, inviteFriendElementFragment)
                            .commit();
            }
            v.getRootView().setVisibility(View.GONE);
        }
    }
}