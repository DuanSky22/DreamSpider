package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DeafultFormatFilterRegistry implements FormatFilterRegistry {


    List<FormatFilter> filters = new LinkedList<FormatFilter>();

    public boolean registerFormatFilter(FormatFilter filter) {
        if(!filters.contains(filter)) {
            filters.add(filter);
            return true;
        }
        return false;
    }

    public boolean deleteFormatFilter(FormatFilter filter) {
        if(filters.contains(filter)) {
            filters.remove(filter);
            return true;
        }
        return false;
    }

    public int size() {
        return filters.size();
    }

    public List<FormatFilter> getFormatFilters(){
        return filters;
    }
}
