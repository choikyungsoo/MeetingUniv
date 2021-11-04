package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ChatRoomMembersDialogFragment extends Fragment {
    private Context context;
    private Dialog dlg;
    private Button chatRoomInvite_BTN;
    private InviteFriendElementFragment inviteFriendElementFragment;

    public ChatRoomMembersDialogFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.chatRoomInvite_BTN = view.findViewById(R.id.chatRoomInvite_BTN);
    }

    public void showThisChatRoomMembers(TextView numOfChatRoomMembers) {
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.fragment_chat_room_members_dialog);
        dlg.show();

        this.chatRoomInvite_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.translate_up,R.anim.translate_up)
                        .replace(R.id.Framecontainer, inviteFriendElementFragment)
                        .commit();
            }
        });
    }

}