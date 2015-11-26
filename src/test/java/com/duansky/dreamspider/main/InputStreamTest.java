/**
* @author DuanSky
* @date 2015年11月26日 下午4:00:41
* @content 
*/
package com.duansky.dreamspider.main;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class InputStreamTest {
	
	public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException{
		InputStream input=InputStreamTest.class.getClassLoader().getResourceAsStream("http___www.sohu.com_");
		InputStreamReader isr1=new InputStreamReader(input,"GBK");
		InputStreamReader isr2=new InputStreamReader(input,"GB2312");
		int i=0;
	}

}
