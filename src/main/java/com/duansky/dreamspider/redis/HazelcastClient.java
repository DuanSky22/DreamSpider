/**
* @author DuanSky
* @date 2015年11月17日 下午4:41:28
* @content 
*/
package com.duansky.dreamspider.redis;

import com.duansky.dreamspider.main.DreamSpiderConfig;
import com.duansky.dreamspider.main.SpiderConfigParser;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClient {
	
	private static HazelcastInstance instance=null;
	
	public static HazelcastInstance getInstance(){
		if(instance==null){
			ClientConfig clientConfig=new ClientConfig();
			DreamSpiderConfig dsc=SpiderConfigParser.getConfig();
			clientConfig.getNetworkConfig().addAddress(dsc.getMasterIp());
			instance=com.hazelcast.client.HazelcastClient.newHazelcastClient(clientConfig);
		}
		return instance;
	}

}
