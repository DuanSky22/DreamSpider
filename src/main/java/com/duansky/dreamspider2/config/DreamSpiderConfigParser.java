package com.duansky.dreamspider2.config;

import com.duansky.dreamspider2.config.impl.DefaultDreamSpiderConfigParser;
import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public interface DreamSpiderConfigParser {

    String DEFAULT_CONFIG_FILE = "dream-spider.xml";

    /**
     * 使用DefaultDreamSpiderConfigParser作为默认的分析器。
     */
    DreamSpiderConfigParser INSTANCE = new DefaultDreamSpiderConfigParser(DEFAULT_CONFIG_FILE);

    DreamSpiderConfig getDreamSpiderConfig();

}
