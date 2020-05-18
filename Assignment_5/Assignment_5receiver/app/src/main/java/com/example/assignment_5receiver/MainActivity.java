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

import java.util.List;

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
        MyReceiver = new MyBroadcastReceiver(getApplicationContext(),this);
        if(intentFilter != null)
        {
            registerReceiver(MyReceiver, intentFilter);
        }
    }

    @Override
    protected void onResume(){
        SharedPreferences sharedPref = this.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        int size = sharedPref.getInt(total_broadcast,0);
        int i=0;
        while(i < size){
            String text = sharedPref.getString(message_key+"/"+Integer.toString(i),default_string);
            Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
            i++;
            try{
                Thread.sleep(100);
            }
            catch(Exception e){

            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }

}
