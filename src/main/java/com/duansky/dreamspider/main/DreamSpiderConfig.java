/**
* @author DuanSky
* @date 2015��11��17�� ����12:43:06
* @content 
*/
package com.duansky.dreamspider.main;

import java.util.ArrayList;
import java.util.List;

public class DreamSpiderConfig {

	/**
	 * ��Ҫ��ȡ��վ��URL�б�
	 */
	private List<String> urlList = new ArrayList<String>();

	/**
	 * ��ȡģʽ��single / attach��
	 */
	private Mode mode=Mode.SINGLE;

	/**
	 * ������ڵ㣺��attachģʽ�£���Ҫ�������棨��Ⱥ����IP��ַ���Ա�
	 * �ýڵ��ܹ����ӵ����漯Ⱥ�С�
	 */
	private String masterIp=null;

	/**
	 * �������Ŀ��
	 */
	private int workerNumber = 4;

	/**
	 * ��ȡҳ�����ȡ����� a��ҳ�����ӵ�b��b��ҳ�����ӵ�c����ôc��ҳ�����Ϊ3,����������ȡ��ҳ��
	 ������õ�ֵΪ-1����ʾInteger�����ֵ��
	 */
	private int deep = 20;

	/**
	 * ��ȡ��ҳ���������ܹ���Ҫ��ȡ���ٸ���ҳ��������õ�ֵΪ-1����ʾΪLong�����ֵ��
	 */
	private long pageNubmer = 1000000L;

	/**
	 * ��ȡ����ҳ����ĵ�ַ��Ĭ������±����ڱ��ش����С�
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
	 * �������ȡģʽ��
	 */
	public static enum Mode {
		/**
		 * ����ģʽ�£������浥����ȡ�û�ָ����ҳ�档������������ʵ���޹ء�
		 */
		SINGLE("single"),

		/**
		 * ����ģʽ�£����������ӵ�ĳ�����棨��Ⱥ���������ǵĴ���ȡ��������������ȡ��ҳ��
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
