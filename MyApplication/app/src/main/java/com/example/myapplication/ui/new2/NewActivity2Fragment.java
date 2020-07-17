package com.example.myapplication.ui.new2;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class NewActivity2Fragment extends Fragment {

    private New_activity2_View_Model mViewModel;

    public static NewActivity2Fragment newInstance() {
        return new NewActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        View root = inflater.inflate(R.layout.fragment_new_activity2, container, false);
        final TextView text_new2 = root.findViewById(R.id.text_new2);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_new2.setText("This is IZ*ONE fragment");
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        // TODO: Use the ViewModel


    }

}