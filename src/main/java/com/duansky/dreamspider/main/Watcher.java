/**
* @author DuanSky
* @date 2015年11月24日 下午11:10:29
* @content 
*/
package com.duansky.dreamspider.main;


public class Watcher implements Runnable{
	private Manager manager;
	
	public Watcher(Manager manager){
		this.manager=manager;
	}
	
	public void run() {
		while(manager.isAlive()){
			try {
				Thread.sleep(10*1000);
				System.out.println(manager.getCurrentState());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("By now the manager is alive?"+manager.isAlive());
	}

}
