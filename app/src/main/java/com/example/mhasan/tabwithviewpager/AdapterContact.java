package com.example.mhasan.tabwithviewpager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mhasan on 4/16/2017.
 */

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.DataHolder> {
    private LayoutInflater inflater;
    private Context context;
    ArrayList<HashMap<String, String>> FinalContactList;

    public AdapterContact(Context context, ArrayList<HashMap<String, String>> contactList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        FinalContactList = contactList;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_data, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        HashMap<String, String> current = FinalContactList.get(position);
        holder.fullName.setText(current.get("fullName"));
        holder.title.setText(current.get("title"));
        Glide.with(context).load(current.get("pic"))
                .placeholder(R.drawable.img)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return FinalContactList.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView fullName, title;

        de.hdodenhof.circleimageview.CircleImageView pic ;

        DataHolder(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            title = (TextView) itemView.findViewById(R.id.title);
            pic = (de.hdodenhof.circleimageview.CircleImageView ) itemView.findViewById(R.id.pic);
        }
    }
}
