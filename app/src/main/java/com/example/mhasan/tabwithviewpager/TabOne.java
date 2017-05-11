package com.example.mhasan.tabwithviewpager;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import java.net.URI;
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
    ArrayList<User> contactRes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactList = new ArrayList<>();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     /*   RecyclerView mContactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
        mCAdapter = new AdapterContact(getActivity(), contactList);
        mCAdapter.setOnItemClickedListener(this);
        mContactRV.setAdapter(mCAdapter);

        mContactRV.setLayoutManager(new LinearLayoutManager(getActivity()));*/
        insertData();


        mDB = new DatabaseHelper(getActivity());
        DataBroadCastReceiver mDBR = new DataBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("JSON_Obj_is_Send");
        getActivity().registerReceiver(mDBR, filter);
        getActivity().startService(new Intent(getActivity(), MyService.class));

    }

    private void insertData() {
        ContentValues values= new ContentValues();
        values.put("ID","12");
        values.put("NAME", "hasan");
        values.put("TITLE", "zxxxx");
        values.put("DESCRIPTION", "jhjhj");
        values.put("PIC", "https://cdn.pixabay.com/photo/2014/07/09/10/04/man-388104_960_720.jpg");
        Uri userId= getContext().getContentResolver().insert(DataProvider.CONTENT_URI,values);
        Log.d("MainActivity",(userId.getLastPathSegment()));

    }

    private void updateView() {

        this.contactList = mDB.getData();

        RecyclerView mContactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
        mCAdapter = new AdapterContact(getActivity(), contactList);
        mCAdapter.setOnItemClickedListener(this);
        mContactRV.setAdapter(mCAdapter);

        mContactRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d("contactList", String.valueOf(contactList));


        this.mCAdapter.notifyDataSetChanged();

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



