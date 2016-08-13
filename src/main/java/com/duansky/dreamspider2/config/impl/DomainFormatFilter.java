package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterChain;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DomainFormatFilter implements FormatFilter{

    private String domainString;
    private Pattern domainPattern;

    public DomainFormatFilter(String domain){
        this.domainString = "(http(s)://[^/]*?)"+domain+"/)(.*?)";
        this.domainPattern = Pattern.compile(domainString);
    }

    public DomainFormatFilter(List<String> domains){
        this(domains.toArray(new String[]{}));
    }

    public DomainFormatFilter(String[] domains){
        StringBuffer sb = new StringBuffer();
        /** 如果用户没有限定域名，则默认匹配任意字符。否则，匹配用户指定的域名。*/
        if(domains == null || domains.length == 0
                ||(domains.length == 1 && "".equals(domains[0])))
            sb.append("(*?)");
        else{
            sb.append("(");
            for(String domain : domains)
                sb.append(domain + "|");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        domainString = sb.toString();
        domainPattern = Pattern.compile(domainString);
    }


    public boolean proceed(FormatFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return domainPattern.matcher(url).matches();
    }
}
