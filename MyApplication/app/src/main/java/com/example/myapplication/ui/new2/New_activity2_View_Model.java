package com.example.myapplication.ui.new2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class New_activity2_View_Model extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public New_activity2_View_Model() {
        mText = new MutableLiveData<>();
        mText.setValue("This is IZ*ONE fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}