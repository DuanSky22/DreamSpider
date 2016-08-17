package com.duansky.dreamspider2.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 创建Hazelcast服务器。
 */
public class HazelcastServer {

	private static HazelcastInstance INSTANCE = Hazelcast.newHazelcastInstance();

	/**
	 * 以单例模式创建服务器，及在单独的JVM里，只存在一个Hazelcast实例。
	 * @return 已经存在的Hazelcast服务器模式单例对象。
	 */
	public static HazelcastInstance getInstance(){
		return INSTANCE;
	}
}
