package com.softcon.meetinguniv.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Space;

import com.softcon.meetinguniv.R;
import com.softcon.meetinguniv.main.setting.ChangeNickContentFragment;
import com.softcon.meetinguniv.main.setting.SettingsContentFragment;
import com.softcon.meetinguniv.main.setting.SettingsScreenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    private MainScreenFragment mainScreenFragment;
    private FriendsListScreenFragment friendsListScreenFragment;
//    private ChattingScreenFragment chattingScreenFragment;
    // 일반 채팅 제거
    private MatchChattingContentFragment matchChattingContentFragment;
    private ShopScreenFragment shopScreenFragment;
    private SettingsScreenFragment settingsScreenFragment;

    private Space status_bar_space;
    private Button needmoreheart_btn;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private int statusBarHeight;

    private String userID;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainScreenFragment = new MainScreenFragment();
        this.friendsListScreenFragment = new FriendsListScreenFragment();
//        this.chattingScreenFragment = new ChattingScreenFragment();
        this.matchChattingContentFragment = new MatchChattingContentFragment();
        this.shopScreenFragment = new ShopScreenFragment();
        this.settingsScreenFragment = new SettingsScreenFragment();

        this.userID = getActivity().getIntent().getStringExtra("userID");
        Log.d("회원아이디", String.valueOf(this.userID));

        Bundle bundle = new Bundle();
        this.mainScreenFragment = new MainScreenFragment();
        bundle.putString("userID", this.userID);
        this.mainScreenFragment.setArguments(bundle);

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        
        this.bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        this.needmoreheart_btn = view.findViewById(R.id.needmoreheartBTN);

//        this.bottomNavigationView.setSelectedItemId(R.id.main);
        PersonalProfileScreenFragment PPS = new PersonalProfileScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userID", this.userID);
        PPS.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.screenfragmentContainer, this.mainScreenFragment).commit();
        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main:
                        setFragment(0);
                        break;
                    case R.id.friends:
                        setFragment(1);
                        break;
                    case R.id.chatting:
                        setFragment(2);
                        break;
                    case R.id.shop:
                        setFragment(3);
                        break;
                    case R.id.setting:
                        setFragment(4);
                        break;
                }
                return true;
            }
        });
//        this.bottomNavigationView.setOnNavigationItemSelectedListener(7new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.main:
//                        setFragment(0);
//                        break;
//                    case R.id.chatting:
//                        setFragment(2);
//                        break;
//                    case R.id.shop:
//                        setFragment(3);
//                        break;
//                    case R.id.setting:
//                        setFragment(4);
//                        break;
//                }
//                return true;
//            }
//        });

        this.needmoreheart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.shop);
//                setFragment(3);
            }
        });
    }


    public void setFragment(int n) {
        this.fragmentManager = this.getActivity().getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        switch (n){
            case 0:
                this.mainScreenFragment = new MainScreenFragment();
                bundle2.putString("userID", this.userID);
                this.mainScreenFragment.setArguments(bundle2);
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.mainScreenFragment);
                break;
            case 1:
                this.friendsListScreenFragment = new FriendsListScreenFragment();
                bundle.putString("userID", this.userID);
                this.friendsListScreenFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.friendsListScreenFragment);
                break;
            case 2:
//                this.chattingScreenFragment = new ChattingScreenFragment();
                this.matchChattingContentFragment = new MatchChattingContentFragment();
                bundle.putString("userID", this.userID);
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.matchChattingContentFragment);
                break;
            case 3:
                this.shopScreenFragment = new ShopScreenFragment();
                bundle.putString("userID", this.userID);
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.shopScreenFragment);
                break;
            case 4:
                this.settingsScreenFragment = new SettingsScreenFragment();
                bundle.putString("userID", this.userID);
                this.settingsScreenFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.settingsScreenFragment);
                break;
        }
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}