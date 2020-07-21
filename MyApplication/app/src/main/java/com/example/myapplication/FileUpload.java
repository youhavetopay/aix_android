package com.example.myapplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUpload {
    public static void send2Server(File file){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files",file.getName(), RequestBody.create(MultipartBody.FORM, file)).build();

        Request request = new Request.Builder().url("http://zkwpdlxm.dothome.co.kr/image_upload.php").post(requestBody).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                System.out.println("test :"+ response.body().string());

            }
        });
    }
}
