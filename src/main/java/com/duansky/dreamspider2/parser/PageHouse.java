package com.duansky.dreamspider2.parser;

import com.duansky.dreamspider2.bean.Page;
import com.duansky.dreamspider2.bean.UrlWapper;

/**
 * PageHouse 存储所有爬取结束的网页。既包括成功爬取的网页，
 * 也包括爬取失败的网页。
 *
 * Created by shikai.dsk on 2016/8/16.
 */
public interface PageHouse {

    /**
     * 添加等待爬取的网页。
     * @param urlWapper 等待爬取的网页。
     */
    void addWaitingUrl(UrlWapper urlWapper);
    int getWaitingUrlSize();
    UrlWapper fetchWaitingUrl();

    /**
     * 添加成功爬取的网页。
     * @param page 成功爬取的网页。
     */
    void addSuccessfulPage(Page page);
    int getSuccessfulPageSize();
    Page fetchSuccessfulPage();

    /**
     * 添加爬取失败的网页。
     * @param page 爬取失败的网页。
     */
    void addErrorPage(Page page);
    int getErrorPageSize();
    Page fetchErrorPage();

}
