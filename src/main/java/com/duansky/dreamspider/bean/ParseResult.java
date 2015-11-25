 package com.duansky.dreamspider.bean;

import static com.duansky.dreamspider.util.DreamSpiderString.*;
import static com.duansky.dreamspider.util.DreamSpiderString.PARSED_URLS;
import static com.duansky.dreamspider.util.DreamSpiderString.TOTAL_PARSED_URL_NUMBER;
import static com.duansky.dreamspider.util.DreamSpiderString.TOTAL_URLS;
import static com.duansky.dreamspider.util.DreamSpiderString.WAITING_URLS;

import java.util.List;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ISet;
/**
 * ParseResult record the parse result, includes waiting/parsed/failed url list.
 * 
* @author DuanSky
* @date 2015年11月17日 下午4:33:51
* @content 
*/
public class ParseResult {
	
	private ILock lock=null; //only one thread can add url.
	
	private ISet<String> totalUrl; //total url including waiting/parsing/failed url.
	private IQueue<UrlWapper> waitingUrl; //current waiting url queue.
	private IQueue<Page> parsedUrl; //The waiting url been parsed will add to parsed url queue,but the 
									//store method will consume the parsed url queue, so this queue indicate
									//that the current left parsed url that didn't be consumed.
	private IList<Page> failedUrl;
	private IMap<String,Integer> totalParsedUrlNumber;
	

	
	public ParseResult(HazelcastInstance node){
		lock=node.getLock(DISTRIBUTE_LOCK);
		totalUrl=node.getSet(TOTAL_URLS);
		waitingUrl=node.getQueue(WAITING_URLS);
		parsedUrl=node.getQueue(PARSED_URLS);
		failedUrl=node.getList(FAILED_URLS);
		totalParsedUrlNumber=node.getMap(TOTAL_PARSED_URL_NUMBER);
		totalParsedUrlNumber.put(TOTAL_PARSED_URL_NUMBER, 0);
	}
	
	public boolean isParsed(String url){
		try{
		lock.lock();
		if(totalUrl.contains(url))
			return true;
		else
			return false;
		}
		finally{
			lock.unlock();
		}
	}
	
	public void addWaitingUrl(UrlWapper url){
		if(!isParsed(url.getUrl())){
			totalUrl.add(url.getUrl());
			waitingUrl.add(url);
		}
		
	}
	
	public UrlWapper fetchWaitingUrl(){
		return waitingUrl.poll();
	}
	
	public Page fetchParsedUrl(){
		return parsedUrl.poll();
	}
	
	public int getWaitingUrlSize(){
		return waitingUrl.size();
	}
	
	public void addParseUrl(Page url){
		try{
			totalParsedUrlNumber.lock(TOTAL_PARSED_URL_NUMBER);
			totalParsedUrlNumber.put(TOTAL_PARSED_URL_NUMBER, totalParsedUrlNumber.get(TOTAL_PARSED_URL_NUMBER)+1);
			parsedUrl.add(url);
		}
		finally{
			totalParsedUrlNumber.unlock(TOTAL_PARSED_URL_NUMBER);
		}
	}
	
	public int getPraseUrlSize(){
		return totalParsedUrlNumber.get(TOTAL_PARSED_URL_NUMBER);
	}
	
	public void addFailedUrl(Page url){
		try{
			totalParsedUrlNumber.lock(TOTAL_PARSED_URL_NUMBER);
			totalParsedUrlNumber.put(TOTAL_PARSED_URL_NUMBER, totalParsedUrlNumber.get(TOTAL_PARSED_URL_NUMBER)+1);
			failedUrl.add(url);
		}
		finally{
			totalParsedUrlNumber.unlock(TOTAL_PARSED_URL_NUMBER);
		}
	}
	
	public void addWaitingUrls(List<UrlWapper> urls){
		for(UrlWapper url : urls)
			addWaitingUrl(url);
	}

	public int getFailedUrlSize(){
		return failedUrl.size();
	}
	

	

}
