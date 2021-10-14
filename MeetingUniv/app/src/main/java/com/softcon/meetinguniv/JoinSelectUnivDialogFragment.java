package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.softcon.meetinguniv.main.PersonalProfileScreenFragment;

public class JoinSelectUnivDialogFragment extends Dialog {
    private View view;
    private Context context;
    private Dialog dlg;

    public JoinSelectUnivDialogFragment(@NonNull Context context) {
        super(context);
    }

//    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_join_select_univ_dialog, container, false);
        return view;
    }

//    public void showJoinSelectUnivDialog() {
//        this.dlg = new Dialog(context);
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dlg.setContentView(R.layout.fragment_join_select_univ_dialog);
//        dlg.show();
//
//    }
}