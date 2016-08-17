package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.DreamSpiderConfigParser;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.FormatFilterRegistry;
import com.duansky.dreamspider2.hazelcast.HazelcastNode;
import com.duansky.dreamspider2.parser.DuplicateHandler;
import com.duansky.dreamspider2.parser.PageHouse;
import com.duansky.dreamspider2.parser.impl.BoundedPageHouse;
import com.duansky.dreamspider2.parser.impl.HazelcastDuplicateHandler;
import com.hazelcast.core.HazelcastInstance;

import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DreamSpiderContext {

    /**数据**/
    DreamSpiderConfig config;       //配置文件
    HazelcastInstance hazelcast;    //hazelcast的的实例对象
    PageHouse pageHouse;            //网页或链接仓库

    /**工具类**/
    DreamSpiderConfigParser parser = DreamSpiderConfigParser.INSTANCE;
    FormatFilterRegistry registry = FormatFilterRegistry.INSTANCE;
    DuplicateHandler handler;
    List<UrlFilter> filters;

    /**默认的单例对象，默认情况下，配置文件从"dream-spider.xml"读取。*/
    public static final DreamSpiderContext INSTANCE = new DreamSpiderContext(
            DreamSpiderConfigParser.INSTANCE.getDreamSpiderConfig()
    );

    public DreamSpiderContext(DreamSpiderConfig config){
        this.config = config;
        hazelcast = HazelcastNode.getNode(config);
        handler = new HazelcastDuplicateHandler(config);
        filters = getChain0();
        pageHouse = new BoundedPageHouse();
        initPageHouse();
    }

    /**
     * 将种子URL加入到队列中。
     */
    private void initPageHouse(){
        for(String seed : config.getUrlList())
            pageHouse.addWaitingUrl(new UrlWapper(seed,1));
    }

    protected List<UrlFilter> getChain0(){
        /**必须是合法的URL。**/
        registry.registerFormatFilter(UrlFilters.getUrlFormatFilter());

//        /**URL的后缀名必须是用户指定的格式**/
//        List<String> formats = config.getFormats();
//        List<String> formatExtends = config.getFormatExtends();
//        List<String> extensions = new LinkedList<String>();
//
//        if(formats != null && formats.size() != 0)
//            extensions.addAll(formats);
//        if(formatExtends != null && formatExtends.size() != 0)
//            extensions.addAll(formatExtends);

//        registry.registerFormatFilter(UrlFilters.newExtensionsUrlFilter(extensions));

        /**URL是否必须在用户指定的域名内**/
        boolean inside = config.isOnlyInside();
        if(inside)
            registry.registerFormatFilter(UrlFilters.newDomainUrlFilter(config.getUrlList()));

        /**URL 的深度不能超过最大值**/
        registry.registerFormatFilter(UrlFilters.newUrlDeepFilter(config.getDeep()));

        /**URL 不能重复**/
        registry.registerFormatFilter(UrlFilters.newRemoveDulpicateUrlFilter(handler));

        return registry.getFormatFilters();
    }

    public List<UrlFilter> getFilters(){
        return filters;
    }

    public DreamSpiderConfig getConfig() {
        return config;
    }

    public HazelcastInstance getHazelcast(){
        return hazelcast;
    }

    public PageHouse getPageHouse() {
        return pageHouse;
    }

    public void setPageHouse(PageHouse pageHouse) {
        this.pageHouse = pageHouse;
    }
}
