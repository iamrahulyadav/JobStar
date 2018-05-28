package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.adapter.EmployerAdapter;
import com.papercutlabs.jobstar.model.Employer;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ritwik on 04-04-2018.
 */

public class SelectEmployerActivity extends AppCompatActivity {

    private Context mContext;
    private ListView lv_employers;
    private Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_employer);
        mContext = SelectEmployerActivity.this;

        lv_employers = (ListView) findViewById(R.id.lv_employers);
        job = (Job) getIntent().getSerializableExtra("jobDetails");

        fetchEmployers();

    }

    private void fetchEmployers() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", Util.fetchUserClass(mContext).getUserId());
        PostWithJsonWebTask.callPostWithJsonWebtask(SelectEmployerActivity.this, Url.FETCH_EMPLOYER, requestMap, new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    if (jsonObject.optInt("result") == 1) {
                        JSONArray employerArray = jsonObject.optJSONArray("data");
                        final ArrayList<Employer> employers = new ArrayList<>();
                        if (employerArray != null && employerArray.length() > 0) {
                            for (int i = 0; i < employerArray.length(); i++) {
                                JSONObject employerObj = employerArray.optJSONObject(i);
                                Employer employer = new Employer();
                                employer.setEmployerID("" + employerObj.optString("id"));
                                employer.setUserID("" + employerObj.optString("user_id"));
                                employer.setEmail("" + employerObj.optString("email"));
                                employer.setAboutCompany("" + employerObj.optString("about_company"));
                                employer.setWebsite("" + employerObj.optString("url"));
                                employer.setLogoUrl("" + employerObj.optString("logo_path"));
                                employers.add(employer);
                            }
                            EmployerAdapter adapter = new EmployerAdapter(mContext, employers);
                            lv_employers.setAdapter(adapter);
                            lv_employers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                        onAddEmployerItemClick(employers.get(i));
                                                                    }
                                                                }
                            );

                        } else {
                            Util.showMessageWithOk(SelectEmployerActivity.this, "No employer Added! Please add one.");
                        }
                    } else if (jsonObject.optInt("result") == 0) {
                        Util.showMessageWithOk(SelectEmployerActivity.this, "" + jsonObject.optString("data"));
                    } else {
                        Util.showMessageWithOk(SelectEmployerActivity.this, "Something went wrong! Please try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(SelectEmployerActivity.this, "Something went wrong! Please try again.");
                }
            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);
    }

    private void onAddEmployerItemClick(Employer employer) {
        Intent intent = new Intent(mContext, AddEmployerActivity.class);
        job.employer = employer;
        intent.putExtra("jobDetails", job);
        startActivity(intent);

    }

    public void onAddEmployerClick(View view) {

        Intent intent = new Intent(mContext, AddEmployerActivity.class);
        intent.putExtra("jobDetails", job);
        startActivity(intent);

    }

    public void onPreview(View view) {

    }
}
