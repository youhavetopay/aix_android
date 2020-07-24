package com.example.myapplication.ui.new1;

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

public class NewActivityFragment extends Fragment {

    private New_activity1_View_model mViewModel;

    private View root;

    private String str_id;
    private TextView textView;

    public static NewActivityFragment newInstance() {
        return new NewActivityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_new_activity, container, false);


        // String 으로 id 지정해서 R.id 없이 사용하기
        str_id = "C001";
        int resID = getResources().getIdentifier(str_id, "id",getActivity().getPackageName());
        textView = root.findViewById(resID);
        textView.setText("아이즈원!!!");


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(New_activity1_View_model.class);
        // TODO: Use the ViewModel
    }

}