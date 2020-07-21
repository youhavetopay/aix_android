package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.icu.number.IntegerWidth;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView header_name, header_number, total_score;

    private ArrayList<String> student_info = new ArrayList<String>();

    PHP_request_score php_request_score;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        student_info = (ArrayList<String>)intent.getSerializableExtra("student_info");


        php_request_score = new PHP_request_score(student_info.get(1),"12345");
        try {
            result = php_request_score.execute("http://zkwpdlxm.dothome.co.kr/total_score.php").get();
        }
        catch (Exception e){
            System.out.println("가져오기 Err");
            e.printStackTrace();
        }

        int abc = php_request_score.get_pashing_data();

        System.out.println("pashing result: "+abc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        /**
         * 사용자 정보를 출력하는 header 부분에 있기 때문에 이런식으로 해야 함
         * **/
        View nav_header_view = navigationView.getHeaderView(0);
        header_name = (TextView)nav_header_view.findViewById(R.id.header_name);
        header_number = (TextView)nav_header_view.findViewById(R.id.header_number);
        total_score = (TextView)nav_header_view.findViewById(R.id.total_score);
        header_name.setText(student_info.get(2));
        header_number.setText(student_info.get(1));
        total_score.setText("획득점수: "+abc);


        // 새로운 페이지 설정할 땐 여기에 id추가
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_new, R.id.nav_new2,R.id.db_connect)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    /**
     * 획득한 총 점수를 들고오기 위한 클래스
     * PHPRequset 클래스를 상속받아서 재정의 함
     * **/

    class PHP_request_score extends PHPRequset{

        private static final String TAG_RESULT = "SUM(STEMP_SCORE)";

        private int get_total_score;


        public PHP_request_score(String id, String pw) {
            super(id, pw);
        }

        public int get_pashing_data(){
            return get_total_score;
        }

        /**
         * php에서 가져오는 값들이 달라 pashing 함수를 재정의 해서 사용
         * **/
        @Override
        public void pashing_json(String get_sb) {
            try {
                JSONObject jsonObject = new JSONObject(get_sb);
                get_total_score = jsonObject.getInt(TAG_RESULT);
                System.out.println(get_total_score);

            }
            catch (Exception e){
                System.out.println("pashing err");
                e.printStackTrace();
            }
        }
    }
}