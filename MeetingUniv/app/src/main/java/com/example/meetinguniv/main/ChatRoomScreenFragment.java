package com.example.meetinguniv.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.meetinguniv.R;

public class ChatRoomScreenFragment extends Fragment {
    boolean openMenu = false;

    Animation translateLeftAnim;
    LinearLayout basePage;
    LinearLayout menuPage;
    ImageView menubtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatRoomScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChattingScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatRoomScreenFragment newInstance(String param1, String param2) {
        ChatRoomScreenFragment fragment = new ChatRoomScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        basePage = view.findViewById(R.id.basePage);
        menuPage = view.findViewById(R.id.menuPage);

        menubtn = view.findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPage.setVisibility(View.VISIBLE);
                openMenu = true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMenu) {
                    menuPage.setVisibility(View.GONE);
                }
            }
        });
    }
}