package com.example.assignment_4receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Context mContext;
    public MyBroadcastReceiver(){
    }
    public MyBroadcastReceiver(Context context){
        mContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("onReceive",intent.getStringExtra("key"));
        Toast.makeText(mContext,intent.getStringExtra("key"), Toast.LENGTH_SHORT).show();
    }


}
