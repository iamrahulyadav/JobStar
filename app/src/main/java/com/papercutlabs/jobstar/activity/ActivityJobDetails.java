package com.papercutlabs.jobstar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.papercutlabs.jobstar.R;

/**
 * Created by knowalluser on 23-04-2018.
 */

public class ActivityJobDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
    }

    public void onApplyJob(View v) {
        Intent intent = new Intent(ActivityJobDetails.this, SelectEmployerActivity.class);
        startActivity(intent);
    }
}
