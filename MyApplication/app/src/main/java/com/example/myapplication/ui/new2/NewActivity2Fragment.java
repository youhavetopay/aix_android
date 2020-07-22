package com.example.myapplication.ui.new2;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ImageUpload;
import com.example.myapplication.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.Permission;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.app.Activity.RESULT_OK;

public class NewActivity2Fragment extends Fragment {

    private static final int MESSAGE_PERMISSION_GRANTED = 101;
    private static final int MESSAGE_PERMISSION_DENIED = 102;

    private New_activity2_View_Model mViewModel;
    private ImageView upload_img;
    private Button push_img_btn,send_img_btn;
    private File temp_select_file;
    private Uri selectedImageUri;
    private View root;

    private String imagePath;





    public static NewActivity2Fragment newInstance() {
        return new NewActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        root = inflater.inflate(R.layout.fragment_new_activity2, container, false);
        final TextView text_new2 = root.findViewById(R.id.text_new2);
        push_img_btn = root.findViewById(R.id.push_img);
        send_img_btn = root.findViewById(R.id.send_img);
        upload_img = root.findViewById(R.id.upload_img);
        send_img_btn.setEnabled(false);

        send_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImageUpload().execute("http://zkwpdlxm.dothome.co.kr/image_upload2.php",imagePath);
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
           selectedImageUri = data.getData();
           upload_img.setImageURI(selectedImageUri);
           System.out.println("img uri  "+ data.getData());

           /**
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null,null,null);

            if(cursor != null){
                System.out.println("cursor not null ");
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                System.out.println("이미지 경로  "+imagePath);
                cursor.moveToFirst();

                Picasso.with(getActivity()).load(new File(imagePath)).into(upload_img);
                cursor.close();

                send_img_btn.setEnabled(true);

            }
            **/

        }
        else{
            Toast.makeText(getContext(), "이미지 가져오기 실패",Toast.LENGTH_SHORT).show();
            return;
        }




    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        // TODO: Use the ViewModel


    }

    private void ShowPermissionDialog(){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getActivity(), "권한 허가", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "권한 거부", Toast.LENGTH_SHORT).show();
            }
        };


    }





}