package com.softcon.meetinguniv.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcon.meetinguniv.R;
import com.google.android.material.tabs.TabLayout;

public class MainScreenFragment extends Fragment {

    private View view;
    private TabLayout tabs;
    private MatchingContentFragment fragment1;
    private CurrentContentsFragment fragment2;

    private String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        this.fragment1 = new MatchingContentFragment();
        this.fragment2 = new CurrentContentsFragment();

        tabs = view.findViewById(R.id.tabs2);

//        this.userID = getArguments().getLong("userID");
//        Log.d("MainScreenFragment - 회원아이디", String.valueOf(this.userID));

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment1).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Bundle bundle = new Bundle();
                Fragment selected = null;
                if (position == 0) {
                    //////////////////////////////////
                    fragment1 = new MatchingContentFragment();
                    userID = getArguments().getString("userID");
                    bundle.putString("userID", userID);
                    fragment1.setArguments(bundle);
                    //////////////////////////////////
                    selected = fragment1;
                }
                else if (position == 1) {
                    //////////////////////////////////
                    fragment2 = new CurrentContentsFragment();
                    //////////////////////////////////
                    selected = fragment2;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container2, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        this.userID = getArguments().getString("userID");
        bundle.putString("userID", this.userID);
        this.fragment1.setArguments(bundle);
//        Log.d("MainScreenFragment - 회원아이디", this.userID);

    }
}