 package com.duansky.dreamspider.bean;

import static com.duansky.dreamspider.util.DreamSpiderString.Z_DISTRIBUTE_LOCK;
import static com.duansky.dreamspider.util.DreamSpiderString.Z_SWAP_URLS;
import static com.duansky.dreamspider.util.DreamSpiderString.Z_TOTAL_URLS;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
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
	private IQueue<UrlWapper> swapUrl; //different node swap their url list. Every ten seconds the node will handle on their url.
	
	private LinkedBlockingQueue<UrlWapper> waitingUrl; //current waiting url queue.
	private ConcurrentLinkedQueue<Page> parsedUrl; //The waiting url been parsed will add to parsed url queue,but the 
									//store method will consume the parsed url queue, so this queue indicate
									//that the current left parsed url that didn't be consumed.
	private ConcurrentLinkedQueue<Page> failedUrl;
	private AtomicLong parsedUrlNumber;
	

	
	public ParseResult(HazelcastInstance node){
		lock=node.getLock(Z_DISTRIBUTE_LOCK);
		totalUrl=node.getSet(Z_TOTAL_URLS);
		swapUrl=node.getQueue(Z_SWAP_URLS);
		waitingUrl=new LinkedBlockingQueue<UrlWapper>();
		parsedUrl=new ConcurrentLinkedQueue<Page>();
		failedUrl=new ConcurrentLinkedQueue<Page>();
		parsedUrlNumber=new AtomicLong(0);
		
		swapUrl();
	}
	
	private void swapUrl(){
		Timer timer =new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				if(!waitingUrl.isEmpty()) //if(waitingUrl is not empty, we peek a url to add to swap url list.)
					swapUrl.add(waitingUrl.poll());
			}
		}, 10, 10*1000);
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
			parsedUrlNumber.incrementAndGet();
		}
		
	}
	
	public UrlWapper fetchWaitingUrl(){
		return waitingUrl.poll();
	}
	
	public void addWaitingUrlFromSwapUrl(){
		if(!swapUrl.isEmpty())
			waitingUrl.offer(swapUrl.poll());
	}
	
	public Page fetchParsedUrl(){
		return parsedUrl.poll();
	}
	
	public int getWaitingUrlSize(){
		return waitingUrl.size();
	}
	
	public boolean isWaitingUrlEmpty(){
		return waitingUrl.isEmpty();
	}
	
	public boolean isSwapUrlEmpty(){
		return swapUrl.isEmpty();
	}
	
	public void addParseUrl(Page url){
			parsedUrl.add(url);	
	}
	
	public long getPraseUrlSize(){
		return parsedUrlNumber.get();
	}
	
	public void addFailedUrl(Page url){
			failedUrl.add(url);
			parsedUrlNumber.incrementAndGet();
	}
	
	public void addWaitingUrls(List<UrlWapper> urls){
		for(UrlWapper url : urls)
			addWaitingUrl(url);
	}

	public int getFailedUrlSize(){
		return failedUrl.size();
	}
	

	

}
