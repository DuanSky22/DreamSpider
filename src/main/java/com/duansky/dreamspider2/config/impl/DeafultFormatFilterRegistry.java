package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.FormatFilterRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DeafultFormatFilterRegistry implements FormatFilterRegistry {


    List<UrlFilter> filters = new LinkedList<UrlFilter>();

    public boolean registerFormatFilter(UrlFilter filter) {
        if(!filters.contains(filter)) {
            filters.add(filter);
            return true;
        }
        return false;
    }

    public boolean deleteFormatFilter(UrlFilter filter) {
        if(filters.contains(filter)) {
            filters.remove(filter);
            return true;
        }
        return false;
    }

    public int size() {
        return filters.size();
    }

    public List<UrlFilter> getFormatFilters(){
        return filters;
    }
}
