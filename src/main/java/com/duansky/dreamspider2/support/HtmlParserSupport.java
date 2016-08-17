/**
 * For higher efficient, this UrlParser is attach to DreamSpider.
 * @author DuanSky
 * @date 2015年11月18日 下午2:02:22
 * @content
 */
package com.duansky.dreamspider2.support;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Asserts;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class HtmlParserSupport {

    public static final char CHARSET_STOP_CHAR[]={'"',' ','>','/'};
    public static final String META_TAGS=
            "([\\s\\S]*?)<([\\s\\S]*?)meta([\\s\\S]*?)charset([\\s\\S]*?)>([\\s\\S]*?)";

    //^(ht|f)tp(s?)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-‌​\.\?\,\'\/\\\+&amp;%\$#_]*)?$
    //private static String urlPattern="(http://[^/]*?"+"/[^.]*?).(shtml|html|htm|shtm|php|asp#|cgi|jsp|aspx|.*)";

    //<meta charset="utf-8">
    //<meta http-equiv="content-type" content="text/html; charset=GBK" />

    /**
     * 从字符串形式的内容中中获取字符编码。
     * @param content  内容
     * @return 返回正确方式编码下的字符串。
     */
    public static String getCharSetByContent(String content){
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


    /**
     * 从字节数组中获取字符编码。
     * @param input 字节数组
     * @return 返回正确方式编码下的字符串。
     */
    public static String getCharSetByByteArray(byte[] input) {
        int foot=64; int current=0; int length=input.length;
        String charset = null;
        StringBuilder temp = new StringBuilder(256);

        while(current<length){
            temp.append(new String(Arrays.copyOfRange(input, current, current + foot), HTTP.DEF_CONTENT_CHARSET));
            if(Pattern.compile(META_TAGS).matcher(temp).matches()){
                charset= getCharSetByContent(temp.toString());
                break;
            }
            current += foot;
        }
        return charset;
    }

    /**
     * 从Header（消息头）中获取文件的编码方式。
     * @param entity 消息实体
     * @return
     * @throws ParseException
     */
    public static String getCharSetByEntity(final HttpEntity entity) throws ParseException {
        if (entity == null) {
            return null;
        }
        String charset = null;
        if (entity.getContentType() != null) {
            HeaderElement values[] = entity.getContentType().getElements();
            if (values != null && values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");
                if (param != null) {
                    charset = param.getValue();
                }
            }
        }
        return charset;
    }

    public static String getUrlType(String url){
        Asserts.notEmpty(url,"url");
        int pos = url.lastIndexOf('.');
        if(pos == -1) return null;
        else return url.substring(pos + 1);
    }

    public static void main(String args[]){
//		List<String> domains=new ArrayList<String>();
//		domains.add("http://htmlparser.sourceforge.net/");
//		String url="http://htmlparser.sourceforge.net/123";
//		System.out.println(UrlParser.getInstance(true, domains).isInside(url));

        //String content="abddaega<meta http-equiv=\"content-type\" content=\"text/html;charset  =  GBK  \"   />ageg";
        String content="<meta charset=\"utf-8>";
        System.out.println(HtmlParserSupport.getCharSetByContent(content));
    }
}
