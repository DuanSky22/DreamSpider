/**
* @author DuanSky
* @date 2015年11月20日 下午2:21:05
* @content 
*/
package com.duansky.dreamspider.main;

import com.duansky.dreamspider.store.StorerInFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParseEngine {
	
	public static void main(String args[]){
		int coreNumber = Runtime.getRuntime().availableProcessors();
		//start the manager.
		DreamSpiderInstance dsi=DreamSpider.newInstance();
		Manager manager=new Manager(dsi);
		manager.start();
		//start the watcher.
		new Thread(new Watcher(manager)).start();
		//start the storer.
		ExecutorService es = Executors.newFixedThreadPool(coreNumber * 2);
		for(int i = 0; i < coreNumber; i++)
			es.submit(new StorerInFile(manager));

		while(true) {
			if (!manager.isAlive())
				System.exit(0);
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
