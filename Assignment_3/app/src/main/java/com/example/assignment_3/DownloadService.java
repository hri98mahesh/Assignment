package com.example.assignment_3;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.content.Context;
/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    String filename = "downloded";
    Context context;
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection;
            connection = (URLConnection) url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            String mimeType = connection.getContentType();
            Log.v("onHandleIntent",Integer.toString(fileLength));
            Log.v("onHandleIntent",mimeType);
            InputStream input = new BufferedInputStream(connection.getInputStream());
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
                    fos.write(data);
                    try{
                        Thread.sleep(100);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                input.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }
}
