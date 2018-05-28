package com.papercutlabs.jobstar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.fragment.FragmentBlogs;
import com.papercutlabs.jobstar.fragment.FragmentVideos;
import com.papercutlabs.jobstar.network.ServerResponseCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by knowalluser on 07-04-2018.
 */

public class ActivityBlogVideo extends AppCompatActivity implements ServerResponseCallback {

    private Context mContext;
    // private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_video);
        mContext = ActivityBlogVideo.this;
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
     /*   volleyTaskManager = new VolleyTaskManager(mContext);
        volleyTaskManager.doGetBlogs(Url.GOOGLE_NEWS_QUERY);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentBlogs(), "Blogs");
        adapter.addFragment(new FragmentVideos(), "Videos");
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {

    }

    @Override
    public void onError() {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public void onSearchMenuClick(View view) {
        Intent intent = new Intent(mContext, ActivitySearchJob.class);
        startActivity(intent);
        finish();
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

    }
}
