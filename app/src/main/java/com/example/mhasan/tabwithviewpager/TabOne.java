package com.example.mhasan.tabwithviewpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mhasan on 4/12/2017.
 * TabOne
 */

public class TabOne extends Fragment implements AdapterContact.OnItemClickListener {
    private AdapterContact mCAdapter;
    private DatabaseHelper mDB;
    ArrayList<User> contactList = new ArrayList<>();
    ArrayList<User> contactRes = new ArrayList<>();

    public String[] serProjection = {
            "ID",
            "NAME",
            "TITLE",
            "PIC",
            "DESCRIPTION"
    };
    public String[] photoProjection = {
            "ID",
            "IMG",
            "user_id"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactList = new ArrayList<>();
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDB = new DatabaseHelper(getActivity());
        DataBroadCastReceiver mDBR = new DataBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("JSON_Obj_is_Send");
        getActivity().registerReceiver(mDBR, filter);
        getActivity().startService(new Intent(getActivity(), MyService.class));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateView() {

        this.contactList =getData();

        RecyclerView mContactRV = (RecyclerView) getActivity().findViewById(R.id.contactList);
        mCAdapter = new AdapterContact(getActivity(), contactList);
        mCAdapter.setOnItemClickedListener(this);
        mContactRV.setAdapter(mCAdapter);

        mContactRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d("contactList", String.valueOf(contactList));


        this.mCAdapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<User> getData() {
        String[] serProjection = {
                "ID",
                "NAME",
                "TITLE",
                "PIC",
                "DESCRIPTION"
        };
        String[] photoProjection = {
                "IMG",
                "user_id"
        };
        ArrayList<User> usersWithImg = new ArrayList<>();
        Cursor userCursor = getContext().getContentResolver().query(DataProvider.CONTENT_URI, serProjection, null, null, null, null);

        ArrayList<User> users = new ArrayList<>();

        while (userCursor.moveToNext()) {
            User mUser = new User();
            mUser.fullName = userCursor.getString(userCursor.getColumnIndexOrThrow("NAME"));
            mUser.title = userCursor.getString(userCursor.getColumnIndexOrThrow("TITLE"));
            mUser.description = userCursor.getString(userCursor.getColumnIndexOrThrow("DESCRIPTION"));
            mUser.profilePic = userCursor.getString(userCursor.getColumnIndexOrThrow("PIC"));
            mUser.id = userCursor.getInt(userCursor.getColumnIndexOrThrow("ID"));
            users.add(mUser);
        }
        Log.d("retrieved result", String.valueOf(users));


        for (User obj : users) {
            int userID = obj.id;
            String mSelectionClause = "SELECT photo.IMG FROM 'photo'   WHERE user_ID= '" + userID + "'";
            Cursor photoCursor = getContext().getContentResolver().query(DataProvider.CONTENT_URI2, photoProjection, mSelectionClause, null, null);
            while (photoCursor.moveToNext()) {
                String img = photoCursor.getString(photoCursor.getColumnIndexOrThrow("IMG"));
                obj.images.add(img);
                Log.d( "getData: ",img);
            }
            usersWithImg.add(obj);
        }

        return usersWithImg;

    }


    public class DataBroadCastReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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



