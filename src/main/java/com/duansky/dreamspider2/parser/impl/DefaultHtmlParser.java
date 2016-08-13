package com.duansky.dreamspider2.parser.impl;

import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterChain;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import com.duansky.dreamspider2.parser.HtmlParser;
import com.duansky.dreamspider2.support.ParserWapper;
import org.htmlparser.Parser;

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

    public List<String> getUrls() {
        return null;
    }
}
