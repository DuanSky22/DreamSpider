/**
* @author DuanSky
* @date 2015年11月17日 下午12:31:29
* @content 
*/
package com.duansky.dreamspider.redis;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastServer {

	public static HazelcastInstance instance=null;
	
	public static HazelcastInstance getInstance(){
		if(instance==null)
			instance=Hazelcast.newHazelcastInstance();
		return instance;
	}
	
	
}
