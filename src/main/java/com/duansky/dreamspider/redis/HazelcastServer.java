/**
* @author DuanSky
* @date 2015��11��17�� ����12:31:29
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
