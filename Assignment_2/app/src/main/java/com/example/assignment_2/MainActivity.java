package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;

import static android.view.Gravity.*;

public class MainActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;
    TextView titleText;
    String title;
    List<String> questions = new ArrayList<String>();
    List<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
    List<String> type = new ArrayList<String>();
    ArrayList<Set<Integer>> markedOptions = new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        read_json();
        titleText = findViewById(R.id.titleText);
        titleText.setText(title);
        addQuestionToRecycleView();
    }

    public void read_json(){
        String json;
        try {
            InputStream is = getAssets().open("read.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");

            if(json=="" || json==null){
                return ;
            }
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray jsonArray =  (JSONArray)obj;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            JSONObject o = iterator.next();
            if(o==null){
                return ;
            }
            if(!(o.containsKey("title") && o.containsKey("questions"))) {
                return;
            }
            title = (String)o.get("title");
            JSONArray ques = (JSONArray)o.get("questions");
            iterator = ques.iterator();
            while(iterator.hasNext()){
                JSONObject temp = iterator.next();
                questions.add((String)temp.get("question"));
                type.add((String)temp.get("type"));
                markedOptions.add(new HashSet<Integer>());
                ArrayList<String> temp_list = new ArrayList<String>();
                JSONArray type_list = (JSONArray) temp.get("options");
                Iterator<String> iterator2 = type_list.iterator();
                while(iterator2.hasNext()){
                    Object o1 = iterator2.next();
                    temp_list.add((String)o1);

                }
                options.add(temp_list);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void addQuestionToRecycleView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,questions,options,type,markedOptions);
        recyclerView.setAdapter(adapter);
    }

}
