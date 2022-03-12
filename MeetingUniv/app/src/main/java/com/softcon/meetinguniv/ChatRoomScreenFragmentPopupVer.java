package com.softcon.meetinguniv;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softcon.meetinguniv.main.ChatRoomRecyclerAdapter;
import com.softcon.meetinguniv.main.ChatRoomRecyclerItem;
import com.softcon.meetinguniv.main.ChattingScreenFragment;
import com.softcon.meetinguniv.main.MainFragment;
import com.softcon.meetinguniv.main.MatchChattingContentFragment;
import com.softcon.meetinguniv.main.PersonalProfileScreenFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoomScreenFragmentPopupVer extends Fragment implements View.OnClickListener, onBackPressedListener {

    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();

    private TextView chatRoomMembers;
    private TextView numOfChatRoomMembers;
    private EditText chatRoomEditText;
    private LinearLayout inputChatRoom;
    private Button backFromChatRoomPop_BTN;
    private ImageView leaveChatRoom_BTN;
    private RecyclerView chatRecyclerView;

    // for recyclerview
    private ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    private ChatRoomRecyclerItem chatRoomRecyclerItem;

    private ConstraintLayout ChatRoomScreen;
    private int keyboardheight;
    private boolean isKeyboardShowing = false;
    private View rootView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private InviteFriendElementFragment inviteFriendElementFragment = new InviteFriendElementFragment();
    private ChattingScreenFragment chattingScreenFragment;
    private MatchChattingContentFragment matchChattingContentFragment;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        this.chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        chattingScreenFragment = new ChattingScreenFragment();

        this.chatRoomMembers.setOnClickListener(this);
        this.backFromChatRoomPop_BTN.setOnClickListener(this);
        this.leaveChatRoom_BTN.setOnClickListener(this);
//        this.chatRoomEditText.setOnClickListener(this);

//        setChatRoomScreen();

        this.chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this.list);
        this.chatRecyclerView.setAdapter(chatRoomRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.chatRecyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem("2022-02-25", null, null, null, null, null, 0);
//        addRecyclerItem(null, null, null, "테스트 메세지입니다.1", "time", "3", 1);
//        addRecyclerItem(null, null, "채윤", "테스트 메세지입니다.2", "time", "3", 2);
//        addRecyclerItem(null, null, "채윤2", "테스트 메세지입니다.3", "time", "3", 2);

        db.collection("ChattingContents2")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        if (document.getData().get("sender") != null) {
                            addRecyclerItem(null, null, document.getData().get("sender").toString(),
                                    document.getData().get("text").toString(),
                                    document.getData().get("time").toString(),
                                    document.getData().get("uncheck").toString(),
                                    2
                            );
                        } else {
                            addRecyclerItem(null, null, null,
                                    document.getData().get("text").toString(),
                                    document.getData().get("time").toString(),
                                    document.getData().get("uncheck").toString(),
                                    1
                            );
                        }

//                        addRecyclerItem2(3, "참가자, 참가자, 참가자, 참가자, 참가자, 참가자", 6, 10);
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

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

//    @Override public void onResume() {
//        super.onResume();
//        getActivity().setOnBackPressedListener(this);
//    }
//    @Override public void onBackPressed() {
//        getActivity().setCurrentItem();
//    }

//    @Override
//    public void onBackPressed() {
//        goToMain();
//    }
//
//    //프래그먼트 종료
//    private void goToMain(){
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().remove(this).commit();
//        fragmentManager.popBackStack();
//    }

    private void addRecyclerItem(String date, Uri profileImage, String nickname, String message, String time, String uncheck, int viewType) {
        this.chatRoomRecyclerItem = new ChatRoomRecyclerItem();
        this.chatRoomRecyclerItem.setDate(date);
        this.chatRoomRecyclerItem.setProfileImage(profileImage);
        this.chatRoomRecyclerItem.setNickname(nickname);
        this.chatRoomRecyclerItem.setMessage(message);
        this.chatRoomRecyclerItem.setTime(time);
        this.chatRoomRecyclerItem.setUncheck(uncheck);
        this.chatRoomRecyclerItem.setViewType(viewType);
        this.list.add(this.chatRoomRecyclerItem);
        chatRoomRecyclerAdapter.notifyDataSetChanged();
    }



    @Override
    public void onBackPressed() {
//        Toast.makeText(getContext(), "왜 안 돼!!!", Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "안되나??---------------------------------------------------------------------");
//
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().remove(ChatRoomScreenFragmentPopupVer.this).commit();
//        fragmentManager.popBackStack();
//        /////////////////////
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                .replace(R.id.constraintLayoutOfchatRoomPopup, matchChattingContentFragment)
                .commit();
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