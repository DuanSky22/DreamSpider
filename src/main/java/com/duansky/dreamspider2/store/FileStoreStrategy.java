package com.duansky.dreamspider2.store;

import com.duansky.dreamspider2.bean.Page;
import com.duansky.dreamspider2.config.impl.DreamSpiderContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Created by shikai.dsk on 2016/8/17.
 */
public class FileStoreStrategy implements StoreStrategy {

    Log logger = LogFactory.getLog(getClass());

    String basePath = getClass().getResource("").toString();

    FileStoreStrategy(DreamSpiderContext context){
        this.basePath = context.getConfig().getDir();
    }

    @Override
    public void store(Page page) {
        if(page == null) return;

        String url = page.getUrlWapper().getUrl();
        String content = page.getContent();
        String encoding = page.getEncoding();

        if(encoding == null || encoding.length() == 0) {
            logger.info(String.format("unknown encoding for %s", url));
            encoding = "utf-8";
        }

        store(url,content,encoding);

    }

    private void store(String url, String content,String encoding) {
        int pos=-1;

        /**
         * TODO Maybe we not only can store html file,
         * we can also store other format file.
         */

        url = url.replaceAll("[\\?/:*|<>\"]", "_");
        if((pos=url.indexOf("html"))!=-1 && pos!=url.length()-4)
            url = url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
        File file = new File(basePath +File.separator+url);
        BufferedWriter bw = null;
        try {
            if(!file.exists()) file.createNewFile();
            bw = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(file),encoding));
            bw.write(content);
            bw.flush();
            bw.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
