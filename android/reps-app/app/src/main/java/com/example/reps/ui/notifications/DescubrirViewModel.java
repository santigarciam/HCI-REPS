package com.example.reps.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DescubrirViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DescubrirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Descubrir");
    }

    public LiveData<String> getText() {
        return mText;
    }
}