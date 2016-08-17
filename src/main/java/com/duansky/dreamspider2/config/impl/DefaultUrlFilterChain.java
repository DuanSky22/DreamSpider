package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link UrlFilterChain}的默认实现类。
 * Created by shikai.dsk on 2016/8/12.
 */
public class DefaultUrlFilterChain implements UrlFilterChain {
    /**
     * 需要判断的URL。
     */
    private UrlWapper urlWapper;

    /**
     * 注册的过滤器。
     */
    private List<UrlFilter> filters = new LinkedList<UrlFilter>();

    /**
     * 当前正在进行的过滤器的下标。
     */
    private int currentIndex = -1;

    public DefaultUrlFilterChain(UrlWapper urlWapper,List<UrlFilter> filters){
        this.urlWapper = urlWapper;
        this.filters = filters;
    }

    public boolean proceed(String url) {
        if(currentIndex == filters.size() - 1)
            return true;
        UrlFilter filter = filters.get(++currentIndex);
        boolean currResult = filter.proceed(this);
        /**如果没有通过当前的过滤器，则直接返回false;否则继续往下传。**/
        if(currResult == false)
            return false;
        else
            return proceed(url);
    }

    public UrlWapper getUrlWapper() {
        return urlWapper;
    }
}
