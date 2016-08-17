package com.duansky.dreamspider2.manager;

import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.PageHouse;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shikai.dsk on 2016/8/17.
 */
public class DefaultDreamSpiderMonitor implements DreamSpiderMonitor {

    PageHouse pageHouse;
    public DefaultDreamSpiderMonitor(DreamSpiderContext context){
        this.pageHouse = context.getPageHouse();
    }

    @Override
    public void print() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                currentStatus();
            }
        },1,5, TimeUnit.SECONDS);

    }

    public void currentStatus(){
        long waitingUrlSize = pageHouse.getWaitingUrlSize();
        long successfulPageSize = pageHouse.getSuccessfulPageSize();
        long errorPageSize = pageHouse.getErrorPageSize();
        System.out.println(new Date() +
                String.format(":(waiting Url Size:%s," +
                        "successful page size:%s," +
                        "error page size:%s)",waitingUrlSize,successfulPageSize,errorPageSize ));

    }

}
