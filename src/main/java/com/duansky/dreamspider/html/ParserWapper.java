/**
 @author DuanSky
* @date 2015��11��18�� ����4:08:07
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
