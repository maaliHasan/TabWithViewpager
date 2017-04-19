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

class AdapterContact extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    ArrayList<User> FinalContactList;

    public AdapterContact(Context context, ArrayList<User> contactList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        FinalContactList = contactList;
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.container_data, parent, false);
            View view2 = inflater.inflate(R.layout.container_data2, parent, false);
            View view3 = inflater.inflate(R.layout.container_data3, parent, false);
         /*  switch (viewType) {
                case 0:
                    return new DataHolder(view);
                case 1:
                    return new DataHolde2(view2);
                case 2:
                    return new DataHoder3(view3);
            }*/
            return new DataHolder(view);
        }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataHolder holder1=(DataHolder) holder;
        User current = FinalContactList.get(position);
        holder1.fullName.setText(current.fullName);
        holder1.title.setText(current.title);
        Glide.with(context).load(current.profilePic)
                .into(holder1.pic);
      /*    switch (holder.getItemViewType()) {
            case 0:
                DataHolder holder1=(DataHolder) holder;
                User current = FinalContactList.get(position);
               holder1.fullName.setText(current.fullName);
                holder1.title.setText(current.title);
                Glide.with(context).load(current.profilePic)
                        .into(holder1.pic);
                break;
          case 1:
                DataHolde2 holder2=(DataHolde2)holder;
                User current2 = FinalContactList.get(position);
                holder2.fullName.setText(current2.fullName);
                holder2.title.setText(current2.title);
                Glide.with(context).load(current2.profilePic)
                        .into(holder2.pic);

                Glide.with(context).load(current2.images[0])
                        .into(holder2.img1);

                Glide.with(context).load(current2.images[1])
                        .into(holder2.img2);


        }*/
    }

    @Override
    public int getItemCount() {
        Log.d("list size ",String.valueOf(FinalContactList.size()));
        return FinalContactList.size();
    }

   /* @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }*/

    private class DataHolder extends RecyclerView.ViewHolder {
        TextView fullName, title;
        CircleImageView pic;

        DataHolder(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            title = (TextView) itemView.findViewById(R.id.title);
            pic = (CircleImageView) itemView.findViewById(R.id.pic);
        }
    }


    class DataHolde2 extends RecyclerView.ViewHolder {
        TextView fullName, title;
        CircleImageView pic;
        ImageView img1, img2;

        public DataHolde2(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            title = (TextView) itemView.findViewById(R.id.title);
            pic = (CircleImageView) itemView.findViewById(R.id.pic);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);

        }

    }

    class DataHoder3 extends RecyclerView.ViewHolder {
        TextView fullName, title;
        CircleImageView pic;
        ImageView img1, img2, img3;

        public DataHoder3(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            title = (TextView) itemView.findViewById(R.id.title);
            pic = (CircleImageView) itemView.findViewById(R.id.pic);
            img1 = (ImageView) itemView.findViewById(R.id.image1);
            img2 = (ImageView) itemView.findViewById(R.id.image2);
            img3 = (ImageView) itemView.findViewById(R.id.image3);
        }
    }

}
