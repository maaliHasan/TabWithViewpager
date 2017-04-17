package com.example.mhasan.tabwithviewpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by mhasan on 4/12/2017.
 * TabOne
 */

public class TabOne extends Fragment {
    ArrayList<HashMap<String, String>> contactList;
    private RecyclerView contactRV;
    private AdapterContact cAdapter;
    private DataBroadCastRecv Drcv;

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
        Drcv = new DataBroadCastRecv();
        IntentFilter filter = new IntentFilter();
        filter.addAction("JSON_Obj_is_Send");
        getActivity().registerReceiver(Drcv, filter);
    }

    private void updateView(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
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

    public class DataBroadCastRecv extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("JSON_Obj_is_Send".equals(intent.getAction())) {
                String result = intent.getStringExtra("result");
                updateView(result);
            }
        }
    }
}



