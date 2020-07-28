package com.example.myapplication;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload extends AsyncTask<String, Integer, Boolean> {

    JSONParser jsonParser;

    @Override
    protected Boolean doInBackground(String... strings) {

        try {

            jsonParser = new JSONParser();

            JSONObject jsonObject = jsonParser.uploadImage(strings[0], strings[1]);
            if(jsonObject != null){
                return jsonObject.getString("result").equals("success");
            }
        }
        catch (Exception e){

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (aBoolean){
            System.out.println("파일 업로드 성공");
        }
        else{
            System.out.println("파일 업로드 실패...");
        }
    }

    public class JSONParser {
        public JSONObject uploadImage(String imageUploadUrI, String sourceImageFile){

            try {
                File sourceFile = new File(sourceImageFile);

                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/")+1);

                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("result","photo_image").build();

                Request request = new Request.Builder()
                        .addHeader("Connection","close")
                        .url(imageUploadUrI)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .build();

                Response response = client.newCall(request).execute();
                String res = response.body().string();
                return new JSONObject(res);
            }
            catch (Exception e){

                System.out.println("전송오류");
                e.printStackTrace();
                return null;

            }
        }
    }


}
