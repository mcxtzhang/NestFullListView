package com.mcxtzhang.cstnorecyclelistview.other;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.TestBean;

import java.util.List;

/**
 * 介绍：嵌套第一层Adapter
 * 本类用于验证重写onMeasure()方法的ListView，性能有多低。
 * getView会被重复调用多次
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/10.
 */

public class LvAdapter extends BaseAdapter {
    private static final String TAG = "zxt/LvAdapter";
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public LvAdapter(List<TestBean> mDatas, Context mContext) {
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
        Log.d(TAG, "嵌套第1层的 getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
        LvViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_list_view, parent, false);
            holder = new LvViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.lv = (ListViewForScrollView) convertView.findViewById(R.id.lv2);
            convertView.setTag(holder);
        } else {
            holder = (LvViewHolder) convertView.getTag();
        }
        TestBean testBean = mDatas.get(position);
        holder.tv.setText(testBean.getName());
        Glide.with(mContext)
                .load(testBean.getUrl())
                .into(holder.iv);
        holder.lv.setAdapter(new NestAdapter(testBean.getNest(), mContext));

        return convertView;
    }

    private static class LvViewHolder {
        TextView tv;
        ImageView iv;
        ListViewForScrollView lv;
    }

}
