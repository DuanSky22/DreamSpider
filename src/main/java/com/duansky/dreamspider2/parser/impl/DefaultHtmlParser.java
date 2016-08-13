package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.config.FormatFilterChain;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.HtmlParser;
import com.duansky.dreamspider2.support.ParserWapper;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikai.dsk on 2016/8/13.
 */
public class DefaultHtmlParser implements HtmlParser {

    private DreamSpiderContext context;
    private FormatFilterChain chain;
    private static final Parser parser = ParserWapper.getInstance();

    public DefaultHtmlParser(){
        this(DreamSpiderContext.INSTANCE);
    }

    public DefaultHtmlParser(DreamSpiderContext context){
        this.context = context;
        this.chain = context.getChain();
    }

    public List<String> getUrls(String url){
        List<String> urls=new ArrayList<String>();
        try {
            parser.setURL(url);
            for(NodeIterator iter = parser.elements(); iter.hasMoreNodes();)
                _getUrlList(iter.nextNode(),urls);

        } catch (ParserException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public List<String> getUrlList(Node node){
        List<String> urls=new ArrayList<String>();
        _getUrlList(node,urls);
        return urls;
    }

    private void _getUrlList(Node node,List<String> urls){
        if(node instanceof LinkTag){
            String url=((LinkTag) node).getLink();
            if(chain.proceed(url))
                urls.add(url);
        }
        if(node==null || node.getChildren()==null)
            return;
        for(Node child : node.getChildren().toNodeArray())
            _getUrlList(child,urls);
    }
}
