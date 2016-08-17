package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.DreamSpiderController;
import com.duansky.dreamspider2.parser.SpiderWorker;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫的控制器，采用观察者模式，将{@link SpiderWorker}注册
 * 到{@link DefaultDreamSpiderController}中，通过此类来实现对{@link SpiderWorker}
 * 的管理。
 * Created by shikai.dsk on 2016/8/16.
 */
public class DefaultDreamSpiderController implements DreamSpiderController {

    private DreamSpiderConfig config;
    private List<SpiderWorker> workers = new LinkedList<SpiderWorker>();

    public DefaultDreamSpiderController(DreamSpiderContext context){
        this.config = context.getConfig();
        int num = Math.min(config.getWorkerNumber(),
                Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < num; i++)
            registerSpiderWorker(new SpiderWorker(context));
    }

    public boolean registerSpiderWorker(SpiderWorker worker){
        if(!workers.contains(worker)){
            workers.add(worker);
            return true;
        }
        return false;
    }

    public boolean removeSpiderWorker(SpiderWorker worker){
        if(workers.contains(worker)){
            workers.remove(worker);
            return true;
        }
        return false;

    }

    public void parse() {
        ExecutorService es = Executors.newFixedThreadPool(workers.size());
        for(SpiderWorker worker : workers)
            es.submit(worker);
    }

    public void stop() {
        for(SpiderWorker worker : workers)
            worker.stop();
    }
}
