package com.mcxtzhang.cstnorecyclelistview.other;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.NestBean;

import java.util.List;


/**
 * 介绍：嵌套第二层Adapter
 * 本类用于验证重写onMeasure()方法的ListView，性能有多低。
 * getView会被重复调用多次
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/10.
 */

public class NestAdapter extends BaseAdapter {
    private static final String TAG = "zxt/NestAdapter";
    private List<NestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public NestAdapter(List<NestBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mDatas ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "嵌套第二层的 getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
        NestViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_nest_lv, parent, false);
            holder = new NestViewHolder();
            holder.nestIv = (ImageView) convertView.findViewById(R.id.nestIv);
            convertView.setTag(holder);
        } else {
            holder = (NestViewHolder) convertView.getTag();
        }
        NestBean nestBean = mDatas.get(position);
        Glide.with(mContext)
                .load(nestBean.getUrl())
                .into(holder.nestIv);
        return convertView;
    }

    private static class NestViewHolder {
        ImageView nestIv;
    }

}
