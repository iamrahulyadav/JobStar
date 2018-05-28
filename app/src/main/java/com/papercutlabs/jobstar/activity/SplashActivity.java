package com.papercutlabs.jobstar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.UserClass;
import com.papercutlabs.jobstar.util.Util;

/**
 * Created by ritwik on 11-05-2018.
 */

public class SplashActivity extends AppCompatActivity {
    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;

        // Progress Loader Dialog
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");

        new SplashTimerTask().execute();

    }


    /**
     * The Splash Timer. duration ---> 2345ms
     *
     * @params {@link AsyncTask}
     */
    private class SplashTimerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2345);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            hideProgressDialog();
            UserClass userClass = Util.fetchUserClass(mContext);
            if (userClass != null && userClass.getIsLoggedin()) {
                openSearchJobActivity();
            } else {
                openSignUpActivity();
            }

        }

    }

    private void openSearchJobActivity() {

        Intent intent = new Intent(mContext, ActivitySearchJob.class);
        startActivity(intent);
        finish();
    }

    private void openSignUpActivity() {

        Intent intent = new Intent(mContext, ActivitySignin.class);
        startActivity(intent);
        finish();
    }

    public void showProgressDialog() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
