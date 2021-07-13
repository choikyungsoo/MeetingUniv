package com.example.meetinguniv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    private MainScreenFragment mainScreenFragment;
    private ShopScreenFragment shopScreenFragment;
    private SettingsScreenFragment settingsScreenFragment;

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
        this.shopScreenFragment = new ShopScreenFragment();
        this.settingsScreenFragment = new SettingsScreenFragment();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.main:
                        setFragment(0);
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
            case 3:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.shopScreenFragment);
                break;
            case 4:
                fragmentTransaction.replace(R.id.screenfragmentContainer, this.settingsScreenFragment);
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}