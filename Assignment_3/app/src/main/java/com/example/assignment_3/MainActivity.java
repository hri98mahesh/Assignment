package com.example.assignment_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String url = "https://dummyimage.com/3400X3400/000000/0011ff.png&text=Hello";
//    "https://dummyimage.com/3400X3400/000000/0011ff.png&text=Hello";
    public static TextView textView ;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onstartDownload(View view){
        textView = findViewById(R.id.progress);
        Intent intent;
        intent = new Intent(this, DownloadService.class);
        intent.putExtra("url",url);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        startService(intent);
    }

    private class DownloadReceiver extends ResultReceiver{
        public DownloadReceiver(Handler handler) {
            super(handler);
            setContentView(R.layout.activity_main);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            TextView textView_temp = findViewById(R.id.progress);
            if(resultCode == DownloadService.URL_TYPE){
                String message = resultData.getString("DownloadCategory");
                if(message=="INVALID"){
                     textView_temp.setText("Invalid URL");
                }
                if(message=="PROBLEM"){
                    textView_temp.setText("Problem while downloading the file");
                }
                if(message=="ALREADY_EXIST"){
                    textView_temp.setText("File is already Downloaded");
                }
            }
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                if (progress == 100) {
                    textView_temp.setText("Downloaded");
                }
                else{
                    textView_temp.setText("Downloading ...... "+Integer.toString(progress)+"%");
                }
            }
        }
    }


}
