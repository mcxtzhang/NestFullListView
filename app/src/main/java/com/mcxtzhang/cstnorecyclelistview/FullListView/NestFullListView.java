package com.mcxtzhang.cstnorecyclelistview.FullListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：完全伸展开的ListView（LinearLayout）
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class NestFullListView extends LinearLayout {
    private LayoutInflater mInflater;
    private List<NestFullViewHolder> mVHCahces;//缓存ViewHolder,按照add的顺序缓存，

    public NestFullListView(Context context) {
        this(context, null);
    }

    public NestFullListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mVHCahces = new ArrayList<NestFullViewHolder>();
        //annotate by zhangxutong 2016 09 23 for 让本控件能支持水平布局，项目的意外收获= =
        //setOrientation(VERTICAL);
    }


    private NestFullListViewAdapter mAdapter;

    /**
     * 外部调用  同时刷新视图
     *
     * @param mAdapter
     */
    public void setAdapter(NestFullListViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        updateUI();
    }


    public void updateUI() {
        if (null != mAdapter) {
            if (null != mAdapter.getDatas() && !mAdapter.getDatas().isEmpty()) {
                //数据源有数据
                if (mAdapter.getDatas().size() > getChildCount() - getFooterCount()) {//数据源大于现有子View不清空

                } else if (mAdapter.getDatas().size() < getChildCount() - getFooterCount()) {//数据源小于现有子View，删除后面多的
                    removeViews(mAdapter.getDatas().size(), getChildCount() - mAdapter.getDatas().size() - getFooterCount());
                    //删除View也清缓存
                    while (mVHCahces.size() > mAdapter.getDatas().size()) {
                        mVHCahces.remove(mVHCahces.size() - 1);
                    }
                }
                for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                    NestFullViewHolder holder;
                    if (mVHCahces.size() - 1 >= i) {//说明有缓存，不用inflate，否则inflate
                        holder = mVHCahces.get(i);
                    } else {
                        holder = new NestFullViewHolder(getContext(), mInflater.inflate(mAdapter.getItemLayoutId(), this, false));
                        mVHCahces.add(holder);//inflate 出来后 add进来缓存
                    }
                    mAdapter.onBind(i, holder);
                    //如果View没有父控件 添加
                    if (null == holder.getConvertView().getParent()) {
                        this.addView(holder.getConvertView(), getChildCount() - getFooterCount());
                    }
                }
            } else {
                removeViews(0, getChildCount() - getFooterCount());//数据源没数据 清空视图
            }
        } else {
            removeViews(0, getChildCount() - getFooterCount());//适配器为空 清空视图
        }
    }

    /******
     * 2016 10 11 add 增加FooterView
     */
    private List<View> mFooterViews;//暂时用存View，觉得可以不存。还没考虑好

    public int getFooterCount() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    public void addFooterView(View footer) {
        if (null == mFooterViews) {
            mFooterViews = new ArrayList<>();
        }
        mFooterViews.add(footer);
        addView(footer);
    }

    /**
     * 在指定位置插入footerview
     *
     * @param pos
     * @param footer
     */
    public void setFooterView(int pos, View footer) {
        if (null == mFooterViews || mFooterViews.size() <= pos) {
            addFooterView(footer);
        } else {
            mFooterViews.set(pos, footer);
            //5 item 1 footer , pos 0, getchildcout =6, remove :
            int realPos = getChildCount() - getFooterCount() + pos;
            removeViewAt(realPos);
            addView(footer, realPos);
        }
    }

}
