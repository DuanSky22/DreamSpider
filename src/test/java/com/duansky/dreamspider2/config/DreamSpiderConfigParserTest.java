package com.duansky.dreamspider2.config;

import com.duansky.dreamspider2.config.impl.DefaultDreamSpiderConfigParser;
import com.duansky.dreamspider2.config.impl.DreamSpiderConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shikai.dsk on 2016/8/12.
 */
public class DreamSpiderConfigParserTest {

    DreamSpiderConfigParser dcp = null;

    @Before
    public void init(){
        dcp = new DefaultDreamSpiderConfigParser();
    }

    @Test
    public void testParser(){
        DreamSpiderConfig dsc = dcp.getDreamSpiderConfig();
        Assert.assertNotNull(dsc);
    }

}
