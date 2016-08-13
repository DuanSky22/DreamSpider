package com.duansky.dreamspider2.config;

import com.duansky.dreamspider2.config.impl.DeafultFormatFilterRegistry;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public interface FormatFilterRegistry {

    boolean registerFormatFilter(FormatFilter filter);
    boolean deleteFormatFilter(FormatFilter filter);
    int size();

    FormatFilterRegistry instance = new DeafultFormatFilterRegistry();

}
