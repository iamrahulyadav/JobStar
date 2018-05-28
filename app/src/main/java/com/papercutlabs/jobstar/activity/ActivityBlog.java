package com.papercutlabs.jobstar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.util.AlertDialogCallBack;
import com.papercutlabs.jobstar.util.Util;

/**
 * Created by ritwik.rai on 08-04-2018.
 */


public class ActivityBlog extends AppCompatActivity {

    ProgressDialog mDialog;
    private Context mContext;
    private WebView wv_blog;
    private String url = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        mContext = ActivityBlog.this;

        url = getIntent().getStringExtra("url");

        mDialog = new ProgressDialog(mContext);
        mDialog.setCancelable(true);
        mDialog.setMessage("Loading! Please wait..");
        mDialog.setTitle("JobStar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        wv_blog = (WebView) findViewById(R.id.wv_blog);
        wv_blog.getSettings().setDomStorageEnabled(true);
        wv_blog.getSettings().setSaveFormData(true);
        wv_blog.getSettings().setAllowContentAccess(true);
        wv_blog.getSettings().setAllowFileAccess(true);
        //wv_blog.getSettings().setAllowFileAccessFromFileURLs(true);
        //wv_blog.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv_blog.getSettings().setSupportZoom(true);
        wv_blog.getSettings().setJavaScriptEnabled(true);
        wv_blog.getSettings().setSupportMultipleWindows(true);
        wv_blog.setWebChromeClient(new WebChromeClient());
        wv_blog.setWebViewClient(new myWebClient());
        wv_blog.setClickable(true);
        // wv_mindhour_test.loadUrl("http://deferred-live.net/onlinetest/quizzes/physics-101-quiz/");
        //wv_mindhour_test.loadUrl("http://www.google.co.in");
        wv_blog.loadUrl("" + url);

    }


    // ====================================
    // ===== WEB CLIENT ===================

    private class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.e("URL", "" + url);

            mDialog.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mDialog.dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDialog.dismiss();

        }
    }

    @Override
    public void onBackPressed() {

        /*Util.showCallBackMessageWithOkCancel(mContext, "Are you sure you want to exit?", new AlertDialogCallBack() {
            @Override
            public void onSubmit() {
                ActivityBlog.super.onBackPressed();
            }

            @Override
            public void onCancel() {

            }
        });*/

        super.onBackPressed();

    }

   /* public void onBackButtonClick(View view) {

        Util.showCallBackMessageWithOkCancel(mContext, "Are you sure you want to exit?", new AlertDialogCallBack() {
            @Override
            public void onSubmit() {
                ActivityBlog.super.onBackPressed();
            }

            @Override
            public void onCancel() {

            }
        }, "JobStar");

    }*/

    public void onBackButtonPressed(View view) {
        onBackPressed();
    }

}
