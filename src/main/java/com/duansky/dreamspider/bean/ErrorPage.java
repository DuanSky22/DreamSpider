/**
* @author DuanSky
* @date 2015��11��19�� ����8:16:05
* @content 
*/
package com.duansky.dreamspider.bean;

import java.io.Serializable;

/**
 * ErrorPage is the page that the
 */
public class ErrorPage extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
