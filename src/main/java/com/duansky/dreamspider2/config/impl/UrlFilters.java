package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.parser.DuplicateHandler;

import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class UrlFilters {

    private static final UrlFilter URL_FORMAT_FILTER = new UrlFormatFilter();

    public static UrlFilter getUrlFormatFilter(){
        return newUrlFormatFilter(false);
    }

    public static UrlFilter newUrlFormatFilter(boolean getNew){
        if(getNew) return new UrlFormatFilter();
        else return URL_FORMAT_FILTER;
    }

    public static UrlFilter newExtensionsUrlFilter(List<String> extensions){
        return new ExtensionsUrlFilter(extensions);
    }

    public static UrlFilter newRemoveDulpicateUrlFilter(DuplicateHandler handler){
        return new RemoveDulpicateUrlFilter(handler);
    }

    public static UrlFilter newDomainUrlFilter(List<String> domains){
        return new DomainUrlFilter(domains);
    }

    public static UrlFilter newUrlDeepFilter(int maxDeep){
        return new UrlDeepFilter(maxDeep);
    }
}
