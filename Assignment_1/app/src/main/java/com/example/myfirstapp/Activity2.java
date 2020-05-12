package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    public static String result_key = "result";
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);
    }

    public void calculateSum(View view){
        Intent intent = getIntent();
        EditText firstNumber = (EditText) findViewById(R.id.first_number);
        EditText secondNumber = (EditText) findViewById(R.id.second_number);
        int firstNum=0,secondNum = 0;
        if(firstNumber != null){
            firstNum = Integer.parseInt(firstNumber.getText().toString());
        }
        if(secondNumber != null){
            secondNum = Integer.parseInt(secondNumber.getText().toString());
        }
        intent.putExtra(this.result_key,firstNum+secondNum);
        setResult(Activity1.RESULT_OK,intent);
        finish();
    }

}
