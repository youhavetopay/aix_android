package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPRequset extends AsyncTask<String , Void, String>{

    private String id, pw;



    public PHPRequset(String id, String pw){
        this.id = id;
        this.pw = pw;
    }

    @Override
    protected String doInBackground(String... strings) {


        System.out.println("뭐지?  "+id + pw);

        String serverURL = (String)strings[0];
        String postParameters = "name=" + id + "&pw=" + pw;

        System.out.println(postParameters);


        try {

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();



            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d("aaaaa", "POST response code - " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @NotNull
                        @Override
                        public Response intercept(@NotNull Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Connection","Close").build();
                            return chain.proceed(request);
                        }
                    }).build();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();



            System.out.println("성공!!!");
            System.out.println(sb.toString());

            return sb.toString();


        } catch (Exception e) {

            Log.d("aaaaa", "InsertData: Error ", e);

            return new String("ProtocolException");
        }

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


    }
}
