package com.softcon.meetinguniv;

import android.app.Dialog;
import android.content.Context;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.softcon.meetinguniv.main.chooseteamElementFragment;

public class AddTeamOkDialog {
    private Context context;
    private EditText teamNameInput;
    private Button addTeamOk_okBTN, addTeamOk_cancelBTN;

    public AddTeamOkDialog(Context context) {
        this.context = context;
    }

    public void showDialog(chooseteamElementFragment.ClickHandler clickHandler) {
        Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_add_team_ok);
        dlg.show();

        this.teamNameInput = dlg.findViewById(R.id.teamNameInput);
        this.addTeamOk_okBTN = dlg.findViewById(R.id.addTeamOk_okBTN);
        this.addTeamOk_cancelBTN = dlg.findViewById(R.id.addTeamOk_cancelBTN);

        this.addTeamOk_okBTN.setOnClickListener(clickHandler);
        this.addTeamOk_cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

}