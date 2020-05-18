package com.example.assignment_3;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    public static final int URL_TYPE = 1223;
    String filename = "downloded";
    Context context;
    public DownloadService() {
        super("DownloadService");
    }

//    @Override
//    public int onStartCommand(final Intent intent, final int flags, final int startId) {
//        Log.v("onStartCommand","in the process");
//        onStart(intent, startId);
//        Log.v("onStartCommand","after the process");
//        return START_REDELIVER_INTENT;
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            URL url = new URL(urlToDownload);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            String mimeType = connection.getContentType();
            Log.v("onHandleIntent",Integer.toString(fileLength));
            Log.v("onHandleIntent",mimeType);
            InputStream input = new BufferedInputStream(connection.getInputStream());
            try{
                String path = this.getFilesDir().getAbsolutePath();
                File f = new File(path+"/"+filename);
                if(f.exists() && !f.isDirectory()){
                    Log.v("onHandleIntent",path+"/"+filename);
                    Bundle resultData1 = new Bundle();
                    resultData1.putString("DownloadCategory", "ALREADY_EXIST");
                    receiver.send(URL_TYPE, resultData1);
                    return ;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    Log.v("onHandleIntent",Integer.toString((int)(total * 100.0) /fileLength));
                    total += count;
                    Bundle resultData = new Bundle();
                    resultData.putInt("progress" ,(int)((total * 100.0) /fileLength));
                    receiver.send(UPDATE_PROGRESS, resultData);
                    fos.write(data,0,count);
                    try{
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                input.close();
                Bundle resultData1 = new Bundle();
                resultData1.putInt("progress" ,100);
                receiver.send(UPDATE_PROGRESS, resultData1);
            }
            catch(Exception e){
                e.printStackTrace();
                Bundle resultData1 = new Bundle();
                resultData1.putString("DownloadCategory", "PROBLEM");
                receiver.send(URL_TYPE, resultData1);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Bundle resultData1 = new Bundle();
            resultData1.putString("DownloadCategory", "INVALID");
            receiver.send(URL_TYPE, resultData1);
        }

    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent){
//        super.onTaskRemoved(rootIntent);
//        Intent intent;
//        intent = new Intent(this, DownloadService.class);
//        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
//        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pintent);
//        Log.v("onTaskRemoved","in the process");
//    }
}
