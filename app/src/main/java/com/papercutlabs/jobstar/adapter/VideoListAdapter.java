package com.papercutlabs.jobstar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.YoutubeVideoClass;

import java.util.ArrayList;

/**
 * Created by ritwik.rai on 07-04-2018.
 */

public class VideoListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<YoutubeVideoClass> youtubeVideos = new ArrayList<>();


    public VideoListAdapter(Context mContext, ArrayList<YoutubeVideoClass> youtubeVideos) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.youtubeVideos = youtubeVideos;
    }

    @Override
    public int getCount() {
        return youtubeVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return youtubeVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View hView = convertView;
        if (convertView == null) {
            hView = mInflater.inflate(R.layout.list_item_blog, null);
            ViewHolder holder = new ViewHolder();
            holder.tv_title = (TextView) hView.findViewById(R.id.tv_title);
            holder.tv_author = (TextView) hView.findViewById(R.id.tv_author);
            holder.iv_blog = (ImageView) hView.findViewById(R.id.iv_blog);
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();

        // Set title and Image
        holder.tv_title.setText(youtubeVideos.get(position).getTitle());
        holder.tv_author.setText(youtubeVideos.get(position).getChannelTitle());
        Glide.with(holder.iv_blog.getContext()).load(youtubeVideos.get(position).getThumbnailMedium()).fitCenter().override(50, 50).dontTransform().into(holder.iv_blog);

        return hView;
    }


    class ViewHolder {
        TextView tv_title, tv_author;
        ImageView iv_blog;

    }
}

