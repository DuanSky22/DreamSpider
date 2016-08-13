/**
 @author DuanSky
* @date 2015��11��18�� ����4:08:07
* @content 
*/
package com.duansky.dreamspider2.support;

import org.htmlparser.Parser;

public abstract class ParserWapper {
	
	private static Parser parser= new Parser();
	
	public static Parser getInstance(){
		return parser;
	}
}
