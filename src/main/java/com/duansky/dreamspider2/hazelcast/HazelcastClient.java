/**
* @author DuanSky
* @date 2015��11��17�� ����4:41:28
* @content 
*/
package com.duansky.dreamspider2.hazelcast;

import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClient {
	
	private static volatile HazelcastInstance INSTANCE = null;
	
	public static HazelcastInstance getInstance(DreamSpiderConfig config){
		if(INSTANCE == null){
			synchronized (INSTANCE){
				ClientConfig clientConfig=new ClientConfig();
				clientConfig.getNetworkConfig().addAddress(config.getMasterIp());
				INSTANCE =com.hazelcast.client.HazelcastClient.newHazelcastClient(clientConfig);
			}
		}
		return INSTANCE;
	}
}
