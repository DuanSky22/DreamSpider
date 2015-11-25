package com.duansky.dreamspider.main;
/**
 * Use DreamSpiderInstance to init. It contains ParseResult to record 
 * the parsing result, it also monitors the parsing state.
* @author DuanSky
* @date 2015年11月19日 上午10:16:25
* @content 
*/
import static com.duansky.dreamspider.util.DreamSpiderString.ACTIVE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.duansky.dreamspider.bean.ParseResult;
import com.duansky.dreamspider.bean.UrlWapper;
import com.hazelcast.core.IMap;

public class Manager {
	
	private DreamSpiderInstance dsi=null;
	private ParseResult pr=null;
	private IMap<String,Boolean> active;
	private long delay=1000*60*5;
	private Map<String,Long> monitorMap=new HashMap<String,Long>();
	private DreamSpiderConfig dsc=null;
	
	public Manager(DreamSpiderInstance dsi){
		this.dsi=dsi;
		this.dsc=this.dsi.getDsc();
		pr=new ParseResult(dsi.getNode()); //here we will find that one manager contains it own parse result.
											//Different manager own different parse result.
		active=dsi.getNode().getMap(ACTIVE);
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				if(pr.getWaitingUrlSize()==0){
					if(pr.getPraseUrlSize()>dsc.getPageNubmer())
						active.put(ACTIVE, false);
					if(monitorMap.isEmpty()) //the waiting queue is empty for the first time.
						monitorMap.put("empty", System.currentTimeMillis());
					else{
						long current=System.currentTimeMillis();
						long before=monitorMap.get("empty");
						if(current-before<delay*1.5){
							active.put(ACTIVE, false);
							System.out.println("========The Waiting Queue is empty!========");
						}
						else
							monitorMap.put("empty", current);
					}
				}
			}
			
		}, 0,delay);
	}
	
	private void parse(){
		ExecutorService es=Executors.newFixedThreadPool(dsc.getWorkerNumber());
		for(int i=0;i<dsc.getWorkerNumber();i++){
			es.execute(new Worker(this));
		}
	}
	
	public void start(){
		active.put(ACTIVE, true);
		List<UrlWapper> urls=new ArrayList<UrlWapper>();
		for(String url : dsc.getUrlList()){
			UrlWapper urlWapper=new UrlWapper();
			urlWapper.setDeep(1);
			urlWapper.setUrl(url);
			urls.add(urlWapper);
		}
		pr.addWaitingUrls(urls);
		parse();
	}
	
	public void stop(){
		active.put(ACTIVE, false);
	}
	
	public boolean isAlive(){
		return active.get(ACTIVE);
	}
	

	public ParseResult getPr() {
		return pr;
	}

	public void setPr(ParseResult pr) {
		this.pr = pr;
	}

	public DreamSpiderConfig getDsc() {
		return dsc;
	}

	public void setDsc(DreamSpiderConfig dsc) {
		this.dsc = dsc;
	}
	
	public String getCurrentState(){
		return "[By now "+new Date()+",we have successfully parsed "+pr.getPraseUrlSize()+
				";there are "+pr.getWaitingUrlSize()+" still waiting for parse; and "+pr.getFailedUrlSize()+" url failed to"
						+ " parse.]";
	}

}
