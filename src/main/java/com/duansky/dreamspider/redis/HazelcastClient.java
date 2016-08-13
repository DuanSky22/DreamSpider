/**
* @author DuanSky
* @date 2015��11��17�� ����4:41:28
* @content 
*/
package com.duansky.dreamspider.redis;

import com.duansky.dreamspider.main.DreamSpiderConfig;
import com.duansky.dreamspider.main.DreamSpiderConfigParser;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClient {
	
	private static HazelcastInstance instance=null;
	
	public static HazelcastInstance getInstance(){
		if(instance==null){
			ClientConfig clientConfig=new ClientConfig();
			DreamSpiderConfig dsc= DreamSpiderConfigParser.getConfig();
			clientConfig.getNetworkConfig().addAddress(dsc.getMasterIp());
			instance=com.hazelcast.client.HazelcastClient.newHazelcastClient(clientConfig);
		}
		return instance;
	}

}
