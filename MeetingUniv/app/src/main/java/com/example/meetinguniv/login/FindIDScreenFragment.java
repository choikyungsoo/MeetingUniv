package com.example.meetinguniv.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


public class FindIDScreenFragment extends Fragment implements View.OnClickListener{
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
        this.phoneNum = view.findViewById(R.id.PhoneNum);
        this.verifykey = view.findViewById(R.id.Verifykey);
        this.verifyBTN = view.findViewById(R.id.VerifyBTN);
        this.VcheckBTN = view.findViewById(R.id.VcheckBTN);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseAuthSettings  = firebaseAuth.getFirebaseAuthSettings();

//        this.phoneNumber = String.valueOf(this.phoneNum);
//        this.smsCode = String.valueOf(this.verifykey);

        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);

        this.verifyBTN.setOnClickListener(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_findingid_screen, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.VerifyBTN:
               verfiyphone();
               break;
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