package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.FormatFilter;

import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class FormatFilters {

    private static final FormatFilter URL_FORMAT_FILTER = new UrlFormatFilter();

    public static FormatFilter getUrlFormatFilter(){
        return newUrlFormatFilter(false);
    }

    public static FormatFilter newUrlFormatFilter(boolean getNew){
        if(getNew) return new UrlFormatFilter();
        else return URL_FORMAT_FILTER;
    }

    public static FormatFilter newExtensionsFormatFilter(List<String> extensions){
        return new ExtensionsFormatFilter(extensions);
    }

    public static FormatFilter newDomainFormatFilter(List<String> domains){
        return new DomainFormatFilter(domains);
    }
}
