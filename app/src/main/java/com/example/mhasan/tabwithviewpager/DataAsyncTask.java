package com.example.mhasan.tabwithviewpager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by mhasan on 4/16/2017.
 */

public class DataAsyncTask extends AsyncTask<String, Void, String> {

    public static final int CONNECTION_TIMEOUT = 30000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<HashMap<String, String>> contactList;
    private ProgressDialog pDialog;
    HttpURLConnection conn;
    URL url = null;
    Context context;

    public DataAsyncTask(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String[] params) {
        StringBuilder result = null;
        final String KEY_INTENT_MSG = "JSON_Obj_is_Send";
        try {
            Log.d("inside doInBackground", " make call to server");
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        }
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("can't open connection", e.toString());
        }
        try {
            int response_code = conn.getResponseCode();
            Log.d("response_code", String.valueOf(response_code));
            if (response_code == HttpURLConnection.HTTP_OK) {
                //READ DATA SENT FROM SERVER
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("result", result.toString());
            } else {
                Log.d("READ DATA FROM SERVER", "Can't read data");
                result = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        } finally {
            conn.disconnect();
        }
        Intent intent = new Intent(KEY_INTENT_MSG);
        intent.putExtra("result", result.toString());
        context.sendBroadcast(intent);
        return null;
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        pDialog.dismiss();

    }


}