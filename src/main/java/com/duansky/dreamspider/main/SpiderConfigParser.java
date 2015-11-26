/**
 * Parse the dream spider configuration.
 * 
 * @author DuanSky
 * @date 2015年11月17日 下午12:20:41
 * @content 
 */
package com.duansky.dreamspider.main;

import java.util.Arrays;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.duansky.dreamspider.main.DreamSpiderConfig.Mode;

public class SpiderConfigParser {
	
	public static DreamSpiderConfig config=null;
	
	public static DreamSpiderConfig getConfig(){
		if(config==null)
			config=parse();
		return config;
	}
	
	@SuppressWarnings("unchecked")
	private static DreamSpiderConfig parse(){
		DreamSpiderConfig dsc=new DreamSpiderConfig();
		SAXReader saxReader=new SAXReader();
		try {
			Document doc=saxReader.read(SpiderConfigParser.class.getClassLoader().getResourceAsStream("dream-spider.xml"));
			Element root=doc.getRootElement();
			for(Iterator<Element> iter=root.elementIterator();iter.hasNext();){
				Element elem=iter.next();
				setValue(dsc,elem);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return dsc;
		}
		return dsc;
	}

	private static void setValue(DreamSpiderConfig dsc, Element elem) {
		if(elem.getName().equalsIgnoreCase("urls")){
			String urls=elem.getData().toString().trim();
			if(urls==null || urls.length()==0)
				throw new IllegalArgumentException("Please input url list.");
			dsc.setUrlList(Arrays.asList(urls.split(",")));
		}
		else if(elem.getName().equalsIgnoreCase("mode")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			else{
				data=data.toLowerCase();
				if(data.equals("single"))
					dsc.setMode(Mode.SINGLE);
				else if(data.equals("attach"))
					dsc.setMode(Mode.ATTACH);
			}
		}
		else if(elem.getName().equalsIgnoreCase("master-ip")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			else{
				if(dsc.getMode().equals(Mode.SINGLE))
					return;
				else if(dsc.getMode().equals(Mode.ATTACH))
					dsc.setMasterIp(data);
				else
					throw new IllegalArgumentException("When you choose attach mode, you should give the master ip address!"); 
			}
		}
		else if(elem.getName().equalsIgnoreCase("worker-number")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setWorkerNumber(Integer.parseInt(data));
		}
		else if(elem.getName().equalsIgnoreCase("deep")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setDeep(Integer.parseInt(data));
		}
		else if(elem.getName().equalsIgnoreCase("page-number")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setPageNubmer(Integer.parseInt(data));
		}
		else if(elem.getName().equalsIgnoreCase("dir")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setDir(data);
		}
		else if(elem.getName().equalsIgnoreCase("onlyInside")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			if(data.equalsIgnoreCase("false"))
				dsc.setOnlyInside(false);
			if(data.equalsIgnoreCase("true"))
				dsc.setOnlyInside(true);
		}
		else if(elem.getName().equalsIgnoreCase("need-authority")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			if(data.equalsIgnoreCase("false"))
				dsc.setNeedAuthority(false);
			if(data.equalsIgnoreCase("true"))
				dsc.setNeedAuthority(true);
		}
		else if(elem.getName().equalsIgnoreCase("username")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setUsername(data);
		}
		else if(elem.getName().equalsIgnoreCase("password")){
			String data=elem.getData().toString().trim();
			if(data==null || data.length()==0)
				return;
			dsc.setPassword(data);
		}
	}
}
