/**
* @author DuanSky
* @date 2015年11月18日 下午1:44:48
* @content 
*/
package com.duansky.dreamspider.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UrlWapper urlWapper;
	private String content;
	private String encoding;
	
	public UrlWapper getUrlWapper() {
		return urlWapper;
	}
	
	public void setUrlWapper(UrlWapper urlWapper) {
		this.urlWapper = urlWapper;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
}
