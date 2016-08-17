package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import com.duansky.dreamspider2.hazelcast.HazelcastNode;
import com.duansky.dreamspider2.parser.DuplicateHandler;
import com.hazelcast.core.HazelcastInstance;

import java.util.Set;

/**
 * Created by shikai.dsk on 2016/8/16.
 */
public class HazelcastDuplicateHandler implements DuplicateHandler {

    private HazelcastInstance hazelcastInstance;
    private Set<String> urls;

    public HazelcastDuplicateHandler(DreamSpiderConfig config){
        this.hazelcastInstance = HazelcastNode.getNode(config);
        urls = hazelcastInstance.getSet("total-urls-with-no-dulpicate");
    }

    public boolean isDulpicate(String url) {
        if(urls.contains(url)){
            return true;
        }
        urls.add(url);
        return false;
    }
}
