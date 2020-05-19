package com.example.assignment_5receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver MyReceiver;
    String default_string = "ERROR";
    public String preference_file_key = "shared_file";
    public String total_broadcast = "Total_Number_of_Broadcast";
    public String message_key = "Message_key_to_find_values";
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter("com.pkg.perform.MultipleBroadcast");
        MyReceiver = new MyBroadcastReceiver();
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v("onResume","Integer.toString(size)");
        SharedPreferences sharedPref = this.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        Set<String> broadcastMessage = sharedPref.getStringSet(message_key, null);
        if(broadcastMessage != null){
            Iterator<String> it = broadcastMessage.iterator();
            while(it.hasNext()){
                String message = it.next();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(message_key, new HashSet<String>());
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }

}
