package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity1 extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_layout);
    }

    public void launch_act2(View view){
        Intent intent = new Intent(this, Activity2.class);
        startActivityForResult(intent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView resultText = (TextView) findViewById(R.id.resultText);
        if(requestCode == 0 && resultCode == Activity1.RESULT_OK){
            if((resultText != null) && (data != null)) {
                resultText.setText("Result : "+Integer.toString(data.getIntExtra(Activity2.result_key,0)));
            }
            else{
                resultText.setText("Result : ");
            }
        }
        else{
            resultText.setText("Result : ");
        }
    }

}
