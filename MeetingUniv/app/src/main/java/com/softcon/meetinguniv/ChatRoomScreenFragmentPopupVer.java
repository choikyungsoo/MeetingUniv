package com.softcon.meetinguniv;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softcon.meetinguniv.main.ChatRoomRecyclerAdapter;
import com.softcon.meetinguniv.main.ChatRoomRecyclerItem;
import com.softcon.meetinguniv.main.ChattingScreenFragment;
import com.softcon.meetinguniv.main.MatchChattingContentFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatRoomScreenFragmentPopupVer extends Fragment implements View.OnClickListener, onBackPressedListener {

    private ArrayList<ChatRoomRecyclerItem> list = new ArrayList<ChatRoomRecyclerItem>();

    private TextView chatRoomMembers;
    private TextView numOfChatRoomMembers;
    private EditText chatRoomEditText;
    private LinearLayout inputChatRoom;
    private Button backFromChatRoomPop_BTN;
    private ImageView leaveChatRoom_BTN;
    private RecyclerView chatRecyclerView;
    private ScrollView chatScrollView;
    private LinearLayout inputLinearChatRoom;

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
    private List<Map<String, Object>> contents;
    private List<Map<String, Object>> contents_get;
    private List<String> oldPost_get;
    private String oldestPostId;

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
        this.chatScrollView = view.findViewById(R.id.chatScrollView);
        this.inputLinearChatRoom = view.findViewById(R.id.inputLinearChatRoom);

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

        contents = new ArrayList<java.util.Map<String, Object>>();
        contents_get = new ArrayList<java.util.Map<String, Object>>();
        oldPost_get = new ArrayList<String>();

        db.collection("ChattingContent").orderBy(FieldPath.documentId()).limit(20).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        contents.add(document.getData());
                        contents_get.add(0, document.getData());
                        Log.d("TAG", document.getId() + " => " + document.getData());
//                        Log.d("TAG", "********" + contents.toString());

                        oldPost_get.add(document.getId());
//                        Log.d("TAG", document.getId() + " => " + document.getData());
//                        if (document.getData().get("sender") != null) {
//                            addRecyclerItem(null, null, document.getData().get("sender").toString(),
//                                    document.getData().get("text").toString(),
//                                    document.getData().get("time").toString(),
//                                    document.getData().get("uncheck").toString(),
//                                    2
//                            );
//                        } else {
//                            addRecyclerItem(null, null, null,
//                                    document.getData().get("text").toString(),
//                                    document.getData().get("time").toString(),
//                                    document.getData().get("uncheck").toString(),
//                                    1
//                            );
//                        }

//                        addRecyclerItem2(3, "참가자, 참가자, 참가자, 참가자, 참가자, 참가자", 6, 10);
                    }
                    loadChattingContent(contents);

                    oldestPostId = oldPost_get.get(0);
                    Log.d("TAG", "oldestPostId : " + oldestPostId);

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        this.chatRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    Toast.makeText(getContext(), "스크롤 올라감", Toast.LENGTH_SHORT).show();
                }

//                db.collection("ChattingContent").orderBy(FieldPath.documentId())
//                        .endAt(oldestPostId).limit(20).get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        contents_get.clear(); //임시저장 위치
//                        oldPost_get.clear();
//
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            contents_get.add(0, document.getData());
//                            oldPost_get.add(document.getId());
//                        }
//                        //불러오는 중인지, 전부 불러왔는지 if문
//                        if (contents_get.size() > 1) {//1개라도 있으면 불러옴
//                            //마지막 중복되는 부분 삭제
//                            contents_get.remove(0);
//                            //contents 뒤에 추가
//                            contents.addAll(contents_get);
//                            oldestPostId = oldPost_get.get(0);
//                            //메시지 갱신 위치
//                            loadChattingContent(contents);
//                        }
//                    }
//                });
            }
        });

    }

    private void loadChattingContent(List<Map<String, Object>> contents) {
        for (int i=0; i<contents.size(); i++) {
            Map<String, Object> content = contents.get(i);

            String date = null;
            Uri profileImage = null;
            String nickname;
            int viewType = 0;
            if (content.get("sender") != null) {
                nickname = content.get("sender").toString();
                viewType = 2;
            } else {
                nickname = null;
                viewType = 1;
            }
            String message = content.get("text").toString();
            String time = content.get("time").toString();
            String uncheck = content.get("uncheck").toString();

            addRecyclerItem(date, profileImage, nickname, message, time, uncheck, viewType);
        }

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