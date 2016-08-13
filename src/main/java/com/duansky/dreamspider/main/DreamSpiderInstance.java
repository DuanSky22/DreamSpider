package com.duansky.dreamspider.main;

import java.util.List;

import com.duansky.dreamspider.html.HtmlParser;
import com.duansky.dreamspider.redis.HazelcastNode;
import com.hazelcast.core.HazelcastInstance;

/**
 * Dream Spider Instance contains two important component,
 * one is Dream Spider config, another is distribute node.
 * 
* @author DuanSky
* @date 2015年11月17日 下午12:19:54
* @content 
*/

public class DreamSpiderInstance {
	
	private  DreamSpiderConfig dsc;
	private  HazelcastInstance node;
	
	public DreamSpiderInstance(){
		this(DreamSpiderConfigParser.getConfig());
	}
	
	public DreamSpiderInstance(DreamSpiderConfig dsc){
		init(dsc);
	}
	
	
	
	private void init(DreamSpiderConfig dsc){
		this.dsc=dsc;
		//init redis node
		HazelcastNode.setNode(dsc);
		node=HazelcastNode.getNode();
		//init html parser
		List<String> domains=dsc.getUrlList();
		boolean onlyInside=dsc.isOnlyInside();
		HtmlParser.getInstance(onlyInside,domains);
		
	}
	
	public  DreamSpiderConfig getDsc() {
		return dsc;
	}

	public  void setDsc(DreamSpiderConfig dsc) {
		this.dsc = dsc;
	}

	public  HazelcastInstance getNode() {
		return node;
	}

	public  void setNode(HazelcastInstance node) {
		this.node = node;
	}	
	
}
