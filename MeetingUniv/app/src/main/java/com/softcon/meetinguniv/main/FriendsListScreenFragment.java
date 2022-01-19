package com.softcon.meetinguniv.main;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.TextTemplate;
import com.softcon.meetinguniv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FriendsListScreenFragment extends Fragment implements View.OnClickListener {
//    private ArrayList<FriendsListRecycleritem> list = new ArrayList<FriendsListRecycleritem>();
    private List<FriendsListRecycleritem> list;
    private Boolean isFloatOpen = false;
    private FriendsListAdapterRecycleritem recyclerItemAdapter;
    private FloatingActionButton inviteFriends, sub1_invite, sub2_add;
    private ImageButton kakaoshare;
    private ImageView PersonalProfile;
    private EditText editSearch;
    private TextView inviteCode,sub1Text, sub2Text;
    private View floatingbg;

    private LinearLayout inviting_code;
    private ConstraintLayout friendlistbackground;

    private Animation float_open, float_close;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("회원정보");

    private long userID;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // bundle data 받기
        this.userID = getArguments().getLong("userID");
        Log.d("FriendsListScreenFragment - 회원아이디", String.valueOf(this.userID));

        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist);
        this.list = new ArrayList<FriendsListRecycleritem>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "테스트용 입니다");
        addRecyclerItem(R.drawable.prot, "meetingUniv");

        this.recyclerItemAdapter = new FriendsListAdapterRecycleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

        this.editSearch = (EditText) view.findViewById(R.id.searchFriendsListEditText);
        // 엔터 눌렀을 때 키보드 숨기기
        this.editSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( editSearch.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                recyclerItemAdapter.filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void addRecyclerItem(int profile, String memberlist){
        FriendsListRecycleritem recyclerItem = new FriendsListRecycleritem();
        recyclerItem.setF_chatingProfile(profile);
        recyclerItem.setF_memberlist(memberlist);
        list.add(recyclerItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_friends_list_screen, container, false);
        this.float_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.floating_open);
        this.float_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.flaoting_close);
        this.friendlistbackground = rootView.findViewById(R.id.friendlistbackground);
        this.inviteFriends = rootView.findViewById(R.id.invitingfriends);
        this.sub1_invite = rootView.findViewById(R.id.sub1_invite);
        this.sub2_add = rootView.findViewById(R.id.sub2_add);
        this.sub1Text = rootView.findViewById(R.id.friendinvitetext);
        this.sub2Text = rootView.findViewById(R.id.friendaddtext);
        this.PersonalProfile = rootView.findViewById(R.id.chatprofile);
        this.floatingbg = rootView.findViewById(R.id.floatingbg);
        this.inviteFriends.setOnClickListener(this);
        this.sub1_invite.setOnClickListener(this);
        this.sub2_add.setOnClickListener(this);
        this.PersonalProfile.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invitingfriends:
                action(v);
                break;
            case R.id.sub1_invite:
                action(v);
                showsub1Dialog(v);
                break;
            case R.id.sub2_add:
                action(v);
                showsub2Dialog(v);
                break;
            case R.id.chatprofile:
                movetoPersonalProfile(v);
                break;
        }
    }

    private void showsub2Dialog(View v) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.popup_addfriend, null));

        dialog = builder.create();
        dialog.show();
    }

    private void showsub1Dialog(View v) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.popup_invite, null));

        dialog = builder.create();
        dialog.show();

        this.inviteCode = dialog.findViewById(R.id.invitecode);
        this.databaseReference.child(String.valueOf(this.userID)).child("추천인코드").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inviteCode.setText((CharSequence) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this.inviting_code = dialog.findViewById(R.id.inviting_code);
        this.inviting_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("ID",inviteCode.getText()); //클립보드에 ID라는 이름표로 id 값을 복사하여 저장
                clipboardManager.setPrimaryClip(clipData);

                //복사가 되었다면 토스트메시지 노출
                Toast.makeText(getContext(),"ID가 복사되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });

        this.kakaoshare = dialog.findViewById(R.id.kakaoshare);
        this.kakaoshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카카오톡 공유하기 기능 구현 부분
                sendkakaomessage();
            }
        });
    }

    private void sendkakaomessage() {
        //FeedTemplate Message
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("디저트 사진",
                        "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("아메리카노, 빵, 케익")
                        .build())
                .setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                        .setSharedCount(30).setViewCount(40).build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("'https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendDefault(this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });
        //Text Template
    }

    private void movetoPersonalProfile(View v) {
        Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_personalProfileScreenFragment);
    }

    private void action(View v){
        if (isFloatOpen) {
            inviteFriends.setImageResource(R.drawable.add);
            floatingbg.setVisibility(v.INVISIBLE);
            sub1_invite.startAnimation(float_close);
            sub2_add.startAnimation(float_close);
            sub1Text.startAnimation(float_close);
            sub2Text.startAnimation(float_close);
            sub1_invite.setClickable(false);
            sub2_add.setClickable(false);
            isFloatOpen = false;
        } else {
            inviteFriends.setImageResource(R.drawable.cancel);
            floatingbg.setVisibility(v.VISIBLE);
            sub1_invite.startAnimation(float_open);
            sub2_add.startAnimation(float_open);
            sub1Text.startAnimation(float_open);
            sub2Text.startAnimation(float_open);
            sub1_invite.setClickable(true);
            sub2_add.setClickable(true);
            isFloatOpen = true;
        }
    }
    private void InviteFriendDialog(View v){
        this.friendlistbackground.setBackgroundColor(77000000);

    }
}