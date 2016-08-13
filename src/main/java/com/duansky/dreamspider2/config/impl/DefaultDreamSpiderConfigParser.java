package com.duansky.dreamspider2.config.impl;

import com.duansky.dreamspider2.config.DreamSpiderConfigParser;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DefaultDreamSpiderConfigParser implements DreamSpiderConfigParser {

    DreamSpiderConfig config = new DreamSpiderConfig();
    Log logger = LogFactory.getLog(getClass());

    public DefaultDreamSpiderConfigParser(){
        this(DreamSpiderConfigParser.DEFAULT_CONFIG_FILE);
    }

    public DefaultDreamSpiderConfigParser(String filePath){
        InputStream input = null;
        try{

            if(filePath.contains("/"))
                input = new FileInputStream(filePath);
            else
                input = DefaultDreamSpiderConfigParser.class.getClassLoader().getResourceAsStream(filePath);

            /** here we parse this doc and get the config.**/
            if(input == null)
                throw new IllegalArgumentException("could not load config form " + filePath);

            parse(input);
            logger.info(String.format("load the dream spider from [%s] config done.", filePath));

        }catch (Exception e){
            logger.error(String.format("open %s failed. ", filePath),e);
        }

    }

    private void parse(InputStream input){
		SAXReader saxReader=new SAXReader();
		try {
			Document doc=saxReader.read(input);
			Element root=doc.getRootElement();
			for(Iterator<Element> iter = root.elementIterator(); iter.hasNext();){
				Element elem=iter.next();
				setValue(config,elem);
			}
		} catch (DocumentException e) {

		}
	}

    private void setValue(DreamSpiderConfig dsc, Element elem) {
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
                    dsc.setMode(DreamSpiderConfig.Mode.SINGLE);
                else if(data.equals("attach"))
                    dsc.setMode(DreamSpiderConfig.Mode.ATTACH);
            }
        }
        else if("formats".equalsIgnoreCase(elem.getName())){
            String formats = elem.getData().toString().trim();
            if(formats==null || formats.length()==0)
                return;
            dsc.setFormats(Arrays.asList(formats.split(",")));
        }
        else if("format-extends".equalsIgnoreCase(elem.getName())){
            String formatExtends = elem.getData().toString().trim();
            if(formatExtends==null || formatExtends.length()==0)
                return;
            dsc.setFormatExtends(Arrays.asList(formatExtends.split(",")));
        }
        else if(elem.getName().equalsIgnoreCase("master-ip")){
            String data=elem.getData().toString().trim();
            if(data==null || data.length()==0)
                return;
            else{
                if(dsc.getMode().equals(DreamSpiderConfig.Mode.SINGLE))
                    return;
                else if(dsc.getMode().equals(DreamSpiderConfig.Mode.ATTACH))
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
            int deep = Integer.parseInt(data);
            dsc.setDeep(deep < 0 ? Integer.MAX_VALUE : deep);
        }
        else if(elem.getName().equalsIgnoreCase("page-number")){
            String data=elem.getData().toString().trim();
            if(data==null || data.length()==0)
                return;
            Long pageNumber = Long.parseLong(data);
            dsc.setPageNubmer(pageNumber < 0 ? Long.MAX_VALUE : pageNumber);
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

    public DreamSpiderConfig getDreamSpiderConfig() {
        return config;
    }
}
