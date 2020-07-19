package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPRequset extends AsyncTask<String , Void, String>{

    private static final String TAG_RESPONSE ="response";
    private static final String TAG_USWER_CODE = "USWER_CODE";
    private static final String TAG_USER_NAME = "USER_NAME";

    private String id, pw;

    JSONObject jsonObject;

    private String[] student_information;
    private ArrayList<String> pashing_result = new ArrayList<String>();



    // 외부에서 사용자 정보 들고오는 함수
    public ArrayList<String> get_pashing_phprequest(){

        return pashing_result;

    }

    /**
     * 사용자 정보 들고 올때 json형태 ArrayList에 String 형태로 담아두는 함수
     *
     * 다른 정보 들고 올땐 클래스 상속해서 재정의 하면 될듯???
     * **/
    public void pashing_json(String get_sb){

        try {
            jsonObject =new JSONObject(get_sb);
            pashing_result.add(jsonObject.getString(TAG_USWER_CODE));
            pashing_result.add(jsonObject.getString(TAG_USER_NAME));

            for(String i : pashing_result){
                System.out.println(i);
            }


        }
        catch (Exception e){
            System.out.println("pashing err :"+e);
            e.printStackTrace();

        }

    }



    public PHPRequset(String id, String pw){
        this.id = id;
        this.pw = pw;
    }



    /**
     * 왜 그런지는 모르겠는데 안드로이드에서 네트워크 통신? 할려면 AsyncTask라는 백그라운드 쓰레드? 만들어서 진행해야함
     * **/
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
            Log.d("HTTP CODE", "POST response code - " + responseStatusCode);

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


            // 이부분에서 자꾸 오류남 : 예기치 않은 입력스트림의 끝?
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

            pashing_json(sb.toString().trim());

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
