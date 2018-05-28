package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.papercutlabs.jobstar.R;

/**
 * Created by knowalluser on 23-04-2018.
 */

public class ActivityNotification extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext= ActivityNotification.this;
        getSupportActionBar().setTitle("Notification");
    }

    public void onVideoClick(View v) {
        Intent intent = new Intent(ActivityNotification.this, ActivityJobDetails.class);
        startActivity(intent);

    }
    public void onSearchMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivitySearchJob.class);
        startActivity(intent);
        finish();
    }

    public void onNotificationMenuClick(View view) {

    }

    public void onProfileMenuClick(View view) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void onMediaMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivityBlogVideo.class);
        startActivity(intent);

    }
}
