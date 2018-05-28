package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.adapter.JobListViewAdapter;
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
 * Created by ritwik on 23-04-2018.
 */

public class ActivitySearchJob extends AppCompatActivity {

    private Context mContext;
    private ListView lv_searchedJobs;
    private int firstElement = 0;
    private int secondElement = 1;
    private int thirdElement = 2;
    private ArrayList<Job> searchedJobs;
    private EditText et_search_query;
    private JobListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_job);
        mContext = ActivitySearchJob.this;

        lv_searchedJobs = (ListView) findViewById(R.id.lv_searchedJobs);
        et_search_query = (EditText) findViewById(R.id.et_search_query);

        fetchAllLatestJobs();

      /*  et_search_query.setOnEditorActionListener(new TextView.OnEditorActionListener().OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });*/
        et_search_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    String searchQuery = et_search_query.getText().toString().trim();
                    if (TextUtils.isEmpty(searchQuery)) {
                        Toast.makeText(mContext, "Enter Search keyword.", Toast.LENGTH_SHORT).show();

                    } else {
                        searchJob(searchQuery);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void searchJob(String searchQuery) {

        HashMap<String, String> map = new HashMap<>();
        map.put("key_words", "" + searchQuery);
        PostWithJsonWebTask.callPostWithJsonWebtask(ActivitySearchJob.this, Url.FETCH_JOBS, map, new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    if (jsonObject.optInt("result") == 1) {
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        searchedJobs = new ArrayList<>();
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobJSObject = jsonArray.optJSONObject(i);
                                Job job = new Job();
                                job.setJobID(jobJSObject.optString("id"));
                                job.setJobTitle(jobJSObject.optString("title"));
                                job.setJobDescription(jobJSObject.optString("description"));
                                job.setDateOfPosting(jobJSObject.optString("job_post_date"));
                                job.setJobResponsibility(jobJSObject.optString("responsibility"));
                                job.setJobPerks(jobJSObject.optString("job_peerts"));
                                job.setContractType(jobJSObject.optString("type_of_contract"));
                                job.setCategory(jobJSObject.optString("category"));
                                if (jobJSObject.optString("is_relocate").trim().equalsIgnoreCase("1"))
                                    job.setRelocationAvailable(true);
                                else if (jobJSObject.optString("is_relocate").trim().equalsIgnoreCase("0"))
                                    job.setRelocationAvailable(false);
                                job.setJobLocation(jobJSObject.optString("location"));
                                job.setDatePositionFilling(jobJSObject.optString("position_filling_date"));
                                job.setAboutCompany(jobJSObject.optString("about_company"));
                                // TODO ADD CompanyName
                                job.setCompanyName(jobJSObject.optString("company_name"));

                                if (i == firstElement) {
                                    job.setBackgroundDrawable(R.drawable.purple_border_box);
                                    firstElement = firstElement + 3;
                                } else if (i == secondElement) {
                                    job.setBackgroundDrawable(R.drawable.light_green_border_box);
                                    secondElement = secondElement + 3;
                                } else if (i == thirdElement) {
                                    job.setBackgroundDrawable(R.drawable.pink_border_box);
                                    thirdElement = thirdElement + 3;
                                }
                                searchedJobs.add(job);

                            }
                            adapter = new JobListViewAdapter(mContext, searchedJobs);
                            lv_searchedJobs.setAdapter(adapter);
                            lv_searchedJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    onJobClick(position);
                                    //Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Util.showMessageWithOk(ActivitySearchJob.this, "No Jobs Found.");
                        }
                    } else if (jsonObject.optInt("result") == 0) {
                        Util.showMessageWithOk(ActivitySearchJob.this, "" + jsonObject.optString("data"));
                    } else {
                        Util.showMessageWithOk(ActivitySearchJob.this, "Something went wrong! Please try again.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(ActivitySearchJob.this, "Something went wrong! Please try again.");
                }
            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);
    }

    private void fetchAllLatestJobs() {

        PostWithJsonWebTask.callPostWithJsonWebtask(ActivitySearchJob.this, Url.FETCH_JOBS, new HashMap<String, String>(), new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    if (jsonObject.optInt("result") == 1) {
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        searchedJobs = new ArrayList<>();
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobJSObject = jsonArray.optJSONObject(i);
                                Job job = new Job();
                                job.setJobID(jobJSObject.optString("id"));
                                job.setJobTitle(jobJSObject.optString("title"));
                                job.setJobDescription(jobJSObject.optString("description"));
                                job.setDateOfPosting(jobJSObject.optString("job_post_date"));
                                job.setJobResponsibility(jobJSObject.optString("responsibility"));
                                job.setJobPerks(jobJSObject.optString("job_peerts"));
                                job.setContractType(jobJSObject.optString("type_of_contract"));
                                job.setCategory(jobJSObject.optString("category"));
                                if (jobJSObject.optString("is_relocate").trim().equalsIgnoreCase("1"))
                                    job.setRelocationAvailable(true);
                                else if (jobJSObject.optString("is_relocate").trim().equalsIgnoreCase("0"))
                                    job.setRelocationAvailable(false);
                                job.setJobLocation(jobJSObject.optString("location"));
                                job.setDatePositionFilling(jobJSObject.optString("position_filling_date"));
                                job.setAboutCompany(jobJSObject.optString("about_company"));
                                // TODO ADD CompanyName
                                job.setCompanyName(jobJSObject.optString("company_name"));

                                if (i == firstElement) {
                                    job.setBackgroundDrawable(R.drawable.purple_border_box);
                                    firstElement = firstElement + 3;
                                } else if (i == secondElement) {
                                    job.setBackgroundDrawable(R.drawable.light_green_border_box);
                                    secondElement = secondElement + 3;
                                } else if (i == thirdElement) {
                                    job.setBackgroundDrawable(R.drawable.pink_border_box);
                                    thirdElement = thirdElement + 3;
                                }
                                searchedJobs.add(job);

                            }
                            adapter = new JobListViewAdapter(mContext, searchedJobs);
                            lv_searchedJobs.setAdapter(adapter);
                            lv_searchedJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    onJobClick(position);
                                    //Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Util.showMessageWithOk(ActivitySearchJob.this, "No Jobs Found.");
                        }
                    } else if (jsonObject.optInt("result") == 0) {
                        Util.showMessageWithOk(ActivitySearchJob.this, "" + jsonObject.optString("data"));
                    } else {
                        Util.showMessageWithOk(ActivitySearchJob.this, "Something went wrong! Please try again.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(ActivitySearchJob.this, "Something went wrong! Please try again.");
                }
            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);
    }

    private void onJobClick(int position) {

        Intent intent = new Intent(mContext, ApplyJobActivity.class);
        intent.putExtra("job", searchedJobs.get(position));
        startActivity(intent);

    }

    public void onSearchClick(View v) {

        Intent intent = new Intent(ActivitySearchJob.this, ActivityNotification.class);
        startActivity(intent);

    }


    public void onSearchMenuClick(View view) {

    }

    public void onNotificationMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivityNotification.class);
        startActivity(intent);
        finish();
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
