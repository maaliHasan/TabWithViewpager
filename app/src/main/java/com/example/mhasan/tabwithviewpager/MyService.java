package com.example.mhasan.tabwithviewpager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mhasan on 4/17/2017.
 */

public class MyService extends Service {
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 15000;
    private HttpURLConnection mConn;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private final class ServiceHandler extends Handler{

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try  {
                StringBuilder result = null;
                //   Log.d("inside getDate", " ********");

                URL url=null;
                try {
                    Log.d("inside doInBackground", " make call to server");
                    url = new URL("https://api.myjson.com/bins/n7vjb");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("MalformedURLExc error", e.toString());

                }
                try {
                    mConn = (HttpURLConnection)url.openConnection();
                    mConn.setReadTimeout(READ_TIMEOUT);
                    mConn.setConnectTimeout(CONNECTION_TIMEOUT);
                    mConn.setRequestMethod("GET");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("can't open connection", e.toString());
                }
                try {
                    int response_code = mConn.getResponseCode();
                    Log.d("response_code", String.valueOf(response_code));
                    if (response_code == HttpURLConnection.HTTP_OK) {
                        //READ DATA SENT FROM SERVER
                        InputStream input = mConn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        Log.d("result in thread", result.toString());
                    } else {
                        Log.d("READ DATA FROM SERVER", "Can't read data");
                        result = null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    result = null;
                } finally {
                    mConn.disconnect();
                }

                // return result != null ? result.toString() : null;
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper=thread.getLooper();
        mServiceHandler= new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Log.d("inside service", " service start");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
