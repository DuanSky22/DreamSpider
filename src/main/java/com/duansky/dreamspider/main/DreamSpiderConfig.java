/**
* @author DuanSky
* @date 2015年11月17日 下午12:43:06
* @content 
*/
package com.duansky.dreamspider.main;

import java.util.ArrayList;
import java.util.List;

public class DreamSpiderConfig {

	private List<String> urlList = new ArrayList<String>();
	private Mode mode=Mode.SINGLE;
	private String masterIp=null;
	private int workerNumber = 4;
	private int deep = 20;
	private long pageNubmer = 1000000L;
	private String dir="D://dream spider";
	private boolean onlyInside=true;
	private boolean needAuthority=false;
	private String username="";
	private String password="";
	
	public int getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(int workerNumber) {
		this.workerNumber = workerNumber;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public long getPageNubmer() {
		return pageNubmer;
	}

	public void setPageNubmer(long pageNubmer) {
		this.pageNubmer = pageNubmer;
	}

	public static enum Mode {
		SINGLE("single"), ATTACH("attach");
		private String description;

		public String getDescription() {
			return description;
		}

		private Mode(String description) {
			this.description = description;
		}
	}

	public String getMasterIp() {
		return masterIp;
	}

	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public boolean isOnlyInside() {
		return onlyInside;
	}

	public void setOnlyInside(boolean onlyInside) {
		this.onlyInside = onlyInside;
	}

	public boolean isNeedAuthority() {
		return needAuthority;
	}

	public void setNeedAuthority(boolean needAuthority) {
		this.needAuthority = needAuthority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
