package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.softcon.meetinguniv.main.PersonalProfileScreenFragment;

public class JoinSelectUnivDialogFragment extends Fragment {
    private Context context;
    private Dialog dlg;

    public JoinSelectUnivDialogFragment(Context context) {
        this.context = context;
    }

    public void showJoinSelectUnivDialog() {
        this.dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.fragment_join_select_univ_dialog);
        dlg.show();

    }
}