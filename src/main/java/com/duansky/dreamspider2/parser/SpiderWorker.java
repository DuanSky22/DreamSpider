package com.duansky.dreamspider2.parser;

import com.duansky.dreamspider2.bean.ErrorPage;
import com.duansky.dreamspider2.bean.Page;
import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.impl.DefaultUrlParser;
import com.duansky.dreamspider2.support.HtmlParserSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫的一个工作节点。
 * 用来从一个等待队列中获取一个URL，解析URL成功，则存入到成功爬取的队列中，
 * 同时分析该URL内部包含的其他链接；解析失败，则存入到爬取失败的队列中。
 * Created by shikai.dsk on 2016/8/16.
 */
public class SpiderWorker implements Runnable {

    Log logger = LogFactory.getLog(getClass());

    private PageHouse pageHouse;
    private DreamSpiderConfig config;
    private volatile boolean isStop = false;
    private UrlParser urlParser;

    public SpiderWorker(DreamSpiderContext context){
        this.pageHouse = context.getPageHouse();
        this.config = context.getConfig();
        this.urlParser = new DefaultUrlParser(context);
    }

    public void run() {
        while(!isStop && !Thread.currentThread().isInterrupted()){
            /**从等待的队列中取一条URL。**/
            UrlWapper urlWapper = pageHouse.fetchWaitingUrl();
            if (urlWapper != null) {
                String url = urlWapper.getUrl();
                CloseableHttpClient httpclient  = HttpClients.createDefault();
                CloseableHttpResponse response = getResponseByConfig(httpclient,url);
                if (response != null) {
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode >= 200 && statusCode < 300)
                        successParse(urlWapper, response);
                    else
                        failureParse(urlWapper, statusLine);
                }
            }
        }
    }

    public void stop(){
        this.isStop = true;
    }

    private void failureParse(UrlWapper urlWapper, StatusLine statusLine) {
        logger.info(String.format("parse failed for [%s]", urlWapper.getUrl()));
        ErrorPage page = new ErrorPage();
        page.setContent(statusLine.getReasonPhrase());
        page.setErrorCode(statusLine.getStatusCode());
        page.setUrlWapper(urlWapper);
        pageHouse.addErrorPage(page);
    }

    private void successParse(UrlWapper urlWapper,
                              CloseableHttpResponse response) {
        try {
            if(logger.isDebugEnabled())
                logger.info(String.format("parse success for [%s]", urlWapper.getUrl()));
            Page page = new Page();
            HttpEntity httpEntity = response.getEntity();

            String encoding;
            String content = "";

            String temp = null;
            if (httpEntity != null) {
                /**首先尝试从消息头中获取字符编码。**/
                if ((temp = HtmlParserSupport.getCharSetByEntity(httpEntity)) != null){
                    encoding = temp;
                    content= EntityUtils.toString(httpEntity,encoding);
                }
                /**如果无法从消息头中获取字符编码，则尝试从字节数组中获取消息编码。**/
                else{
                    byte[] in=EntityUtils.toByteArray(httpEntity);
                    encoding = HtmlParserSupport.getCharSetByByteArray(in);
                    if(encoding != null && encoding.length() != 0)
                        content = new String(in,encoding);
                    else
                        logger.info(String.format("Couldn't find the charset for %s", urlWapper.getUrl()));
                }
                /** 成功爬取网页后设置网页内容。**/
                page.setUrlWapper(urlWapper);
                page.setContent(content);
                page.setEncoding(encoding);
                page.setType(HtmlParserSupport.getUrlType(urlWapper.getUrl()));
                pageHouse.addSuccessfulPage(page);

                afterParse(urlWapper);
            }else{
                logger.info(String.format("empty content for ", urlWapper.getUrl()));
            }
        }catch (Exception e){
//            e.printStackTrace();
            logger.error(e);
        }
    }

    private void afterParse(UrlWapper urlWapper){
        String url = urlWapper.getUrl();
        int deep = urlWapper.getDeep();
        for(UrlWapper wapper : urlParser.getUrls(urlWapper)){
            pageHouse.addWaitingUrl(wapper);
        }
    }

    private CloseableHttpResponse getResponseByConfig(CloseableHttpClient httpclient,String url) {
        CloseableHttpResponse response = null;
        /**判断是否需要授权，如果需要，则在请求中加入用户名和密码**/
        if (config.isNeedAuthority()) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("username", config.getUsername()));
            nvps.add(new BasicNameValuePair("password", config.getPassword()));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                response = httpclient.execute(httpPost);
                return response;
            } catch (Exception e) {
//                e.printStackTrace();
                return null;
            }
        } else {
            try {
                HttpGet httpGet = new HttpGet(url);
                response = httpclient.execute(httpGet);
                return response;
            } catch (Exception e) {
//                e.printStackTrace();
                return null;
            }
        }
    }
}
