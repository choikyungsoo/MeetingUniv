package com.softcon.meetinguniv.main.setting;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.softcon.meetinguniv.R;

public class ChangePersonalNameDialog {
    private Context context;
    private EditText changePersonalNameInput;
    private Button changePersonalName_okBTN;
    private Button changePersonalName_cancelBTN;
    private String userID;

    private InputMethodManager imm;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference M_databaseReference = database.getReference("회원정보");

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

        this.imm = (InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE);

        //프로필 이름 가져오기
        M_databaseReference.child(String.valueOf(this.userID)).child("닉네임").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                changePersonalNameInput.setText((CharSequence) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.changePersonalName_okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                changePersonalName.setText(changePersonalNameInput.getText().toString());
                changeNickName(changePersonalNameInput.getText().toString());
                imm.hideSoftInputFromWindow(changePersonalName_okBTN.getWindowToken(),0);
                dlg.dismiss();
            }
        });
        this.changePersonalName_cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(changePersonalName_cancelBTN.getWindowToken(),0);
                dlg.dismiss();
            }
        });
    }

    private void changeNickName(String changeNick) {
        M_databaseReference.child(this.userID).child("닉네임").setValue(changeNick);
    }

    public void giveUserID(String userID) {
        this.userID = userID;
    }
}
