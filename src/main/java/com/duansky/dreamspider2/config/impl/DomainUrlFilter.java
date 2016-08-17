package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;

import java.util.*;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DomainUrlFilter implements UrlFilter {


    private List<String> domains = new LinkedList<>();

    public DomainUrlFilter(String domain){
        this(new String[]{domain});
    }

    public DomainUrlFilter(String[] domains){
        this(new LinkedList<String>(Arrays.asList(domains)));
    }

    public DomainUrlFilter(List<String> domains){
        this.domains.addAll(domains);
    }

    public boolean isInDomain(String url){
        for(String domain : domains){
            if(url.contains(domain))
                return true;
        }
        return false;
    }

    public boolean proceed(UrlFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return isInDomain(url);
    }
}
