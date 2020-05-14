package com.example.assignment_4receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver MyReceiver;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter("com.pkg.perform.Action");
        MyReceiver = new MyBroadcastReceiver(getApplicationContext());
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }

}
