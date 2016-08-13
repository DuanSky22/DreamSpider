package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterChain;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link FormatFilterChain}的默认实现类。
 * Created by shikai.dsk on 2016/8/12.
 */
public class DefaultFormatFilterChain implements FormatFilterChain{
    /**
     * 需要判断的URL。
     */
    private UrlWapper urlWapper;

    /**
     * 注册的过滤器。
     */
    private List<FormatFilter> filters = new LinkedList<FormatFilter>();

    /**
     * 当前正在进行的过滤器的下标。
     */
    private int currentIndex = -1;

    /**
     * 过滤结果。
     */
    private boolean result = true;

    public DefaultFormatFilterChain(List<FormatFilter> filters){
        this.filters = filters;
    }

    public boolean filter(String url) {
        if(currentIndex == filters.size() - 1)
            return result;
        FormatFilter filter = filters.get(++currentIndex);
        boolean currResult = filter.proceed(this);
        /**如果没有通过当前的过滤器，则直接返回false;否则继续往下传。**/
        if(currResult == false)
            return false;
        else
            return filter(url);
    }

    public UrlWapper getUrlWapper() {
        return urlWapper;
    }
}
