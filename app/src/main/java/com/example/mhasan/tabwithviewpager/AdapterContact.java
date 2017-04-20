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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mhasan on 4/16/2017.
 * AdapterContact
 */

class AdapterContact extends RecyclerView.Adapter<AdapterContact.MyHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<User> FinalContactList;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //int itemPostion=mCont;
        }
    };

    AdapterContact(Context context, ArrayList<User> contactList) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        FinalContactList = contactList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view = inflater.inflate(R.layout.container_data, parent, false);
                view.setOnClickListener(onClickListener);
                return new DataHolder(view);
            case 2:
                View view2 = inflater.inflate(R.layout.container_data2, parent, false);
                view2.setOnClickListener(onClickListener);
                return new DataHolder2(view2);
            case 3:
                View view3 = inflater.inflate(R.layout.container_data3, parent, false);
                view3.setOnClickListener(onClickListener);
                return new DataHolder3(view3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        int type = getItemViewType(position);
        Log.d("view type", String.valueOf(type));
        switch (type) {
            case 1:
                DataHolder holder1 = (DataHolder) holder;
                User current = FinalContactList.get(position);
                holder1.fullName.setText(current.fullName);
                holder1.title.setText(current.title);
                Glide.with(mContext)
                        .load(current.profilePic)
                        .centerCrop()
                        .into(holder1.pic);
                Glide.with(mContext)
                        .load(current.images.get(0))
                        .centerCrop()
                        .into(holder1.img1);
                break;
            case 2:
                DataHolder2 holder2 = (DataHolder2) holder;
                User current2 = FinalContactList.get(position);
                holder2.fullName.setText(current2.fullName);
                holder2.title.setText(current2.title);
                Glide.with(mContext)
                        .load(current2.profilePic)
                        .centerCrop()
                        .into(holder2.pic);
                Glide.with(mContext)
                        .load(current2.images.get(0))
                        .centerCrop()
                        .into(holder2.img1);
                Glide.with(mContext)
                        .load(current2.images.get(1))
                        .centerCrop()
                        .into(holder2.img2);
                break;
            case 3:
                DataHolder3 holder3 = (DataHolder3) holder;
                User current3 = FinalContactList.get(position);
                holder3.fullName.setText(current3.fullName);
                holder3.title.setText(current3.title);
                Glide.with(mContext)
                        .load(current3.profilePic)
                        .centerCrop()
                        .into(holder3.pic);
                Glide.with(mContext)
                        .load(current3.images.get(0))
                        .centerCrop()
                        .into(holder3.img1);
                Glide.with(mContext)
                        .load(current3.images.get(1))
                        .centerCrop()
                        .into(holder3.img2);
                Glide.with(mContext)
                        .load(current3.images.get(2))
                        .centerCrop()
                        .into(holder3.img3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.d("list size ", String.valueOf(FinalContactList.size()));
        return FinalContactList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("user images ", String.valueOf(FinalContactList.get(position).images.size()));
        if (FinalContactList.get(position).images.size() == 1) {
            return 1;
        } else if (FinalContactList.get(position).images.size() == 2) {
            return 2;
        }
        return 3;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView fullName, title;
        CircleImageView pic;

        MyHolder(View itemView) {
            super(itemView);

            fullName = (TextView) itemView.findViewById(R.id.fullName);
            title = (TextView) itemView.findViewById(R.id.title);
            pic = (CircleImageView) itemView.findViewById(R.id.pic);
        }
    }

    private class DataHolder extends MyHolder {
        ImageView img1;

        DataHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
        }
    }


    private class DataHolder2 extends MyHolder {
        ImageView img1, img2;

        DataHolder2(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);
        }

    }

    private class DataHolder3 extends MyHolder {
        private ImageView img1, img2, img3;

        DataHolder3(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);
            img3 = (ImageView) itemView.findViewById(R.id.image3);
        }
    }

}
