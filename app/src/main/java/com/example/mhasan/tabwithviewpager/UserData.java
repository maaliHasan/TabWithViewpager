package com.example.mhasan.tabwithviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        mUser = (User) getIntent().getSerializableExtra("UserInfo");
        fullName.setText(mUser.fullName);
        title.setText(mUser.title);
        description.setText(mUser.description);
        Glide.with(getBaseContext())
                .load(mUser.profilePic)
                .centerCrop()
                .into(pic);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                700, 350, 1.0f
        );
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int imagesNum = mUser.images.size();
        for (int i = 0; i < imagesNum; i++) {
            Log.d("imagesNum", String.valueOf(i));
            ImageView img1 = new ImageView(this);
            img1.setLayoutParams(params);
            Glide.with(getBaseContext())
                    .load(mUser.images.get(i))
                    .centerCrop()
                    .into(img1);
            linearLayout.addView(img1);
        }


    }


}
