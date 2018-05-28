package com.papercutlabs.jobstar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Job;
import com.papercutlabs.jobstar.view.CustomTextView;

import java.util.ArrayList;

/**
 * Created by ritwik.rai on 07-04-2018.
 */

public class JobListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Job> jobs = new ArrayList<>();


    public JobListViewAdapter(Context mContext, ArrayList<Job> jobs) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.jobs = jobs;
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Object getItem(int position) {
        return jobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View hView = convertView;
        if (convertView == null) {
            hView = mInflater.inflate(R.layout.item_list_search_result, null);
            ViewHolder holder = new ViewHolder();
            holder.ctv_job_location = (CustomTextView) hView.findViewById(R.id.ctv_job_location);
            holder.ctv_jobTitle = (CustomTextView) hView.findViewById(R.id.ctv_jobTitle);
            holder.ctv_companyName = (CustomTextView) hView.findViewById(R.id.ctv_companyName);
            holder.ctv_contractType = (CustomTextView) hView.findViewById(R.id.ctv_contractType);
            holder.ctv_date = (CustomTextView) hView.findViewById(R.id.ctv_date);
            holder.ll_organization_layout = (LinearLayout) hView.findViewById(R.id.ll_organization_layout);
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();

        holder.ctv_job_location.setText(jobs.get(position).getJobLocation());
        holder.ctv_jobTitle.setText(jobs.get(position).getJobTitle());
        holder.ctv_companyName.setText(jobs.get(position).getCompanyName());
        holder.ctv_contractType.setText(jobs.get(position).getContractType());
        holder.ctv_date.setText(jobs.get(position).getDateOfPosting());
        holder.ctv_companyName.setText(jobs.get(position).getCompanyName());
        // holder.ctv_current.setText(jobs.get(position).getSource());
        // Glide.with(holder.iv_blog.getContext()).load(jobs.get(position).getUrlImage()).centerCrop().override(50, 50).into(holder.iv_blog);

        // LOGIC TO CHANGE ORG. BACKGROUND COLOR
        holder.ll_organization_layout.setBackgroundResource(jobs.get(position).getBackgroundDrawable());
        return hView;
    }


    class ViewHolder {
        CustomTextView ctv_job_location, ctv_jobTitle, ctv_companyName, ctv_contractType, ctv_date;
        LinearLayout ll_organization_layout;
    }
}

