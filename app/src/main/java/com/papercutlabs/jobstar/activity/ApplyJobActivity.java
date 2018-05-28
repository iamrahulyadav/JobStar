package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.view.CustomTextView;

/**
 * Created by ritwik on 15-04-2018.
 */

public class ApplyJobActivity extends AppCompatActivity {


    private Context mContext;
    private Job job;
    private CustomTextView ctv_jobTitle, ctv_aboutCompany, ctv_job_description, ctv_responsibility, ctv_keyskills;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply_details);
        mContext = ApplyJobActivity.this;

        job = (Job) getIntent().getSerializableExtra("job");

        ctv_jobTitle = (CustomTextView) findViewById(R.id.ctv_jobTitle);
        ctv_aboutCompany = (CustomTextView) findViewById(R.id.ctv_aboutCompany);
        ctv_job_description = (CustomTextView) findViewById(R.id.ctv_job_description);
        ctv_responsibility = (CustomTextView) findViewById(R.id.ctv_responsibility);
        ctv_keyskills = (CustomTextView) findViewById(R.id.ctv_keyskills);

        ctv_jobTitle.setText(job.getJobTitle());
        ctv_aboutCompany.setText(job.getAboutCompany());
        ctv_job_description.setText(job.getJobDescription());
        ctv_responsibility.setText(job.getCategory());
        // ctv_keyskills.setText(job.getKeySkills());

    }

    public void onApplyJobClick(View view) {

    }

    public void onBackButtonPressed(View view) {
        onBackPressed();
    }

    public void onShareClick(View view) {
        String message = "Hey chek out JobStar at: https://play.google.com/store/apps/details?id=com.connectapp.user";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Share App: "));
    }
}
