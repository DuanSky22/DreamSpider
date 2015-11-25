/**
* @author DuanSky
* @date 2015年11月19日 下午8:16:05
* @content 
*/
package com.duansky.dreamspider.bean;

import java.io.Serializable;

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
