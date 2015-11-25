/**
 * @author DuanSky
 * @date 2015年11月17日 下午4:47:24
 * @content 
 */
package com.duansky.dreamspider.redis;

import com.duansky.dreamspider.main.DreamSpiderConfig;
import com.duansky.dreamspider.main.DreamSpiderConfig.Mode;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastNode {

	public static HazelcastInstance instance = null;

	public static void setNode(DreamSpiderConfig dsc) {
		if (dsc.getMode().equals(Mode.SINGLE))
			instance = HazelcastServer.getInstance();
		if (dsc.getMode().equals(Mode.ATTACH))
			instance = HazelcastClient.getInstance();
	}

	public static HazelcastInstance getNode() {
		if (instance == null)
			throw new IllegalArgumentException(
					"Please init the HazelcastNode use setNode(DreamSpiderConfig dsc) to init the class first.");
		return instance;
	}

}
