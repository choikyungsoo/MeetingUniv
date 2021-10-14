package com.softcon.meetinguniv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcon.meetinguniv.R;

public class ChatRoomContentFragmentVersion2 extends Fragment {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_content_version2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setChatRoomTopBar();
        setChatRoomScreen();
    }

    public void setChatRoomScreen() {
        this.fragmentManager = this.getActivity().getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();

        ChatRoomScreenPopup2Fragment chatRoomScreenPopup2Fragment = new ChatRoomScreenPopup2Fragment();
        fragmentTransaction.replace(R.id.chatRoomScreenPopFragmentContainer, chatRoomScreenPopup2Fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setChatRoomTopBar() {
        this.fragmentManager = this.getActivity().getSupportFragmentManager();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();

        ChatRoomScreenPopup2Fragment chatRoomScreenPopup2Fragment = new ChatRoomScreenPopup2Fragment();
        fragmentTransaction.replace(R.id.chatRoomScreenPopFragmentContainer, chatRoomScreenPopup2Fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}