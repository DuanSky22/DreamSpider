package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;
import com.duansky.dreamspider2.config.impl.DefaultUrlFilterChain;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.UrlParser;
import com.duansky.dreamspider2.support.ParserWapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/13.
 */
public class DefaultUrlParser implements UrlParser {

    Log logger = LogFactory.getLog(getClass());

    private static final Parser parser = ParserWapper.getInstance();

    private List<UrlFilter> filters;

    public DefaultUrlParser(DreamSpiderContext context){
        this.filters = context.getFilters();
    }

    /**
     * 获取该URL指定的网页内所有的链接。该链接可能指向新的链接，也可能指向文档、
     * 视频等其他资源。因此需要通过过滤器链来过滤链接，只抓取用户真正想用的资源。
     * @param  subroot 需要爬取的URL链接
     * @return 该URL链接指定的内容内所有符合用户期望（过滤器链返回为true）的其他资源链接。
     */
    public List<UrlWapper> getUrls(UrlWapper subroot){
        List<UrlWapper> urls=new LinkedList<UrlWapper>();
        try {
            parser.setURL(subroot.getUrl());
            for(NodeIterator iter = parser.elements(); iter.hasMoreNodes();)
                _getUrlList(iter.nextNode(),subroot,urls);

        } catch (ParserException e) {
            if(logger.isDebugEnabled()) {
//                e.printStackTrace();
            }
        }
        return urls;
    }

    private void _getUrlList(Node node, UrlWapper subroot,List<UrlWapper> urls){
        if(node instanceof LinkTag){
            String url=((LinkTag) node).getLink();
            UrlWapper wapper = new UrlWapper(url,subroot.getDeep() + 1);
            UrlFilterChain chain = new DefaultUrlFilterChain(wapper,filters);
            if(chain.proceed(url))
                urls.add(wapper);
        }
        if(node==null || node.getChildren()==null)
            return;
        for(Node child : node.getChildren().toNodeArray())
            _getUrlList(child,subroot,urls);
    }
}
