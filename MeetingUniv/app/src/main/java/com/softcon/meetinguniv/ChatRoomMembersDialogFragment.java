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
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatRoomMembersDialogFragment extends Fragment {
    private Context context;
    private Dialog dlg;
    private LinearLayout chatRoomInvite_BTN;
    private View view;

    public ChatRoomMembersDialogFragment(Context context) {
        this.context = context;
    }

    public void showThisChatRoomMembers(TextView numOfChatRoomMembers, ChatRoomScreenFragmentPopupVer.ClickHandler clickHandler) {
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.fragment_chat_room_members_dialog);
        dlg.show();

        this.chatRoomInvite_BTN = dlg.findViewById(R.id.chatRoomInvite_BTN);
        this.chatRoomInvite_BTN.setOnClickListener(clickHandler);
    }

}