/**
* @author DuanSky
* @date 2015��11��19�� ����8:17:13
* @content 
*/
package com.duansky.dreamspider.bean;

import java.io.Serializable;

public class SuccessPage extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
