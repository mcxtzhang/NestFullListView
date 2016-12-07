package com.mcxtzhang.cstnorecyclelistview.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.cstnorecyclelistview.R;
import com.mcxtzhang.cstnorecyclelistview.bean.NestBean;
import com.mcxtzhang.cstnorecyclelistview.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类用于验证重写onMeasure()方法的ListView，性能有多低。
 * getView会被重复调用多次
 */
public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = "zxt/FullListView";
    private List<TestBean> mDatas;
    private ListViewForScrollView listViewForScrollView;
    private LvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initDatas();
        listViewForScrollView = (ListViewForScrollView) findViewById(R.id.lv);
        listViewForScrollView.setAdapter(mAdapter = new LvAdapter(mDatas, this));
    }

    private void initDatas() {
        int i = 0;
        mDatas = new ArrayList<>();
        ArrayList<NestBean> nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://jiangsu.china.com.cn/uploadfile/2015/0827/1440653790186574.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg", nestBeen));
        nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://imgs.ebrun.com/resources/2016_03/2016_03_24/201603244791458784582125_origin.jpg"));
        nestBeen.add(new NestBean("http://www.wccdaily.com.cn/hxdsb/20151204/6f443028313f1888b7a9fb19549d6ef6.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg", nestBeen));
        nestBeen = new ArrayList<>();
        nestBeen.add(new NestBean("http://img.mp.itc.cn/upload/20160427/316a154e56684a59b1e81df03a0860c4_th.png"));
        nestBeen.add(new NestBean("http://cdn.duitang.com/uploads/item/201509/17/20150917161810_exXGU.jpeg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", nestBeen));
        mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
        mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
        mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
    }

    public void add(View view) {
        mDatas.add(new TestBean("add", "http://finance.gucheng.com/UploadFiles_7830/201603/2016032110220685.jpg"));
        mAdapter.notifyDataSetChanged();
    }

    public void del(View view) {
        mDatas.remove(mDatas.size() - 1);
        mAdapter.notifyDataSetChanged();
    }
}
