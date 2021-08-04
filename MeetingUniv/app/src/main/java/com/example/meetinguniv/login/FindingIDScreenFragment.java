package com.example.meetinguniv.login;

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
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class FindingIDScreenFragment extends Fragment implements View.OnClickListener{
    private EditText name;
    private EditText phoneNum;
    private EditText verifykey;
    private Button verifyBTN;
    private Button VcheckBTN;

    private String phoneNumber;
    private String verifycode;

    //파이어베이스
    private static final String TAG = "PhoneAuthActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuthSettings firebaseAuthSettings;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.name = view.findViewById(R.id.Nameinput);
        this.phoneNum = view.findViewById(R.id.PhoneNum);
        this.verifykey = view.findViewById(R.id.PW_Verifykey);
        this.verifyBTN = view.findViewById(R.id.VerifyBTN);
        this.VcheckBTN = view.findViewById(R.id.ID_checkBTN);


        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseAuthSettings  = this.mAuth.getFirebaseAuthSettings();

        this.phoneNumber = String.valueOf(this.phoneNum);
        this.verifycode = String.valueOf(this.verifykey);

        this.verifyBTN.setOnClickListener(this);
        this.VcheckBTN.setOnClickListener(this);

        phoneVerfiyfunction();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_findingid_screen, container, false);
    }



    private void moveToshowID(View v) {
        if(this.name.getText().toString().equals("")){
            Toast.makeText(getContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            Navigation.findNavController(v).navigate(R.id.action_findIDScreenFragment_to_showIDScreenFragment);
        }
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

    private void phoneVerfiyfunction() {
        this.mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
                Toast.makeText(getContext(), "인증실패", Toast.LENGTH_SHORT).show();
                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this.getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this.getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
    }

    private void verfiyphone() {
        startPhoneNumberVerification(this.phoneNumber);
//        verifyPhoneNumberWithCode(this.mVerificationId, this.verifycode);
        //방법1
//        this.mAuth = FirebaseAuth.getInstance();
//        startPhoneNumberVerification(this.phoneNumber);
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                // This callback will be invoked in two situations:
//                // 1 - Instant verification. In some cases the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without
//                //     user action.
//                Log.d(TAG, "onVerificationCompleted:" + credential);
//
//                signInWithPhoneAuthCredential(credential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                // This callback is invoked in an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                Log.w(TAG, "onVerificationFailed", e);
//
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                }
//
//                // Show a message and update the UI
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:" + verificationId);
//
//                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;
//
//            }
//        };
//        verifyPhoneNumberWithCode(this.mVerificationId, this.verifycode);
//    }
//    private void startPhoneNumberVerification(String phoneNumber) {
//        // [START start_phone_auth]
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this.getActivity())                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//        // [END start_phone_auth]
//    }
//
//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        // [START verify_with_code]
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        // [END verify_with_code]
//    }
//
//    // [START resend_verification]
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this.getActivity())                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
        //방법2
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//
//    }
//        this.firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(this.phoneNumber, this.verifycode);
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                .setPhoneNumber(this.phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(this.getActivity())
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        // Instant verification is applied and a credential is directly returned.
//                        // ...
//                        Log.d("test", "onVerificationCompleted:" + credential);
//                        Toast.makeText(getContext(),"인증코드가 전송되었습니다.", Toast.LENGTH_SHORT).show();
//                        signInWithPhoneAuthCredential(credential);
//                    }
//
//                    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
//                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                            // Invalid request
//                        } else if (e instanceof FirebaseTooManyRequestsException) {
//                            // The SMS quota for the project has been exceeded
//                        }
//
//                        Toast.makeText(getContext(),"인증실패", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onCodeSent(@NonNull String verificationId,
//                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                        mVerificationId = verificationId;
//                        mResendToken = token;
//
//                    }
//
//                    // ...
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}