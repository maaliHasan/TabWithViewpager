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
import java.util.Map;

/**
 * Created by mhasan on 4/12/2017.
 */

public class TabOne extends Fragment {
    ArrayList<HashMap<String, String>> contactList;
    private RecyclerView contactRV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactList = new ArrayList<>();
        new GetData().execute();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    public class GetData extends AsyncTask {

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
        protected Object doInBackground(Object[] params) {
            try {
                Log.d("inside doInBackground", " make call to server");
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
                Log.e("can't open connection", e.toString());


            }

            try {
                int response_code = conn.getResponseCode();
                Log.d("response_code", String.valueOf(response_code));
                if (response_code == HttpURLConnection.HTTP_OK) {
                    //READ DATA SENT FROM SERVER
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    Log.d("result", result.toString());
                    String finalResult = result.toString();
                    try {
                        JSONObject jsonObject = new JSONObject(finalResult);
                        JSONArray contacts = jsonObject.getJSONArray("contacts");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String id = c.getString("id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String address = c.getString("address");
                            String gender = c.getString("gender");
                            HashMap<String, String> contact = new HashMap<>();
                            contact.put("id", id);
                            contact.put("name", name);
                            contact.put("email", email);

                            contactList.add(contact);
                        }

                    } catch (final JSONException e) {
                        Log.e("Json parsing error: ", e.getMessage());
                    }


                    return (result.toString());

                } else {
                    Log.d("READ DATA FROM SERVER", "Can't read data");
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();

            } finally {
                conn.disconnect();

            }


        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (pDialog.isShowing())
                pDialog.dismiss();
            for (HashMap<String, String> map : contactList)
                for (Map.Entry<String, String> entry : map.entrySet())
                    Log.d("Contactlist", entry.getValue());
            contactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
            AdapterContact cAdapter = new AdapterContact(getActivity(), contactList);
            contactRV.setAdapter(cAdapter);
            contactRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }
}

