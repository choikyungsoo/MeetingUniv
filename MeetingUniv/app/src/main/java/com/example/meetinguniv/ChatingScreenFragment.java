package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class ChatingScreenFragment extends Fragment {

    private View view;
    private TabLayout tabs;
    private MatchChatingContentsFragment fragment1;
    private PersonalChatingContentsFragment fragment2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chating_screen, container, false);
        fragment1 = new MatchChatingContentsFragment();
        fragment2 = new PersonalChatingContentsFragment();

        tabs = view.findViewById(R.id.tabs2);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment1).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0)
                    selected = fragment1;
                else if (position == 1)
                    selected = fragment2;
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
}