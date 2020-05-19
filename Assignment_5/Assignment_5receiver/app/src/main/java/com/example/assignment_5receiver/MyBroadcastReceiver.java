package com.example.assignment_5receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Context mContext;
    Context actContext;
    public static List<String> broadcast = new ArrayList<String>();
    public String preference_file_key = "shared_file";
    public String total_broadcast = "Total_Number_of_Broadcast";
    public String message_key = "Message_key_to_find_values";
    public String intentAction = "com.pkg.perform.MultipleBroadcast";

    public MyBroadcastReceiver(){
    }

    public MyBroadcastReceiver(Context context){
        mContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intentAction.equals(intent.getAction())){
            Log.v("onReceive",intent.getStringExtra("key"));
            String message = intent.getStringExtra("key");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Set<String> broadcastMessages = sharedPref.getStringSet(message_key, new HashSet<String>());
            broadcastMessages.add(message);
            editor.putStringSet(message_key, broadcastMessages);
            editor.apply();
        }
    }
}

