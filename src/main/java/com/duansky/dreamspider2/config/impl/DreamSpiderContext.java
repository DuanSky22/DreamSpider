package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.DreamSpiderConfigParser;
import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterChain;
import com.duansky.dreamspider2.config.FormatFilterRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DreamSpiderContext {

    /**数据**/
    DreamSpiderConfig config;
    FormatFilterChain chain;

    /**工具类**/
    DreamSpiderConfigParser parser = DreamSpiderConfigParser.INSTANCE;
    FormatFilterRegistry registry = FormatFilterRegistry.instance;

    private void init(){
        config = parser.getDreamSpiderConfig();

    }

    private void initChain(){
        /**必须是合法的URL。**/
        registry.registerFormatFilter(FormatFilters.getUrlFormatFilter());

        /**URL的后缀名必须是用户指定的格式**/
        List<String> formats = config.getFormats();
        List<String> formatExtends = config.getFormatExtends();
        List<String> extensions = new LinkedList<String>();

        if(formats != null && formats.size() != 0)
            extensions.addAll(formats);
        if(formatExtends != null && formatExtends.size() != 0)
            extensions.addAll(formatExtends);

        registry.registerFormatFilter(FormatFilters.newExtensionsFormatFilter(extensions));

        /**URL是否必须在用户指定的域名内**/
        boolean inside = config.isOnlyInside();
        if(inside)
            registry.registerFormatFilter(FormatFilters.newDomainFormatFilter(config.getDir()));
    }


}
