package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class ChatRoomMembersDialogFragment extends Fragment {
    private Context context;
    private Dialog dlg;

    public ChatRoomMembersDialogFragment(Context context) { this.context = context; }

    public void showThisChatRoomMembers(TextView numOfChatRoomMembers) {
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.fragment_chat_room_members_dialog);
        dlg.show();


    }

}