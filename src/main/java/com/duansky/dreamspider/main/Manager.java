package com.duansky.dreamspider.main;

/**
 * Use DreamSpiderInstance to init. It contains ParseResult to record 
 * the parsing result, it also monitors the parsing state.
 * @author DuanSky
 * @date 2015年11月19日 上午10:16:25
 * @content 
 */
import static com.duansky.dreamspider.util.DreamSpiderString.Z_ACTIVE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.duansky.dreamspider.bean.ParseResult;
import com.duansky.dreamspider.bean.UrlWapper;
import com.hazelcast.core.IMap;

public class Manager {
	// All managers share the same state. Shared by multi-process(or node)
	private IMap<String, Boolean> active; // main switch. are all managers
											// alive?

	private DreamSpiderInstance dsi = null;
	private ParseResult pr = null;
	private DreamSpiderConfig dsc = null;

	// This manager's state.Shared by multi-thread.(The same process)
	private AtomicInteger emptyTimes = new AtomicInteger(0); // how many times
																// that the
																// waiting queue
																// is empty.
	private final int MAX_EMPTY_NUMS = 600; // if more than MAX_EMPTY_NUMS times
											// the waiting queue is still empty,
											// we shut down it.
	private AtomicBoolean currentManagerState = new AtomicBoolean(true); // is
																			// this
																			// manager
																			// alive?

	public Manager(DreamSpiderInstance dsi) {
		this.dsi = dsi;
		this.dsc = this.dsi.getDsc();
		this.pr = new ParseResult(dsi.getNode()); // here we will find that one
													// manager contains it own
													// parse result.
		// Different manager own different parse result.
		this.active = dsi.getNode().getMap(Z_ACTIVE);
		try {
			active.lock(Z_ACTIVE);
			if (active.isEmpty())
				active.put(Z_ACTIVE, true);
		} finally {
			active.unlock(Z_ACTIVE);
			monitor();
		}
	}

	private void monitor() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!active.get(Z_ACTIVE)) {
					System.out
							.println("========The controller has shut down all managers!========");
				}
				if (pr.getPraseUrlSize() > dsc.getPageNubmer()) {
					currentManagerState.set(false);
					System.out
							.println("========The number of this manager can parsed is overflow!========");
				}
				if (pr.getWaitingUrlSize() == 0) {

					if (emptyTimes.incrementAndGet() > MAX_EMPTY_NUMS) {
						currentManagerState.set(false);
						System.out
								.println("========The Waiting Queue is empty for "
										+ 1.0
										* MAX_EMPTY_NUMS
										/ 12
										+ " minutes This manager is shutting down!========");
					}
				} else
					emptyTimes.set(0);
			}
		}, 0, 1000 * 5);
	}

	private void parse() {
		ExecutorService es = Executors
				.newFixedThreadPool(dsc.getWorkerNumber());
		for (int i = 0; i < dsc.getWorkerNumber(); i++) {
			es.execute(new Worker(this));
		}
	}

	public void start() {

		List<UrlWapper> urls = new ArrayList<UrlWapper>();
		for (String url : dsc.getUrlList()) {
			UrlWapper urlWapper = new UrlWapper();
			urlWapper.setDeep(1);
			urlWapper.setUrl(url);
			urls.add(urlWapper);
		}
		pr.addWaitingUrls(urls);
		parse();
	}

	// stop this manager.
	public void stop() {
		currentManagerState.set(false);
	}

	// Is this manage alive?
	public boolean isAlive() {
		return currentManagerState.get();
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

	public String getCurrentState() {
		return "[By now " + new Date() + ",we have successfully parsed "
				+ pr.getPraseUrlSize() + ";there are " + pr.getWaitingUrlSize()
				+ " still waiting for parse; and " + pr.getFailedUrlSize()
				+ " url failed to" + " parse.]";
	}

}
