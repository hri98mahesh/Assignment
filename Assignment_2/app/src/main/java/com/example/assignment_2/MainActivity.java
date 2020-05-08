package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        read_json_and_set_view();

    }

    public void read_json_and_set_view(){
        String json;
        String title;
        List<String> questions = new ArrayList<String>();
        List<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
        List<String> type = new ArrayList<String>();
        try {
            InputStream is = getAssets().open("read.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray jsonArray =  (JSONArray)obj;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            JSONObject o = iterator.next();
            title = (String)o.get("title");
            JSONArray ques = (JSONArray)o.get("questions");
            iterator = ques.iterator();
            while(iterator.hasNext()){
                JSONObject temp = iterator.next();
                questions.add((String)temp.get("question"));
                type.add((String)temp.get("type"));
                ArrayList<String> temp_list = new ArrayList<String>();
                JSONArray type_list = (JSONArray) temp.get("options");
                Iterator<String> iterator2 = type_list.iterator();
                while(iterator2.hasNext()){
                    Object o1 = iterator2.next();
                    temp_list.add((String)o1);

                }
                options.add(temp_list);
            }
            LinearLayout myLinearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
            final TextView titleView = new TextView(this);
            titleView.setText(title);
            titleView.setTextSize(30);
            titleView.setGravity(Gravity.CENTER);
            myLinearLayout.addView(titleView);
            RadioGroup rg;
            RadioButton rb;
            for(int i=0;i<questions.size();i++){
                final TextView rowTextView = new TextView(this);
                rg = new RadioGroup(this);
                for(int j=0;j<options.get(i).size();j++){
                    rb = new RadioButton(this);
                    rb.setText(options.get(i).get(j));
                    rb.setTextSize(15);
                    rg.addView(rb);
                }
                rg.setOrientation(RadioGroup.VERTICAL);
                rowTextView.setText(questions.get(i));
                rowTextView.setTextSize(20);
                rowTextView.setPadding(0,100,0,0);
                myLinearLayout.addView(rowTextView);
                myLinearLayout.addView(rg);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
