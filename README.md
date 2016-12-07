# NestFullListView

【Android】ListView、RecyclerView、ScrollView里嵌套ListView 相对优雅的解决方案:NestFullListView

##  在哪里找到我：
我的github：

https://github.com/mcxtzhang

我的CSDN博客：

http://blog.csdn.net/zxt0601

我的稀土掘金：

http://gold.xitu.io/user/56de210b816dfa0052e66495

我的简书：

http://www.jianshu.com/users/8e91ff99b072/timeline
***

## 博文

【Android】ListView、RecyclerView、ScrollView里嵌套ListView 相对优雅的解决方案:NestFullListView

http://blog.csdn.net/zxt0601/article/details/52494665

## 使用：
Step 1. 在项目根build.gradle文件中增加JitPack仓库依赖。
```
    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
    dependencies {
	        compile 'com.github.mcxtzhang:NestFullListView:V1.0.0'
	}
```

Step 3.
in xml:
```
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <com.mcxtzhang.nestlistview.NestFullListView
                android:id="@+id/cstFullShowListView"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical" />
        </LinearLayout>

    </ScrollView>
```

in java:
```
        nestFullListView = (NestFullListView) findViewById(R.id.cstFullShowListView);

        nestFullListView.setAdapter(new NestFullListViewAdapter<TestBean>(R.layout.item_lv, mDatas) {
            @Override
            public void onBind(int pos, TestBean testBean, NestFullViewHolder holder) {
                Log.d(TAG, "嵌套第一层ScrollView onBind() called with: pos = [" + pos + "], testBean = [" + testBean + "], v = [" + holder + "]");
                //TextView tv = (TextView) v.findViewById(R.id.tv);
                holder.setText(R.id.tv, testBean.getName());

                Glide.with(MainActivity.this)
                        .load(testBean.getUrl())
                        .into((ImageView) holder.getView(R.id.iv));


                ((NestFullListView) holder.getView(R.id.cstFullShowListView2)).setAdapter(new NestFullListViewAdapter<NestBean>(R.layout.item_nest_lv, testBean.getNest()) {
                    @Override
                    public void onBind(int pos, NestBean nestBean, NestFullViewHolder holder) {
                        Log.d(TAG, "嵌套第二层onBind() called with: pos = [" + pos + "], nestBean = [" + nestBean + "], v = [" + holder + "]");
                        Glide.with(MainActivity.this)
                                .load(nestBean.getUrl())
                                .into((ImageView) holder.getView(R.id.nestIv));
                    }
                });
            }
        });
```

### 注意：

因为本控件是由LinearLayout改造而成，所以支持 水平和垂直布局。通过
`android:orientation="vertical"`改变。

---
## 更新日志:


2016 10 11 更新：
1 支持多种FooterView：
```
        TextView textView = new TextView(this);
        textView.setText("我是尾巴");
        nestFullListView.setFooterView(0, textView);
        TextView textView4 = new TextView(this);
        textView4.setText("我是尾巴4444444444444444");
        nestFullListView.addFooterView(textView4);
        
```
---

ScrollView里嵌套ListView，一直是Android开发者（反正至少是我们组）最讨厌的设计之一，完美打破ListView（RecyclerView）的复用机制，成功的将Native页面变成一个又臭又长的H5网页效果，但由于这种设计需求在我司项目实在太多见，无奈之下，我还是决定封装一下，毕竟，一个项目里同样的代码写第二遍的程序员都不是好的圣斗士。但是我真的是拒绝的 ！拒绝的！拒绝的！真的不喜欢这种界面：

