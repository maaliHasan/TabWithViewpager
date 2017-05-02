package com.example.mhasan.tabwithviewpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhasan on 4/12/2017.
 * TabOne
 */

public class TabOne extends Fragment implements AdapterContact.OnItemClickListener {
    private AdapterContact mCAdapter;
    private DatabaseHelper mDB;
    ArrayList<User> contactList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactList = new ArrayList<>();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mContactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
        mCAdapter = new AdapterContact(getActivity(), contactList);
        mCAdapter.setOnItemClickedListener(this);
        mContactRV.setAdapter(mCAdapter);

        mContactRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDB = new DatabaseHelper(getActivity());
        DataBroadCastReceiver mDBR = new DataBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("JSON_Obj_is_Send");
        getActivity().registerReceiver(mDBR, filter);
        getActivity().startService(new Intent(getActivity(), MyService.class));

    }

    private void updateView( ) {
        contactList= mDB.getData();
        Log.d("contactList", String.valueOf(contactList));
        mCAdapter.FinalContactList=contactList;
        //mCAdapter.notifyDataSetChanged();
/*
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray contacts = jsonObject.getJSONArray("posts");
            for (int i = 0; i < contacts.length(); i++) {
                User userData = new User();
                JSONObject c = contacts.getJSONObject(i);
                String pic = c.getString("profile_pic");
                String title = c.getString("title");
                int id = c.getInt("id");
                String description = c.getString("description");

                JSONObject user = c.getJSONObject("user");
                String firstName = user.getString("first_name");
                String lastName = user.getString("last_name");

                JSONArray images = c.getJSONArray("images");
                for (int j = 0; j < images.length(); j++) {
                    userData.images.add(images.optString(j));
                }
                userData.firstName = firstName;
                userData.lastName = lastName;
                userData.fullName = firstName.concat(" ").concat(lastName);
                userData.description = description;
                userData.id = id;
                userData.title = title;
                userData.profilePic = pic;
                contactList.add(userData);
                Log.d("profilePic", userData.fullName);
            }
        } catch (final JSONException e) {
            Log.e("Json parsing error: ", e.getMessage());
        }

*/

    }

    public class DataBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("JSON_Obj_is_Send".equals(intent.getAction())) {
              //  String result = intent.getStringExtra("result");
              updateView();

            }

        }
    }



    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), UserData.class);
        intent.putExtra("UserInfo", contactList.get(position));
        startActivity(intent);
    }
}



