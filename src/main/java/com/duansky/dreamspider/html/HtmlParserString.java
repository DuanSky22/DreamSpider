/**
* @author DuanSky
* @date 2015年11月26日 下午2:31:35
* @content 
*/
package com.duansky.dreamspider.html;

public class HtmlParserString {
	public static final String URL_PATTERN="(\\b(http|https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
	public static final char CHARSET_STOP_CHAR[]={'"',' ','>','/'};
	public static final String META_TAGS="<(.*)meta(.*)charset(.*)>";
}
