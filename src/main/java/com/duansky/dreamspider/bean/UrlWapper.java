/**
* @author DuanSky
* @date 2015年11月18日 下午1:43:39
* @content 
*/
package com.duansky.dreamspider.bean;

import java.io.Serializable;

public class UrlWapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	private int deep;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	
	@Override 
	public boolean equals(Object url){
		if(url instanceof UrlWapper){
			UrlWapper temp=(UrlWapper) url;
			return temp.getUrl().equals(this.url);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.url.hashCode();
	}
	
}
