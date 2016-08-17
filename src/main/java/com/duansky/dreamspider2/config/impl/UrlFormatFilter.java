package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;

import java.util.regex.Pattern;

/**
 * 判断一个对象是否是合法的URL。（过滤掉内部跳转等）
 * Created by shikai.dsk on 2016/8/12.
 */
public class UrlFormatFilter implements UrlFilter {

    private static final String URL_STRING = "(\\b(http|https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";

    private String urlString;
    private Pattern urlPattern;

    public UrlFormatFilter(){
        this(URL_STRING);
    }

    public UrlFormatFilter(String urlString){
        this.urlString = urlString;
        this.urlPattern = Pattern.compile(urlString);
    }

    public boolean proceed(UrlFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return urlPattern.matcher(url).matches();
    }
}
