/**
* @author DuanSky
* @date 2015��11��19�� ����5:13:34
* @content 
*/
package com.duansky.dreamspider.main;

public class DreamSpider {
	
	public static DreamSpiderInstance newInstance(){
		return new DreamSpiderInstance();
	}
	
	public static DreamSpiderInstance newInstance(DreamSpiderConfig dsc){
		return new DreamSpiderInstance(dsc);
	}

}
