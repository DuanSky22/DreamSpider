package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;
import com.duansky.dreamspider2.parser.DuplicateHandler;

/**
 * 去除重复URL的过滤链。
 * Created by shikai.dsk on 2016/8/16.
 */
public class RemoveDulpicateUrlFilter implements UrlFilter {

    /**
     * 判断URL是否重复的处理器。
     */
    private DuplicateHandler handler;

    public RemoveDulpicateUrlFilter(DuplicateHandler handler){
        this.handler = handler;
    }

    public boolean proceed(UrlFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return !handler.isDulpicate(url);
    }
}
