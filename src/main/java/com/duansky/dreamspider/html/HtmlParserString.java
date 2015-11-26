/**
* @author DuanSky
* @date 2015年11月26日 下午2:31:35
* @content 
*/
package com.duansky.dreamspider.html;

import java.util.regex.Pattern;

public class HtmlParserString {
	public static final String URL_PATTERN="(\\b(http|https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
	public static final char CHARSET_STOP_CHAR[]={'"',' ','>','/'};
	public static final String META_TAGS="([\\s\\S]*?)<([\\s\\S]*?)meta([\\s\\S]*?)charset([\\s\\S]*?)>([\\s\\S]*?)";
	
	//<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
//	<html xmlns="http://www.w3.org/1999/xhtml">
//	<head>
//	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
//	 <meta http-e
	public static void main(String args[]){
		String content="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \n"
				+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
				+"<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
				+ "<meta http-e\"";
		System.out.println(Pattern.compile(META_TAGS).matcher(content).matches());
	}
}
