package com.duansky.dreamspider2;

import com.duansky.dreamspider2.config.impl.DreamSpiderContext;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class Main {

    public static void main(String args[]) throws InterruptedException {
        DreamSpiderContext context = DreamSpiderContext.INSTANCE;
        DreamSpider spider = new DefaultDreamSpider(context);
        spider.start();
    }


}
