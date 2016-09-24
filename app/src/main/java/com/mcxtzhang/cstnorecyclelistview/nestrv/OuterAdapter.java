package com.mcxtzhang.cstnorecyclelistview.nestrv;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.TestBean;

import java.util.List;

/**
 * 介绍：外层Adapter
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/24.
 */

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.LvViewHolder> {
    private static final String TAG = "zxt/OuterAdapter";
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public OuterAdapter(List<TestBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public LvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LvViewHolder(mInflater.inflate(R.layout.item_nest_rv_1, parent, false));
    }

    @Override
    public void onBindViewHolder(LvViewHolder holder, int position) {
        TestBean testBean = mDatas.get(position);
        holder.tv.setText(testBean.getName());
        Glide.with(mContext)
                .load(testBean.getUrl())
                .into(holder.iv);
        holder.nestRv.setAdapter(new NestAdapter(testBean.getNest(), mContext));
        holder.nestRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    public static class LvViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        RecyclerView nestRv;

        public LvViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            nestRv = (RecyclerView) itemView.findViewById(R.id.nestRv);
        }
    }
}
