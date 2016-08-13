/**
 * For higher efficient, this HtmlParser is attach to DreamSpider.
 * @author DuanSky
 * @date 2015年11月18日 下午2:02:22
 * @content
 */
package com.duansky.dreamspider2.support;

import static com.duansky.dreamspider.html.HtmlParserString.CHARSET_STOP_CHAR;
import static com.duansky.dreamspider.html.HtmlParserString.META_TAGS;
import static com.duansky.dreamspider.html.HtmlParserString.URL_PATTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
public class HtmlParserSupport {

    //^(ht|f)tp(s?)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-‌​\.\?\,\'\/\\\+&amp;%\$#_]*)?$
    //private static String urlPattern="(http://[^/]*?"+"/[^.]*?).(shtml|html|htm|shtm|php|asp#|cgi|jsp|aspx|.*)";

    //<meta charset="utf-8">
    //<meta http-equiv="content-type" content="text/html; charset=GBK" />
    public static String getCharSet(String content){
        String charset=null;
        Pattern p=Pattern.compile(META_TAGS);
        Matcher m=p.matcher(content);
        if(m.find()){
            String finder=m.group(0); //find the meta which including charset.
            int charsetPos=finder.indexOf("charset"); //find the "charset" position.
            charsetPos=finder.indexOf("=",charsetPos); //swap the "=" & " " & """
            while(finder.substring(charsetPos,charsetPos+1).equals(" ") ||
                    finder.substring(charsetPos,charsetPos+1).equals("\"") ||
                    finder.substring(charsetPos,charsetPos+1).equals("="))
                charsetPos++;
            int charsetEndPos=findCharsetEndPos(charsetPos,finder);
            if(charsetPos>=0 && charsetEndPos<content.length())
                charset=finder.substring(charsetPos,charsetEndPos);
        }
        return charset;
    }

    private static int findCharsetEndPos(int start,String finder){
        int pos=Integer.MAX_VALUE;
        for(char stop : CHARSET_STOP_CHAR){
            int temp=finder.indexOf(stop,start+1);
            if(temp!=-1 && temp<pos)
                pos=temp;
        }
        return pos;
    }

    public List<String> getUrlList(String url){
        Parser parser=ParserWapper.getInstance();
        List<String> urls=new ArrayList<String>();
        try {
            parser.setURL(url);
            for(NodeIterator iter=parser.elements();iter.hasMoreNodes();)
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
            if()
                    urls.add(url);
        }
        if(node==null || node.getChildren()==null)
            return;
        for(Node child : node.getChildren().toNodeArray())
            _getUrlList(child,urls);
    }

    public static void main(String args[]){
//		List<String> domains=new ArrayList<String>();
//		domains.add("http://htmlparser.sourceforge.net/");
//		String url="http://htmlparser.sourceforge.net/123";
//		System.out.println(HtmlParser.getInstance(true, domains).isInside(url));

        //String content="abddaega<meta http-equiv=\"content-type\" content=\"text/html;charset  =  GBK  \"   />ageg";
        String content="<meta charset=\"utf-8>";
        System.out.println(HtmlParserSupport.getCharSet(content));
    }
}
