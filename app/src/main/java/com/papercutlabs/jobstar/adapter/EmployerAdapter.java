package com.papercutlabs.jobstar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.model.Employer;
import com.papercutlabs.jobstar.view.CustomTextView;

import java.util.ArrayList;

/**
 * Created by ritwik.rai on 07-04-2018.
 */

public class EmployerAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Employer> employers = new ArrayList<>();


    public EmployerAdapter(Context mContext, ArrayList<Employer> employers) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.employers = employers;
    }

    @Override
    public int getCount() {
        return employers.size();
    }

    @Override
    public Object getItem(int position) {
        return employers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View hView = convertView;
        if (convertView == null) {
            hView = mInflater.inflate(R.layout.include_employer_details, null);
            ViewHolder holder = new ViewHolder();
            holder.ctv_companyName = (CustomTextView) hView.findViewById(R.id.ctv_companyName);
            holder.ctv_current = (CustomTextView) hView.findViewById(R.id.ctv_current);
            holder.iv_check = (ImageView) hView.findViewById(R.id.iv_check);
            holder.iv_edit = (ImageView) hView.findViewById(R.id.iv_edit);
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();

        // Set title and Image
        holder.ctv_companyName.setText(employers.get(position).getEmployerName());
        // holder.ctv_current.setText(employers.get(position).getSource());
        // Glide.with(holder.iv_blog.getContext()).load(employers.get(position).getUrlImage()).centerCrop().override(50, 50).into(holder.iv_blog);

        return hView;
    }


    class ViewHolder {
        ImageView iv_check, iv_edit;
        CustomTextView ctv_companyName, ctv_current;

    }
}

