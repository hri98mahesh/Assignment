package com.example.assignment_4sender;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendBroadcastOnClick( View view){
        EditText editText;
        editText = (EditText) findViewById(R.id.broadcastMessage);
        final Intent intent=new Intent();
        intent.setAction("com.pkg.perform.Action");
        intent.putExtra("key",editText.getText().toString());
        editText.setText("");
        sendBroadcast(intent);
    }


}
