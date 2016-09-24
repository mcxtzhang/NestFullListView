package com.mcxtzhang.cstnorecyclelistview.nestrv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.NestBean;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/24.
 */

public class NestAdapter extends RecyclerView.Adapter<NestAdapter.NestViewHolder> {

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
    public NestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        return new NestViewHolder(mInflater.inflate(R.layout.item_nest_rv_2, parent, false));
    }

    @Override
    public void onBindViewHolder(NestViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        Glide.with(mContext)
                .load(mDatas.get(position).getUrl())
                .into(holder.nestIv);
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    public static class NestViewHolder extends RecyclerView.ViewHolder {
        ImageView nestIv;

        public NestViewHolder(View itemView) {
            super(itemView);
            nestIv = (ImageView) itemView.findViewById(R.id.nestIv);
        }
    }
}
