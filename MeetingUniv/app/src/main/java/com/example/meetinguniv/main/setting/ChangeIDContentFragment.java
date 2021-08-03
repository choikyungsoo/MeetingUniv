package com.example.meetinguniv.main.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meetinguniv.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ChangeIDContentFragment extends Fragment implements View.OnClickListener{

    private EditText name;
    private EditText phoneNum;
    private EditText verifykey;
    private Button verifyBTN;
    private Button VcheckBTN;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuthSettings firebaseAuthSettings;

    private String phoneNumber = "+11074051954";
    private String smsCode = "195419";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.name = view.findViewById(R.id.Nameinput);
        this.phoneNum = view.findViewById(R.id.PhoneNum);
        this.verifykey = view.findViewById(R.id.PW_Verifykey);
        this.verifyBTN = view.findViewById(R.id.VerifyBTN);
        this.VcheckBTN = view.findViewById(R.id.ID_checkBTN);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseAuthSettings  = firebaseAuth.getFirebaseAuthSettings();

//        this.phoneNumber = String.valueOf(this.phoneNum);
//        this.smsCode = String.valueOf(this.verifykey);

        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

        this.verifyBTN.setOnClickListener(this);
        this.VcheckBTN.setOnClickListener(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_i_d_content, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.VerifyBTN:
                verfiyphone();
                break;
            case R.id.ID_checkBTN:
                moveToshowID(v);
                break;
        }
    }

    private void moveToshowID(View v) {
        if(this.name.getText().toString().equals("")){
            Toast.makeText(getContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            Navigation.findNavController(v).navigate(R.id.action_changeIDContentFragment_to_changeIDNewContentFragment);
        }
    }

    private void verfiyphone() {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(String.valueOf(this.phoneNum))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Instant verification is applied and a credential is directly returned.
                        // ...
                        Log.d("test", "onVerificationCompleted:" + credential);

                        signInWithPhoneAuthCredential(credential);
                    }

                    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        Toast.makeText(getContext(),"인증실패", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}