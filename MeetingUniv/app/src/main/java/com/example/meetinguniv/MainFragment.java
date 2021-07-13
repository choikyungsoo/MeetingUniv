package com.example.meetinguniv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    private MainScreenFragment mainScreenFragment;
    private ChatingScreenFragment chatingScreenFragment;
    private ShopScreenFragment shopScreenFragment;
    private SettingsContentFragment settingsContentFragment;

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
        this.chatingScreenFragment = new ChatingScreenFragment();
        this.shopScreenFragment = new ShopScreenFragment();
        this.settingsContentFragment = new SettingsContentFragment();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.screenfragmentContainer, this.mainScreenFragment).commit();
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.main:
                        setFragment(0);
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
    }

    public void setFragment(int n) {
        this.fragmentManager = this.getActivity().getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        switch (n){
            case 0:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.mainScreenFragment);
                break;
            case 2:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.chatingScreenFragment);
                break;
            case 3:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.shopScreenFragment);
                break;
            case 4:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.settingsContentFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}