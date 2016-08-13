/**
* @author DuanSky
* @date 2015��11��17�� ����12:43:06
* @content 
*/
package com.duansky.dreamspider2.config.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ��������������ļ�����ϸ��������ȡ��URL��ַ������ģʽ�Լ��������������ȡ�
 */
public class DreamSpiderConfig {

	/**
	 *ϣ����ȡ�ĸ�ʽ
	 [webpage] ��ҳ:.html,.htm,.jsp,.asp;
	 [picture] ͼƬ:bmp,jpg,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw
	 [video]   ��Ƶ:wmv,asf,rm,rmvb,mov,avi,dat,mpg,mpeg
	 [doc]     �ĵ�:pdf,doc,docx
	 */
	List<String> formats= new LinkedList<String>();

	/**
	 * �û��Զ���ĺ�׺����չ��ʽ
	 */
	List<String> formatExtends = new LinkedList<String>();


	/**
	 * ��Ҫ��ȡ��վ��URL�б�
	 */
	private List<String> urlList = new ArrayList<String>();

	/**
	 * ��ȡģʽ��single / attach��
	 */
	private Mode mode= Mode.SINGLE;

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

	/**
	 * �Ƿ�ֻ��ȡ�û��������ӵ�ַ�ڵ���ҳ�������û���������ַ�ǣ�http://htmlparser.sourceforge.net/��
	 ��ô������ȡ����ҳ�������Ը���ַ��ͷ����ҳ��������http://htmlparser.sourceforge.net/******��ʽ��
	 ��������������ַ��ͷ����ᱻ�Թ���
	 */
	private boolean onlyInside=true;

	/**
     * ����ȡ����ҳ��Ҫ�û���������ʱ����Ҫ���������½ڵ��������û��������롣
	 */
	private boolean needAuthority=false;
	private String username="";
	private String password="";

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

	public List<String> getFormats() {
		return formats;
	}

	public void setFormats(List<String> formats) {
		this.formats = formats;
	}

	public List<String> getFormatExtends() {
		return formatExtends;
	}

	public void setFormatExtends(List<String> formatExtends) {
		this.formatExtends = formatExtends;
	}
}
