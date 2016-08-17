package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;

/**
 * Created by shikai.dsk on 2016/8/16.
 */
public class UrlDeepFilter implements UrlFilter {

    private int maxDeep;

    public UrlDeepFilter(int maxDeep){
        this.maxDeep = maxDeep;
    }

    public boolean proceed(UrlFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        return urlWapper.getDeep() <= maxDeep;
    }
}
