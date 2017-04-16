package com.example.mhasan.tabwithviewpager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.HashMap;


/**
 * Created by mhasan on 4/12/2017.
 */

public class TabOne extends Fragment {
    ArrayList<HashMap<String, String>> contactList;
    private RecyclerView contactRV;
    private AdapterContact cAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactList = new ArrayList<>();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
        cAdapter = new AdapterContact(getActivity(), contactList);
        contactRV.setAdapter(cAdapter);
        contactRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        new GetData().execute("https://api.myjson.com/bins/n7vjb");
    }

    public class GetData extends AsyncTask<String, Void, String> {
        public static final int CONNECTION_TIMEOUT = 30000;
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
        protected String doInBackground(String[] params) {
            StringBuilder result = null;
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
            return result == null ? null : result.toString();
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            pDialog.dismiss();
            updateView(o);
        }
    }

    private void updateView(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray contacts = jsonObject.getJSONArray("posts");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String pic = c.getString("profile_pic");
                String title = c.getString("title");

                JSONObject user = c.getJSONObject("user");
                String firstName = user.getString("first_name");
                String lastName = user.getString("last_name");

                HashMap<String, String> contact = new HashMap<>();
                contact.put("title", title);
                contact.put("pic", pic);
                contact.put("fullName", firstName.concat(" ").concat(lastName));
                contactList.add(contact);
            }
        } catch (final JSONException e) {
            Log.e("Json parsing error: ", e.getMessage());
        }
        cAdapter.notifyDataSetChanged();
    }
}

