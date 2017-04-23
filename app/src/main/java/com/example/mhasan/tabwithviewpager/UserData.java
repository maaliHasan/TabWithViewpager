package com.example.mhasan.tabwithviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserData extends AppCompatActivity {

    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        getUserData();
    }

    private void getUserData() {

        TextView fullName = (TextView) findViewById(R.id.fullName);
        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        CircleImageView pic = (CircleImageView) findViewById(R.id.pic);
        ImageView img1 = (ImageView)findViewById(R.id.image);

        mUser = (User) getIntent().getSerializableExtra("UserInfo");
        fullName.setText(mUser.fullName);
        title.setText(mUser.title);
        description.setText(mUser.description);
        Glide.with(getBaseContext())
                .load(mUser.profilePic)
                .centerCrop()
                .into(pic);

        int imagesNum = mUser.images.size();
        switch (imagesNum) {

            case 1:
                Glide.with(getBaseContext())
                        .load(mUser.images.get(0))
                        .centerCrop()
                        .into(img1);
                break;
            case 2:
                ImageView   img2 = (ImageView) findViewById(R.id.image2);
                Glide.with(getBaseContext())
                        .load(mUser.images.get(0))
                        .centerCrop()
                        .into(img1);
                Glide.with(getBaseContext())
                        .load(mUser.images.get(1))
                        .centerCrop()
                        .into(img2);
                break;
            case 3:
                img2 = (ImageView) findViewById(R.id.image2);
                ImageView   img3 = (ImageView) findViewById(R.id.image3);
                Glide.with(getBaseContext())
                        .load(mUser.images.get(0))
                        .centerCrop()
                        .into(img1);
                Glide.with(getBaseContext())
                        .load(mUser.images.get(1))
                        .centerCrop()
                        .into(img2);
                Glide.with(getBaseContext())
                        .load(mUser.images.get(2))
                        .centerCrop()
                        .into(img3);
                break;
        }

    }


}
