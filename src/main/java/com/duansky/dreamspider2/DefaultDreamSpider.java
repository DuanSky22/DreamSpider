package com.duansky.dreamspider2;

import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.manager.DefaultDreamSpiderMonitor;
import com.duansky.dreamspider2.manager.DreamSpiderMonitor;
import com.duansky.dreamspider2.parser.DreamSpiderController;
import com.duansky.dreamspider2.parser.impl.DefaultDreamSpiderController;
import com.duansky.dreamspider2.store.DreamSpiderStorer;
import com.duansky.dreamspider2.store.FileDreamSpiderStorer;

/**
 * Created by shikai.dsk on 2016/8/16.
 */
public class DefaultDreamSpider implements DreamSpider {

    DreamSpiderContext context;

    DreamSpiderController controller;

    DreamSpiderStorer storer;

    DreamSpiderMonitor monitor;

    public DefaultDreamSpider(DreamSpiderContext context){
        this.context = context;
        controller = new DefaultDreamSpiderController(context);
        storer = new FileDreamSpiderStorer(context);
        monitor = new DefaultDreamSpiderMonitor(context);
    }

    public void start() {
        controller.parse();
        storer.store();
        monitor.print();
    }

    public void stop() {
        controller.stop();
    }
}
