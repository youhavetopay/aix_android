package com.example.myapplication.ui.Db_connect;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DbConnectFragment extends Fragment {

    private Db_connect_ViewModel mViewModel;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";

    String myJSON;

    JSONArray peoples =null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    String test;

    public static DbConnectFragment newInstance() {
        return new DbConnectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(Db_connect_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bb_connect, container, false);
        final TextView izone_text = root.findViewById(R.id.izone_text);
        list = root.findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://zkwpdlxm.dothome.co.kr/PHP_connection.php");
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               
            }
        });




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Db_connect_ViewModel.class);
        // TODO: Use the ViewModel
    }

    protected void showList(){
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            peoples = jsonObject.optJSONArray(TAG_RESULTS);

            for (int i=0;i<peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String age = c.getString(TAG_AGE);

                HashMap<String, String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_AGE,age);

                System.out.println(c.getString(TAG_NAME) + i);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_AGE},
                    new int[]{R.id.id,R.id.name, R.id.age}

            );

            list.setAdapter(adapter);


        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void getData(final String url){
        class GetDataJSON extends AsyncTask<String,Void,String > {

            @Override
            protected String doInBackground(String... params) {

                System.out.println(params[0]);

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine())!=null){
                        sb.append(json +"\n");
                    }

                    System.out.println(sb.toString().trim());

                    return sb.toString().trim();

                }
                catch (Exception e){
                    System.out.println(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null){
                    System.out.println("공백");
                }
                else{
                    myJSON = result;
                    System.out.println(myJSON);
                    showList();

                }
            }
        }
        System.out.println(url);
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


}