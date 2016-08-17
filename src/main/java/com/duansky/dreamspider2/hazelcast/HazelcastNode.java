/**
 * @author DuanSky
 * @date 2015年11月17日 下午4:47:24
 * @content 
 */
package com.duansky.dreamspider2.hazelcast;


import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastNode {

	public static HazelcastInstance getNode(DreamSpiderConfig config) {
		HazelcastInstance INSTANCE = null;
		if (config.getMode().equals(DreamSpiderConfig.Mode.SINGLE))
			INSTANCE = HazelcastServer.getInstance();
		if (config.getMode().equals(DreamSpiderConfig.Mode.ATTACH))
			INSTANCE = HazelcastClient.getInstance(config);
		return INSTANCE;
	}
}
