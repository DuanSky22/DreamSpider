package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.bean.UrlWapper;
import com.duansky.dreamspider2.config.UrlFilter;
import com.duansky.dreamspider2.config.UrlFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *    扩展格式过滤器用来过滤URL的后缀名是否是用户指定的后缀名。
 * 以此可以用来过滤后缀名为.html,.doc,.pdf等等格式的URL。
 * Created by shikai.dsk on 2016/8/12.
 */
public class ExtensionsUrlFilter implements UrlFilter {

    private static final String PREFIX = "(http(s)://[^/]*?/[^.]*?).";

    public static final String WEB_PAGE = "|html|htm|jsp|asp";
    public static final String PICTURE = "bmp|jpg|tiff|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw";
    public static final String VIDEO = "wmv|asf|rm|rmvb|mov|avi|dat|mpg|mpeg";
    public static final String DOC = "pdf|doc|docx|txt";

    private static final Map<String,String> map = new HashMap<String,String>();
    static {
        map.put("webpage",WEB_PAGE);
        map.put("picture",PICTURE);
        map.put("video",VIDEO);
        map.put("doc",DOC);
    }

    private Pattern pattern;

    public ExtensionsUrlFilter(){
        this("");
    }

    public ExtensionsUrlFilter(String extension){
        this(new String[]{extension});
    }

    public ExtensionsUrlFilter(List<String> extensions){
        this(extensions.toArray(new String[]{}));
    }

    public ExtensionsUrlFilter(String[] extensions){
        StringBuffer sb = new StringBuffer();
        /** 如果用户没有限定后缀名，则默认匹配任意字符。否则，匹配用户指定的后缀名。*/
        if(extensions == null || extensions.length == 0
                ||(extensions.length == 1 && "".equals(extensions[0])))
            sb.append("(*?)");
        else{
            sb.append("(");
            for(String extension : extensions)
                sb.append(map.get(extension.toLowerCase()) + "|");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        pattern = Pattern.compile(PREFIX + sb.toString());
    }

    public boolean proceed(UrlFilterChain chain) {
        UrlWapper urlWapper = chain.getUrlWapper();
        if(urlWapper == null) return false;
        String url = urlWapper.getUrl();
        return pattern.matcher(url).matches();
    }
}
