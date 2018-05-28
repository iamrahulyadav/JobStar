package com.papercutlabs.jobstar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.papercutlabs.jobstar.adapter.VideoListAdapter;
import com.papercutlabs.jobstar.model.Blog;
import com.papercutlabs.jobstar.model.YoutubeVideoClass;
import com.papercutlabs.jobstar.network.PostWithJsonWebTask;
import com.papercutlabs.jobstar.network.ServerResponseStringCallback;
import com.papercutlabs.jobstar.network.Url;
import com.papercutlabs.jobstar.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentVideos extends Fragment {

    private Context mContext;
    private ListView lv_videos;
    private VideoListAdapter blogListAdapter;
    private ArrayList<YoutubeVideoClass> videos = new ArrayList<>();

    public FragmentVideos() {
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
        View view = inflater.inflate(R.layout.fragment_videos, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mContext = getActivity();
        lv_videos = (ListView) view.findViewById(R.id.lv_videos);
        PostWithJsonWebTask.callPostWithJsonWebtask(getActivity(), Url.YOUTUBE_QUERY, new HashMap<String, String>(), new ServerResponseStringCallback() {
            @Override
            public void onSuccess(String resultJsonObject) {
                Log.e("Response", "Response: " + resultJsonObject);
                try {
                    JSONObject resultJson = new JSONObject(resultJsonObject);
                    JSONObject error = resultJson.optJSONObject("error");
                    if (error != null) {
                        Log.e("Error", "Error " + error.optInt("code"));
                        Util.showMessageWithOk(getActivity(), "" + error.optString("message"));
                    } else {
                        JSONArray jsonArray = resultJson.optJSONArray("items");
                        videos = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            YoutubeVideoClass videoClass = new YoutubeVideoClass();
                            videoClass.setKind(jsonObject.optString("kind"));
                            videoClass.setEtag(jsonObject.optString("etag"));
                            JSONObject snippetObject = jsonObject.optJSONObject("snippet");
                            JSONObject idObject = jsonObject.optJSONObject("id");

                            videoClass.setVideoID(idObject.optString("videoId"));
                            videoClass.setPublishedAt(snippetObject.optString("publishedAt"));
                            videoClass.setChannelID(snippetObject.optString("channelId"));
                            videoClass.setTitle(snippetObject.optString("title"));
                            videoClass.setDescription(snippetObject.optString("description"));
                            videoClass.setChannelTitle(snippetObject.optString("channelTitle"));
                            videoClass.setLiveBroadcastContent(snippetObject.optString("liveBroadcastContent"));
                            JSONObject thumbnailsObject = snippetObject.optJSONObject("thumbnails");
                            JSONObject defaultImageObject = thumbnailsObject.optJSONObject("default");
                            JSONObject mediumImageObject = thumbnailsObject.optJSONObject("medium");
                            JSONObject highImageObject = thumbnailsObject.optJSONObject("high");

                            videoClass.setThumbnailDefault(defaultImageObject.optString("url"));
                            videoClass.setThumbnailMedium(mediumImageObject.optString("url"));
                            videoClass.setThumbnailHigh(highImageObject.optString("url"));
                            videos.add(videoClass);

                        }
                        blogListAdapter = new VideoListAdapter(mContext, videos);
                        lv_videos.setAdapter(blogListAdapter);
                        lv_videos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                playVideo(videos.get(i).getVideoID());
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
        }, false, Request.Method.GET);

    }

    public void playVideo(String video_id) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + video_id));
        startActivity(intent);
    }

}
