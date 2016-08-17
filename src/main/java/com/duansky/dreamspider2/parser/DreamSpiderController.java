package com.duansky.dreamspider2.parser;

/**
 * 爬虫的控制器。用来分发爬虫任务。
 * Created by shikai.dsk on 2016/8/16.
 */
public interface DreamSpiderController {
    void parse();
    void stop();
}
