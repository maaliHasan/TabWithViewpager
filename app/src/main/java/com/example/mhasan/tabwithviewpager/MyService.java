package com.example.mhasan.tabwithviewpager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Process;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mhasan on 4/17/2017.
 * MyService
 */

public class MyService extends Service {
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 15000;
    private static final String KEY_INTENT_MSG = "JSON_Obj_is_Send";
    private HttpURLConnection mConn;
    private DatabaseHelper mDB;
    private ArrayList<User> contactList = new ArrayList<>();
    private ServiceHandler mServiceHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                StringBuilder result = null;
                URL url = null;
                try {
                    Log.d("inside handleMessage", " make call to server");
                    url = new URL("https://api.myjson.com/bins/n7vjb");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("MalformedURLExc error", e.toString());

                }
                try {
                    mConn = (HttpURLConnection) url.openConnection();
                    mConn.setReadTimeout(READ_TIMEOUT);
                    mConn.setConnectTimeout(CONNECTION_TIMEOUT);
                    mConn.setRequestMethod("GET");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("can't open connection", e.toString());
                }
                try {
                    int response_code = mConn.getResponseCode();
                    if (response_code == HttpURLConnection.HTTP_OK) {
                        InputStream input = mConn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
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
                sendServiceBroadcast(result != null ? result.toString() : null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        mDB = new DatabaseHelper(this);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("  service id", String.valueOf(startId));
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendServiceBroadcast(String result) {
        if (result != null) {
            saveData(result);
            Intent intent = new Intent(KEY_INTENT_MSG);
            intent.putExtra("result", "data saved into DB");
            sendBroadcast(intent);

        }

    }

    private void saveData(String result) {
        Log.d("saveData into DB", "inside saveData");
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray contacts = jsonObject.getJSONArray("posts");
            for (int i = 0; i < contacts.length(); i++) {
                User mUser = new User();
                JSONObject c = contacts.getJSONObject(i);
                String pic = c.getString("profile_pic");
                String title = c.getString("title");
                int id = c.getInt("id");
                String description = c.getString("description");

                JSONObject user = c.getJSONObject("user");
                String firstName = user.getString("first_name");
                String lastName = user.getString("last_name");
                String fullName = firstName.concat(" ").concat(lastName);
                JSONArray images = c.getJSONArray("images");

                for (int j = 0; j < images.length(); j++) {
                    mUser.images.add(images.optString(j));
                }
                mUser.id=id;
                mUser.profilePic=pic;
                mUser.title=title;
                mUser.description=description;
                mUser.fullName=fullName;
                Boolean res = mDB.insertData(mUser);
                Log.d("res of inserting data", String.valueOf(res));
            }
        } catch (final JSONException e) {
            Log.e("Json parsing error: ", e.getMessage());
        }


    }


}
