package com.duansky.dreamspider2.config;

/**
 * 爬取地址的格式过滤器。
 * Created by shikai.dsk on 2016/8/12.
 */
public interface UrlFilter {

    boolean proceed(UrlFilterChain chain);

}
