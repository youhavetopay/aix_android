package com.example.myapplication.ui.new2;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

import static android.app.Activity.RESULT_OK;

public class NewActivity2Fragment extends Fragment {

    private static final int MESSAGE_PERMISSION_GRANTED = 101;
    private static final int MESSAGE_PERMISSION_DENIED = 102;

    private New_activity2_View_Model mViewModel;
    private ImageView upload_img;
    private Button push_img_btn, send_img_btn;

    private File temp_select_file;
    private Uri selectedImageUri;
    private View root;

    private String imagePath;


    public static NewActivity2Fragment newInstance() {
        return new NewActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
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

                int permissionCheak = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permissionCheak == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MESSAGE_PERMISSION_GRANTED);
                } else {
                    new ImageUpload().execute("http://zkwpdlxm.dothome.co.kr/image_upload2.php", imagePath);

                }
            }
        });

        push_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MESSAGE_PERMISSION_GRANTED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new ImageUpload().execute("http://zkwpdlxm.dothome.co.kr/image_upload2.php", imagePath);
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            System.out.println("img uri  " + data.getData());

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                System.out.println("cursor not null ");
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                System.out.println("이미지 경로  " + imagePath);
                cursor.moveToFirst();

                String now_time = new SimpleDateFormat("yyyyMMddHMsS").format(new Date());

                temp_select_file = new File(imagePath);
                if(temp_select_file.renameTo(new File(imagePath, now_time))){
                    System.out.println("변경 성공");
                }
                else {
                    System.out.println("변경 실패");
                }

                Picasso.with(getActivity()).load(new File(imagePath)).into(upload_img);
                upload_img.setImageURI(selectedImageUri);
                cursor.close();



                send_img_btn.setEnabled(true);



            }

        } else {
            Toast.makeText(getContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
            return;
        }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(New_activity2_View_Model.class);
        // TODO: Use the ViewModel


    }


}