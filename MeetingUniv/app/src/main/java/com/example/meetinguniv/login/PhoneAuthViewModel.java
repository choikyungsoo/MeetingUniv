package com.example.meetinguniv.login;

import android.text.method.SingleLineTransformationMethod;

import androidx.lifecycle.MutableLiveData;

public class PhoneAuthViewModel {
    private String phoneAuthNum;
    private Boolean isResendPhoneAuth = false;
    private MutableLiveData<String> etPhoneNum = new MutableLiveData<String>();
    private MutableLiveData<String> etAuthNum = new MutableLiveData<String>();
    private MutableLiveData<Boolean> _requestPhoneAuth = new MutableLiveData<Boolean>();
    private MutableLiveData<Boolean> _requestResendPhoneAuth = new MutableLiveData<Boolean>();
    private SingleLineTransformationMethod _authComplete =  new SingleLineTransformationMethod();

    public MutableLiveData<Boolean> get_requestPhoneAuth() {
        return _requestPhoneAuth;
    }

    public MutableLiveData<Boolean> get_requestResendPhoneAuth() {
        return _requestResendPhoneAuth;
    }

    public SingleLineTransformationMethod get_authComplete() {
        return _authComplete;
    }

    public void requestPhoneAuth(){
        if(!isResendPhoneAuth){
//            get_requestPhoneAuth() = !etAuthNum.getValue().isEmpty();
        }
    }
}
