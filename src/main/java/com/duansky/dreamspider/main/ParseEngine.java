/**
* @author DuanSky
* @date 2015年11月20日 下午2:21:05
* @content 
*/
package com.duansky.dreamspider.main;

import com.duansky.dreamspider.store.StorerInFile;

public class ParseEngine {
	
	public static void main(String args[]){
		DreamSpiderInstance dsi=DreamSpider.newInstance();
		Manager manager=new Manager(dsi);
		manager.start();
		
		new Thread(new Watcher(manager)).start();
		new Thread(new StorerInFile(manager)).start();
		
		while(true)
			if(!manager.isAlive())
				System.exit(0);
	}

}
