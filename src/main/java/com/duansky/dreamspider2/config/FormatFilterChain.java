package com.duansky.dreamspider2.config;

import com.duansky.dreamspider2.bean.UrlWapper;

import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public interface FormatFilterChain {
    UrlWapper getUrlWapper();
    boolean filter(String url);
}
