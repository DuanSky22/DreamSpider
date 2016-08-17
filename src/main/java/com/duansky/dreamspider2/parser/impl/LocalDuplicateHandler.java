package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.parser.DuplicateHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shikai.dsk on 2016/8/17.
 */
public class LocalDuplicateHandler implements DuplicateHandler {

    Set<String> set = new HashSet<>(1024);

    @Override
    public synchronized boolean isDulpicate(String url) {
        if(set.contains(url)){
            return true;
        }else{
            set.add(url);
            return false;
        }
    }
}
