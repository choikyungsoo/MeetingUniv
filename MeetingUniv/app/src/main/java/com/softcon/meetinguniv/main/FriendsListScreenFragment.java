package com.softcon.meetinguniv.main;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kakao.sdk.common.util.KakaoCustomTabsClient;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.link.WebSharerClient;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.Social;
import com.kakao.sdk.template.model.TextTemplate;
import com.softcon.meetinguniv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Arrays;
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
    private TextView inviteCode,sub1Text, sub2Text, MyProfile;
    private View floatingbg;

    private LinearLayout inviting_code;
    private ConstraintLayout friendlistbackground;

    private Animation float_open, float_close;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("????????????");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private String userID;

    private FeedTemplate feedTemplate;
    private TextTemplate textTemplate;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // bundle data ??????
        this.userID = getArguments().getString("userID");
        Log.d("FriendsListScreenFragment - ???????????????", String.valueOf(this.userID));

        RecyclerView recyclerView = view.findViewById(R.id.F_chatinglist);
        this.list = new ArrayList<FriendsListRecycleritem>();
        this.PersonalProfile = view.findViewById(R.id.chatprofile);
        this.MyProfile = view.findViewById(R.id.memberlist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addRecyclerItem(R.drawable.prot, "???????????? ?????????");
        addRecyclerItem(R.drawable.prot, "meetingUniv");

        this.recyclerItemAdapter = new FriendsListAdapterRecycleritem(this.getActivity(), this.list);
        recyclerView.setAdapter(this.recyclerItemAdapter);

        this.editSearch = (EditText) view.findViewById(R.id.searchFriendsListEditText);
        // ?????? ????????? ??? ????????? ?????????
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

        //????????? ???????????? ??????
        storageRef.child(this.userID+ "/" + "????????? ??????.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(view).load(uri).into(PersonalProfile);
            }
        });

        //????????? ????????? ????????????
        databaseReference.child(String.valueOf(this.userID)).child("?????????").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MyProfile.setText((CharSequence) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addRecyclerItem(int profile, String memberlist){
        FriendsListRecycleritem recyclerItem = new FriendsListRecycleritem();
        recyclerItem.setFriendListProfile(profile);
        recyclerItem.setFriendNameList(memberlist);
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
        this.databaseReference.child(String.valueOf(this.userID)).child("???????????????").addValueEventListener(new ValueEventListener() {
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
                ClipData clipData = ClipData.newPlainText("ID",inviteCode.getText()); //??????????????? ID?????? ???????????? id ?????? ???????????? ??????
                clipboardManager.setPrimaryClip(clipData);

                //????????? ???????????? ?????????????????? ??????
                Toast.makeText(getContext(),"ID??? ?????????????????????.",Toast.LENGTH_SHORT).show();

            }
        });

        this.kakaoshare = dialog.findViewById(R.id.kakaoshare);
        this.kakaoshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //???????????? ???????????? ?????? ?????? ??????
                sendkakaomessage();
                sendingkakaolink();
                sendingwebkakakolink();
            }
        });
    }

    private void sendkakaomessage() {
        String code = "?????? ????????? ?????? ????????? ???????????????."+"\n" + "?????? ?????? ????????? " + (String) this.inviteCode.getText() + "?????????." ;
         this.feedTemplate = new FeedTemplate(
                new Content("?????????????????? ????????? ???????????????.",
                        "https://blogger.googleusercontent.com/img/a/AVvXsEgFqGhp0Xo0BdLQn3soDesJMb3xk4A0l-i-wpV01Ejkun2Ref6keAZNcZmDJIloEoVrk-hu2roMO9EuhishsZOdflpg69H_FoiPZnr_gJD1CTzX0tjQYrrnByxN-aFef2SzAujRiStAN2X7uCLW4Mu7gMicJhYyC4S43VZeFXGJtAWeR9famN2QmG_-=s320",
                        new Link(),
                        code
                ),
                new Social(),
                Arrays.asList(new com.kakao.sdk.template.model.Button("????????? ??????", new Link("https://play.google.com/store", "https://play.google.com/store")))
        );
        //Text Template
        //custom Template
    }


    public void sendingkakaolink(){
        String TAG = "kakaoLink()";
        // ?????????????????? ??????????????? ?????? ??????
        LinkClient.getInstance().defaultTemplate(getContext(), feedTemplate, null, (linkResult, error) -> {
            if (error != null) {
                Log.e("TAG", "??????????????? ????????? ??????", error);
            } else if (linkResult != null) {
                Log.d(TAG, "??????????????? ????????? ?????? ${linkResult.intent}");
                this.startActivity(linkResult.getIntent());

                // ??????????????? ???????????? ??????????????? ?????? ?????? ???????????? ????????? ?????? ?????? ???????????? ?????? ???????????? ?????? ??? ????????????.
                Log.w("TAG", "Warning Msg: " + linkResult.getWarningMsg());
                Log.w("TAG", "Argument Msg: " + linkResult.getArgumentMsg());
            }
            return null;
        });
    }

    public void sendingwebkakakolink(){
        String TAG = "webKakaoLink()";

        // ???????????? ?????????: ??? ?????? ?????? ??????
        // ??? ?????? ?????? ??????
        Uri sharerUrl = WebSharerClient.getInstance().defaultTemplateUri(feedTemplate);

        // CustomTabs?????? ??? ???????????? ??????
        // 1. CustomTabs?????? Chrome ???????????? ??????
        try {
            KakaoCustomTabsClient.INSTANCE.openWithDefault(getContext(), sharerUrl);
        } catch (UnsupportedOperationException e) {
            // Chrome ??????????????? ?????? ??? ????????????
        }

        // 2. CustomTabs?????? ???????????? ?????? ???????????? ??????
        try {
            KakaoCustomTabsClient.INSTANCE.open(getContext(), sharerUrl);
        } catch (ActivityNotFoundException e) {
            // ????????? ??????????????? ?????? ??? ????????????
        }
    }
    private void movetoPersonalProfile(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("userID", this.userID);
        Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_personalProfileScreenFragment, bundle);
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