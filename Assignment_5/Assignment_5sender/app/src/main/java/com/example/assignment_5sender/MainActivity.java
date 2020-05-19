package com.example.assignment_5sender;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String receiverPackageName = "com.example.assignment_5receiver";
    public static final String receiverName = "com.example.assignment_5receiver.MyBroadcastReceiver";
    public static final String permission = "com.assignment.PERMISSION_TO_BROADCAST";
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void sendBroadcastOnClick( View view){
        EditText editText;
        editText = (EditText) findViewById(R.id.broadcastMessage);
        final Intent intent=new Intent();
        intent.setAction("com.pkg.perform.MultipleBroadcast");
        intent.putExtra("key",editText.getText().toString());
        intent.setComponent(new ComponentName(receiverPackageName, receiverName));
        editText.setText("");
        Log.v("sendBroadcastOnClick","MessageSent");
        sendBroadcast(intent,permission);
    }
}
