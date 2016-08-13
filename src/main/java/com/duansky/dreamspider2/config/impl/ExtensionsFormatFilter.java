package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.FormatFilter;
import com.duansky.dreamspider2.config.FormatFilterChain;

import java.util.List;
import java.util.regex.Pattern;

/**
 *    扩展格式过滤器用来过滤URL的后缀名是否是用户指定的后缀名。
 * 以此可以用来过滤后缀名为.html,.doc,.pdf等等格式的URL。
 * Created by shikai.dsk on 2016/8/12.
 */
public class ExtensionsFormatFilter implements FormatFilter{

    private static final String PREFIX = "(http(s)://[^/]*?/[^.]*?).";

    private Pattern pattern;

    public ExtensionsFormatFilter(){
        this("");
    }

    public ExtensionsFormatFilter(String extension){
        this(new String[]{extension});
    }

    public ExtensionsFormatFilter(List<String> extensions){
        this(extensions.toArray(new String[]{}));
    }

    public ExtensionsFormatFilter(String[] extensions){
        StringBuffer sb = new StringBuffer();
        /** 如果用户没有限定后缀名，则默认匹配任意字符。否则，匹配用户指定的后缀名。*/
        if(extensions == null || extensions.length == 0
                ||(extensions.length == 1 && "".equals(extensions[0])))
            sb.append("(*?)");
        else{
            sb.append("(");
            for(String extension : extensions)
                sb.append(extension + "|");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        pattern = Pattern.compile(PREFIX + sb.toString());
    }

    public boolean proceed(FormatFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return pattern.matcher(url).matches();
    }
}
