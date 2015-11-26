/**
 @author DuanSky
* @date 2015年11月18日 下午4:08:07
* @content 
*/
package com.duansky.dreamspider.html;

import org.htmlparser.Parser;

public class ParserWapper {
	
	static Parser parser=null;
	
	public static Parser getInstance(){
		if(parser==null)
			parser=new Parser();
		return parser;
	}
}
