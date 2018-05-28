package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.AlertDialogCallBack;
import com.papercutlabs.jobstar.util.Util;
import com.papercutlabs.jobstar.view.CustomTextView;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ritwik on 1-05-2018.
 */

public class PostJobPreviewActivity extends AppCompatActivity {

    private Context mContext;
    private CustomTextView ctv_jobTitle, ctv_aboutCompany, ctv_job_description, ctv_responsibility, ctv_keyskills;
    private Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_details);
        mContext = PostJobPreviewActivity.this;

        job = (Job) getIntent().getSerializableExtra("jobDetails");

        ctv_jobTitle = (CustomTextView) findViewById(R.id.ctv_jobTitle);
        ctv_aboutCompany = (CustomTextView) findViewById(R.id.ctv_aboutCompany);
        ctv_job_description = (CustomTextView) findViewById(R.id.ctv_job_description);
        ctv_responsibility = (CustomTextView) findViewById(R.id.ctv_responsibility);
        ctv_keyskills = (CustomTextView) findViewById(R.id.ctv_keyskills);


        ctv_jobTitle.setText(job.getJobTitle());
        ctv_aboutCompany.setText(job.employer.getAboutCompany());
        ctv_job_description.setText(job.getJobDescription());
        ctv_responsibility.setText(job.getCategory());
        ctv_keyskills.setText(job.getKeySkills());

    }

    public void onPostJobClick(View view) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", "" + job.getUserID());
        requestMap.put("employer_id", "" + job.employer.getEmployerID());
        requestMap.put("title", "" + job.getJobTitle());
        requestMap.put("description", "" + job.getJobDescription());
        requestMap.put("key_words", "" + job.getKeySkills());
        requestMap.put("about_company", "" + job.employer.getAboutCompany());
        requestMap.put("responsibility", "" + job.getCategory());
        requestMap.put("job_peerts", "");
        requestMap.put("type_of_contract", "" + job.getContractType());
        requestMap.put("is_relocate", "" + job.isRelocationAvailable());
        requestMap.put("category", "" + job.getCategory());
        requestMap.put("location", "" + job.getJobLocation());
        requestMap.put("candidate_upload", "");
        requestMap.put("position_filling_date", "" + job.getDateForFilling());
        PostWithJsonWebTask.callPostWithJsonWebtask(PostJobPreviewActivity.this, Url.POST_JOB_URL, requestMap, new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {

                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    if (jsonObject.optInt("result") == 1) {

                        Util.showCallBackMessageWithOkCallback(mContext, "" + jsonObject.optString("data"), new AlertDialogCallBack() {
                            @Override
                            public void onSubmit() {
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    } else if (jsonObject.optInt("result") == 0) {

                        Util.showMessageWithOk(PostJobPreviewActivity.this, "" + jsonObject.optString("data"));
                    } else {

                        Util.showMessageWithOk(PostJobPreviewActivity.this, "Something went wrong! Please try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(PostJobPreviewActivity.this, "Something went wrong! Please try again.");
                }
            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);

    }

    public void onBackButtonPressed(View view) {
        onBackPressed();
    }
}
