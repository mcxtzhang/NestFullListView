package com.mcxtzhang.cstnorecyclelistview.bean;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/9.
 */
public class TestBean {
    private String name;
    private String url;
    private List<NestBean> nest;

    public TestBean(String name, String url, List<NestBean> nest) {
        this.name = name;
        this.url = url;
        this.nest = nest;
    }

    public List<NestBean> getNest() {
        return nest;
    }

    public void setNest(List<NestBean> nest) {
        this.nest = nest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TestBean(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
