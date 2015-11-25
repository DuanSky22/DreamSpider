/**
* @author DuanSky
* @date 2015年11月24日 下午9:28:21
* @content 
*/
package com.duansky.dreamspider.store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.duansky.dreamspider.bean.Page;
import com.duansky.dreamspider.bean.ParseResult;
import com.duansky.dreamspider.main.Manager;

public class StorerInFile implements Runnable,Storer{
	
	private Manager manager=null;
	private String baseUrl="D://dream sp";
	private ParseResult pr=null;
	public StorerInFile(Manager manager){
		this.manager=manager;
		this.baseUrl=manager.getDsc().getDir();
		this.pr=manager.getPr();
		File file=new File(baseUrl);
		if(!file.exists()) file.mkdirs();
	}

	public void run() {
		while(manager.isAlive()){
			if(pr.getPraseUrlSize()!=0){
				Page page=pr.fetchParsedUrl();
				if(page!=null)
					store(page.getUrlWapper().getUrl(),page.getContent(),page.getEncoding());
			}
		}
	}

	public void store(String url, String content,String encoding) {
		int pos=-1;
		if((pos=url.indexOf("html"))!=-1)
		{
			if(pos==url.length()-4)
				url = url.replaceAll("[\\?/:*|<>\"]", "_");
			else
				url = url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
			File file=new File(baseUrl+File.separator+url);
			if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			try {
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),encoding));
				bw.write(content);
				bw.flush();
				bw.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	

}
