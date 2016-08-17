package com.duansky.dreamspider2.parser;

import com.duansky.dreamspider2.bean.UrlWapper;

import java.util.List;

/**
 * UrlParser用来分析给定的URL中，包含的所有的合法的URL。
 * 合法的URL是指通过UrlFilterChain过滤的URL。
 * Created by shikai.dsk on 2016/8/13.
 */
public interface UrlParser {

    List<UrlWapper> getUrls(UrlWapper wapper);
}
