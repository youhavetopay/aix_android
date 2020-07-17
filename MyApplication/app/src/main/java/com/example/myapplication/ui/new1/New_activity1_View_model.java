package com.example.myapplication.ui.new1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class New_activity1_View_model extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;

    public New_activity1_View_model() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}