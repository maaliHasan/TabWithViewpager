package com.example.mhasan.tabwithviewpager;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mhasan on 4/16/2017.
 */

public class AdapterContact extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    ArrayList<HashMap<String, String>> FinalContactList;
    TabOne tab1;
    int currentPos=0;

    public AdapterContact(Context context, ArrayList<HashMap<String, String>> contactList) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        FinalContactList =contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_data, parent,false);
        DataHolder holder= new DataHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    DataHolder dataHolder= (DataHolder)holder;
        HashMap<String, String> current= FinalContactList.get(position);
        dataHolder.name.setText( current.get("name"));
        dataHolder.email.setText( current.get("email"));
        dataHolder.id.setText( current.get("id"));





    }

    @Override
    public int getItemCount() {
        return FinalContactList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder{
        TextView name,email,id;
        public DataHolder(View itemView) {
            super(itemView);
            name= (TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            id=(TextView)itemView.findViewById(R.id.id);
        }

    }
}
