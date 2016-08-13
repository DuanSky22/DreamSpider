/**
* @author DuanSky
* @date 2015��11��18�� ����1:43:39
* @content 
*/
package com.duansky.dreamspider2.bean;


import java.io.Serializable;

/**
 * Url 的包装类，用来封装URL以及深度。URL深度的定义参见
 * {@link com.duansky.dreamspider2.config.impl.DreamSpiderConfig}的deep属性。
 */
public class UrlWapper implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * URL
	 */
	private String url;

	/**
	 * URL的深度
	 */
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
		if(url == this) return true;
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
