package com.example.myapplication.ui.new2;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.FileUpload;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewActivity2Fragment extends Fragment {

    private New_activity2_View_Model mViewModel;
    private ImageView upload_img;
    private Button push_img_btn,send_img_btn;
    private File temp_select_file;

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
        push_img_btn = root.findViewById(R.id.push_img);
        send_img_btn = root.findViewById(R.id.send_img);
        upload_img = root.findViewById(R.id.upload_img);
        send_img_btn.setEnabled(false);

        send_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUpload.send2Server(temp_select_file);
            }
        });

        push_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,200);
            }
        });
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_new2.setText("This is IZ*ONE fragment");
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedImageUri = data.getData();
            upload_img.setImageURI(selectedImageUri);
        }
        Uri dataUri = data.getData();
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(dataUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            upload_img.setImageBitmap(bitmap);
            inputStream.close();

            String img_date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
            temp_select_file = new File(Environment.getExternalStorageDirectory()+"Pictures/Test/", "temp_"+img_date+".jpg");

            System.out.println("awawd     "+temp_select_file);


            OutputStream outputStream = new FileOutputStream(temp_select_file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("임시저장 성공");
        send_img_btn.setEnabled(true);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        // TODO: Use the ViewModel


    }

    private void tedPermission(){
        PermissionListener
    }

}