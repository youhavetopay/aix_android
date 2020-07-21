package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Login_activity extends AppCompatActivity {

    TextView test_name;
    EditText login_id, login_pw;
    Button btn1;

    ArrayList<String> data = new ArrayList<String>();

    String user_id, user_pw;

    PHPRequset requset;

    String php_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        login_id =(EditText) findViewById(R.id.login_id);
        login_pw =(EditText) findViewById(R.id.login_pw);
        btn1 =(Button) findViewById(R.id.login_btn1);
        test_name =(TextView) findViewById(R.id.test_name);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = login_id.getText().toString();
                user_pw = login_pw.getText().toString();
                if(user_id.equals("") || user_pw.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        System.out.println("보내기2 "+user_id+", "+user_pw);
                        requset = new PHPRequset(user_id,user_pw);
                        php_result = requset.execute("http://zkwpdlxm.dothome.co.kr/test_login.php").get();
                        data = requset.get_pashing_phprequest();

                    }
                    catch (Exception e){
                        System.out.println("Error   "+e);
                        e.printStackTrace();
                    }

                    if(php_result.equals("ProtocolException")){
                        Toast.makeText(getApplication(), "프로토콜 오류",Toast.LENGTH_SHORT).show();
                    }
                    else if(data.get(0).equals("false")){
                        Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                    }
                    else if(data.get(0).equals("true")){
                        Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("student_info",data);

                        startActivity(intent);
                    }

                }

            }
        });
    }
}