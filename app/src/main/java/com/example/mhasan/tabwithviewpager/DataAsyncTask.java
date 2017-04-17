package com.example.mhasan.tabwithviewpager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mhasan on 4/16/2017.
 * DataAsyncTask
 */

class DataAsyncTask extends AsyncTask<String, Void, String> {
    private static final String KEY_INTENT_MSG = "JSON_Obj_is_Send";

    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 15000;
    private ProgressDialog pDialog;
    private HttpURLConnection mConn;
    private Context context;

    DataAsyncTask(Context context) {
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

        URL url;
        try {
            Log.d("inside doInBackground", " make call to server");
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
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
                Log.d("result", result.toString());
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

        return result != null ? result.toString() : null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pDialog.dismiss();
        if (result != null) {
            Intent intent = new Intent(KEY_INTENT_MSG);
            intent.putExtra("result", result);
            context.sendBroadcast(intent);
        }
    }


}