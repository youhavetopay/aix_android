package com.example.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PHPRequset {

    private URL url;

    public PHPRequset(String url) throws MalformedURLException {this.url = new URL(url);}

    private String readStream(InputStream in) throws IOException{
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String line = null;

        while ((line = reader.readLine())!=null){
            jsonHtml.append(line);
        }
        reader.close();
        return jsonHtml.toString();
    }

    public String PhPtest(final String data1, final String data2){
        try {
            String postData = "Data1= "+data1+"&"+"Data2="+data2;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream =conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        }
        catch (Exception e){
            Log.i("PHP요청","실패"+e);
            return null;
        }
    }
}
