package com.example.myapplication.ui.Db_connect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Db_connect_ViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;

    public Db_connect_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is 앚즈 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}