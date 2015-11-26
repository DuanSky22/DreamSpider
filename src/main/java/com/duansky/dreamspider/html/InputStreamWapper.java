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
import java.util.regex.Pattern;

import org.apache.http.protocol.HTTP;

public class InputStreamWapper {
	
	private InputStream input=null;
	private String charset=HTTP.DEF_CONTENT_CHARSET.toString();
	private boolean findCharset=false;
	private String content="";
	
	public InputStreamWapper(InputStream input){
		this.input=input;
		parse();
	}
	
	private void parse(){
		InputStreamReader reader1=new InputStreamReader(input,HTTP.DEF_CONTENT_CHARSET);
		String e1=reader1.getEncoding();
		BufferedReader br = new BufferedReader(reader1);
		try {
			String line="";
			while((line=br.readLine())!=null){
				content+=line;
				if(Pattern.compile(META_TAGS).matcher(line).matches()){
					charset = HtmlParser.getCharSet(content);
					findCharset=true;
					break;
				}
			}
			if(findCharset){
//				Charset cst=Charset.forName(charset);
//				SortedMap<String, Charset> sc=Charset.availableCharsets();
				InputStreamReader reader2=new InputStreamReader(input,charset.toUpperCase());
				String e2=reader2.getEncoding();
				br=new BufferedReader(reader2);
			}
			
			while((line=br.readLine())!=null){
				content+=line;
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	public String getCharSet(){
		return charset;
	}
	
	public String getContent(){
		return content;
	}

}
