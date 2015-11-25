/**
 * For higher efficient, this HtmlParser is attach to DreamSpider.
* @author DuanSky
* @date 2015年11月18日 下午2:02:22
* @content 
*/
package com.duansky.dreamspider.html;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class HtmlParser {
	
	public static void main(String args[]){
		List<String> domains=new ArrayList<String>();
		domains.add("http://htmlparser.sourceforge.net/");
		String url="http://htmlparser.sourceforge.net/123";
		System.out.println(HtmlParser.getInstance(true, domains).isInside(url));
	}
	
	//^(ht|f)tp(s?)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-‌​\.\?\,\'\/\\\+&amp;%\$#_]*)?$
	//private static String urlPattern="(http://[^/]*?"+"/[^.]*?).(shtml|html|htm|shtm|php|asp#|cgi|jsp|aspx|.*)";
	private static boolean onlyInside=true; //parsed url must is the inside web.
	private static List<String> domains=new ArrayList<String>(); // the root web url.
	private static String urlPattern="(\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
	
	public static HtmlParser instance = null;
	private HtmlParser(boolean onlyInside,List<String> domains){
		HtmlParser.onlyInside=onlyInside;
		HtmlParser.domains=domains;
	}
	
	public static HtmlParser getInstance(boolean onlyInside,List<String> domains){
		if(instance==null)
			instance=new HtmlParser(onlyInside,domains);
		return instance;
	}
	
	public static HtmlParser getInstance(){
		return instance;
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
			if(isLegalUrl(url))
				if(onlyInside && isInside(url))
					urls.add(url);
		}
		if(node==null || node.getChildren()==null)
			return;
		for(Node child : node.getChildren().toNodeArray())
			_getUrlList(child,urls);
	}
	

	private boolean isLegalUrl(String url){
		if(url==null || url.length()<=7)
			return false;
		return Pattern.compile(urlPattern).matcher(url).matches();
	}
	
	private boolean isInside(String url){
		for(String domain : domains){
			if(url.contains(domain))
				return true;
		}
		return false;
	}



}
