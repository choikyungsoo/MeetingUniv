package com.example.meetinguniv.main.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetinguniv.R;

public class ChangePersonalNameDialog {
    private Context context;
    private EditText changePersonalNameInput;
    private Button changePersonalName_okBTN;
    private Button changePersonalName_cancelBTN;

    public ChangePersonalNameDialog(Context context) {
        this.context = context;
    }

    public void changeNameFunction(TextView changePersonalName) {
        Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.dialog_change_personal_name);
        dlg.show();

        this.changePersonalNameInput = dlg.findViewById(R.id.changePersonalNameInput);
        this.changePersonalName_okBTN = dlg.findViewById(R.id.changePersonalName_okBTN);
        this.changePersonalName_cancelBTN = dlg.findViewById(R.id.changePersonalName_cancelBTN);

        this.changePersonalName_okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePersonalName.setText(changePersonalNameInput.getText().toString());
                dlg.dismiss();
            }
        });
        this.changePersonalName_cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
