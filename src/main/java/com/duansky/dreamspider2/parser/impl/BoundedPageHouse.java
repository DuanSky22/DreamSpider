package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.bean.Page;
import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.parser.DuplicateHandler;
import com.duansky.dreamspider2.parser.PageHouse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * {@link PageHouse} 接口的默认实现类。该实现类中等待爬取、爬取成功或者失败的网页的数目
 * 是有限的。即最多允许用户指定的数目{maxWaitingPageNum,maxSuccessfulPageNum,maxErrorPageNum}的
 * 网页能够同时在内存中，防止内存撑爆。
 *
 * Created by shikai.dsk on 2016/8/16.
 */
public class BoundedPageHouse implements PageHouse {

    Log logger = LogFactory.getLog(getClass());

    DuplicateHandler handler = new LocalDuplicateHandler();

    /**
     * 队列中最多允许有多少个爬取成功的网页。
     */
    private static int DEFAULT_WAITING_URL_NUM = Integer.MAX_VALUE;

    /**
     * 队列中最多允许有多少个爬取成功的网页。
     */
    private static int DEFAULT_SUCCESSFUL_PAGE_NUM = Integer.MAX_VALUE;

    /**
     * 队列中最多允许有多少个爬取失败的网页。
     */
    private static int DEFAULT_ERROR_PAGE_NUM = Integer.MAX_VALUE;

    BlockingQueue<UrlWapper> waitingUrls;
    BlockingQueue<Page> successfulPages;
    BlockingQueue<Page> errorPages;

    public BoundedPageHouse(){
        this(DEFAULT_WAITING_URL_NUM,DEFAULT_SUCCESSFUL_PAGE_NUM,DEFAULT_ERROR_PAGE_NUM);
    }

    public BoundedPageHouse(int maxWaitingPageNum){
        this(maxWaitingPageNum,DEFAULT_SUCCESSFUL_PAGE_NUM,DEFAULT_ERROR_PAGE_NUM);
    }

    public BoundedPageHouse(int maxWaitingPageNum,int maxSuccessfulPageNum){
        this(maxWaitingPageNum,maxSuccessfulPageNum,DEFAULT_ERROR_PAGE_NUM);
    }

    public BoundedPageHouse(int maxWaitingPageNum,int maxSuccessfulPageNum,int maxErrorPageNum){
        waitingUrls = new LinkedBlockingQueue<UrlWapper>(maxWaitingPageNum);
        successfulPages = new LinkedBlockingQueue<Page>(maxSuccessfulPageNum);
        errorPages = new LinkedBlockingQueue<Page>(maxErrorPageNum);
    }

    /** for successful page operation.**/
    public void addSuccessfulPage(Page page) {
        try {
            successfulPages.put(page);
        }catch (InterruptedException e){
            logger.info(String.format("Successful page put operation for %s has interrupted.", page),e);
        }
    }

    public int getSuccessfulPageSize() {
        return successfulPages.size();
    }

    public Page fetchSuccessfulPage() {
        try {
            return successfulPages.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.info(String.format("Successful page take operation has been interrupted."),e);
        }
        return null;
    }

    /** for error page operation.**/
    public void addErrorPage(Page page) {
        try {
            errorPages.put(page);
        }catch (InterruptedException e){
            logger.info(String.format("Error page put operation for %s has interrupted.", page),e);
        }
    }

    public int getErrorPageSize() {
        return errorPages.size();
    }

    public Page fetchErrorPage() {
        try {
            return errorPages.take();
        } catch (InterruptedException e) {
            logger.info(String.format("Error page take operation has been interrupted."),e);
        }
        return null;
    }

    /** for waiting page operation.**/
    public void addWaitingUrl(UrlWapper urlWapper) {
        try {
            if(!handler.isDulpicate(urlWapper.getUrl()))
                waitingUrls.put(urlWapper);
        }catch (InterruptedException e){
            logger.info(String.format("Waiting url put operation for %s has interrupted.", urlWapper),e);
        }
    }

    public int getWaitingUrlSize() {
        return waitingUrls.size();
    }

    public UrlWapper fetchWaitingUrl() {
        try {
            return waitingUrls.take();
        } catch (InterruptedException e) {
            logger.info(String.format("Waiting url take operation has been interrupted."),e);
        }
        return null;
    }

    public DuplicateHandler getHandler() {
        return handler;
    }

    public void setHandler(DuplicateHandler handler) {
        this.handler = handler;
    }
}
