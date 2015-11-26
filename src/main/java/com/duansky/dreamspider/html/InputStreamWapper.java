/**
 * @author DuanSky
 * @date 2015年11月26日 下午3:01:02
 * @content 
 */
package com.duansky.dreamspider.html;

import static com.duansky.dreamspider.html.HtmlParserString.META_TAGS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.protocol.HTTP;

public class InputStreamWapper {

	private byte[] input = null;
	private String charset = HTTP.DEF_CONTENT_CHARSET.toString();
	private String content = "";

	public InputStreamWapper(byte[] input) {
		this.input = input;
		parse();
	}

	private void parse() {
		int foot=32;int current=0; int length=input.length;
		
		while(current<length){
			content+=new String(Arrays.copyOfRange(input, current, current+foot),HTTP.DEF_CONTENT_CHARSET);
			if(Pattern.compile(META_TAGS).matcher(content).matches()){
				charset=HtmlParser.getCharSet(content);
				break;
			}
			current+=foot;
		}
		try {
			content=new String(input,charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
	}

	public String getCharSet() {
		return charset;
	}

	public String getContent() {
		return content;
	}

}
