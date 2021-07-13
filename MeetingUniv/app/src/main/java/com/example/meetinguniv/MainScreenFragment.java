package com.example.meetinguniv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class MainScreenFragment extends Fragment {

    private View view;
    private TabLayout tabs;
    private MatchingContentsFragment fragment1;
    private CurrentContentsFragment fragment2;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        fragment1 = new MatchingContentsFragment();
        fragment2 = new CurrentContentsFragment();

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
//        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        tabs = view.findViewById(R.id.tabs);
//
//        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();
////        this.fragmentManager = this.getActivity().getSupportFragmentManager();
////        this.fragmentTransaction = this.fragmentManager.beginTransaction();
////        fragmentTransaction.addToBackStack(null);
//
//
//        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                int position = tab.getPosition();
//                Fragment selected = null;
//                if (position == 0)
//                    selected = fragment1;
////                    fragmentTransaction.replace(R.id.container, fragment1);
//                else if (position == 1)
//                    selected = fragment2;
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).addToBackStack(null).commit();
////                    fragmentTransaction.replace(R.id.container, fragment2);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//        });
//    }
}