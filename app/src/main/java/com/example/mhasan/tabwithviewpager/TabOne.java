package com.example.mhasan.tabwithviewpager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * Created by mhasan on 4/12/2017.
 */

public class TabOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new GetData().execute();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    public class GetData extends AsyncTask {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
        private ProgressDialog pDialog;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                url = new URL("http://api.androidhive.info/contacts/");
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
                return e.toString();
            }

            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    //READ DATA SENT FROM SERVER
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    return (result.toString());

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();

            } finally {
                conn.disconnect();
              //  pDialog.dismiss();
            }


        }


      //  @Override
        protected void onPostExecute(String  result) {
          //  super.onPostExecute(o);
            pDialog.dismiss();
            try {
                JSONArray jArray = new JSONArray(result);
                for(int i=0 ;i<jArray.length();i++){
                    JSONObject json_data= jArray.getJSONObject(i);
                    Log.d("fish name is ",json_data.getString("name"));

                }
            }
            catch (JSONException e){
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}

