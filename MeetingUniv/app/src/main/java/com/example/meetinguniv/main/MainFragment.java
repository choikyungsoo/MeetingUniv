package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meetinguniv.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    private MainScreenFragment mainScreenFragment;
    private FriendsListScreenFragment friendsListScreenFragment;
    private ChattingScreenFragment chattingScreenFragment;
    private ShopScreenFragment shopScreenFragment;
    private SettingsContentFragment settingsContentFragment;

    private Button needmoreheart_btn;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainScreenFragment = new MainScreenFragment();
        this.friendsListScreenFragment = new FriendsListScreenFragment();
        this.chattingScreenFragment = new ChattingScreenFragment();
        this.shopScreenFragment = new ShopScreenFragment();
        this.settingsContentFragment = new SettingsContentFragment();

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        this.needmoreheart_btn = view.findViewById(R.id.needmoreheartBTN);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.screenfragmentContainer, this.mainScreenFragment).commit();
        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
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
//        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                setFragment(3);
            }
        });
    }

    public void setFragment(int n) {
        this.fragmentManager = this.getActivity().getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        switch (n){
            case 0:
                this.mainScreenFragment = new MainScreenFragment();
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.mainScreenFragment);
                break;
            case 1:
                this.friendsListScreenFragment = new FriendsListScreenFragment();
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.friendsListScreenFragment);
                break;
            case 2:
                this.chattingScreenFragment = new ChattingScreenFragment();
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.chattingScreenFragment);
                break;
            case 3:
                this.shopScreenFragment = new ShopScreenFragment();
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.shopScreenFragment);
                break;
            case 4:
                this.settingsContentFragment = new SettingsContentFragment();
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.settingsContentFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}