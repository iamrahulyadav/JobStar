package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Employer;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.Util;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ritwik on 12-05-2018.
 */

public class AddEmployerActivity extends AppCompatActivity {

    private Context mContext;
    private EditText et_employer_name, et_website, et_twitter_handle, et_email, et_aboutCompany;
    private String employerID = "";
    private Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employer);
        mContext = AddEmployerActivity.this;
        job = (Job) getIntent().getSerializableExtra("jobDetails");

        //INIT
        et_employer_name = (EditText) findViewById(R.id.et_employer_name);
        et_website = (EditText) findViewById(R.id.et_website);
        et_twitter_handle = (EditText) findViewById(R.id.et_twitter_handle);
        et_email = (EditText) findViewById(R.id.et_email);
        et_aboutCompany = (EditText) findViewById(R.id.et_aboutCompany);

    }

    public void onPreviewJobPostClick(View view) {

        final String employersName = et_employer_name.getText().toString().trim();
        final String website = et_website.getText().toString().trim();
        final String twitterHandle = et_twitter_handle.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String aboutCompany = et_aboutCompany.getText().toString().trim();

        if (TextUtils.isEmpty(employersName)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter employer name.");
            return;

        } else if (TextUtils.isEmpty(website)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter company website url.");
            return;
        } else if (TextUtils.isEmpty(twitterHandle)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter Twitter handle.");
            return;
        } else if (TextUtils.isEmpty(email)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter company email.");
            return;
        } else if (TextUtils.isEmpty(aboutCompany)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter company details.");
            return;
        } else if (!Util.isValidEmail(email)) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter a valid email.");
            return;
        } else if (aboutCompany.length() < 20) {
            Util.showMessageWithOk(AddEmployerActivity.this, "Please enter few more details about the company.");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", Util.fetchUserClass(mContext).getUserId());
        map.put("employer_name", employersName);
        map.put("url", website);
        map.put("employer_email", email);
        map.put("about_company", aboutCompany);

        PostWithJsonWebTask.callPostWithJsonWebtask(AddEmployerActivity.this, Url.ADD_EMPLOYER, map, new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    Log.e("result", "result" + jsonObject.optString("result"));
                    try {
                        if (jsonObject.optInt("result") == 1) {
                            employerID = jsonObject.optString("employer_id");
                            Employer employer = new Employer();
                            employer.setEmployerID(jsonObject.optString("employer_id"));
                            employer.setEmployerName(employersName);
                            employer.setWebsite(website);
                            employer.setEmail(email);
                            employer.setAboutCompany(aboutCompany);
                            employer.setTwitter(twitterHandle);
                            job.employer = employer;

                            Toast.makeText(mContext, "" + jsonObject.optString("data"), Toast.LENGTH_SHORT).show();

                            showJobPreview();
                        } else if (jsonObject.optInt("result") == 0) {
                            Util.showMessageWithOk(AddEmployerActivity.this, "" + jsonObject.optString("data"));
                        } else {
                            Util.showMessageWithOk(AddEmployerActivity.this, "Something went wrong! Please try again.");
                        }

                    } catch (Exception e) {
                        if (jsonObject.optString("result").equalsIgnoreCase("1")) {

                            employerID = jsonObject.optString("employer_id");
                            Employer employer = new Employer();
                            employer.setEmployerID(jsonObject.optString("employer_id"));
                            employer.setEmployerName(employersName);
                            employer.setWebsite(website);
                            employer.setEmail(email);
                            employer.setAboutCompany(aboutCompany);
                            employer.setTwitter(twitterHandle);
                            job.employer = employer;

                            Toast.makeText(mContext, "" + jsonObject.optString("data"), Toast.LENGTH_SHORT).show();

                            showJobPreview();

                        } else if (jsonObject.optString("result").equalsIgnoreCase("0")) {
                            Util.showMessageWithOk(AddEmployerActivity.this, "" + jsonObject.optString("data"));
                        } else {
                            Util.showMessageWithOk(AddEmployerActivity.this, "Something went wrong! Please try again.");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(AddEmployerActivity.this, "Something went wrong! Please try again.");
                }

            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);

    }

    private void showJobPreview() {
        Intent intent = new Intent(mContext, PostJobPreviewActivity.class);
        intent.putExtra("jobDetails", job);
        startActivity(intent);
        finish();

    }
}
