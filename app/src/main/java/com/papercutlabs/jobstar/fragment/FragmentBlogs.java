package com.papercutlabs.jobstar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.activity.ActivityBlog;
import com.papercutlabs.jobstar.adapter.BlogListAdapter;
import com.papercutlabs.jobstar.model.Blog;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.network.VolleyTaskManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentBlogs extends Fragment {

    private Context mContext;
    private ListView lv_blogs;
    private BlogListAdapter blogListAdapter;

    public FragmentBlogs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blogs, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mContext = getActivity();
        lv_blogs = (ListView) view.findViewById(R.id.lv_blogs);

        PostWithJsonWebTask.callPostWithJsonWebtask(getActivity(), Url.GOOGLE_NEWS_QUERY_JOB, new HashMap<String, String>(), new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                Log.e("Response", "Response: " + resultJsonObject);
                try {
                    JSONObject resultJson = new JSONObject(resultJsonObject);
                    if (resultJson != null && resultJson.optString("status").trim().equalsIgnoreCase("ok")) {
                        JSONArray jsonArray = resultJson.optJSONArray("articles");
                        final ArrayList<Blog> blogs = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject articleJsonObj = jsonArray.optJSONObject(i);
                            JSONObject source = articleJsonObj.optJSONObject("source");
                            Blog blog = new Blog();
                            blog.setSource(source.optString("name"));
                            blog.setTitle(articleJsonObj.optString("title"));
                            blog.setAuthor(articleJsonObj.optString("author"));
                            blog.setDescription(articleJsonObj.optString("description"));
                            blog.setPublishedDate(articleJsonObj.optString("publishedAt"));
                            blog.setUrl(articleJsonObj.optString("url"));
                            blog.setUrlImage(articleJsonObj.optString("urlToImage"));
                            blogs.add(blog);
                        }

                        blogListAdapter = new BlogListAdapter(mContext, blogs);
                        lv_blogs.setAdapter(blogListAdapter);
                        lv_blogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(mContext, ActivityBlog.class);
                                intent.putExtra("url", "" + blogs.get(i).getUrl());
                                startActivity(intent);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void ErrorMsg(VolleyError error) {

            }
        }, true, Request.Method.GET);

    }

}
