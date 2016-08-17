package com.duansky.dreamspider2.bean;

import java.io.Serializable;

/**
 * Page 代表一次爬取结果，可能是一个完整的网页，也可能是一张图片。
 * 可能爬取成功，也可能爬取失败。
 * Created by shikai.dsk on 2016/8/12.
 */

public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一个URL，包装了URL的深度。
     */
    private UrlWapper urlWapper;

    /**
     * 资源的类型。（后缀名）
     */
    private String type;

    /**
     * URL指定资源的内容。
     */
    private String content;

    /**
     * URL指定资源的编码方式。
     */
    private String encoding;


    public Page(){}

    public Page(UrlWapper urlWapper,String type,String content,String encoding){
        this.urlWapper = urlWapper;
        this.type = type;
        this.content = content;
        this.encoding = encoding;
    }

    public UrlWapper getUrlWapper() {
        return urlWapper;
    }

    public void setUrlWapper(UrlWapper urlWapper) {
        this.urlWapper = urlWapper;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}