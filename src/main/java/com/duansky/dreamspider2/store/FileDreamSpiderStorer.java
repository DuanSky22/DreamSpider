package com.duansky.dreamspider2.store;

import com.duansky.dreamspider2.bean.Page;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.PageHouse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shikai.dsk on 2016/8/17.
 */
public class FileDreamSpiderStorer implements  DreamSpiderStorer {

    private final PageHouse pageHouse;
    private StoreStrategy storeStrategy;

    private volatile boolean isStoped = false;

    public FileDreamSpiderStorer(DreamSpiderContext context){
        this.pageHouse = context.getPageHouse();
        this.storeStrategy = new FileStoreStrategy(context);
    }

    @Override
    public void store() {
        multiThread();
    }

    @Override
    public void abandon() {
        isStoped = true;
    }

    private void singleThread(){
        while(!isStoped){
            Page successPage = pageHouse.fetchSuccessfulPage();
            System.out.println("===============store page:" + successPage+"=========");
            storeStrategy.store(successPage);
        }
    }

    private void multiThread(){
        int num = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService es = Executors.newFixedThreadPool(num);
        for(int i = 0; i < num; i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    while(!isStoped){
                        Page successPage = pageHouse.fetchSuccessfulPage();
                        storeStrategy.store(successPage);
                    }

                }
            });
        }
    }
}
