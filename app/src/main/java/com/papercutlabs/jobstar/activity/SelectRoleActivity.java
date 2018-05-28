package com.papercutlabs.jobstar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.UserClass;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.AlertDialogCallBack;
import com.papercutlabs.jobstar.util.Util;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ritwik on 19-04-2018.
 */

public class SelectRoleActivity extends AppCompatActivity {

    private String role = "jobSeeker";
    private String email = "";
    private String name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
    }

    public void onJobSeekerClick(View view) {
        role = "jobSeeker";
        HashMap<String, String> map = new HashMap<>();
        map.put("email", "" + email);
        map.put("name", "" + name);
        map.put("role", "jobSeeker");
        registerUser(map);
    }

    public void onJobGiverClick(View view) {
        role = "jobGiver";
        HashMap<String, String> map = new HashMap<>();
        map.put("email", "" + email);
        map.put("name", "" + name);
        map.put("role", "jobGiver");
        registerUser(map);
    }

    private void registerUser(HashMap<String, String> requestMap) {
        PostWithJsonWebTask.callPostWithJsonWebtask(SelectRoleActivity.this, Url.REGISTRATION, requestMap, new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {

                try {
                    JSONObject jsonObject = new JSONObject(resultJsonObject);
                    if (jsonObject.optString("result").equalsIgnoreCase("1")) {

                        UserClass userClass = Util.fetchUserClass(SelectRoleActivity.this);
                        if (userClass == null)
                            userClass = new UserClass();
                        userClass.setEmail(email);
                        userClass.setUserName(name);
                        userClass.setUserId(jsonObject.optString("user_id"));
                        userClass.setRole(role);
                        userClass.setIsLoggedin(true);
                        Util.saveUserClass(SelectRoleActivity.this, userClass);

                        Util.showCallBackMessageWithOkCallback(SelectRoleActivity.this, "" + jsonObject.optString("data"), new AlertDialogCallBack() {
                            @Override
                            public void onSubmit() {
                                Intent intent = new Intent(SelectRoleActivity.this, ActivitySearchJob.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    } else if (jsonObject.optString("result").equalsIgnoreCase("0")) {
                        Util.showMessageWithOk(SelectRoleActivity.this, "" + jsonObject.optString("data"));
                    } else {
                        Util.showMessageWithOk(SelectRoleActivity.this, "Something went wrong! Please try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.showMessageWithOk(SelectRoleActivity.this, "Something went wrong! Please try again.");
                }


            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.POST);
    }
}
