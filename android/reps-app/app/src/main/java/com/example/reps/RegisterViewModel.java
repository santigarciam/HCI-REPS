package com.example.reps;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> userName,password,mail;
    private MutableLiveData<Integer> token;

    public RegisterViewModel(){

    }

    public MutableLiveData<Integer> getToken(){
        return token;
    }
    public MutableLiveData<String> getCurrentMail(){
        return mail;
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }
}