![这里写图片描述](http://img.blog.csdn.net/20160822185350343?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

还拿我前两天做的这个项目来说吧，如上图，技能认可是一个“ListView”，工作经历是一个“ListView”，每个"ListView"的Item里还会有评论，评论又是一个“ListView”，项目经历 教育经历与此类似。。世界上最恐怖的事，不是ListView套ListView，是ListView套的ListView，里面还要继续嵌套ListView。。 
（题外话，这个页面头部是个巨幅Headerview，巨幅HeaderView里面嵌套最多两层ListView，然后底部还是一个分页的列表，不断加载更多.......  这个坑爹货也导致了我另一篇文章的产生： 让HeaderView也参与回收机制,自我感觉是优雅的为 RecyclerView 添加 HeaderView (FooterView)的解决方案http://blog.csdn.net/zxt0601/article/details/52267325 ）
***
##  竞品分析：
对于以上情况， 由于需要在ScrollView中嵌套ListView
，或者ListView中嵌套ListView....总结就是要嵌套ListView在另外的可以滑动的ViewGroup中，这就有两个问题，

**一，ListView和ViewGroup的滑动冲突。**

**二，ListView并不是全部展开的（View是复用的，ListView最多只有一屏的高度）。**

市面上的解决方案，常见三种：

1、手动遍历子View，设置ListView高度（麻烦，且**Item的根布局是RelativeLayout的时候无法测量**，在android系统版本在17级以下（包含17的时候），RelativeLayout.measure（w,h）时，会出现空指针，只能外层再套一个其他Layout，这是硬伤）

2、通过重写ListView的onMeasure()方法:

```
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
```

以前项目里常用这个，最容易百度出来的“最优”解，代码量最少，那时年少的我看到它是如获至宝的，因为那篇文章里口口声声告诉我，这样的话View还可以复用，真的很”优雅”。
但我经过实战发现Adapter的getView()会被重复调用多次，如果嵌套两层,getView（）倍数调用，太伤性能，**它根本不能复用View，不仅不复用，反而变本加厉**。
故弃用之。**下一节中会提供证据**,一定让你李菊福。
而且在某些极端情况下，例如每个Item的高度不一样，这个ListView的高度计算偶尔会不准确。

3、**使用LinearLayout模拟ListView**（写起来麻烦，inflate 的死去活来，但无明显缺点。

一开始我是拒绝这种方案的，太傻啦，自己inflate addView findViewById 多蠢，我有方法2 搭配CommonAdapter ViewHolder等工具类，要他何用。
但是在我知道方法2的真面目后，我只能选用本方法，它至少不会多次调用getView(),重复渲染视图，反正**View的复用机制已经被打破，使用ListView不再有任何意义**。

So本文就是基于此种思路，封装一下固定代码，方便二次快速使用，且尽量的优化，一定程度上提高性能）

###本文做了啥：
抽象封装往LinearLayout里inflate，addView的过程，暴漏出绑定数据的方法，并一定程度上考虑性能，缓存View。
在此基础上，利用ViewHolder 思想，尽量避免每次刷新都走findViewById这些耗性能的方法。

###为啥要使用ViewHolder，为什么要封装这些缓存？

 这种页面往往需要刷新，最无脑的办法就是removeAllViews(),简单粗暴，啥都不考虑，用户体验将会变成，刷新时闪一下，很差，因为View全部要inflate，addView，findViewById一遍。
所以我们在封装的NestFullListView里**尽力避免刷新时** View的inflate addView，
在ViewHolder **尽力避免刷新时**  findViewById();




##  总结：
其实这种方法，真的称不上优雅，只不过跟别的方法比起来，相对优雅吧。
在我心中最好的方法就是利用RecyclerView的ItemViewType来解决，可惜由于接口，以及数据结构限制，只能退而求其次。如若有朋友有更好的办法，欢迎交流。
再次强调一遍~本文的方法只是尽可能的**节省刷新时的性能消耗**，
不再每次都无脑removeAllViews()，inflate()，addView()。
利用通用的ViewHolder，减少刷新时的findViewById()操作。

不管是ListViewForScrollView 还是本文的NestFullListView，
它们都是在一开始，就把所有的子View统统inflate add bind好了，
不像ListView，RecyclerView..兄弟们，是子View在屏幕上可见时才创建，添加，数据绑定。


欢迎讨论交流，拍板砖，如有更优方法，真心求指教。
***
