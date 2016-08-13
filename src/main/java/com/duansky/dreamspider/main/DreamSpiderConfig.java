/**
* @author DuanSky
* @date 2015年11月17日 下午12:43:06
* @content 
*/
package com.duansky.dreamspider.main;

import java.util.ArrayList;
import java.util.List;

public class DreamSpiderConfig {

	/**
	 * 需要爬取网站的URL列表。
	 */
	private List<String> urlList = new ArrayList<String>();

	/**
	 * 爬取模式：single / attach。
	 */
	private Mode mode=Mode.SINGLE;

	/**
	 * 主爬虫节点：在attach模式下，需要配置爬虫（集群）的IP地址，以便
	 * 该节点能够连接到爬虫集群中。
	 */
	private String masterIp=null;

	/**
	 * 爬虫的数目。
	 */
	private int workerNumber = 4;

	/**
	 * 爬取页面的深度。例如 a网页有链接到b，b网页有链接到c，那么c网页的深度为3,避免过深的爬取网页。
	 如果设置的值为-1，表示Integer的最大值。
	 */
	private int deep = 20;

	/**
	 * 爬取网页的总量。总共需要爬取多少个网页，如果设置的值为-1，表示为Long的最大值。
	 */
	private long pageNubmer = 1000000L;

	/**
	 * 爬取的网页存入的地址。默认情况下保存在本地磁盘中。
	 */
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

	/**
	 * 爬虫的爬取模式。
	 */
	public static enum Mode {
		/**
		 * 单独模式下，此爬虫单独爬取用户指定的页面。与其他的爬虫实例无关。
		 */
		SINGLE("single"),

		/**
		 * 依附模式下，此爬虫链接到某个爬虫（集群），从他们的待爬取队列中来接着爬取网页。
		 */
		ATTACH("attach");


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
