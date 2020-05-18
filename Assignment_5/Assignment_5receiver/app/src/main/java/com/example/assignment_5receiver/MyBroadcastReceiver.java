package com.example.assignment_5receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Context mContext;
    Context actContext;
    public static List<String> broadcast = new ArrayList<String>();
    public String preference_file_key = "shared_file";
    public String total_broadcast = "Total_Number_of_Broadcast";
    public String message_key = "Message_key_to_find_values";
    public MyBroadcastReceiver(){
    }
    public void updateSharedFile(){
        SharedPreferences sharedPref;
        sharedPref = actContext.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(total_broadcast,broadcast.size());
        int i=0;
        while(i<broadcast.size()){
            editor.putString(message_key+"/"+Integer.toString(i),broadcast.get(i));
            i= i+1;
        }
        editor.commit();
    }
    public MyBroadcastReceiver(Context context,Context context1){
        mContext = context;
        actContext = context1;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        broadcast.add(intent.getStringExtra("key"));
//        Toast.makeText(context,intent.getStringExtra("key"), Toast.LENGTH_SHORT).show();
    }
}

